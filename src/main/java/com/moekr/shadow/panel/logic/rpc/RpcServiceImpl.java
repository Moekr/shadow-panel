package com.moekr.shadow.panel.logic.rpc;

import com.alibaba.dubbo.config.annotation.Service;
import com.moekr.shadow.panel.data.dao.NodeDAO;
import com.moekr.shadow.panel.data.dao.RecordDAO;
import com.moekr.shadow.panel.data.dao.UserDAO;
import com.moekr.shadow.panel.data.entity.Node;
import com.moekr.shadow.panel.data.entity.Port;
import com.moekr.shadow.panel.data.entity.Record;
import com.moekr.shadow.panel.data.entity.User;
import com.moekr.shadow.panel.logic.TransactionWrapper;
import com.moekr.shadow.panel.util.ToolKit;
import com.moekr.shadow.panel.util.enums.NodeStatus;
import com.moekr.shadow.rpc.RpcService;
import com.moekr.shadow.rpc.vo.*;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service(version = RpcService.VERSION)
@Component
@CommonsLog
public class RpcServiceImpl implements RpcService, NodeManager {
	private final UserDAO userDAO;
	private final NodeDAO nodeDAO;
	private final RecordDAO recordDAO;
	private final TransactionWrapper transactionWrapper;

	private final Map<Integer, ManagedNode> managedNodeMap = new HashMap<>();

	@Autowired
	public RpcServiceImpl(UserDAO userDAO, NodeDAO nodeDAO, RecordDAO recordDAO, TransactionWrapper transactionWrapper) {
		this.userDAO = userDAO;
		this.nodeDAO = nodeDAO;
		this.recordDAO = recordDAO;
		this.transactionWrapper = transactionWrapper;
	}

	@Override
	public Configuration exchange(Integer nodeId, Statistic statistic) {
		ManagedNode node = managedNodeMap.get(nodeId);
		if (node == null) return null;
		node.statusBuffer = true;
		Map<Integer, Long> trafficMap = node.trafficMap;
		statistic.getTrafficMap().entrySet().stream()
				.filter(entry -> Math.min(entry.getValue().getUpload(), entry.getValue().getDownload()) >= 0)
				.peek(entry -> trafficMap.putIfAbsent(entry.getKey(), 0L))
				.forEach(entry -> trafficMap.compute(entry.getKey(), (key, value) -> value + entry.getValue().getDownload() + entry.getValue().getUpload()));
		Configuration configuration = new Configuration();
		configuration.setAction(action(nodeId, statistic.isRunning()));
		configuration.setServers(node.servers);
		configuration.setVirtualServers(node.virtualServers);
		return configuration;
	}

	@Scheduled(cron = "0 * * * * *")
	protected void updateStatus() {
		for (ManagedNode node : managedNodeMap.values()) {
			boolean history = node.statusDeque.pollFirst();
			node.statusDeque.add(node.statusBuffer);
			if (node.statusBuffer ^ history) {
				if (node.statusBuffer) {
					node.statusCount = node.statusCount + 1;
				} else {
					node.statusCount = node.statusCount - 1;
				}
			}
			node.statusBuffer = false;
		}
	}

	@Scheduled(cron = "0 0 * * * *")
	protected void saveRecord() {
		try {
			transactionWrapper.wrap(() -> {
				LocalDateTime time = LocalDateTime.now().minusHours(1);
				for (Map.Entry<Integer, ManagedNode> nodeEntry : managedNodeMap.entrySet()) {
					Node node = nodeDAO.findById(nodeEntry.getKey());
					if (node == null) continue;
					long totalTraffic = 0;
					for (Map.Entry<Integer, Long> userEntry : nodeEntry.getValue().trafficMap.entrySet()) {
						if (userEntry.getValue() <= 0) continue;
						User user = userDAO.findByPort(userEntry.getKey());
						if (user == null) continue;
						Record record = new Record();
						record.setDate(time.toLocalDate());
						record.setHour(time.getHour());
						record.setNode(node);
						record.setUser(user);
						record.setData(userEntry.getValue());
						recordDAO.save(record);
						totalTraffic = totalTraffic + userEntry.getValue();
					}
					node.setUsedData(node.getUsedData() + totalTraffic);
					nodeDAO.save(node);
				}
			});
		} catch (Exception e) {
			log.error("Failed to save statistic data [" + e.getClass().getName() + "]: " + e.getMessage());
		} finally {
			managedNodeMap.values().forEach(node -> node.trafficMap.clear());
		}
	}

	private Action action(Integer nodeId, boolean running) {
		ManagedNode node = managedNodeMap.get(nodeId);
		if (node == null || node.action == null) return Action.NONE;
		switch (node.action) {
			case START:
				if (running) return Action.NONE;
				break;
			case STOP:
				if (!running) return Action.NONE;
				break;
			case RESTART:
				node.action = Action.START;
				return Action.RESTART;
			case NONE:
				if (running) {
					node.action = Action.START;
				} else {
					node.action = Action.STOP;
				}
				return Action.NONE;
		}
		return node.action;
	}

	@Override
	public void setPort(int nodeId, Set<Port> portSet) {
		if (portSet == null) {
			managedNodeMap.remove(nodeId);
		} else {
			ManagedNode node = managedNodeMap.computeIfAbsent(nodeId, key -> new ManagedNode());
			node.servers.clear();
			portSet.stream().map(port -> {
				Server server = new Server();
				server.setPort(port.getPort());
				server.setPassword(port.getPassword());
				server.setMethod(port.getMethod());
				server.setProtocol(port.getProtocol());
				server.setObfs(port.getObfs());
				server.setObfsParam(port.getObfsParam());
				return server;
			}).forEach(node.servers::add);
		}
	}

	@Override
	@Transactional
	public void updateAvailableUser() {
		List<User> userList = userDAO.findAll().stream().filter(user -> user.getBalance() >= 0).collect(Collectors.toList());
		for (Map.Entry<Integer, ManagedNode> entry : managedNodeMap.entrySet()) {
			Node node = nodeDAO.findById(entry.getKey());
			ManagedNode managedNode = entry.getValue();
			managedNode.virtualServers.clear();
			userList.stream()
					.filter(user -> user.getPlan().getNodeSet().contains(node))
					.forEach(user -> {
						VirtualServer virtualServer = new VirtualServer();
						virtualServer.setPort(user.getPort());
						virtualServer.setPassword(user.getPassword());
						managedNode.virtualServers.add(virtualServer);
					});
		}
	}

	@Override
	public void start(int nodeId) {
		ManagedNode node = managedNodeMap.get(nodeId);
		ToolKit.assertNotNull(node);
		node.action = Action.START;
	}

	@Override
	public void stop(int nodeId) {
		ManagedNode node = managedNodeMap.get(nodeId);
		ToolKit.assertNotNull(node);
		node.action = Action.STOP;
	}

	@Override
	public void restart(int nodeId) {
		ManagedNode node = managedNodeMap.get(nodeId);
		ToolKit.assertNotNull(node);
		node.action = Action.RESTART;
	}

	@Override
	public NodeStatus status(int nodeId) {
		ManagedNode node = managedNodeMap.get(nodeId);
		if (node == null) return NodeStatus.STOPPED;
		if (node.action == Action.NONE || !node.statusDeque.peekLast()) {
			return NodeStatus.OFFLINE;
		} else if (node.action == Action.STOP) {
			return NodeStatus.STOPPED;
		} else {
			if (node.statusCount > 25) {
				return NodeStatus.ONLINE;
			} else {
				return NodeStatus.UNSTABLE;
			}
		}
	}

	private class ManagedNode {
		final Set<Server> servers = new HashSet<>();
		final Set<VirtualServer> virtualServers = new HashSet<>();
		final Deque<Boolean> statusDeque = new LinkedList<>();
		final Map<Integer, Long> trafficMap = new HashMap<>();
		Action action = Action.NONE;
		boolean statusBuffer = false;
		int statusCount = 30;

		ManagedNode() {
			for (int i = 0; i < 30; i++) {
				statusDeque.add(true);
			}
		}
	}
}

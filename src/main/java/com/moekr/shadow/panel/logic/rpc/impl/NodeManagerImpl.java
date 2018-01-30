package com.moekr.shadow.panel.logic.rpc.impl;

import com.moekr.shadow.panel.data.dao.NodeDAO;
import com.moekr.shadow.panel.data.dao.RecordDAO;
import com.moekr.shadow.panel.data.dao.UserDAO;
import com.moekr.shadow.panel.data.entity.Node;
import com.moekr.shadow.panel.data.entity.Record;
import com.moekr.shadow.panel.data.entity.User;
import com.moekr.shadow.panel.logic.rpc.Instance;
import com.moekr.shadow.panel.logic.rpc.NodeManager;
import com.moekr.shadow.panel.logic.rpc.RpcConfiguration;
import com.moekr.shadow.panel.logic.rpc.vo.Configuration;
import com.moekr.shadow.panel.logic.rpc.vo.Statistic;
import com.moekr.shadow.panel.util.enums.NodeStatus;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@CommonsLog
public class NodeManagerImpl implements NodeManager {
	private final UserDAO userDAO;
	private final NodeDAO nodeDAO;
	private final RecordDAO recordDAO;
	private final PlatformTransactionManager transactionManager;
	private final RpcConfiguration rpcConfiguration;

	private RestTemplate restTemplate;
	private Map<Integer, InstanceImpl> instanceMap = new HashMap<>();
	private Configuration configuration;

	@Autowired
	public NodeManagerImpl(UserDAO userDAO, NodeDAO nodeDAO, RecordDAO recordDAO,
						   PlatformTransactionManager transactionManager, RpcConfiguration rpcConfiguration) {
		this.userDAO = userDAO;
		this.nodeDAO = nodeDAO;
		this.recordDAO = recordDAO;
		this.transactionManager = transactionManager;
		this.rpcConfiguration = rpcConfiguration;
	}

	@PostConstruct
	private void initial() {
		RpcConfiguration.Authorization authorization = rpcConfiguration.getAuthorization();
		AuthorizationClientHttpRequestFactory factory = new AuthorizationClientHttpRequestFactory(authorization.getUsername(), authorization.getPassword());
		factory.setConnectTimeout(rpcConfiguration.getConnectTimeout());
		factory.setReadTimeout(rpcConfiguration.getReadTimeout());
		restTemplate = new RestTemplate(factory);
	}

	@Override
	public void configure(Configuration configuration) {
		if (this.configuration == null) {
			this.configuration = configuration;
		} else if (!this.configuration.equals(configuration)) {
			this.configuration = configuration;
			for (InstanceImpl instance : instanceMap.values()) {
				instance.conf(configuration);
				if (instance.getStatus() != NodeStatus.STOPPED) {
					try {
						instance.restart();
					} catch (Throwable e) {
						log.error("Failed to restart instance [" + instance.getAddress() + "] to apply new configuration: " + e.getMessage());
					}
				}
			}
		}
	}

	@Override
	public void addInstance(int id, String address) {
		if (configuration == null) {
			throw new IllegalStateException("Node manager is not configured");
		}
		InstanceImpl instance = new InstanceImpl(address, rpcConfiguration, restTemplate, configuration);
		instanceMap.put(id, instance);
	}

	@Override
	public Instance findInstance(int id) {
		if (configuration == null) {
			throw new IllegalStateException("Node manager is not configured");
		}
		return instanceMap.get(id);
	}

	@Override
	public void removeInstance(int id) {
		if (configuration == null) {
			throw new IllegalStateException("Node manager is not configured");
		}
		Instance instance = instanceMap.remove(id);
		if (instance != null) {
			instance.stop();
		}
	}

	@Scheduled(cron = "0 * * * * *")
	private void invokeFetchStatus() {
		if (configuration == null) {
			return;
		}
		for (InstanceImpl instance : instanceMap.values()) {
			instance.fetchStatus();
		}
	}

	@Scheduled(cron = "0 0 * * * *")
	private void invokeFetchStatistic() {
		if (configuration == null) {
			return;
		}
		DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
		transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);
		LocalDateTime time = LocalDateTime.now().minusHours(1);
		try {
			for (Map.Entry<Integer, InstanceImpl> instanceEntry : instanceMap.entrySet()) {
				Statistic statistic;
				try {
					statistic = instanceEntry.getValue().fetchStatistic();
				} catch (Throwable e) {
					log.error("Failed to fetch statistic from instance [" + instanceEntry.getValue().getAddress() + "]: " + e.getMessage());
					continue;
				}
				Node node = nodeDAO.findById(instanceEntry.getKey());
				Map<Integer, Long> traffic = statistic.getTraffic();
				long used = 0;
				for (Map.Entry<Integer, Long> trafficEntry : traffic.entrySet()) {
					User user = userDAO.findByPort(trafficEntry.getKey());
					if (user != null) {
						Record record = new Record();
						record.setDate(time.toLocalDate());
						record.setHour(time.getHour());
						record.setData(trafficEntry.getValue());
						record.setUser(user);
						record.setNode(node);
						recordDAO.save(record);
						used = used + trafficEntry.getValue();
					}
				}
				node.setUsedData(node.getUsedData() + used);
				nodeDAO.save(node);
			}
			transactionManager.commit(transactionStatus);
		} catch (Throwable e) {
			log.error("Failed to save statistic data: " + e.getMessage());
			transactionManager.rollback(transactionStatus);
		}
	}
}

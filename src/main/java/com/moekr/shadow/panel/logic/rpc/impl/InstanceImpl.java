package com.moekr.shadow.panel.logic.rpc.impl;

import com.moekr.shadow.panel.logic.rpc.Instance;
import com.moekr.shadow.panel.logic.rpc.RpcConfiguration;
import com.moekr.shadow.panel.logic.rpc.vo.Configuration;
import com.moekr.shadow.panel.logic.rpc.vo.Instruction;
import com.moekr.shadow.panel.logic.rpc.vo.Statistic;
import com.moekr.shadow.panel.logic.rpc.vo.Status;
import com.moekr.shadow.panel.util.ServiceException;
import com.moekr.shadow.panel.util.ToolKit;
import com.moekr.shadow.panel.util.enums.NodeStatus;
import lombok.Getter;
import lombok.extern.apachecommons.CommonsLog;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.LinkedList;
import java.util.Queue;

@CommonsLog
public class InstanceImpl implements Instance {
	@Getter
	private final String address;
	private final RpcConfiguration rpcConfiguration;
	private final RestTemplate restTemplate;

	private Configuration configuration;
	private Status status;
	private Queue<Boolean> statusQueue = new LinkedList<>();
	private int successCount = 30;

	InstanceImpl(String address, RpcConfiguration rpcConfiguration, RestTemplate restTemplate, Configuration configuration) {
		this.address = address;
		this.rpcConfiguration = rpcConfiguration;
		this.restTemplate = restTemplate;
		this.configuration = configuration;
		for (int i = 0; i < 30; i++) {
			statusQueue.add(true);
		}
		fetchStatus();
	}

	@Override
	public void start() {
		checkError(request(new Instruction("start", null)));
	}

	@Override
	public void stop() {
		checkError(request(new Instruction("stop", null)));
	}

	@Override
	public void restart() {
		checkError(request(new Instruction("restart", null)));
	}

	@Override
	public NodeStatus getStatus() {
		if (status == null) {
			return NodeStatus.OFFLINE;
		} else if (status.isRunning()) {
			if (successCount > 25) {
				return NodeStatus.ONLINE;
			} else {
				return NodeStatus.UNSTABLE;
			}
		} else {
			return NodeStatus.STOPPED;
		}
	}

	void conf(Configuration configuration) {
		this.configuration = configuration;
		JSONObject result;
		try {
			result = request(new Instruction("conf", ToolKit.parse(configuration)));
			if (result.optInt("err", ServiceException.COMMUNICATION_FAIL) != 0) {
				log.error("Fail to issue node conf to instance [" + address + "]: " + result.optString("msg", null));
			}
		} catch (Throwable e) {
			log.error("Fail to issue node conf to instance [" + address + "]: " + e.getMessage());
		}
	}

	void fetchStatus() {
		JSONObject result;
		boolean success = false;
		for (int count = 0; count < 3; count++) {
			try {
				result = request(new Instruction("status", null));
				checkError(result);
				status = ToolKit.parse(result.optJSONObject("res").toString(), Status.class);
				log.debug(status);
				if (status.getConfiguration() == null) {
					conf(configuration);
				} else if (!status.getConfiguration().equals(configuration)) {
					conf(configuration);
					if (status.isRunning()) {
						try {
							restart();
						} catch (Throwable e) {
							log.error("Failed to restart instance [" + address + "]: " + e.getMessage());
						}
					}
				}
				success = true;
				break;
			} catch (Throwable e) {
				status = null;
				success = false;
			}
		}
		boolean preSuccess = statusQueue.poll();
		statusQueue.add(success);
		if (success ^ preSuccess) {
			if (success) {
				successCount = successCount + 1;
			} else {
				successCount = successCount - 1;
			}
		}
	}

	Statistic fetchStatistic() {
		JSONObject result = request(new Instruction("statistic", null));
		checkError(result);
		JSONObject res = result.optJSONObject("res");
		try {
			Statistic statistic = ToolKit.parse(res.toString(), Statistic.class);
			log.debug(statistic);
			return statistic;
		} catch (Throwable e) {
			throw new ServiceException(ServiceException.COMMUNICATION_FAIL);
		}
	}

	private JSONObject request(Instruction instruction) {
		try {
			URI uri = new URI(rpcConfiguration.getScheme(), null, address, rpcConfiguration.getPort(), "/rpc", null, null);
			String response = restTemplate.postForObject(uri, instruction, String.class);
			return new JSONObject(response);
		} catch (Throwable e) {
			throw new ServiceException(ServiceException.COMMUNICATION_FAIL, "Failed to execute request: " + e.getMessage());
		}
	}

	private void checkError(JSONObject result) {
		int error = result.optInt("err", ServiceException.COMMUNICATION_FAIL);
		if (error != 0) {
			String message = result.optString("msg", null);
			throw message == null ? new ServiceException(error) : new ServiceException(error, message);
		}
	}
}

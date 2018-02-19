package com.moekr.shadow.panel.logic.rpc;

import com.moekr.shadow.panel.data.entity.Port;
import com.moekr.shadow.panel.data.entity.User;
import com.moekr.shadow.panel.util.enums.NodeStatus;

import java.util.Set;

public interface NodeManager {
	void setPort(int nodeId, Set<Port> portSet);

	void setUser(Set<User> userSet);

	void start(int nodeId);

	void stop(int nodeId);

	void restart(int nodeId);

	NodeStatus status(int nodeId);
}

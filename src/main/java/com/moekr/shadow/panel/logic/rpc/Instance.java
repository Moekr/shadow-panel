package com.moekr.shadow.panel.logic.rpc;

import com.moekr.shadow.panel.util.enums.NodeStatus;

public interface Instance {
	void start();

	void stop();

	void restart();

	NodeStatus getStatus();
}

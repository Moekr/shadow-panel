package com.moekr.shadow.panel.logic.rpc;

import com.moekr.shadow.panel.logic.rpc.vo.Configuration;

public interface NodeManager {
	void configure(Configuration configuration);

	void addInstance(int id, String address);

	Instance findInstance(int id);

	void removeInstance(int id);
}

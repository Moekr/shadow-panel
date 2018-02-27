package com.moekr.shadow.panel.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Component
public class TransactionWrapper {
	private final PlatformTransactionManager transactionManager;

	@Autowired
	public TransactionWrapper(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public void wrap(Method method) throws Exception {
		DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
		transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus transactionStatus = transactionManager.getTransaction(transactionDefinition);
		try {
			method.invoke();
			transactionManager.commit(transactionStatus);
		} catch (Exception e) {
			transactionManager.rollback(transactionStatus);
			throw e;
		}
	}

	@FunctionalInterface
	public interface Method {
		void invoke() throws Exception;
	}
}

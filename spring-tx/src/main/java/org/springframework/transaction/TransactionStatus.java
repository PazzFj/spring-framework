/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.transaction;

import java.io.Flushable;

/**
 * 事务状态的表示
 */
public interface TransactionStatus extends SavepointManager, Flushable {

	/**
	 * 是否新事务
	 */
	boolean isNewTransaction();

	/**
	 * 是否保存点
	 */
	boolean hasSavepoint();

	/**
	 * 只设置事务回滚。这指示事务管理器，事务的唯一可能结果可能是回滚，作为抛出异常的替代方法，而异常反过来又会触发回滚
	 * @see org.springframework.transaction.support.TransactionCallback#doInTransaction
	 * @see org.springframework.transaction.interceptor.TransactionAttribute#rollbackOn
	 */
	void setRollbackOnly();

	/**
	 * 返回事务是否被标记为仅回滚(通过应用程序或事务基础设施)
	 */
	boolean isRollbackOnly();

	/**
	 * 将底层会话刷新到数据存储(如果适用):例如，所有受影响的Hibernate/JPA会话
	 */
	@Override
	void flush();

	/**
	 * 返回该事务是否已完成，即是否已提交或回滚
	 * @see PlatformTransactionManager#commit
	 * @see PlatformTransactionManager#rollback
	 */
	boolean isCompleted();

}

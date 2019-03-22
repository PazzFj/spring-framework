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

import java.sql.Connection;

import org.springframework.lang.Nullable;

/**
 * 定义与spring兼容的事务属性的接口。基于类似于EJB CMT属性的传播行为定义
 */
public interface TransactionDefinition {

	/**
	 * 表示当前方法必须运行在事务中。如果当前事务存在，方法将会在该事务中运行。否则，会启动一个新的事务
	 */
	int PROPAGATION_REQUIRED = 0;

	/**
	 * 表示当前方法不需要事务上下文，但是如果存在当前事务的话，那么该方法会在这个事务中运行
	 */
	int PROPAGATION_SUPPORTS = 1;

	/**
	 * 表示该方法必须在事务中运行，如果当前事务不存在，则会抛出一个异常
	 */
	int PROPAGATION_MANDATORY = 2;

	/**
	 * 表示当前方法必须运行在它自己的事务中。一个新的事务将被启动。如果存在当前事务，在该方法执行期间，当前事务会被挂起。如果使用JTATransactionManager的话，则需要访问TransactionManager
	 */
	int PROPAGATION_REQUIRES_NEW = 3;

	/**
	 * 表示该方法不应该运行在事务中。如果存在当前事务，在该方法运行期间，当前事务将被挂起。如果使用JTATransactionManager的话，则需要访问TransactionManager
	 */
	int PROPAGATION_NOT_SUPPORTED = 4;

	/**
	 * 表示当前方法不应该运行在事务上下文中。如果当前正有一个事务在运行，则会抛出异常
	 */
	int PROPAGATION_NEVER = 5;

	/**
	 * 表示如果当前已经存在一个事务，那么该方法将会在嵌套事务中运行。嵌套的事务可以独立于当前事务进行单独地提交或回滚。
	 * 如果当前事务不存在，那么其行为与PROPAGATION_REQUIRED一样。注意各厂商对这种传播行为的支持是有所差异的。
	 * 可以参考资源管理器的文档来确认它们是否支持嵌套事务
	 */
	int PROPAGATION_NESTED = 6;


	/**
	 * 默认隔离
	 */
	int ISOLATION_DEFAULT = -1;

	/**
	 *
	 */
	int ISOLATION_READ_UNCOMMITTED = Connection.TRANSACTION_READ_UNCOMMITTED;

	/**
	 *
	 */
	int ISOLATION_READ_COMMITTED = Connection.TRANSACTION_READ_COMMITTED;

	/**
	 *
	 */
	int ISOLATION_REPEATABLE_READ = Connection.TRANSACTION_REPEATABLE_READ;

	/**
	 *
	 */
	int ISOLATION_SERIALIZABLE = Connection.TRANSACTION_SERIALIZABLE;


	/**
	 *
	 */
	int TIMEOUT_DEFAULT = -1;


	/**
	 * 获取传播行为
	 */
	int getPropagationBehavior();

	/**
	 *
	 */
	int getIsolationLevel();

	/**
	 *
	 */
	int getTimeout();

	/**
	 *
	 */
	boolean isReadOnly();

	/**
	 *
	 */
	@Nullable
	String getName();

}

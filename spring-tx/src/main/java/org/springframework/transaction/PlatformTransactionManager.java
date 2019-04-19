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

import org.springframework.lang.Nullable;

/**
 * 平台事务管理器
 * 这是Spring事务基础结构中的中心接口。应用程序可以直接使用它，但它并不主要意味着API:通常，应用程序将通过AOP使用TransactionTemplate或声明性事务界定
 *
 * <p>此策略接口的默认实现为
 * {@link org.springframework.transaction.jta.JtaTransactionManager} and
 * {@link org.springframework.jdbc.datasource.DataSourceTransactionManager},  jdbc 模块
 */
public interface PlatformTransactionManager {

	/**
	 * 根据事务定义获取事务状态
	 */
	TransactionStatus getTransaction(@Nullable TransactionDefinition definition) throws TransactionException;

	/**
	 * 根据事务状态提交事务
	 */
	void commit(TransactionStatus status) throws TransactionException;

	/**
	 * 根据事务状态回滚事务
	 */
	void rollback(TransactionStatus status) throws TransactionException;

}

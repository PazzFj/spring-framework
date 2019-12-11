/*
 * Copyright 2002-2014 the original author or authors.
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

/**
 * 接口，该接口指定一个API，以编程方式以通用方式管理事务保存点。通过TransactionStatus扩展，以公开特定事务的保存点管理功能
 */
public interface SavepointManager {

	/**
	 * 创建保存点 java.sql.Savepoint
	 */
	Object createSavepoint() throws TransactionException;

	/**
	 * 回滚到给定的保存点  java.sql.Savepoint
	 */
	void rollbackToSavepoint(Object savepoint) throws TransactionException;

	/**
	 * 显式释放给定的保存点。 java.sql.Savepoint
	 */
	void releaseSavepoint(Object savepoint) throws TransactionException;

}

/*
 * Copyright 2002-2017 the original author or authors.
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

package org.springframework.transaction.interceptor;

import org.springframework.lang.Nullable;
import org.springframework.transaction.TransactionDefinition;

/**
 * 这个接口将{@code rollbackOn}规范添加到{@link TransactionDefinition}。
 * 由于自定义{@code rollbackOn}只能用AOP实现，所以该类驻留在AOP事务包中
 *
 * @see DefaultTransactionAttribute
 * @see RuleBasedTransactionAttribute
 */
public interface TransactionAttribute extends TransactionDefinition {

	/**
	 * 返回与此事务属性关联的限定符值
	 */
	@Nullable
	String getQualifier();

	/**
	 * 是否回滚到给定的异常
	 */
	boolean rollbackOn(Throwable ex);

}

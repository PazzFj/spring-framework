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

package org.aopalliance.intercept;

/**
 * 拦截在到达目标的过程中对接口的调用。它们嵌套在目标的“顶部”
 */
@FunctionalInterface
public interface MethodInterceptor extends Interceptor {

	/**
	 * 实现此方法以在调用之前和之后执行额外的处理.
	 */
	Object invoke(MethodInvocation invocation) throws Throwable;

}

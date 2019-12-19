/*
 * Copyright 2002-2012 the original author or authors.
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

package org.springframework.aop.framework.adapter;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;

import org.springframework.aop.Advisor;

/**
 * 接口允许对Spring AOP框架进行扩展，从而允许处理新的顾问和通知类型
 */
public interface AdvisorAdapter {

	/**
	 * 接口允许对Spring AOP框架进行扩展，从而允许处理新的建议器。
	 * 这个适配器理解这个advice对象吗?
	 * 使用包含此建议作为参数的Advisor函数调用{@code getInterceptors}方法是否有效
	 */
	boolean supportsAdvice(Advice advice);

	/**
	 * 返回一个AOP Alliance MethodInterceptor，将给出的建议的行为暴露给一个基于拦截的AOP框架
	 */
	MethodInterceptor getInterceptor(Advisor advisor);

}

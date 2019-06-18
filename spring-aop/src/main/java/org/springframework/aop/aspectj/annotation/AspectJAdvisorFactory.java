/*
 * Copyright 2002-2015 the original author or authors.
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

package org.springframework.aop.aspectj.annotation;

import java.lang.reflect.Method;
import java.util.List;

import org.aopalliance.aop.Advice;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.AopConfigException;
import org.springframework.lang.Nullable;

/**
 * 用于工厂的接口，这些工厂可以从使用AspectJ注释语法注释的类创建Spring AOP advisor
 */
public interface AspectJAdvisorFactory {

	/**
	 * 确定给定的类是否是一个方面
	 */
	boolean isAspect(Class<?> clazz);

	/**
	 * 给定的类是有效的AspectJ方面类吗
	 */
	void validate(Class<?> aspectClass) throws AopConfigException;

	/**
	 * 为指定方面实例上的所有At-AspectJ注释方法构建Spring AOP advisor
	 */
	List<Advisor> getAdvisors(MetadataAwareAspectInstanceFactory aspectInstanceFactory);

	/**
	 * 为给定的AspectJ建议方法构建一个Spring AOP Advisor
	 */
	@Nullable
	Advisor getAdvisor(Method candidateAdviceMethod, MetadataAwareAspectInstanceFactory aspectInstanceFactory, int declarationOrder, String aspectName);

	/**
	 * 为给定的AspectJ通知方法构建Spring AOP通知.
	 */
	@Nullable
	Advice getAdvice(Method candidateAdviceMethod, AspectJExpressionPointcut expressionPointcut,
			MetadataAwareAspectInstanceFactory aspectInstanceFactory, int declarationOrder, String aspectName);

}

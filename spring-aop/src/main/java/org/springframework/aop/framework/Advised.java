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

package org.springframework.aop.framework;

import org.aopalliance.aop.Advice;

import org.springframework.aop.Advisor;
import org.springframework.aop.TargetClassAware;
import org.springframework.aop.TargetSource;

/**
 * 接口，由持有AOP代理工厂配置的类实现。此配置包括拦截器和其他通知、顾问和代理接口
 *
 * <p>从Spring获得的任何AOP代理都可以转换到这个接口，以允许对其AOP通知进行操作
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @since 13.03.2003
 * @see org.springframework.aop.framework.AdvisedSupport
 */
public interface Advised extends TargetClassAware {

	/**
	 * 是否冻结
	 */
	boolean isFrozen();

	/**
	 * 是否代理目标class  (默认false)
	 */
	boolean isProxyTargetClass();

	/**
	 * 代理对象的接口数组
	 */
	Class<?>[] getProxiedInterfaces();

	/**
	 * 确定是否代理给定的接口
	 */
	boolean isInterfaceProxied(Class<?> intf);

	/**
	 * 设置代理目标对象
	 */
	void setTargetSource(TargetSource targetSource);

	/**
	 * Return the {@code TargetSource} used by this {@code Advised} object.
	 */
	TargetSource getTargetSource();

	/**
	 * 设置是否暴露代理 (默认false)
	 */
	void setExposeProxy(boolean exposeProxy);

	/**
	 * 是否暴露代理 (默认false)
	 */
	boolean isExposeProxy();

	/**
	 * 设置此代理配置是否预先筛选，使其仅包含适用的advisor工具(与此代理的目标类匹配)
	 */
	void setPreFiltered(boolean preFiltered);

	/**
	 * 返回是否预先筛选此代理配置，使其仅包含适用的advisor工具(与此代理的目标类匹配)
	 */
	boolean isPreFiltered();

	/**
	 * 返回应用于此代理的顾问
	 */
	Advisor[] getAdvisors();

	/**
	 * 添加顾问 (Advisor)
	 */
	void addAdvisor(Advisor advisor) throws AopConfigException;

	/**
	 * 指定下标添加顾问 (Advisor)
	 */
	void addAdvisor(int pos, Advisor advisor) throws AopConfigException;

	/**
	 * 清除顾问 (Advisor)
	 */
	boolean removeAdvisor(Advisor advisor);

	/**
	 * 清除下标所在的顾问 (Advisor)
	 */
	void removeAdvisor(int index) throws AopConfigException;

	/**
	 * 根据Advisor获取所在下标
	 */
	int indexOf(Advisor advisor);

	/**
	 * 替换Advisor
	 */
	boolean replaceAdvisor(Advisor a, Advisor b) throws AopConfigException;

	/**
	 * 添加通知 (Advice)
	 */
	void addAdvice(Advice advice) throws AopConfigException;

	/**
	 * 根据下标添加通知 (Advice)
	 */
	void addAdvice(int pos, Advice advice) throws AopConfigException;

	/**
	 * 清除通知
	 */
	boolean removeAdvice(Advice advice);

	/**
	 * 通知所在的下标
	 */
	int indexOf(Advice advice);

	/**
	 * 返回AOP代理的等效值
	 * ProxyConfig
	 */
	String toProxyConfigString();

}

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

package org.springframework.beans.factory.config;

import org.springframework.lang.Nullable;

/**
 * 单例Bean注册 接口
 */
public interface SingletonBeanRegistry {

	/**
	 * 在bean注册表中以给定的bean名称将给定的现有对象注册为单例对象
	 */
	void registerSingleton(String beanName, Object singletonObject);

	/**
	 * 返回单例beanName映射的对象
	 */
	@Nullable
	Object getSingleton(String beanName);

	/**
	 * 检测是否包含该beanName
	 */
	boolean containsSingleton(String beanName);

	/**
	 * 返回所有单例BeanName的数组
	 */
	String[] getSingletonNames();

	/**
	 * 返回单例BeanName的数量
	 */
	int getSingletonCount();

	/**
	 * 返回此注册中心(用于外部协作者)使用的单例互斥锁
	 */
	Object getSingletonMutex();

}

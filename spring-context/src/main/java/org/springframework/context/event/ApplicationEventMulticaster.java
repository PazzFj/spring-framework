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

package org.springframework.context.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.ResolvableType;
import org.springframework.lang.Nullable;

/**
 * 应用事件广播器 interface
 */
public interface ApplicationEventMulticaster {

	/**
	 * 添加应用监听器
	 */
	void addApplicationListener(ApplicationListener<?> listener);

	/**
	 * 添加应用监听器bean名称
	 */
	void addApplicationListenerBean(String listenerBeanName);

	/**
	 * 清除应用监听器
	 */
	void removeApplicationListener(ApplicationListener<?> listener);

	/**
	 * 清除应用监听器bean名称
	 */
	void removeApplicationListenerBean(String listenerBeanName);

	/**
	 * 清除所有监听器
	 */
	void removeAllListeners();

	/**
	 * 广播事件
	 */
	void multicastEvent(ApplicationEvent event);

	/**
	 * 广播事件
	 */
	void multicastEvent(ApplicationEvent event, @Nullable ResolvableType eventType);

}

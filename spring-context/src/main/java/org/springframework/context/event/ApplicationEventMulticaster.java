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
 * 接口由可以管理多个 ApplicationListener 对象并向其发布事件的对象实现。
 */
public interface ApplicationEventMulticaster {

	/**
	 * 添加一个侦听器来接收所有事件的通知。
	 */
	void addApplicationListener(ApplicationListener<?> listener);

	/**
	 * 添加一个侦听器bean来接收所有事件的通知。
	 */
	void addApplicationListenerBean(String listenerBeanName);

	/**
	 * Remove a listener from the notification list.
	 * @param listener the listener to remove
	 */
	void removeApplicationListener(ApplicationListener<?> listener);

	/**
	 * Remove a listener bean from the notification list.
	 * @param listenerBeanName the name of the listener bean to add
	 */
	void removeApplicationListenerBean(String listenerBeanName);

	/**
	 * Remove all listeners registered with this multicaster.
	 * <p>After a remove call, the multicaster will perform no action
	 * on event notification until new listeners are being registered.
	 */
	void removeAllListeners();

	/**
	 * 启动应用事件
	 */
	void multicastEvent(ApplicationEvent event);

	/**
	 * 将给定的应用程序事件多播到适当的侦听器
	 */
	void multicastEvent(ApplicationEvent event, @Nullable ResolvableType eventType);

}

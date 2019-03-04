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

import java.util.LinkedList;
import java.util.List;

import org.springframework.util.Assert;

/**
 * 代理工厂的基类。
 * 提供对可配置的AopProxyFactory的方便访问。
 */
@SuppressWarnings("serial")
public class ProxyCreatorSupport extends AdvisedSupport {

	//Aop代理工厂
	private AopProxyFactory aopProxyFactory;

	//AdvisedSupportListener 监听器集合
	private final List<AdvisedSupportListener> listeners = new LinkedList<>();

	/** 在创建第一个AOP代理时设置为true */
	private boolean active = false;


	/**
	 * 创建一个新的ProxyCreatorSupport实例
	 */
	public ProxyCreatorSupport() {
		this.aopProxyFactory = new DefaultAopProxyFactory();
	}

	/**
	 * 创建一个新的ProxyCreatorSupport实例
	 */
	public ProxyCreatorSupport(AopProxyFactory aopProxyFactory) {
		Assert.notNull(aopProxyFactory, "AopProxyFactory must not be null");
		this.aopProxyFactory = aopProxyFactory;
	}


	/**
	 * 定制AopProxyFactory，允许在不改变核心框架的情况下加入不同的策略
	 * <p>默认值是{@link DefaultAopProxyFactory}，根据需要使用动态JDK代理或CGLIB代理。
	 */
	public void setAopProxyFactory(AopProxyFactory aopProxyFactory) {
		Assert.notNull(aopProxyFactory, "AopProxyFactory must not be null");
		this.aopProxyFactory = aopProxyFactory;
	}

	/**
	 * 返回这个ProxyConfig使用的AopProxyFactory
	 */
	public AopProxyFactory getAopProxyFactory() {
		return this.aopProxyFactory;
	}

	/**
	 * 将给定的AdvisedSupportListener添加到此代理配置中
	 */
	public void addListener(AdvisedSupportListener listener) {
		Assert.notNull(listener, "AdvisedSupportListener must not be null");
		this.listeners.add(listener);
	}

	/**
	 * 从这个代理配置中删除给定的AdvisedSupportListener
	 */
	public void removeListener(AdvisedSupportListener listener) {
		Assert.notNull(listener, "AdvisedSupportListener must not be null");
		this.listeners.remove(listener);
	}


	/**
	 * 子类应该调用它来获得一个新的AOP代理。它们应该用{@code this}作为参数创建AOP代理
	 */
	protected final synchronized AopProxy createAopProxy() {
		if (!this.active) {
			activate();
		}
		return getAopProxyFactory().createAopProxy(this);
	}

	/**
	 * 激活此代理配置
	 * @see AdvisedSupportListener#activated
	 */
	private void activate() {
		this.active = true;
		for (AdvisedSupportListener listener : this.listeners) {
			listener.activated(this);
		}
	}

	/**
	 * 将通知更改事件传播给所有AdvisedSupportListeners
	 */
	@Override
	protected void adviceChanged() {
		super.adviceChanged();
		synchronized (this) {
			if (this.active) {
				for (AdvisedSupportListener listener : this.listeners) {
					listener.adviceChanged(this);
				}
			}
		}
	}

	/**
	 * 子类可以调用它来检查是否已经创建了AOP代理
	 */
	protected final synchronized boolean isActive() {
		return this.active;
	}

}

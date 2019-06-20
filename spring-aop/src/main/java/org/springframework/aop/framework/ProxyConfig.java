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

import java.io.Serializable;

import org.springframework.util.Assert;

/**
 * 代理配置类 ProxyConfig
 */
public class ProxyConfig implements Serializable {

	private static final long serialVersionUID = -8409359707199703185L;

	// 代理目标class
	private boolean proxyTargetClass = false;

	// 优化
	private boolean optimize = false;

	// 不透明的
	boolean opaque = false;

	// 暴露代理
	boolean exposeProxy = false;

	// 冻结代理
	private boolean frozen = false;


	public void setProxyTargetClass(boolean proxyTargetClass) {
		this.proxyTargetClass = proxyTargetClass;
	}

	public boolean isProxyTargetClass() {
		return this.proxyTargetClass;
	}

	public void setOptimize(boolean optimize) {
		this.optimize = optimize;
	}

	public boolean isOptimize() {
		return this.optimize;
	}

	public void setOpaque(boolean opaque) {
		this.opaque = opaque;
	}

	public boolean isOpaque() {
		return this.opaque;
	}

	public void setExposeProxy(boolean exposeProxy) {
		this.exposeProxy = exposeProxy;
	}

	public boolean isExposeProxy() {
		return this.exposeProxy;
	}

	public void setFrozen(boolean frozen) {
		this.frozen = frozen;
	}

	public boolean isFrozen() {
		return this.frozen;
	}


	/**
	 * 从另一个配置对象复制配置
	 */
	public void copyFrom(ProxyConfig other) {
		Assert.notNull(other, "Other ProxyConfig object must not be null");
		this.proxyTargetClass = other.proxyTargetClass;
		this.optimize = other.optimize;
		this.exposeProxy = other.exposeProxy;
		this.frozen = other.frozen;
		this.opaque = other.opaque;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("proxyTargetClass=").append(this.proxyTargetClass).append("; ");
		sb.append("optimize=").append(this.optimize).append("; ");
		sb.append("opaque=").append(this.opaque).append("; ");
		sb.append("exposeProxy=").append(this.exposeProxy).append("; ");
		sb.append("frozen=").append(this.frozen);
		return sb.toString();
	}

}

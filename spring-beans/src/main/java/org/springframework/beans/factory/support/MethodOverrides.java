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

package org.springframework.beans.factory.support;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.lang.Nullable;

/**
 * 方法覆盖集合，确定Spring IoC容器在运行时将覆盖托管对象上的哪些方法(如果有的话)。
 */
public class MethodOverrides {

	private final Set<MethodOverride> overrides = Collections.synchronizedSet(new LinkedHashSet<>(2));

	private volatile boolean modified = false;	//已修改标志


	public MethodOverrides() {
	}

	/**
	 * 深拷贝构造函数
	 */
	public MethodOverrides(MethodOverrides other) {
		addOverrides(other);
	}


	public void addOverrides(@Nullable MethodOverrides other) {
		if (other != null) {
			this.modified = true;
			this.overrides.addAll(other.overrides);
		}
	}

	public void addOverride(MethodOverride override) {
		this.modified = true;
		this.overrides.add(override);
	}

	public Set<MethodOverride> getOverrides() {
		this.modified = true;
		return this.overrides;
	}

	public boolean isEmpty() {
		return (!this.modified || this.overrides.isEmpty());
	}

	@Nullable
	public MethodOverride getOverride(Method method) {
		if (!this.modified) {
			return null;
		}
		synchronized (this.overrides) {
			MethodOverride match = null;
			for (MethodOverride candidate : this.overrides) {
				if (candidate.matches(method)) {
					match = candidate;
				}
			}
			return match;
		}
	}


	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MethodOverrides)) {
			return false;
		}
		MethodOverrides that = (MethodOverrides) other;
		return this.overrides.equals(that.overrides);

	}

	@Override
	public int hashCode() {
		return this.overrides.hashCode();
	}

}

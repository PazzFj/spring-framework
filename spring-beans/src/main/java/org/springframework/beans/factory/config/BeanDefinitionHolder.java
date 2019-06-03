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

package org.springframework.beans.factory.config;

import org.springframework.beans.BeanMetadataElement;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 *
 */
public class BeanDefinitionHolder implements BeanMetadataElement {

	private final BeanDefinition beanDefinition;

	private final String beanName;

	@Nullable
	private final String[] aliases;

	public BeanDefinitionHolder(BeanDefinition beanDefinition, String beanName) {
		this(beanDefinition, beanName, null);
	}

	public BeanDefinitionHolder(BeanDefinition beanDefinition, String beanName, @Nullable String[] aliases) {
		Assert.notNull(beanDefinition, "BeanDefinition must not be null");
		Assert.notNull(beanName, "Bean name must not be null");
		this.beanDefinition = beanDefinition;
		this.beanName = beanName;
		this.aliases = aliases;
	}

	public BeanDefinitionHolder(BeanDefinitionHolder beanDefinitionHolder) {
		Assert.notNull(beanDefinitionHolder, "BeanDefinitionHolder must not be null");
		this.beanDefinition = beanDefinitionHolder.getBeanDefinition();
		this.beanName = beanDefinitionHolder.getBeanName();
		this.aliases = beanDefinitionHolder.getAliases();
	}


	public BeanDefinition getBeanDefinition() {
		return this.beanDefinition;
	}

	public String getBeanName() {
		return this.beanName;
	}

	@Nullable
	public String[] getAliases() {
		return this.aliases;
	}

	@Override
	@Nullable
	public Object getSource() {
		return this.beanDefinition.getSource();
	}

	public boolean matchesName(@Nullable String candidateName) {
		return (candidateName != null && (candidateName.equals(this.beanName) ||
				candidateName.equals(BeanFactoryUtils.transformedBeanName(this.beanName)) ||
				ObjectUtils.containsElement(this.aliases, candidateName)));
	}


	public String getShortDescription() {
		StringBuilder sb = new StringBuilder();
		sb.append("Bean definition with name '").append(this.beanName).append("'");
		if (this.aliases != null) {
			sb.append(" and aliases [").append(StringUtils.arrayToCommaDelimitedString(this.aliases)).append("]");
		}
		return sb.toString();
	}

	public String getLongDescription() {
		StringBuilder sb = new StringBuilder(getShortDescription());
		sb.append(": ").append(this.beanDefinition);
		return sb.toString();
	}

	@Override
	public String toString() {
		return getLongDescription();
	}


	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof BeanDefinitionHolder)) {
			return false;
		}
		BeanDefinitionHolder otherHolder = (BeanDefinitionHolder) other;
		return this.beanDefinition.equals(otherHolder.beanDefinition) &&
				this.beanName.equals(otherHolder.beanName) &&
				ObjectUtils.nullSafeEquals(this.aliases, otherHolder.aliases);
	}

	@Override
	public int hashCode() {
		int hashCode = this.beanDefinition.hashCode();
		hashCode = 29 * hashCode + this.beanName.hashCode();
		hashCode = 29 * hashCode + ObjectUtils.nullSafeHashCode(this.aliases);
		return hashCode;
	}

}

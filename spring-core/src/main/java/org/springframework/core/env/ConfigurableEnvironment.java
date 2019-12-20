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

package org.springframework.core.env;

import java.util.Map;

/**
 * 自动装配环境接口：继承 Environment & 自动装配属性解析器.
 * 配置接口将由大多数（如果不是所有的话）{@link Environment}类型实现提供设置活动和默认配置文件
 * 以及操作基础属性源的工具允许客户端通过{@link ConfigurablePropertyResolver}超级界面设置和验证所需的属性、自定义转换服务等
 */
public interface ConfigurableEnvironment extends Environment, ConfigurablePropertyResolver {

	/**
	 * 先clean在添加
	 */
	void setActiveProfiles(String... profiles);

	/**
	 * 直接添加
	 */
	void addActiveProfile(String profile);

	/**
	 * 先clean在添加
	 */
	void setDefaultProfiles(String... profiles);

	/**
	 */
	MutablePropertySources getPropertySources();

	/**
	 */
	Map<String, Object> getSystemProperties();

	/**
	 */
	Map<String, Object> getSystemEnvironment();

	/**
	 */
	void merge(ConfigurableEnvironment parent);

}

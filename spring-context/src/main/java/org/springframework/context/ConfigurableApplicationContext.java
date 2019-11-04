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

package org.springframework.context;

import java.io.Closeable;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ProtocolResolver;
import org.springframework.lang.Nullable;

/**
 * 大多数(如果不是所有)应用程序上下文都要实现SPI接口
 * <p>
 * 除了在{@link org.springframework.context.ApplicationContext}接口中配置应用程序上下文客户端方法外，还提供配置应用程序上下文的工具。
 */
public interface ConfigurableApplicationContext extends ApplicationContext, Lifecycle, Closeable {

	// 这些字符中的任意数量都被认为是单个字符串值中多个上下文配置路径之间的分隔符
	String CONFIG_LOCATION_DELIMITERS = ",; \t\n";

	// 工厂中ConversionService bean的名称。如果没有提供，则应用默认的转换规则。
	String CONVERSION_SERVICE_BEAN_NAME = "conversionService";

	// 工厂中的LoadTimeWeaver bean的名称。如果提供了这样一个bean，
	// 上下文将使用一个临时类加载器进行类型匹配，以便LoadTimeWeaver能够处理所有实际的bean类。
	String LOAD_TIME_WEAVER_BEAN_NAME = "loadTimeWeaver";

	// 工厂中{@link Environment} bean的名称。
	String ENVIRONMENT_BEAN_NAME = "environment";

	// 工厂中的系统属性bean的名称。
	String SYSTEM_PROPERTIES_BEAN_NAME = "systemProperties";

	// 工厂中的系统环境bean的名称。
	String SYSTEM_ENVIRONMENT_BEAN_NAME = "systemEnvironment";


	void setId(String id);

	void setParent(@Nullable ApplicationContext parent);

	void setEnvironment(ConfigurableEnvironment environment);

	@Override
	ConfigurableEnvironment getEnvironment();

	void addBeanFactoryPostProcessor(BeanFactoryPostProcessor postProcessor);

	void addApplicationListener(ApplicationListener<?> listener);

	void addProtocolResolver(ProtocolResolver resolver);

	// 刷新
	void refresh() throws BeansException, IllegalStateException;

	void registerShutdownHook();

	@Override
	void close();

	boolean isActive();

	ConfigurableListableBeanFactory getBeanFactory() throws IllegalStateException;

}

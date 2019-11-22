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

package org.springframework.context.support;

import java.io.IOException;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.ResourceEntityResolver;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.lang.Nullable;

/**
 * 抽象Xml应用上下文
 * @see org.springframework.beans.factory.xml.XmlBeanDefinitionReader
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableConfigApplicationContext {

	private boolean validating = true;

	public AbstractXmlApplicationContext() {
	}

	public AbstractXmlApplicationContext(@Nullable ApplicationContext parent) {
		super(parent);
	}


	/**
	 * 设置是否使用XML验证。默认值是{@code true}。
	 */
	public void setValidating(boolean validating) {
		this.validating = validating;
	}


	/**
	 * 加载BeanDefinition
	 * 1、创建XmlBeanDefinitionReader xml读取器
	 * 2、配置环境
	 * 3、配置资源加载器
	 * 4、配置是实体解析器
	 * 5、设置xmlBeanDefinitionReader校验
	 * 6、使用XmlBeanDefinitionReader 加载Resource[]
	 */
	@Override
	protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeansException, IOException {
		// Create a new XmlBeanDefinitionReader for the given BeanFactory.
		XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);

		// Configure the bean definition reader with this context's
		// resource loading environment.
		// 使用此上下文的资源加载环境配置bean定义阅读器 this.getEnvironment()父类方法
		beanDefinitionReader.setEnvironment(this.getEnvironment());
		// 设置资源加载器
		beanDefinitionReader.setResourceLoader(this);
		// 设置用于解析的SAX实体解析器。 ResourceEntityResolver
		beanDefinitionReader.setEntityResolver(new ResourceEntityResolver(this));

		// 允许子类提供自定义的读取器初始化，
		// 然后继续实际加载bean定义。
		// 例子：
		// beanDefinitionReader.setValidating(this.validating);
		initBeanDefinitionReader(beanDefinitionReader);
		// 使用XmlBeanDefinitionReader 加载BeanDefinition
		loadBeanDefinitions(beanDefinitionReader);
	}

	/**
	 * 设置 XmlBeanDefinitionReader 是否验证
	 */
	protected void initBeanDefinitionReader(XmlBeanDefinitionReader reader) {
		reader.setValidating(this.validating);
	}

	/**
	 * 使用给定的 XmlBeanDefinitionReader 加载 BeanDefinition
	 */
	protected void loadBeanDefinitions(XmlBeanDefinitionReader reader) throws BeansException, IOException {
		Resource[] configResources = getConfigResources();
		if (configResources != null) {
			reader.loadBeanDefinitions(configResources);
		}
		//如果配置资源路径不为空, 就使用XmlBeanDefinitionReader解析
		String[] configLocations = getConfigLocations();
		if (configLocations != null) {
			reader.loadBeanDefinitions(configLocations);
		}
	}

	/**
	 * Return an array of Resource objects, referring to the XML bean definition
	 * files that this context should be built with.
	 * <p>The default implementation returns {@code null}. Subclasses can override
	 * this to provide pre-built Resource objects rather than location Strings.
	 * @return an array of Resource objects, or {@code null} if none
	 * @see #getConfigLocations()
	 */
	@Nullable
	protected Resource[] getConfigResources() {
		return null;
	}

}

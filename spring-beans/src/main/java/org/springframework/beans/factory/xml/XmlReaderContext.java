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

package org.springframework.beans.factory.xml;

import java.io.StringReader;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.parsing.ProblemReporter;
import org.springframework.beans.factory.parsing.ReaderContext;
import org.springframework.beans.factory.parsing.ReaderEventListener;
import org.springframework.beans.factory.parsing.SourceExtractor;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.lang.Nullable;

/**
 * xml读取上下文, 封装了BeanDefinitionReader读取器、命名空间管理器
 */
public class XmlReaderContext extends ReaderContext {

	// BeanDefinition读取器
	private final XmlBeanDefinitionReader reader;

	/**
	 * 命名空间管理解析器  <xmlns:context="http://www.springframework.org/schema/context">
	 */
	private final NamespaceHandlerResolver namespaceHandlerResolver;


	/**
	 * 构造 XmlReaderContext
	 */
	public XmlReaderContext(
			Resource resource, ProblemReporter problemReporter,
			ReaderEventListener eventListener, SourceExtractor sourceExtractor,
			XmlBeanDefinitionReader reader, NamespaceHandlerResolver namespaceHandlerResolver) {

		super(resource, problemReporter, eventListener, sourceExtractor);
		this.reader = reader;
		this.namespaceHandlerResolver = namespaceHandlerResolver;
	}


	/**
	 *返回正在使用的XML bean定义读取器
	 */
	public final XmlBeanDefinitionReader getReader() {
		return this.reader;
	}

	/**
	 * 返回要使用的bean定义注册
	 */
	public final BeanDefinitionRegistry getRegistry() {
		return this.reader.getRegistry();
	}

	/**
	 * 返回要使用的资源加载器
	 */
	@Nullable
	public final ResourceLoader getResourceLoader() {
		return this.reader.getResourceLoader();
	}

	/**
	 * 返回要使用的bean类装入器
	 */
	@Nullable
	public final ClassLoader getBeanClassLoader() {
		return this.reader.getBeanClassLoader();
	}

	/**
	 * 返回要使用的环境
	 */
	public final Environment getEnvironment() {
		return this.reader.getEnvironment();
	}

	/**
	 * 返回名称空间解析器
	 */
	public final NamespaceHandlerResolver getNamespaceHandlerResolver() {
		return this.namespaceHandlerResolver;
	}


	// Convenience methods to delegate to

	/**
	 * 为给定的bean定义调用bean名称生成器
	 */
	public String generateBeanName(BeanDefinition beanDefinition) {
		return this.reader.getBeanNameGenerator().generateBeanName(beanDefinition, getRegistry());
	}

	/**
	 * 生成beanName, 并注册BeanDefinition到中心
	 */
	public String registerWithGeneratedName(BeanDefinition beanDefinition) {
		String generatedName = generateBeanName(beanDefinition);
		getRegistry().registerBeanDefinition(generatedName, beanDefinition);
		return generatedName;
	}

	/**
	 * 从给定的字符串读取XML文档
	 */
	public Document readDocumentFromString(String documentContent) {
		InputSource is = new InputSource(new StringReader(documentContent));
		try {
			return this.reader.doLoadDocument(is, getResource());
		}
		catch (Exception ex) {
			throw new BeanDefinitionStoreException("Failed to read XML document", ex);
		}
	}

}

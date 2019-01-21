/*
 * Copyright 2002-2015 the original author or authors.
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

import org.w3c.dom.Document;

import org.springframework.beans.factory.BeanDefinitionStoreException;

/**
 * 用于解析包含Spring bean定义的XML文档的SPI
 * 由{@link XmlBeanDefinitionReader}用于实际解析DOM文档
 *
 * <p>实例化每个文档解析:实现可以在执行{@code registerbeandefinition}方法&mdash期间，在实例变量中保存状态;例如，为文档中所有bean定义定义的全局设置。
 *
 * @see XmlBeanDefinitionReader#setDocumentReaderClass
 */
public interface BeanDefinitionDocumentReader {

	/**
	 * 从给定的DOM文档中读取bean定义，并在给定的reader上下文中向注册中心注册它们。
	 *
	 * @param doc the DOM document
	 * @param readerContext the current context of the reader
	 * (includes the target registry and the resource being parsed)
	 * @throws BeanDefinitionStoreException in case of parsing errors
	 */
	void registerBeanDefinitions(Document doc, XmlReaderContext readerContext)
			throws BeanDefinitionStoreException;

}

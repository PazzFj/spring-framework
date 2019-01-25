/*
 * Copyright 2002-2011 the original author or authors.
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

import org.w3c.dom.Element;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.lang.Nullable;

/**
 * 接口，由{@link DefaultBeanDefinitionDocumentReader}用于处理自定义的顶级标记(直接位于{@code <beans/>}之下)
 *
 * <p>实现可以自由地将自定义标记中的元数据转换为尽可能多的元数据
 * {@link BeanDefinition BeanDefinition}
 *
 * <p>解析器从自定义标记所在的名称空间的关联的{@link NamespaceHandler}中定位一个{@link BeanDefinitionParser}
 */
public interface BeanDefinitionParser {

	/**
	 * 解析指定的{@link元素}并使用嵌入在提供的{@link ParserContext}中的
	 * {@link org.springframework.beans.factory.xml.ParserContext#getRegistry() BeanDefinitionRegistry}
	 * 注册生成的{@link BeanDefinition BeanDefinition(s)}。
	 */
	@Nullable
	BeanDefinition parse(Element element, ParserContext parserContext);

}

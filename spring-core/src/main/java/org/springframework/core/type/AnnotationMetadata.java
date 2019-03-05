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

package org.springframework.core.type;

import java.util.Set;

/**
 * 接口，该接口定义对特定类的注解的抽象访问，其形式不需要加载该类
 *
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Phillip Webb
 * @author Sam Brannen
 * @since 2.5
 * @see StandardAnnotationMetadata
 * @see org.springframework.core.type.classreading.MetadataReader#getAnnotationMetadata()
 * @see AnnotatedTypeMetadata
 */
public interface AnnotationMetadata extends ClassMetadata, AnnotatedTypeMetadata {

	/**
	 * 获取所有注解类型
	 */
	Set<String> getAnnotationTypes();

	/**
	 * 获取所有<em>的元注释类型的完全限定类名，这些元注释类型在基础类的给定注释类型上呈现</em>
	 * @return 元注释类型名称，如果没有找到，则为空集
	 */
	Set<String> getMetaAnnotationTypes(String annotationName);

	/**
	 * 是否注解
	 */
	boolean hasAnnotation(String annotationName);

	/**
	 *
	 */
	boolean hasMetaAnnotation(String metaAnnotationName);

	/**
	 *
	 */
	boolean hasAnnotatedMethods(String annotationName);

	/**
	 *
	 */
	Set<MethodMetadata> getAnnotatedMethods(String annotationName);

}

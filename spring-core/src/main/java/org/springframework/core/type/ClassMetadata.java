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

import org.springframework.lang.Nullable;

/**
 * 接口，该接口以不需要加载该类的形式定义特定类的抽象元数据
 */
public interface ClassMetadata {

	/**
	 * 获取类名
	 */
	String getClassName();

	/**
	 * 是否接口
	 */
	boolean isInterface();

	/**
	 * 是否注解
	 */
	boolean isAnnotation();

	/**
	 * 是否抽象类
	 */
	boolean isAbstract();

	/**
	 * 是否具体类
	 */
	boolean isConcrete();

	/**
	 * 是否final标记类
	 */
	boolean isFinal();

	/**
	 * 确定基础类是否独立，即它是顶级类还是可以独立于封闭类构造的嵌套类(静态内部类)
	 */
	boolean isIndependent();

	/**
	 * Return whether the underlying class is declared within an enclosing
	 * class (i.e. the underlying class is an inner/nested class or a
	 * local class within a method).
	 * <p>If this method returns {@code false}, then the underlying
	 * class is a top-level class.
	 */
	boolean hasEnclosingClass();

	/**
	 * Return the name of the enclosing class of the underlying class,
	 * or {@code null} if the underlying class is a top-level class.
	 */
	@Nullable
	String getEnclosingClassName();

	/**
	 * 是否有超类
	 */
	boolean hasSuperClass();

	/**
	 * 超类的类名
	 */
	@Nullable
	String getSuperClassName();

	/**
	 * 接口数组
	 */
	String[] getInterfaceNames();

	/**
	 * Return the names of all classes declared as members of the class represented by
	 * this ClassMetadata object. This includes public, protected, default (package)
	 * access, and private classes and interfaces declared by the class, but excludes
	 * inherited classes and interfaces. An empty array is returned if no member classes
	 * or interfaces exist.
	 * @since 3.1
	 */
	String[] getMemberClassNames();

}

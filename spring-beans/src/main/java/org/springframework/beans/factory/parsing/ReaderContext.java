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

package org.springframework.beans.factory.parsing;

import org.springframework.core.io.Resource;
import org.springframework.lang.Nullable;

/**
 * 在bean定义读取过程中传递的上下文，它封装所有相关的配置和状态
 */
public class ReaderContext {

	//资源
	private final Resource resource;
	//问题记录者
	private final ProblemReporter problemReporter;
	//读取事件监听器
	private final ReaderEventListener eventListener;
	//资源提取器
	private final SourceExtractor sourceExtractor;


	/**
	 * 构造一个ReaderContext
	 */
	public ReaderContext(Resource resource, ProblemReporter problemReporter,
			ReaderEventListener eventListener, SourceExtractor sourceExtractor) {

		this.resource = resource;
		this.problemReporter = problemReporter;
		this.eventListener = eventListener;
		this.sourceExtractor = sourceExtractor;
	}

	public final Resource getResource() {
		return this.resource;
	}


	// Errors and warnings

	/**
	 * Raise a fatal error.
	 */
	public void fatal(String message, @Nullable Object source) {
		fatal(message, source, null, null);
	}

	/**
	 * Raise a fatal error.
	 */
	public void fatal(String message, @Nullable Object source, @Nullable Throwable cause) {
		fatal(message, source, null, cause);
	}

	/**
	 * Raise a fatal error.
	 */
	public void fatal(String message, @Nullable Object source, @Nullable ParseState parseState) {
		fatal(message, source, parseState, null);
	}

	/**
	 * Raise a fatal error.
	 */
	public void fatal(String message, @Nullable Object source, @Nullable ParseState parseState, @Nullable Throwable cause) {
		Location location = new Location(getResource(), source);
		this.problemReporter.fatal(new Problem(message, location, parseState, cause));
	}

	/**
	 * Raise a regular error.
	 */
	public void error(String message, @Nullable Object source) {
		error(message, source, null, null);
	}

	/**
	 * Raise a regular error.
	 */
	public void error(String message, @Nullable Object source, @Nullable Throwable cause) {
		error(message, source, null, cause);
	}

	/**
	 * Raise a regular error.
	 */
	public void error(String message, @Nullable Object source, @Nullable ParseState parseState) {
		error(message, source, parseState, null);
	}

	/**
	 * Raise a regular error.
	 */
	public void error(String message, @Nullable Object source, @Nullable ParseState parseState, @Nullable Throwable cause) {
		Location location = new Location(getResource(), source);
		this.problemReporter.error(new Problem(message, location, parseState, cause));
	}

	/**
	 * Raise a non-critical warning.
	 */
	public void warning(String message, @Nullable Object source) {
		warning(message, source, null, null);
	}

	/**
	 * Raise a non-critical warning.
	 */
	public void warning(String message, @Nullable Object source, @Nullable Throwable cause) {
		warning(message, source, null, cause);
	}

	/**
	 * Raise a non-critical warning.
	 */
	public void warning(String message, @Nullable Object source, @Nullable ParseState parseState) {
		warning(message, source, parseState, null);
	}

	/**
	 * Raise a non-critical warning.
	 */
	public void warning(String message, @Nullable Object source, @Nullable ParseState parseState, @Nullable Throwable cause) {
		Location location = new Location(getResource(), source);
		this.problemReporter.warning(new Problem(message, location, parseState, cause));
	}


	// Explicit parse events

	/**
	 * 触发一个默认注册的事件
	 */
	public void fireDefaultsRegistered(DefaultsDefinition defaultsDefinition) {
		this.eventListener.defaultsRegistered(defaultsDefinition);
	}

	/**
	 * Fire an component-registered event.
	 */
	public void fireComponentRegistered(ComponentDefinition componentDefinition) {
		this.eventListener.componentRegistered(componentDefinition);
	}

	/**
	 * Fire an alias-registered event.
	 */
	public void fireAliasRegistered(String beanName, String alias, @Nullable Object source) {
		this.eventListener.aliasRegistered(new AliasDefinition(beanName, alias, source));
	}

	/**
	 * Fire an import-processed event.
	 */
	public void fireImportProcessed(String importedResource, @Nullable Object source) {
		this.eventListener.importProcessed(new ImportDefinition(importedResource, source));
	}

	/**
	 * Fire an import-processed event.
	 */
	public void fireImportProcessed(String importedResource, Resource[] actualResources, @Nullable Object source) {
		this.eventListener.importProcessed(new ImportDefinition(importedResource, actualResources, source));
	}


	// Source extraction

	/**
	 * 返回正在使用的资源提取器
	 */
	public SourceExtractor getSourceExtractor() {
		return this.sourceExtractor;
	}

	/**
	 * 为给定的源对象调用源提取器
	 */
	@Nullable
	public Object extractSource(Object sourceCandidate) {
		return this.sourceExtractor.extractSource(sourceCandidate, this.resource);
	}

}

/*
 * Copyright 2002-2016 the original author or authors.
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

package org.springframework.web.context.request;

import javax.faces.context.FacesContext;

import org.springframework.core.NamedInheritableThreadLocal;
import org.springframework.core.NamedThreadLocal;
import org.springframework.lang.Nullable;
import org.springframework.util.ClassUtils;

/**
 * 以线程绑定的{@link RequestAttributes}对象的形式公开web请求。如果将{@code inheritable}标志设置为{@code true}，则当前线程派生的任何子线程都将继承请求。
 */
public abstract class RequestContextHolder  {

	private static final boolean jsfPresent = ClassUtils.isPresent("javax.faces.context.FacesContext", RequestContextHolder.class.getClassLoader());

	private static final ThreadLocal<RequestAttributes> requestAttributesHolder = new NamedThreadLocal<>("Request attributes");

	// 标记为可继承的
	private static final ThreadLocal<RequestAttributes> inheritableRequestAttributesHolder = new NamedInheritableThreadLocal<>("Request context");


	/**
	 * 重置当前线程的请求属性
	 */
	public static void resetRequestAttributes() {
		requestAttributesHolder.remove();
		inheritableRequestAttributesHolder.remove();
	}

	/**
	 * 将给定的请求属性绑定到当前线程，而不是将其公开为可继承的子线程
	 */
	public static void setRequestAttributes(@Nullable RequestAttributes attributes) {
		setRequestAttributes(attributes, false);
	}

	/**
	 * 将给定的请求属性绑定到当前线程
	 */
	public static void setRequestAttributes(@Nullable RequestAttributes attributes, boolean inheritable) {
		if (attributes == null) {
			resetRequestAttributes();
		}
		else {
			if (inheritable) {
				inheritableRequestAttributesHolder.set(attributes);
				requestAttributesHolder.remove();
			}
			else {
				requestAttributesHolder.set(attributes);
				inheritableRequestAttributesHolder.remove();
			}
		}
	}

	/**
	 * 返回当前绑定到线程的RequestAttributes
	 */
	@Nullable
	public static RequestAttributes getRequestAttributes() {
		RequestAttributes attributes = requestAttributesHolder.get();
		if (attributes == null) {
			attributes = inheritableRequestAttributesHolder.get();
		}
		return attributes;
	}

	/**
	 * 返回当前绑定到线程的RequestAttributes
	 */
	public static RequestAttributes currentRequestAttributes() throws IllegalStateException {
		RequestAttributes attributes = getRequestAttributes();
		if (attributes == null) {
			if (jsfPresent) {
				attributes = FacesRequestAttributesFactory.getFacesRequestAttributes();
			}
			if (attributes == null) {
				throw new IllegalStateException("No thread-bound request fou");
			}
		}
		return attributes;
	}


	/**
	 * Inner class to avoid hard-coded JSF dependency.
	 * 内部类，以避免硬编码的JSF依赖
 	 */
	private static class FacesRequestAttributesFactory {

		@Nullable
		public static RequestAttributes getFacesRequestAttributes() {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			return (facesContext != null ? new FacesRequestAttributes(facesContext) : null);
		}
	}

}

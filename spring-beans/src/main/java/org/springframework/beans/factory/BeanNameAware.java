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

package org.springframework.beans.factory;

/**
 * 要由希望在bean工厂中知道其bean名称的bean实现的接口。
 * 请注意，通常不建议对象依赖于它的bean名称，
 * 因为这代表了对外部配置的潜在脆弱依赖，以及对Spring API的可能不必要的依赖
 */
public interface BeanNameAware extends Aware {

	/**
	 * 在创建此bean的bean工厂中设置bean的名称
	 */
	void setBeanName(String name);

}

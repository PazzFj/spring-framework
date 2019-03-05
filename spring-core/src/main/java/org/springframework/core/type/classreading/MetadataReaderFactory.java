/*
 * Copyright 2002-2012 the original author or authors.
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

package org.springframework.core.type.classreading;

import java.io.IOException;

import org.springframework.core.io.Resource;

/**
 * {@link MetadataReader}实例的工厂接口。允许缓存每个原始资源的元数据阅读器
 *
 * @see SimpleMetadataReaderFactory  //简单元数组读取工厂
 * @see CachingMetadataReaderFactory //缓存元数据读取工厂
 */
public interface MetadataReaderFactory {

	/**
	 * 获取给定类名的MetadataReader
	 */
	MetadataReader getMetadataReader(String className) throws IOException;

	/**
	 * 获取给定资源的MetadataReader
	 */
	MetadataReader getMetadataReader(Resource resource) throws IOException;

}

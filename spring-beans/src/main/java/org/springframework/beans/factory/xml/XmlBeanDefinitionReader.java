/*
 * Copyright 2002-2018 the original author or authors.
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

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.parsing.EmptyReaderEventListener;
import org.springframework.beans.factory.parsing.FailFastProblemReporter;
import org.springframework.beans.factory.parsing.NullSourceExtractor;
import org.springframework.beans.factory.parsing.ProblemReporter;
import org.springframework.beans.factory.parsing.ReaderEventListener;
import org.springframework.beans.factory.parsing.SourceExtractor;
import org.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.Constants;
import org.springframework.core.NamedThreadLocal;
import org.springframework.core.io.DescriptiveResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.xml.SimpleSaxErrorHandler;
import org.springframework.util.xml.XmlValidationModeDetector;

/**
 * 用于XML BeanDefinition 的Bean定义阅读器。将实际读取的XML文档委托给{@link BeanDefinitionDocumentReader}接口的实现。
 * @see #setDocumentReaderClass
 * @see BeanDefinitionDocumentReader
 * @see DefaultBeanDefinitionDocumentReader
 * @see BeanDefinitionRegistry
 * @see org.springframework.beans.factory.support.DefaultListableBeanFactory
 * @see org.springframework.context.support.GenericApplicationContext
 * @see org.springframework.context.support.AbstractXmlApplicationContext#loadBeanDefinitions(DefaultListableBeanFactory beanFactory)
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

	/**
	 * 表明应禁用验证
	 */
	public static final int VALIDATION_NONE = XmlValidationModeDetector.VALIDATION_NONE;

	/**
	 * 表明应自动检测验证模式(默认)
	 */
	public static final int VALIDATION_AUTO = XmlValidationModeDetector.VALIDATION_AUTO;

	/**
	 * 表明应使用DTD验证
	 */
	public static final int VALIDATION_DTD = XmlValidationModeDetector.VALIDATION_DTD;

	/**
	 * 表明应使用XSD验证
	 */
	public static final int VALIDATION_XSD = XmlValidationModeDetector.VALIDATION_XSD;


	/** 此类的常量实例. */
	private static final Constants constants = new Constants(XmlBeanDefinitionReader.class);

	private int validationMode = VALIDATION_AUTO;
	//默认false
	private boolean namespaceAware = false;

	private Class<? extends BeanDefinitionDocumentReader> documentReaderClass = DefaultBeanDefinitionDocumentReader.class;

	//问题记者
	private ProblemReporter problemReporter = new FailFastProblemReporter();
	//解析事件监听
	private ReaderEventListener eventListener = new EmptyReaderEventListener();
	//资源提取器
	private SourceExtractor sourceExtractor = new NullSourceExtractor();

	@Nullable
	private NamespaceHandlerResolver namespaceHandlerResolver;
	//Document加载器
	private DocumentLoader documentLoader = new DefaultDocumentLoader();
	//Entity解析器
	@Nullable
	private EntityResolver entityResolver;
	//Error处理器
	private ErrorHandler errorHandler = new SimpleSaxErrorHandler(logger);
	//XmlValidation模式检测器
	private final XmlValidationModeDetector validationModeDetector = new XmlValidationModeDetector();
	//存储EncodedResource集合
	private final ThreadLocal<Set<EncodedResource>> resourcesCurrentlyBeingLoaded = new NamedThreadLocal<>("XML bean definition resources currently being loaded");


	public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
		super(registry);
	}


	public void setValidating(boolean validating) {
		this.validationMode = (validating ? VALIDATION_AUTO : VALIDATION_NONE);
		this.namespaceAware = !validating;
	}

	public void setValidationModeName(String validationModeName) {
		setValidationMode(constants.asNumber(validationModeName).intValue());
	}

	public void setValidationMode(int validationMode) {
		this.validationMode = validationMode;
	}

	public int getValidationMode() {
		return this.validationMode;
	}

	public void setNamespaceAware(boolean namespaceAware) {
		this.namespaceAware = namespaceAware;
	}

	public boolean isNamespaceAware() {
		return this.namespaceAware;
	}

	public void setProblemReporter(@Nullable ProblemReporter problemReporter) {
		this.problemReporter = (problemReporter != null ? problemReporter : new FailFastProblemReporter());
	}

	// 设置事件监听器
	public void setEventListener(@Nullable ReaderEventListener eventListener) {
		this.eventListener = (eventListener != null ? eventListener : new EmptyReaderEventListener());
	}

	// 设置资源提取器
	public void setSourceExtractor(@Nullable SourceExtractor sourceExtractor) {
		this.sourceExtractor = (sourceExtractor != null ? sourceExtractor : new NullSourceExtractor());
	}

	// 设置命名空间管理解析器 (用于解析命名空间如: <aop /> <context/>  )
	public void setNamespaceHandlerResolver(@Nullable NamespaceHandlerResolver namespaceHandlerResolver) {
		this.namespaceHandlerResolver = namespaceHandlerResolver;
	}

	public void setDocumentLoader(@Nullable DocumentLoader documentLoader) {
		this.documentLoader = (documentLoader != null ? documentLoader : new DefaultDocumentLoader());
	}

	/**
	 * 设置用于解析的SAX实体解析器。
	 */
	public void setEntityResolver(@Nullable EntityResolver entityResolver) {
		this.entityResolver = entityResolver;
	}

	/**
	 * 返回要使用的EntityResolver，如果没有指定，则构建默认的解析器。
	 */
	protected EntityResolver getEntityResolver() {
		if (this.entityResolver == null) {
			// Determine default EntityResolver to use.
			ResourceLoader resourceLoader = getResourceLoader();
			if (resourceLoader != null) {
				this.entityResolver = new ResourceEntityResolver(resourceLoader);
			}
			else {
				this.entityResolver = new DelegatingEntityResolver(getBeanClassLoader());
			}
		}
		return this.entityResolver;
	}

	public void setErrorHandler(ErrorHandler errorHandler) {
		this.errorHandler = errorHandler;
	}

	public void setDocumentReaderClass(Class<? extends BeanDefinitionDocumentReader> documentReaderClass) {
		this.documentReaderClass = documentReaderClass;
	}


	/**
	 * 从指定的XML文件加载BeanDefinition
	 * @throws BeanDefinitionStoreException in case of loading or parsing errors
	 */
	@Override
	public int loadBeanDefinitions(Resource resource) throws BeanDefinitionStoreException {
		return loadBeanDefinitions(new EncodedResource(resource));
	}

	/**
	 * 从指定的XML文件加载BeanDefinition
	 * @param encodedResource XML文件的资源描述符，允许指定用于解析文件的编码
	 * @throws BeanDefinitionStoreException in case of loading or parsing errors
	 */
	public int loadBeanDefinitions(EncodedResource encodedResource) throws BeanDefinitionStoreException {
		Assert.notNull(encodedResource, "EncodedResource must not be null");
		if (logger.isTraceEnabled()) {
			logger.trace("Loading XML bean definitions from " + encodedResource);
		}

		Set<EncodedResource> currentResources = this.resourcesCurrentlyBeingLoaded.get();
		if (currentResources == null) {
			currentResources = new HashSet<>(4);
			this.resourcesCurrentlyBeingLoaded.set(currentResources);
		}
		// 1、把资源对象Resource加入Set集合
		if (!currentResources.add(encodedResource)) {
			throw new BeanDefinitionStoreException("Detected cyclic loading of " + encodedResource + " - check your import definitions!");
		}
		try {
			// 2、获取输入流
			InputStream inputStream = encodedResource.getResource().getInputStream();
			try {
				//3、InputStream 转 InputSource (封装)
				InputSource inputSource = new InputSource(inputStream);
				if (encodedResource.getEncoding() != null) { //设置编码
					inputSource.setEncoding(encodedResource.getEncoding());
				}
				//4、do 加载
				return doLoadBeanDefinitions(inputSource, encodedResource.getResource());
			}
			finally {
				inputStream.close();
			}
		}
		catch (IOException ex) {
			throw new BeanDefinitionStoreException(
					"IOException parsing XML document from " + encodedResource.getResource(), ex);
		}
		finally {
			currentResources.remove(encodedResource);
			if (currentResources.isEmpty()) {
				this.resourcesCurrentlyBeingLoaded.remove();
			}
		}
	}

	/**
	 * Load bean definitions from the specified XML file.
	 * @param inputSource the SAX InputSource to read from
	 * @return the number of bean definitions found
	 * @throws BeanDefinitionStoreException in case of loading or parsing errors
	 */
	public int loadBeanDefinitions(InputSource inputSource) throws BeanDefinitionStoreException {
		return loadBeanDefinitions(inputSource, "resource loaded through SAX InputSource");
	}

	/**
	 * Load bean definitions from the specified XML file.
	 * @param inputSource the SAX InputSource to read from
	 * @param resourceDescription a description of the resource
	 * (can be {@code null} or empty)
	 * @return the number of bean definitions found
	 * @throws BeanDefinitionStoreException in case of loading or parsing errors
	 */
	public int loadBeanDefinitions(InputSource inputSource, @Nullable String resourceDescription)
			throws BeanDefinitionStoreException {

		return doLoadBeanDefinitions(inputSource, new DescriptiveResource(resourceDescription));
	}


	/**
	 * 实际从指定的XML文件加载bean定义
	 * @param inputSource
	 * @param resource ClassPathResource  或者 ClassPathContextResource  或者  UrlResource
	 * @see #doLoadDocument
	 * @see #registerBeanDefinitions
	 */
	protected int doLoadBeanDefinitions(InputSource inputSource, Resource resource)
			throws BeanDefinitionStoreException {

		try {
			//1、do加载Document
			Document doc = doLoadDocument(inputSource, resource);
			//2、注册BeanDefinition
			int count = registerBeanDefinitions(doc, resource);
			if (logger.isDebugEnabled()) {
				logger.debug("Loaded " + count + " bean definitions from " + resource);
			}
			return count;
		}
		catch (BeanDefinitionStoreException ex) {
			throw ex;
		}
		catch (SAXParseException ex) {
			throw new XmlBeanDefinitionStoreException(resource.getDescription(),
					"Line " + ex.getLineNumber() + " in XML document from " + resource + " is invalid", ex);
		}
		catch (SAXException ex) {
			throw new XmlBeanDefinitionStoreException(resource.getDescription(),
					"XML document from " + resource + " is invalid", ex);
		}
		catch (ParserConfigurationException ex) {
			throw new BeanDefinitionStoreException(resource.getDescription(),
					"Parser configuration exception parsing XML from " + resource, ex);
		}
		catch (IOException ex) {
			throw new BeanDefinitionStoreException(resource.getDescription(),
					"IOException parsing XML document from " + resource, ex);
		}
		catch (Throwable ex) {
			throw new BeanDefinitionStoreException(resource.getDescription(),
					"Unexpected exception parsing XML document from " + resource, ex);
		}
	}

	/**
	 * 使用配置的DocumentLoader实际加载指定的文档。
	 * @return the DOM Document
	 * @throws Exception when thrown from the DocumentLoader
	 * @see #setDocumentLoader
	 * @see DocumentLoader#loadDocument
	 */
	protected Document doLoadDocument(InputSource inputSource, Resource resource) throws Exception {
		return this.documentLoader.loadDocument(inputSource, getEntityResolver(), this.errorHandler, getValidationModeForResource(resource), isNamespaceAware());
	}

	/**
	 * 确定指定的{@link Resource}的验证模式。如果没有配置显式验证模式，那么验证模式将从给定资源中获取{@link #detectValidationMode detection}
	 * <p>如果您想完全控制验证模式，请覆盖此方法，即使设置了{@link #VALIDATION_AUTO}以外的其他内容。
	 * @see #detectValidationMode
	 */
	protected int getValidationModeForResource(Resource resource) {
		int validationModeToUse = getValidationMode();
		if (validationModeToUse != VALIDATION_AUTO) {
			return validationModeToUse;
		}
		int detectedMode = detectValidationMode(resource);
		if (detectedMode != VALIDATION_AUTO) {
			return detectedMode;
		}
		// Hmm, we didn't get a clear indication... Let's assume XSD,
		// since apparently no DTD declaration has been found up until
		// detection stopped (before finding the document's root tag).
		return VALIDATION_XSD;
	}

	/**
	 * Detect which kind of validation to perform on the XML file identified
	 * by the supplied {@link Resource}. If the file has a {@code DOCTYPE}
	 * definition then DTD validation is used otherwise XSD validation is assumed.
	 * <p>Override this method if you would like to customize resolution
	 * of the {@link #VALIDATION_AUTO} mode.
	 */
	protected int detectValidationMode(Resource resource) {
		if (resource.isOpen()) {
			throw new BeanDefinitionStoreException(
					"Passed-in Resource [" + resource + "] contains an open stream: " +
					"cannot determine validation mode automatically. Either pass in a Resource " +
					"that is able to create fresh streams, or explicitly specify the validationMode " +
					"on your XmlBeanDefinitionReader instance.");
		}

		InputStream inputStream;
		try {
			inputStream = resource.getInputStream();
		}
		catch (IOException ex) {
			throw new BeanDefinitionStoreException(
					"Unable to determine validation mode for [" + resource + "]: cannot open InputStream. " +
					"Did you attempt to load directly from a SAX InputSource without specifying the " +
					"validationMode on your XmlBeanDefinitionReader instance?", ex);
		}

		try {
			return this.validationModeDetector.detectValidationMode(inputStream);
		}
		catch (IOException ex) {
			throw new BeanDefinitionStoreException("Unable to determine validation mode for [" +
					resource + "]: an error occurred whilst reading from the InputStream.", ex);
		}
	}

	/**
	 * 注册给定DOM文档中包含的 BeanDefinition.
	 *
	 * @throws BeanDefinitionStoreException in case of parsing errors
	 * @see #loadBeanDefinitions
	 * @see #setDocumentReaderClass
	 * @see BeanDefinitionDocumentReader#registerBeanDefinitions
	 */
	public int registerBeanDefinitions(Document doc, Resource resource) throws BeanDefinitionStoreException {
		//1、创建BeanDefinitionDocumentReader  ==>>  DefaultBeanDefinitionDocumentReader
		BeanDefinitionDocumentReader documentReader = createBeanDefinitionDocumentReader();
		//2、获取DefaultListableBeanFactory注册器中BeanDefinition注册的数量
		int countBefore = getRegistry().getBeanDefinitionCount();
		//3、注册BeanDefinition  	(Resource 转 XmlReaderContext)
		documentReader.registerBeanDefinitions(doc, createReaderContext(resource));
		return getRegistry().getBeanDefinitionCount() - countBefore;
	}

	/**
	 * Create the {@link BeanDefinitionDocumentReader} to use for actually
	 * reading bean definitions from an XML document.
	 * <p>The default implementation instantiates the specified "documentReaderClass".
	 * @see #setDocumentReaderClass
	 */
	protected BeanDefinitionDocumentReader createBeanDefinitionDocumentReader() {
		return BeanUtils.instantiateClass(this.documentReaderClass);
	}

	/**
	 * Create the {@link XmlReaderContext} to pass over to the document reader.
	 */
	public XmlReaderContext createReaderContext(Resource resource) {
		return new XmlReaderContext(resource, this.problemReporter, this.eventListener,
				this.sourceExtractor, this, getNamespaceHandlerResolver());
	}

	/**
	 * Lazily create a default NamespaceHandlerResolver, if not set before.
	 * @see #createDefaultNamespaceHandlerResolver()
	 */
	public NamespaceHandlerResolver getNamespaceHandlerResolver() {
		if (this.namespaceHandlerResolver == null) {
			this.namespaceHandlerResolver = createDefaultNamespaceHandlerResolver();
		}
		return this.namespaceHandlerResolver;
	}

	// 创建命名空间解析器 DefaultNamespaceHandlerResolver
	protected NamespaceHandlerResolver createDefaultNamespaceHandlerResolver() {
		ClassLoader cl = (getResourceLoader() != null ? getResourceLoader().getClassLoader() : getBeanClassLoader());
		return new DefaultNamespaceHandlerResolver(cl);
	}

}

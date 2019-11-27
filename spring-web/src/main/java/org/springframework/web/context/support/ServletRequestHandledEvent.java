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

package org.springframework.web.context.support;

import org.springframework.lang.Nullable;

/**
 * RequestHandledEvent的servlet特定子类，添加servlet特定上下文信息
 */
@SuppressWarnings("serial")
public class ServletRequestHandledEvent extends RequestHandledEvent {

	/** 触发请求的URL */
	private final String requestUrl;

	/** 请求来自的IP地址. */
	private final String clientAddress;

	/** 通常是GET或POST. */
	private final String method;

	/** 处理请求的servlet的名称. */
	private final String servletName;

	/** 响应的HTTP状态码. */
	private final int statusCode;


	public ServletRequestHandledEvent(Object source, String requestUrl,
			String clientAddress, String method, String servletName,
			@Nullable String sessionId, @Nullable String userName, long processingTimeMillis) {

		super(source, sessionId, userName, processingTimeMillis);
		this.requestUrl = requestUrl;
		this.clientAddress = clientAddress;
		this.method = method;
		this.servletName = servletName;
		this.statusCode = -1;
	}

	public ServletRequestHandledEvent(Object source, String requestUrl,
			String clientAddress, String method, String servletName, @Nullable String sessionId,
			@Nullable String userName, long processingTimeMillis, @Nullable Throwable failureCause) {

		super(source, sessionId, userName, processingTimeMillis, failureCause);
		this.requestUrl = requestUrl;
		this.clientAddress = clientAddress;
		this.method = method;
		this.servletName = servletName;
		this.statusCode = -1;
	}

	/**
	 * Create a new ServletRequestHandledEvent.
	 * @param source the component that published the event
	 * @param requestUrl the URL of the request
	 * @param clientAddress the IP address that the request came from
	 * @param method the HTTP method of the request (usually GET or POST)
	 * @param servletName the name of the servlet that handled the request
	 * @param sessionId the id of the HTTP session, if any
	 * @param userName the name of the user that was associated with the
	 * request, if any (usually the UserPrincipal)
	 * @param processingTimeMillis the processing time of the request in milliseconds
	 * @param failureCause the cause of failure, if any
	 * @param statusCode the HTTP status code of the response
	 */
	public ServletRequestHandledEvent(Object source, String requestUrl,
			String clientAddress, String method, String servletName, @Nullable String sessionId,
			@Nullable String userName, long processingTimeMillis, @Nullable Throwable failureCause, int statusCode) {

		super(source, sessionId, userName, processingTimeMillis, failureCause);
		this.requestUrl = requestUrl;
		this.clientAddress = clientAddress;
		this.method = method;
		this.servletName = servletName;
		this.statusCode = statusCode;
	}


	public String getRequestUrl() {
		return this.requestUrl;
	}

	public String getClientAddress() {
		return this.clientAddress;
	}

	public String getMethod() {
		return this.method;
	}

	public String getServletName() {
		return this.servletName;
	}

	public int getStatusCode() {
		return this.statusCode;
	}

	@Override
	public String getShortDescription() {
		StringBuilder sb = new StringBuilder();
		sb.append("url=[").append(getRequestUrl()).append("]; ");
		sb.append("client=[").append(getClientAddress()).append("]; ");
		sb.append(super.getShortDescription());
		return sb.toString();
	}

	@Override
	public String getDescription() {
		StringBuilder sb = new StringBuilder();
		sb.append("url=[").append(getRequestUrl()).append("]; ");
		sb.append("client=[").append(getClientAddress()).append("]; ");
		sb.append("method=[").append(getMethod()).append("]; ");
		sb.append("servlet=[").append(getServletName()).append("]; ");
		sb.append(super.getDescription());
		return sb.toString();
	}

	@Override
	public String toString() {
		return "ServletRequestHandledEvent: " + getDescription();
	}

}

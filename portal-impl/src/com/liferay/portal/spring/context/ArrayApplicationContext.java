/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.spring.context;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.FileNotFoundException;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Brian Wing Shun Chan
 */
public class ArrayApplicationContext extends ClassPathXmlApplicationContext {

	public ArrayApplicationContext(String[] configLocations) {
		super(configLocations);
	}

	@Override
	protected void loadBeanDefinitions(XmlBeanDefinitionReader reader) {
		String[] configLocations = getConfigLocations();

		if (configLocations == null) {
			return;
		}

		for (String configLocation : configLocations) {
			try {
				reader.loadBeanDefinitions(configLocation);
			}
			catch (Exception e) {
				Throwable cause = e.getCause();

				if (cause instanceof FileNotFoundException) {
					if (_log.isWarnEnabled()) {
						_log.warn(cause.getMessage());
					}
				}
				else {
					_log.error(e, e);
				}
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		ArrayApplicationContext.class);

}
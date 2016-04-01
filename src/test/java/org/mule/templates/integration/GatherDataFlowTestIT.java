/**
 * Mule Anypoint Template
 * Copyright (c) MuleSoft, Inc.
 * All rights reserved.  http://www.mulesoft.com
 */

package org.mule.templates.integration;

import org.junit.Assert;
import org.junit.Test;
import org.mule.util.ExceptionUtils;

/**
 * The objective of this class is to validate the correct behavior of the flows
 * for this Anypoint Template that make calls to external systems.
 */
public class GatherDataFlowTestIT extends AbstractTemplateTestCase {
	
	private static String EXPECTED_EXCEPTION_MESSAGE = "[LoginFault [ApiFault  exceptionCode='INVALID_LOGIN'exceptionMessage='Invalid username, password, security token; or user locked out.']]".replaceAll("\\s", "");

	@Test
	public void testGatherDataFlow() throws Exception {
		
		try {
			runFlow("gatherDataFlow");
			Assert.fail("Exception should occure");
		} catch (Exception e) {
			Assert.assertEquals("Expected Invalid credentials", EXPECTED_EXCEPTION_MESSAGE, ExceptionUtils.getRootCause(e).toString().replaceAll("\\s", ""));
		}
	}
}

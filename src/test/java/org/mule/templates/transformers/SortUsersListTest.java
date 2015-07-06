/**
 * Mule Anypoint Template
 * Copyright (c) MuleSoft, Inc.
 * All rights reserved.  http://www.mulesoft.com
 */

package org.mule.templates.transformers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleContext;
import org.mule.api.MuleMessage;
import org.mule.api.config.MuleConfiguration;
import org.mule.api.transformer.TransformerException;

@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class SortUsersListTest {
	@Mock
	private MuleContext muleContext;
	
	@Mock
	private MuleConfiguration muleConfiguration;

	@Test
	public void testSort() throws TransformerException {

		Mockito.when(muleContext.getConfiguration()).thenReturn(muleConfiguration);
		Mockito.when(muleConfiguration.getDefaultEncoding()).thenReturn("UTF-8");
		
		MuleMessage message = new DefaultMuleMessage(createOriginalList(), muleContext);

		SortUsersList transformer = new SortUsersList();
		List<Map<String, String>> sortedList = (List<Map<String, String>>) transformer.transform(message, "UTF-8");

		assertEquals("The merged list obtained is not as expected", createExpectedList(), sortedList);

	}

	static List<Map<String, String>> createExpectedList() {
		Map<String, String> record0 = new HashMap<String, String>();
		record0.put("IDInA", "0");
		record0.put("IDInB", "");
		record0.put("Email", "some.email.0@fakemail.com");
		record0.put("Name", "SomeName_0");
		record0.put("UserNameInA", "username_0_A");
		record0.put("UserNameInB", "");

		Map<String, String> record1 = new HashMap<String, String>();
		record1.put("IDInA", "1");
		record1.put("IDInB", "1");
		record1.put("Email", "some.email.1@fakemail.com");
		record1.put("Name", "SomeName_1");
		record1.put("UserNameInA", "username_1_A");
		record1.put("UserNameInB", "username_1_B");

		Map<String, String> record2 = new HashMap<String, String>();
		record2.put("IDInA", "");
		record2.put("IDInB", "2");
		record2.put("Email", "some.email.2@fakemail.com");
		record2.put("Name", "SomeName_2");
		record2.put("UserNameInA", "");
		record2.put("UserNameInB", "username_2_B");

		List<Map<String, String>> expectedList = new ArrayList<Map<String, String>>();
		expectedList.add(record0);
		expectedList.add(record2);
		expectedList.add(record1);

		return expectedList;

	}

	private List<Map<String, String>> createOriginalList() {
		Map<String, String> record0 = new HashMap<String, String>();
		record0.put("IDInA", "0");
		record0.put("IDInB", "");
		record0.put("Email", "some.email.0@fakemail.com");
		record0.put("Name", "SomeName_0");
		record0.put("UserNameInA", "username_0_A");
		record0.put("UserNameInB", "");

		Map<String, String> record1 = new HashMap<String, String>();
		record1.put("IDInA", "1");
		record1.put("IDInB", "1");
		record1.put("Email", "some.email.1@fakemail.com");
		record1.put("Name", "SomeName_1");
		record1.put("UserNameInA", "username_1_A");
		record1.put("UserNameInB", "username_1_B");

		Map<String, String> record2 = new HashMap<String, String>();
		record2.put("IDInA", "");
		record2.put("IDInB", "2");
		record2.put("Email", "some.email.2@fakemail.com");
		record2.put("Name", "SomeName_2");
		record2.put("UserNameInA", "");
		record2.put("UserNameInB", "username_2_B");

		List<Map<String, String>> originalList = new ArrayList<Map<String, String>>();
		originalList.add(record0);
		originalList.add(record1);
		originalList.add(record2);

		return originalList;

	}

}

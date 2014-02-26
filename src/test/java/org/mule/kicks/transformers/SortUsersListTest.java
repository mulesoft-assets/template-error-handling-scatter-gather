package org.mule.kicks.transformers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleContext;
import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;

@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class SortUsersListTest {
	@Mock
	private MuleContext muleContext;

	@Test
	public void testSort() throws TransformerException {

		MuleMessage message = new DefaultMuleMessage(createOriginalList(), muleContext);

		SortUsersList transformer = new SortUsersList();
		List<Map<String, String>> sortedList = (List<Map<String, String>>) transformer.transform(message, "UTF-8");

		System.out.println(sortedList);
		Assert.assertEquals("The merged list obtained is not as expected", createExpectedList(), sortedList);

	}

	private List<Map<String, String>> createExpectedList() {
		Map<String, String> user0 = new HashMap<String, String>();
		user0.put("IDInA", "0");
		user0.put("IDInB", "");
		user0.put("Email", "some.email.0@fakemail.com");
		user0.put("Name", "SomeName_0");
		user0.put("UserNameInA", "username_0_A");
		user0.put("UserNameInB", "");

		Map<String, String> user1 = new HashMap<String, String>();
		user1.put("IDInA", "1");
		user1.put("IDInB", "1");
		user1.put("Email", "some.email.1@fakemail.com");
		user1.put("Name", "SomeName_1");
		user1.put("UserNameInA", "username_1_A");
		user1.put("UserNameInB", "username_1_B");

		Map<String, String> user2 = new HashMap<String, String>();
		user2.put("IDInA", "");
		user2.put("IDInB", "2");
		user2.put("Email", "some.email.2@fakemail.com");
		user2.put("Name", "SomeName_2");
		user2.put("UserNameInA", "");
		user2.put("UserNameInB", "username_2_B");

		List<Map<String, String>> userList = new ArrayList<Map<String, String>>();
		userList.add(user0);
		userList.add(user2);
		userList.add(user1);

		return userList;

	}

	private List<Map<String, String>> createOriginalList() {
		Map<String, String> user0 = new HashMap<String, String>();
		user0.put("IDInA", "0");
		user0.put("IDInB", "");
		user0.put("Email", "some.email.0@fakemail.com");
		user0.put("Name", "SomeName_0");
		user0.put("UserNameInA", "username_0_A");
		user0.put("UserNameInB", "");

		Map<String, String> user1 = new HashMap<String, String>();
		user1.put("IDInA", "1");
		user1.put("IDInB", "1");
		user1.put("Email", "some.email.1@fakemail.com");
		user1.put("Name", "SomeName_1");
		user1.put("UserNameInA", "username_1_A");
		user1.put("UserNameInB", "username_1_B");

		Map<String, String> user2 = new HashMap<String, String>();
		user2.put("IDInA", "");
		user2.put("IDInB", "2");
		user2.put("Email", "some.email.2@fakemail.com");
		user2.put("Name", "SomeName_2");
		user2.put("UserNameInA", "");
		user2.put("UserNameInB", "username_2_B");

		List<Map<String, String>> userList = new ArrayList<Map<String, String>>();
		userList.add(user0);
		userList.add(user1);
		userList.add(user2);

		return userList;

	}

}

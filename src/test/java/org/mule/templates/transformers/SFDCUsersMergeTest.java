package org.mule.templates.transformers;

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
import org.mule.templates.transformers.SFDCUsersMerge;

@SuppressWarnings("unchecked")
@RunWith(MockitoJUnitRunner.class)
public class SFDCUsersMergeTest {
	private static final String USERS_FROM_SFDC = "usersFromOrgA";
	private static final String USERS_FROM_DB = "usersFromDB";

	@Mock
	private MuleContext muleContext;

	@Test
	public void testMerge() throws TransformerException {
		List<Map<String, String>> usersA = createUserLists("A", 0, 1, false);
		List<Map<String, String>> usersB = createUserLists("B", 1, 2, true);

		MuleMessage message = new DefaultMuleMessage(null, muleContext);
		message.setInvocationProperty(USERS_FROM_SFDC, usersA.iterator());
		message.setInvocationProperty(USERS_FROM_DB, usersB.iterator());

		SFDCUsersMerge transformer = new SFDCUsersMerge();
		List<Map<String, String>> mergedList = (List<Map<String, String>>) transformer.transform(message, "UTF-8");

		System.out.println(mergedList);
		Assert.assertEquals("The merged list obtained is not as expected", createExpectedList(), mergedList);

	}

	private List<Map<String, String>> createExpectedList() {
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
		expectedList.add(record1);
		expectedList.add(record2);

		return expectedList;
	}

	private List<Map<String, String>> createUserLists(String orgId, int start, int end, boolean isFromDB) {
		List<Map<String, String>> userList = new ArrayList<Map<String, String>>();
		for (int i = start; i <= end; i++) {
			Map<String, String> user;
			if (isFromDB) {
				user = createDbUser(orgId, i);
			} else {
				user = createSfdcUser(orgId, i);
			}
			userList.add(user);
		}
		return userList;
	}

	private Map<String, String> createSfdcUser(String orgId, int sequence) {
		Map<String, String> user = new HashMap<String, String>();

		user.put("Id", new Integer(sequence).toString());
		user.put("Username", "username_" + sequence + "_" + orgId);
		user.put("Name", "SomeName_" + sequence);
		user.put("Email", "some.email." + sequence + "@fakemail.com");

		return user;
	}

	private Map<String, String> createDbUser(String orgId, int sequence) {
		Map<String, String> user = new HashMap<String, String>();

		user.put("id", new Integer(sequence).toString());
		user.put("username", "username_" + sequence + "_" + orgId);
		user.put("name", "SomeName_" + sequence);
		user.put("email", "some.email." + sequence + "@fakemail.com");

		return user;
	}
}

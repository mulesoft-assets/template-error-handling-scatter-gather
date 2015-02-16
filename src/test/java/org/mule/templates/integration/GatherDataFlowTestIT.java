/**
 * Mule Anypoint Template
 * Copyright (c) MuleSoft, Inc.
 * All rights reserved.  http://www.mulesoft.com
 */

package org.mule.templates.integration;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mule.MessageExchangePattern;
import org.mule.api.MuleEvent;
import org.mule.processor.chain.SubflowInterceptingChainLifecycleWrapper;
import org.mule.templates.builders.ObjectBuilder;
import org.mule.templates.db.MySQLDbCreator;

/**
 * The objective of this class is to validate the correct behavior of the flows
 * for this Anypoint Template that make calls to external systems.
 * 
 * @author damiansima
 */
public class GatherDataFlowTestIT extends AbstractTemplateTestCase {
	
	private static Logger log = Logger.getLogger(GatherDataFlowTestIT.class);	
	private static final String PATH_TO_TEST_PROPERTIES = "./src/test/resources/mule.test.properties";
	private static final String PATH_TO_SQL_SCRIPT = "src/main/resources/sfdc2jdbc.sql";
	private static final String DATABASE_NAME = "SFDC2DBAccountBroadcast" + new Long(new Date().getTime()).toString();
	private static final MySQLDbCreator DBCREATOR = new MySQLDbCreator(DATABASE_NAME, PATH_TO_SQL_SCRIPT, PATH_TO_TEST_PROPERTIES);

	Map<String, Object> user = null;
	
	@BeforeClass
	public static void beforeTestClass() {
		System.setProperty("db.jdbcUrl", DBCREATOR.getDatabaseUrlWithName());
		DBCREATOR.setUpDatabase();
	}
	
	@Before
	public void setUp() throws Exception {
		createUsersInDB();
	}
	
	@After
	public void tearDown() throws Exception {
		DBCREATOR.tearDownDataBase();
	}
	
	@Test
	public void testGatherDataFlow() throws Exception {
		SubflowInterceptingChainLifecycleWrapper flow = getSubFlow("gatherDataFlow");		
		flow.setMuleContext(muleContext);
		flow.initialise();
		flow.start();
		
		MuleEvent event = flow.process(getTestEvent("", MessageExchangePattern.REQUEST_RESPONSE));
		Iterator<Map<String, String>> mergedList = (Iterator<Map<String, String>>)event.getMessage().getPayload();
		
		Assert.assertTrue("There should be contacts from source A or source B.", mergedList.hasNext());
	}
	
	private Map<String, Object> createDbUser() {
		String name = "tst" + new Long(new Date().getTime()).toString();
		return ObjectBuilder.aUser()
				.with("id", name)
				.with("username", name)
				.with("email",name+"@test.com")
				.build();
	}
	
	private void createUsersInDB() throws Exception {
		SubflowInterceptingChainLifecycleWrapper flow = getSubFlow("insertUserDB");
		flow.initialise();
		user = createDbUser();

		MuleEvent event = flow.process(getTestEvent(user, MessageExchangePattern.REQUEST_RESPONSE));
		Object result = event.getMessage().getPayload();
		log.info("insertUserDB result: " + result);
	}

}

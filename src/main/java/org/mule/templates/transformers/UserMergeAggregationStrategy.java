/**
 * Mule Anypoint Template
 * Copyright (c) MuleSoft, Inc.
 * All rights reserved.  http://www.mulesoft.com
 */

package org.mule.templates.transformers;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.mule.DefaultMuleEvent;
import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.routing.AggregationContext;
import org.mule.routing.AggregationStrategy;

import com.google.common.collect.Lists;

/**
 * This transformer will take to list as input and create a third one that will be the merge of the previous two. The identity of an element of the list is defined by its email.
 */
public class UserMergeAggregationStrategy implements AggregationStrategy {
	
	@Override
	public MuleEvent aggregate(AggregationContext context) throws MuleException {
		List<MuleEvent> muleEventsWithoutException = context.collectEventsWithoutExceptions();
		int muleEventsWithoutExceptionCount = muleEventsWithoutException.size();
		
		// there have to be exactly 2 sources (A and B)
		if (muleEventsWithoutExceptionCount != 2) {
			throw new IllegalArgumentException("There have to be exactly 2 sources (A and B).");
		}
		
		MuleEvent muleEvent = muleEventsWithoutException.get(0);
		MuleMessage muleMessage = muleEvent.getMessage();
		
		List<Map<String, String>> listA = getUsersList(muleEventsWithoutException, 0);
		List<Map<String, String>> listB = getUsersList(muleEventsWithoutException, 1);
		
		// events are ordered so the event index corresponds to the index of each route
		UsersMerge userMerge = new UsersMerge();
		List<Map<String, String>> mergedList = userMerge.mergeList(listA, listB);
		
		muleMessage.setPayload(mergedList.iterator());
		
		return new DefaultMuleEvent(muleMessage, muleEvent);
	}

	@SuppressWarnings("unchecked")
	private List<Map<String, String>> getUsersList(List<MuleEvent> events, int index) {
		Iterator<Map<String, String>> iterator;
		iterator = (Iterator<Map<String, String>>) events.get(index).getMessage().getPayload();
				
		return Lists.newArrayList(iterator);
	}

}

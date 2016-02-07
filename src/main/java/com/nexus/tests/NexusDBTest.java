package com.nexus.tests;

import com.nexus.NexusDB;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class NexusDBTest {
	NexusDB dbTestClass = new NexusDB();
	public final String stringToHash = "hash this";
	
	@Test
	public void hashTest() throws Exception {
		
	String  hash = dbTestClass.hashPassword(stringToHash);
		assertEquals(hash, "19467788BC0CF11790A075EA718452CECF0E79DB59D1964670475E5FE2E4A611");
	}

	@Test
	public void checkFriendTest() throws Exception {
		boolean test1 = dbTestClass.checkFriend("hasher", "hasher1");

		assertEquals(false, test1);
	}


}

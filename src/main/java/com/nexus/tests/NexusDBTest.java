package com.nexus.tests;

import static org.junit.Assert.*;
import com.nexus.*;

import org.junit.Test;


public class NexusDBTest {
	NexusDB dbTestClass = new NexusDB();
	public final String stringToHash = "hash this";
	
	@Test
	public void hashTest() throws Exception {
		
	String  hash = dbTestClass.hashPassword(stringToHash);
		assertEquals(hash, "19467788BC0CF11790A075EA718452CECF0E79DB59D1964670475E5FE2E4A611");
	}

}

package com.nexus.tests;

import com.nexus.NexusDB;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This class contains test methods for NexusDB.
 *
 * @author Raffi
 */
public class NexusDBTest {

    NexusDB nexusDB = new NexusDB();
    public final String stringToHash = "hash this";

    @Test
    public void hashPasswordTest() throws Exception {
        /*Test the hashPassword method in NexusDB.*/

        System.out.println("hashPassword method test results:");
        System.out.println();

        String hash = nexusDB.hashPassword(stringToHash);
        assertEquals("19467788BC0CF11790A075EA718452CECF0E79DB59D1964670475E5FE2E4A611", hash);

        System.out.println();
    }

    @Test
    public void checkFriendTest() throws Exception {
        /*Test the checkFriend method in NexusDB.*/

        System.out.println("checkFriend method test results:");
        System.out.println();

        Boolean areWeFriends = nexusDB.checkFriend("test", "test1");
        Boolean areWeFriends2 = nexusDB.checkFriend("test1", "test3");

        assertFalse(areWeFriends);
        assertTrue(areWeFriends2);

        System.out.println();
    }

    @Test
    public void createFriendRequestTest() throws Exception {
        /*Test the createFriendRequest method in NexusDB.*/

        System.out.println("createFriendRequest method test results:");
        System.out.println();

        /*areWeFriends creates a friend request between two existing users.
        * areWeFriends2 doesn't create a friend request because "usernameTest" user doesn't exist.*/
        Boolean areWeFriends = nexusDB.checkFriend("test1", "test3");
        Boolean areWeFriends2 = nexusDB.checkFriend("test1", "usernameTest");

        assertTrue(areWeFriends);
        assertNull(areWeFriends2);

        System.out.println();
    }

    @Test
    public void isActiveTest() throws Exception {
        /*Test the isActive method in NexusDB.*/

        System.out.println("isActive method test results:");
        System.out.println();

        /*"test1" user exists and will return true if active.
        * "usernameTest" user does not exist and will return false.*/
        Boolean isActive = nexusDB.isActive("test1");
        Boolean isActive2 = nexusDB.isActive("usernameTest");

        assertTrue(isActive);
        assertFalse(isActive2);

        System.out.println();
    }

    @Test
    public void deactivateUserTest() throws Exception {
        /*Test the deactivateUser method in NexusDB.*/

        System.out.println("deactivateUser method test results:");
        System.out.println();

        /*Returns true if user "test4" is deactivated.*/
        Boolean deactivatedUser = nexusDB.deactivateUser("test4");

        assertTrue(deactivatedUser);

        System.out.println();
    }

    @Test
    public void activateUserTest() throws Exception {
        /*Test the activateUser method in NexusDB.*/

        System.out.println("activateUser method test results:");
        System.out.println();

        /*Returns true if user "test4" is activated.*/
        Boolean activatedUser = nexusDB.activateUser("test4");

        assertTrue(activatedUser);

        System.out.println();
    }

    @Test
    public void retrievePasswordTest() throws Exception {
        /*Test the retrievePassword method in NexusDB.*/

        System.out.println("retrievePassword method test results:");
        System.out.println();

        /*Stores password of user "test4" into String object.*/
        String password = nexusDB.retrievePassword("test");

        assertEquals("EE1ACFF1BF4A53E18A75E39CB9A746E92C523D5F98D45786CA41F04DA609F0BF", password);

        System.out.println();
    }

    @Test
    public void getMatchFinderResultsTest() throws Exception {
        /*Test the getMatchFinderResults method in NexusDB.*/

        System.out.println("getMatchFinderResults method test results:");
        System.out.println();


        System.out.println();
    }

    @Test
    public void updateMessagesTest() throws Exception {
        /*Test the updateMessages method in NexusDB.*/

        System.out.println("updateMessages method test results:");
        System.out.println();

        /*Returns true if the message "hi!" is sent from user "test1" to user "test4".*/
        Boolean message = nexusDB.updateMessages("hi!", "test1", "test4");
        Boolean message2 = nexusDB.updateMessages("hi!", "test1", "usernameTest");

        assertTrue(message);
        assertFalse(message2);

        System.out.println();
    }


}

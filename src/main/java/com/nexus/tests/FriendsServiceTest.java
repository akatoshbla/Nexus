package com.nexus.tests;

import com.google.gson.JsonObject;
import com.nexus.FriendsService;
import com.nexus.NexusDB;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static junit.framework.Assert.assertNull;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * This class contains test methods for FriendsService.
 *
 * @author Raffi
 */
public class FriendsServiceTest {

    FriendsService friendsService = new FriendsService();
    String username = "test";
    String username2 = "test1";

    @Mock
    NexusDB nexusDB = new NexusDB();
    /*Mocking nexusDB so FriendsService is not dependant on it.*/

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test1() throws Exception {
        /*This method tests the getFriends method in the FriendsService class.*/

        System.out.println("getFriends method test results:");
        System.out.println();

        JsonObject json;
        List<String> friends = nexusDB.getFriendsList(username);
        List<String> pending = nexusDB.getPendingFriendsList(username);

        /*Pass a profile in getFriendsList and getPendingFriendsList method in nexusDB named
        "test", then return list of friends and pending users.*/
        when(nexusDB.getFriendsList(username)).thenReturn(friends);
        when(nexusDB.getPendingFriendsList(username)).thenReturn(pending);

        /*json object stores the mocked nexusDB account String username.*/
        json = friendsService.getFriends(username);

        /*assertThat json is an instance of a JsonObject*/
        assertThat(json, instanceOf(JsonObject.class));

        System.out.println();
    }

    @Test
    public void test2() throws Exception {
        /*This method tests the friendRequest method in the FriendsService class.*/

        System.out.println("friendRequest method test results:");
        System.out.println();

        Boolean json, json2;

        /*Passing two users into checkFriendRequest in mocked object and returning true.*/
        when(nexusDB.checkFriendRequest(username, username2)).thenReturn(true);

        /*json object stores the mocked nexusDB users using String body.*/
        json = friendsService.friendRequest("{\"from\":\"test\",\"to\":\"test1\"}");
        json2 = friendsService.friendRequest("{\"from\":\"test1\",\"to\":\"usernametest\"}");

        /*assertTrue that user "test1" sent a friend request to user "test2".
        * assertNull that an existing user "test1" cannot send a friend request to a
        * non-exisiting user "usernametest"*/
        assertTrue(json);
        assertNull(json2);

        System.out.println();
    }

    @Test
    public void test3() throws Exception {
        /*This method tests the confirmFriendRequest method in the FriendsService class.*/

        System.out.println("confirmFriendRequest method test results:");
        System.out.println();

        Boolean json;

        /*Passing two users into updateFriendStatus in mocked object and returning true.*/
        when(nexusDB.updateFriendStatus(username, username2)).thenReturn(true);

        /*json object stores the mocked nexusDB users using String body.*/
        json = friendsService.confirmFriendRequest("{\"from\":\"test\",\"to\":\"test1\"}");

        /*assertTrue that user "test2" confirmed a friend request sent by user "test1".*/
        assertTrue(json);

        System.out.println();
    }

    @Test
    public void test4() throws Exception {
        /*This method tests the deleteFriend method in the FriendsService class.*/

        System.out.println("deleteFriend method test results:");
        System.out.println();

        Boolean json;

        /*Passing two users into deleteFriend in mocked object and returning true.*/
        when(nexusDB.deleteFriend(username, username2)).thenReturn(true);

        /*json object stores the mocked nexusDB users using String body.*/
        json = friendsService.deleteFriend("{\"user1\":\"test\",\"user2\":\"test1\"}");

        /*assertTrue that user "test2" confirmed a friend request sent by user "test1".*/
        assertTrue(json);
    }
}
package com.nexus.tests;

import com.google.gson.JsonObject;
import com.nexus.NexusDB;
import com.nexus.Profile;
import com.nexus.ProfileService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * This class contains test methods for ProfileService.
 *
 * @author Raffi
 */
public class ProfileServiceTest {

    ProfileService profileService = new ProfileService();
    JsonObject jsonObject = new JsonObject();

    @Mock
    NexusDB nexusDB = new NexusDB();

    @Mock
    Profile resultProfile = new Profile();
    /*Mocking nexusDB and Profile so ProfileService is not dependent on it.*/

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getUserProfileTest() throws Exception {
        /*This method tests the getUserProfile method in the ProfileService class.
        * Tests if the object returned by this method is an instance of the JsonObject class.*/

        System.out.println("getUserProfile method test results:");
        System.out.println();

        JsonObject json;

        /*Pass a profile in getProfile method in nexusDB named "username", then return the profile object.*/
        when(nexusDB.getProfile("username")).thenReturn(resultProfile);

        /*json object stores the mocked nexusDB account "username"*/
        json = profileService.getUserProfile("username");

        /*assertThat json is an instance of a JsonObject*/
        assertThat(json, instanceOf(JsonObject.class));

        System.out.println();
    }

    @Test
    public void getMatchFinderTest() throws Exception {
        /*This method tests the getMatchFinder method in the ProfileService class.*/

        System.out.println("getMatchFinder method test results:");
        System.out.println();

        JsonObject json, json2;

        /*Pass a profile in getProfile method in nexusDB named "test", then return the profile object.*/
        when(nexusDB.getProfile("test")).thenReturn(resultProfile);
        when(nexusDB.recordExists("test")).thenReturn(true);


        /*json object stores the mocked nexusDB account "test" and "username"*/
        json = profileService.getMatchFinder("test");
        json2 = profileService.getMatchFinder("username");

        /*assertThat json is an instance of a JsonObject
        * assertTrue json is an existing account.
        * assertFalse json2 isn't an existing account.*/
        assertThat(json, instanceOf(JsonObject.class));
        assertTrue(json.get("result").getAsBoolean());
        assertFalse(json2.get("result").getAsBoolean());

        System.out.println();
    }

    @Test
    public void updateRealNameTest() throws Exception {
        /*This method tests the updateRealName method in the ProfileService class.*/

        System.out.println("updateRealName method test results:");
        System.out.println();

        JsonObject json, json2;

        when(resultProfile.getRealName()).thenReturn("test");
        when(nexusDB.updateUserProfile("test", "test", resultProfile.getRealName())).thenReturn("test");

        /*json object stores the mocked nexusDB account updated real name "test1" into user "test".
        * json2 object stores the mocked nexusDB account updated real name "test1" into null user.*/
        json = profileService.updateRealName("test", "{\"realName\":\"test1\"}");
        json2 = profileService.updateRealName(null, "{\"realName\":\"test1\"}");

        /*Assert that json is an instance of the JsonObject class.
        * AssertTrue that a username that is not null returns true and changes realName to "test1".
        * AssertFalse that a username that is null returns false.*/
        assertThat(json, instanceOf(JsonObject.class));
        assertTrue(json.get("result").getAsBoolean());
        assertFalse(json2.get("result").getAsBoolean());

        System.out.println();
    }

    @Test
    public void updateCurrentGameTest() throws Exception {
        /*This method tests the updateCurrentGame method in the ProfileService class.*/

        System.out.println("updateCurrentGame method test results:");
        System.out.println();


        JsonObject json, json2;

        when(resultProfile.getCurrentGame()).thenReturn("League Of Legends");
        when(nexusDB.updateUserProfile("test", "League Of Legends", resultProfile.getCurrentGame()))
                .thenReturn("League Of Legends");

        /*json object stores "League Of Legends" as an updated current game for user "test".
        * json2 object cannot store "League Of Legends" as an updated current game for a null user.*/
        json = profileService.updateCurrentGame("test", "{\"currentGame\":\"League Of Legends\"}");
        json2 = profileService.updateCurrentGame(null, "{\"currentGame\":\"League Of Legends\"}");

        /*Assert that json is an instance of the JsonObject class.
        * AssertTrue that a username that is not null returns true and changes currentGame to "League Of Legends".
        * AssertFalse that a username that is null returns false.*/
        assertThat(json, instanceOf(JsonObject.class));
        assertTrue(json.get("result").getAsBoolean());
        assertFalse(json2.get("result").getAsBoolean());

        System.out.println();
    }

    @Test
    public void updateSocialGamesTest() throws Exception {
        /*This method tests the updateSocialGames method in the ProfileService class.*/

        System.out.println("updateSocialGames method test results:");
        System.out.println();

        JsonObject json, json2;

        when(nexusDB.updateUserProfileLists("test", "socialLinks", "socialName", "link",
                resultProfile.getSocialNames(), resultProfile.getSocialLinks())).thenReturn(resultProfile);

        /*json object stores socialLinks, socialNames, links and updates them for user "test".
        * json2 object cannot store socialLinks, socialNames, links and updates them for a null user.*/
        json = profileService.updateSocialGames("test", "{\"socialLinks\":[\"http://www.facebook.com/\",\"http://www.twitter.com\"],\"socialNames\":[\"testFacebook\",\"testTwitter\"],\"link\":\"testLink\"}");
        json2 = profileService.updateSocialGames(null, "{\"socialLinks\":[\"http://www.facebook.com/\",\"http://www.twitter.com\"],\"socialNames\":[\"testFacebook\",\"testTwitter\"],\"link\":\"testLink\"}");

        /*Assert that json is an instance of the JsonObject class.
        * AssertTrue that a username that is not null returns true and updates socialGames and socialLinks.
        * AssertFalse that a username that is null returns false.*/
        assertThat(json, instanceOf(JsonObject.class));
        assertTrue(json.get("result").getAsBoolean());
        assertFalse(json2.get("result").getAsBoolean());

        System.out.println();
    }

    @Test
    public void updateFavGamesTest() throws Exception {
        /*This method tests the updateFavGames method in the ProfileService class.*/

        System.out.println("updateFavGames method test results:");
        System.out.println();

        JsonObject json;
//        JsonObject json2;

        when(nexusDB.updateUserProfileLists("test", "favGames", "gameName", "gameLink", resultProfile.getFavGameNames(),
                resultProfile.getFavGameLinks())).thenReturn(resultProfile);

        /*json object stores favorite games into user "test".*/
        json = profileService.updateFavGames("test", "{\"favGameNames\":[\"League of Legends\",\"Diablo 3\"],\"favGameLinks\":[\"testLink1\",\"testLink2\"]}");
//        json2 = profileService.updateFavGames(null, "{\"favGameNames\":[\"League of Legends\",\"Diablo 3\"],\"favGameLinks\":[\"testLink1\",\"testLink2\"]}");

        /*Assert that json is an instance of the JsonObject class.
        * AssertTrue that a username that is not null returns true and updates favorite games for the user.
        * AssertFalse that a username that is null returns false.*/
        assertThat(json, instanceOf(JsonObject.class));
        assertTrue(json.get("result").getAsBoolean());
//        assertFalse(json2.get("result").getAsBoolean());

        System.out.println();
    }

    @Test
    public void updateCurrentGamesTest() throws Exception {
        /*This method tests the updateCurrentGames method in the ProfileService class.*/

        System.out.println("updateCurrentGames method test results:");
        System.out.println();

        JsonObject json, json2;

        when(nexusDB.updateLOL("test", "leagueSummoner")).thenReturn(jsonObject);
        when(nexusDB.updateCSGO("test", "csgoCharacter")).thenReturn(jsonObject);
        when(nexusDB.updateDiablo3("test", "diabloCharacter")).thenReturn(jsonObject);
        when(nexusDB.updateWOW("test", "warCraftCharacter", "realmTest")).thenReturn(jsonObject);

        /*json object stores current games to update with their own sub-keys into "test" user.
        * json2 object should not store current games into null user.*/
        json = profileService.updateCurrentGames("test",
                "{\"0\":{\"name\":\"League of Legends\",\"leagueSummoner\":\"test\"}," +
                        "\"1\":{\"name\":\"CS:GO\"}," +
                        "\"2\":{\"name\":\"World of Warcraft\",\"warcraftCharacter\":\"test\",\"warcraftRealm\":\"test\"}}");
        json2 = profileService.updateCurrentGames(null,
                "{\"0\":{\"name\":\"League of Legends\",\"leagueSummoner\":\"test\"}," +
                        "\"1\":{\"name\":\"CS:GO\"}," +
                        "\"2\":{\"name\":\"World of Warcraft\",\"warcraftCharacter\":\"test\",\"warcraftRealm\":\"test\"}}");

        /*Assert that json is an instance of the JsonObject class.
        * AssertTrue that a username that is not null returns true and updates current games for the user.
        * AssertFalse that a username that is null returns false.*/
        assertThat(json, instanceOf(JsonObject.class));
        assertTrue(json.get("result").getAsBoolean());
        assertFalse(json2.get("result").getAsBoolean());

        System.out.println();

    }

    @Test
    public void updateUserDescTest() throws Exception {
        /*This method tests the updateUserDesc method in the ProfileService class.*/

        System.out.println("updateUserDesc method test results:");
        System.out.println();

        JsonObject json, json2;

        when(nexusDB.updateUserProfile("test", "testing", resultProfile.getUserDesc())).thenReturn("testing");

        /*json object stores the mocked nexusDB account userDesc "testing" into user "test".
        * json2 object stores the mocked nexusDB account userDesc "testing into null user.*/
        json = profileService.updateUserDesc("test", "{\"userDesc\":\"testing\"}");
        json2 = profileService.updateUserDesc(null, "{\"userDesc\":\"testing\"}");

        /*Assert that json is an instance of the JsonObject class.
        * AssertTrue that a username that is not null returns true and changes userDesc to "testing".
        * AssertFalse that a username that is null returns false.*/
        assertThat(json, instanceOf(JsonObject.class));
        assertTrue(json.get("result").getAsBoolean());
        assertFalse(json2.get("result").getAsBoolean());

        System.out.println();
    }

    @Test
    public void updateAvatarTest() throws Exception {
        /*This method tests the updateAvatar method in the ProfileService class.*/

        System.out.println("updateAvatar method test results:");
        System.out.println();

        JsonObject json, json2;
        when(nexusDB.updateUserProfile("test", "linkTest", resultProfile.getAvatar())).thenReturn("linkTest");

        /*json object that will updateAvatar String link example "linkTest for user "test".
        * json2 object that will not updateAvatar String link example "linkTest for a null user.*/
        json = profileService.updateAvatar("test", "{\"avatar\":\"linkTest\"}");
        json2 = profileService.updateAvatar(null, "{\"avatar\":\"testing\"}");

        /*Assert that json is an instance of the JsonObject class.
        * AssertTrue that a username that is not null and exists returns true and changes avatar link.
        * AssertFalse that a username that is null returns false.*/
        assertThat(json, instanceOf(JsonObject.class));
        assertTrue(json.get("result").getAsBoolean());
        assertFalse(json2.get("result").getAsBoolean());

        System.out.println();

    }
}

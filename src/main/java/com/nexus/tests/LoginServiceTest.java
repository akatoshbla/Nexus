package com.nexus.tests;

import com.google.gson.JsonObject;
import com.nexus.LoginService;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This class contains test methods for LoginService.
 *
 * @author Raffi
 */
public class LoginServiceTest {

    LoginService loginService = new LoginService();

    @Test
    public void loginResultTest() throws Exception {
        /*Tests loginResult method in LoginService class to check if an account is logged in.*/

        System.out.println("loginResult method test results:");
        System.out.println();

        JsonObject test = loginService.loginResult("{\"username\":\"test\",\"password\":\"221b37fcdb52d0f7c39bbd0be211db0e1c00ca5fbecd5788780463026c6b964b\"}");
        assertTrue(test.get("result").getAsBoolean());

        System.out.println();
    }

    @Test
    public void loginResultTest2() throws Exception {
        /*Tests loginResult method in LoginService class to check if an account that doesn't exist is not logged in.*/
        JsonObject test = loginService.loginResult("{\"username\":\"test1\",\"password\":\"221b37fcdb52d0f7c39bbd0be211db0e1c00ca5fbecd5788780463026c6b964b\"}");
        assertFalse(test.get("result").getAsBoolean());
    }

}

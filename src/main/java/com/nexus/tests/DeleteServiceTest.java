package com.nexus.tests;

import com.google.gson.JsonObject;
import com.nexus.DeleteService;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * This class contains test methods for DeleteService.
 *
 * @author Raffi
 */
public class DeleteServiceTest {

    DeleteService deleteService = new DeleteService();

    @Test
    public void test1() throws Exception {
        /*Tests activateResult method in DeleteService class to check if an account is activated.*/

        System.out.println("activateResult method test results:");
        System.out.println();

        JsonObject test = deleteService.activateResult("{\"username\":\"test\",\"password\":\"123\"}");
        assertTrue(test.get("result").getAsBoolean());

        System.out.println();
    }

    @Test
    public void test2() throws Exception {
        /*Tests activateResult method in DeleteService class to check if an account that doesn't exist
        * is not activated.*/
        JsonObject test = deleteService.activateResult("{\"username\":\"test234\",\"password\":\"123\"}");
        assertFalse(test.get("result").getAsBoolean());

        System.out.println();
    }

    @Test
    public void test3() throws Exception {
        /*Tests deactivateResult method in DeleteService class to check if an account is deactivated.*/

        System.out.println("deactivateResult method test results:");
        System.out.println();

        JsonObject test = deleteService.deactivateResult("{\"username\":\"test\",\"password\":\"123\"}");
        assertTrue(test.get("result").getAsBoolean());

        System.out.println();
    }

    @Test
    public void test4() throws Exception {
        /*Tests deactivateResult method in DeleteService class to check if an account that doesn't
        * exist is not deactivated.*/
        JsonObject test = deleteService.deactivateResult("{\"username\":\"test234\",\"password\":\"123\"}");
        assertTrue(test.get("result").getAsBoolean());
    }
}

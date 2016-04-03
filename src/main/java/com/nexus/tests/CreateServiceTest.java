package com.nexus.tests;

import com.google.gson.JsonObject;
import com.nexus.CreateService;
import org.junit.Test;

import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * This class contains test methods for CreateService.
 *
 * @author Raffi
 */
public class CreateServiceTest {

    CreateService createService = new CreateService();

    @Test
    public void test1() throws Exception {
        /*Tests createResult method in the CreateService class.
        * Checks to make sure that you cannot create a duplicate user.*/

        System.out.println("createResult method test results:");
        System.out.println();

        JsonObject test = createService.createResult("{\"username\":\"test\",\"password\":\"123\"}");
        assertEquals(false, test.get("result").getAsBoolean());

        System.out.println();
    }

    @Test
    public void test2() throws Exception {
        /*Tests createResult method in the CreateService class.
        * Checks to make sure that an object returns a Json.*/

        JsonObject test1 = createService.createResult("{\"username\":\"test1\",\"password\":\"test123\"}");
        assertThat(test1, instanceOf(JsonObject.class));

    }
}

package com.andrijanica.SpringTest1.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static net.bytebuddy.utility.RandomString.make;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
public class GreetingControllerTest {

    @Test
    public void greetingTest_ok() {
        final String name = make(10);
        final String prefix = "Hello ";
        GreetingController greetingController = new GreetingController();
        String result = greetingController.greeting(name);

        assertNotNull(result);
        assertEquals(result, prefix + name);
    }
}

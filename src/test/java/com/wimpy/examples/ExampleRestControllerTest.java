package com.wimpy.examples;

import com.wimpy.examples.logging.ExampleRestController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ExampleRestControllerTest {

    @Autowired
    private ExampleRestController exampleRestController;

    @Test
    public void testingTimingOnMethod() {

        exampleRestController.testingTimingOnMethod();
    }

    @Test
    public void testExceptionHandeling() {

        exampleRestController.testExceptionHandeling();
    }

    @Test
    public void methodLogging() {
        exampleRestController.methodLogging("firstTest", 2, true);
    }

    @Test
    public void testMethodResponse() {

        exampleRestController.methodResponse();
    }

    @Test
    public void superLogging() {
        exampleRestController.superLogging();
    }
}

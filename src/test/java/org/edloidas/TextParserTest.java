package org.edloidas;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TextParserTest {

    private static final Logger LOGGER = Logger.getLogger(TextParserTest.class);

    private static long startTime;
    private static long endTime;

    @BeforeClass
    public static void setUp() {
        startTime = System.currentTimeMillis();
    }

    @Ignore
    @Test(timeout=100)
    public void speedTest() {

    }

    @AfterClass
    public static void tearDown() {
        endTime = System.currentTimeMillis();
        long execTime = endTime - startTime;

        SimpleDateFormat ft = new SimpleDateFormat("hh:mm:ss,SSS");

        LOGGER.info("Start :: " + ft.format(new Date(startTime)));
        LOGGER.info("End   :: " + ft.format(new Date(endTime)));
        LOGGER.info("Exec  :: " + execTime + "ms");

    }
}
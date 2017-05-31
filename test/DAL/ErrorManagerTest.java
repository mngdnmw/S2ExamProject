/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mecaa
 */
public class ErrorManagerTest
{

    public ErrorManagerTest()
    {
    }

    @BeforeClass
    public static void setUpClass()
    {
    }

    @AfterClass
    public static void tearDownClass()
    {
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    /**
     * Test of setErrorCode method, of class ErrorManager.
     */
    @Test
    public void testSetErrorCode()
    {
        System.out.println("setErrorCode");
        int eCode = 10;
        ErrorManager instance = new ErrorManager();
        instance.setErrorCode(eCode);
        int expResult = 10;
        int result = instance.getErrorCode();
        assertEquals(expResult, result);
    }

    /**
     * Test of getErrorString method, of class ErrorManager.
     */
    @Test
    public void testGetErrorString()
    {

        ErrorManager errMan = new ErrorManager();
        errMan.setErrorCode(0);
        String expResult = null;
        String result = errMan.getErrorString();
        System.out.println(errMan.getErrorCode());
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        errMan.setErrorCode(100);
        expResult = "STR_FIRST_TIME_ERROR";
        result = errMan.getErrorString();
        assertEquals(expResult, result);

    }

}

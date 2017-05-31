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
        int eCode = 0;
        ErrorManager instance = new ErrorManager();
        instance.setErrorCode(eCode);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getErrorString method, of class ErrorManager.
     */
    @Test
    public void testGetErrorString()
    {
        System.out.println("getErrorString");
        ErrorManager instance = new ErrorManager();
        String expResult = "";
        String result = instance.getErrorString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

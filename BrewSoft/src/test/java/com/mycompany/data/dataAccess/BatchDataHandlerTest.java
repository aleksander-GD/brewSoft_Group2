/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.data.dataAccess;

import com.mycompany.crossCutting.objects.Batch;
import com.mycompany.crossCutting.objects.MachineState;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author PCATG
 */
public class BatchDataHandlerTest {
    
    public BatchDataHandlerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of insertBatchToQueue method, of class BatchDataHandler.
     */
    @Test
    public void testInsertBatchToQueue() {
        System.out.println("insertBatchToQueue");
        Batch batch = null;
        BatchDataHandler instance = new BatchDataHandler();
        instance.insertBatchToQueue(batch);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLatestBatchID method, of class BatchDataHandler.
     */
    @Test
    public void testGetLatestBatchID() {
        System.out.println("getLatestBatchID");
        BatchDataHandler instance = new BatchDataHandler();
        Integer expResult = null;
        Integer result = instance.getLatestBatchID();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMachineState method, of class BatchDataHandler.
     */
    @Test
    public void testGetMachineState() {
        System.out.println("getMachineState");
        String prodListID = "410";
        BatchDataHandler instance = new BatchDataHandler();
        MachineState expResult = instance.getMachineState("410");
        MachineState result = instance.getMachineState(prodListID);
        assertEquals(expResult, result);
       
        
        
    }

    /**
     * Test of main method, of class BatchDataHandler.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        BatchDataHandler.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
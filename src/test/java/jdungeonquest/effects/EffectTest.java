/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jdungeonquest.effects;

import jdungeonquest.Game;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author User
 */
public class EffectTest {
    
    public EffectTest() {
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
     * Test of doAction method, of class Effect.
     */
    @Test
    public void testDoAction() {
        System.out.println("doAction");
        Game g = null;
        Effect instance = new EffectImpl();
        instance.doAction(g);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class EffectImpl implements Effect {

        public void doAction(Game g) {
        }
    }
}
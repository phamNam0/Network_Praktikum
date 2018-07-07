package TwoDMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class StringMap2DImplTest {

    StringMap2DImplList twoD;

    @Before
    public void setUp() throws Exception {
        twoD = new StringMap2DImplList();

    }

    @Test
    public void testPut() {
        twoD.put("one","two", "three");
        twoD.put("tw","tw", "mmmmmmmmm");
        twoD.put("ne","two", "llllll");
        twoD.put("one","t", "tiiiiiiiiiiiii");
        twoD.put("one", "two", "four");
        assertEquals("four", twoD.get("one","two"));

    }

    @Test
    public void testContains() {
        twoD.put("one","two", "three");
        twoD.put("tw","tw", "mmmmmmmmm");
        twoD.put("ne","two", "llllll");
        twoD.put("one","t", "tiiiiiiiiiiiii");
        twoD.put("one", "two", "four");
        assertEquals(true, twoD.contains("four"));

    }

    @Test
    public void testIsEmpty() {
        twoD.put("one","two", "three");
        twoD.put("tw","tw", "mmmmmmmmm");
        twoD.put("ne","two", "llllll");
        twoD.put("one","t", "tiiiiiiiiiiiii");
        twoD.put("one", "two", "four");
        assertEquals(false, twoD.isEmpty());
        twoD.clear();
        assertEquals(true, twoD.isEmpty());
    }

    @Test
    public void testInterchange() {
        twoD.put("one","two", "three");
        twoD.put("tw","tw", "mmmmmmmmm");
        twoD.put("ne","two", "llllll");
        twoD.put("one","t", "tiiiiiiiiiiiii");
        twoD.put("one", "two", "four");
        twoD.put("two","one","two");
        System.out.println(twoD.size());
        assertEquals("two", twoD.flipped().get("one","two"));
    }



    @After
    public void tearDown() throws Exception {
        twoD.clear();
    }
}
package test;

import material.ordereddictionary.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class OrderedDictionaryTest {
    OrderedDictionary<String, Integer> dict;

    private OrderedDictionary<String, Integer> newDictionary(){
        return new AVLOrderedDict<>();
    }


    @BeforeEach
    void setUp() {
        dict = newDictionary();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void size() {
        assertEquals(dict.size(),0);
        dict.insert("Angel", 9151592);
        assertEquals(dict.size(),1);
        dict.insert("Angel", 9151591);
        assertEquals(dict.size(),2);
        dict.insert("Jose",  9100000);
        assertEquals(dict.size(),3);
    }

    @Test
    void isEmpty() {
        assertTrue(dict.isEmpty());
        dict.insert("Angel", 9151592);
        assertFalse(dict.isEmpty());
    }

    @Test
    void find() {
        dict.insert("Angel", 9151592);
        dict.insert("Angel", 9151591);
        dict.insert("Jose",  9100000);
        Entry<String,Integer> contacto = dict.find("Jose");
        assertEquals(contacto.getValue().intValue(),9100000);
    }

    @Test
    void findAll() {
        int [] telefono = {9151592,9151591,9151593};
        dict.insert("Angel", telefono[0]);
        dict.insert("Angel", telefono[1]);
        dict.insert("Jose",  telefono[2]);
        TreeSet <Integer> cjtoTelefonos = new TreeSet<Integer>();
        for (int cont = 0; cont < 3; cont++)
            cjtoTelefonos.add(telefono[cont]);

        Iterable<Entry <String,Integer>> it = dict.findAll("Angel");
        for (Entry <String,Integer> contacto : it) {
            assertTrue(cjtoTelefonos.contains(contacto.getValue()));
        }
    }

    @Test
    void insert() {
        Random random = new Random(0);
        final int N=1000;
        for (int cont = 0; cont < N; cont++) {
            dict.insert(Integer.toString(random.nextInt(N)), cont);
        }
    }

    @Test
    void remove() {
        int [] telefono = {9151592,9151591,9151593};
        dict.insert("Angel", telefono[0]);
        dict.insert("Angel", telefono[1]);
        dict.insert("Jose",  telefono[2]);
        Entry <String,Integer> e1 = dict.find("Jose");
        dict.remove(e1);
        Entry <String,Integer> f1 = dict.find("Jose");
        assertNull(f1);
        assertEquals(dict.size(),2);
        Entry <String,Integer> e2 = dict.find("Angel");
        dict.remove(e2);
        assertEquals(dict.size(),1);
        Entry <String,Integer> e3 = dict.find("Angel");
        dict.remove(e3);
        assertEquals(dict.size(),0);
    }

    @Test
    void findRange() {
        throw new RuntimeException("Not implemented.");
    }
}
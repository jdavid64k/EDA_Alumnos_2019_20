package test;

import material.maps.Entry;
import material.maps.HashTableMapLP;
import material.maps.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class HashTableMapTest {
    Integer output, oldValue;
    Map<String, Integer> map;


    public static <K,V> Map<K, V> newTestMapInstance() {
        return new HashTableMapLP<>();
    }

    public <K,V> Map<K, V> newTestMapInstance(int capacity) {
        return new HashTableMapLP<>(capacity);
    }

    @BeforeEach
    void setUp() {
        map = newTestMapInstance();
        output = null;
        oldValue = null;
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void size() {
        assertEquals(0, map.size());

        map.put("Jose", 912127001);
        map.put("Angel", 912127002);
        map.put("Abraham", 912127003);
        assertEquals(3, map.size());
        map.put("Mayte", 912127004);
        map.put("Raul", 912127005);
        map.put("Jesus", 91212006);
        map.put("Juan", 912127006);
        assertEquals(7, map.size());

        map.remove("Angel");
        map.remove("Mayte");
        assertEquals(5, map.size());
    }

    @Test
    void isEmpty() {
        assertTrue(map.isEmpty());
        map.put("Jesus", 91212006);
        assertFalse(map.isEmpty());
        map.remove("Jesus");
        assertTrue(map.isEmpty());
    }

    @Test
    void put() {
        Exception e = assertThrows(IllegalStateException.class, () -> map.put(null, 0));
        assertEquals("Invalid key: null.", e.getMessage());

        oldValue = map.put("Jesus", 91212006);
        output = map.get("Jesus");
        assertEquals(91212006, output.intValue());
        assertNull(oldValue);

        oldValue = map.put("Jesus", 91212007);

        output = map.get("Jesus");
        assertEquals(91212007, output.intValue());
        assertEquals(91212006, oldValue.intValue());

        map.put("Jose", 912127001);
        map.put("Angel", 912127002);
        map.put("Mayte", 912127004);
        map.put("Raul", 912127005);
        map.put("Juan", 912127006);
        map.put("Abraham", 912127003);
        assertEquals(7, map.size());


    }

    @Test
    void get() {
        Exception e = assertThrows(IllegalStateException.class, () -> map.get(null));
        assertEquals("Invalid key: null.", e.getMessage());

        output = map.get("Abraham");
        assertNull(output);

        map.put("Abraham", 912127003);
        output = map.get("Abraham");
        assertEquals(912127003, output.intValue());

    }

    @Test
    void remove() {
        Exception e = assertThrows(IllegalStateException.class, () -> map.get(null));
        assertEquals("Invalid key: null.", e.getMessage());

        map.put("Jose", 912127001);
        map.put("Angel", 912127002);
        assertEquals(2, map.size());


        oldValue = map.remove("Abraham");
        assertEquals(2, map.size());
        assertNull(oldValue);

        oldValue = map.remove("Jose");
        assertEquals(1, map.size());
        assertEquals(912127001, oldValue.intValue());
        output = map.get("Jose");
        assertNull(output);

        oldValue = map.remove("Angel");
        assertEquals(0, map.size());
        assertEquals(912127002, oldValue.intValue());
        output = map.get("Angel");
        assertNull(output);
        assertTrue(map.isEmpty());
    }

    @Test
    void keys() {
        String[] nombres = new String[]{"Jose", "Angel", "Abraham", "Mayte", "Raul", "Jesus", "Juan"};
        List<String> list = new LinkedList<>(Arrays.asList(nombres));

        map.put("Jose", 912127001);
        map.put("Angel", 912127002);
        map.put("Abraham", 912127003);
        map.put("Mayte", 912127004);
        map.put("Raul", 912127005);
        map.put("Jesus", 91212006);
        map.put("Juan", 912127006);
        assertEquals(list.size(), map.size());

        for (String s : map.keys()) {
            assertTrue(list.contains(s));
            list.remove(s);
        }
        assertTrue(list.isEmpty());

    }

    @Test
    void values() {
        Integer[] values = new Integer[]{912127001, 912127002, 912127003, 912127004, 912127005, 912127006, 912127006};
        List<Integer> list = new LinkedList<>(Arrays.asList(values));

        map.put("Jose", 912127001);
        map.put("Angel", 912127002);
        map.put("Abraham", 912127003);
        map.put("Mayte", 912127004);
        map.put("Raul", 912127005);
        map.put("Jesus", 912127006);
        map.put("Juan", 912127006);
        assertEquals(list.size(), map.size());

        for (Integer integer : map.values()) {
            assertTrue(list.contains(integer));
            list.remove(integer);
        }
        assertTrue(list.isEmpty());

    }

    @Test
    void entries() {
        Integer[] values = new Integer[]{912127001, 912127002, 912127003, 912127004, 912127005, 912127006, 912127006};
        List<Integer> integerList = new LinkedList<>(Arrays.asList(values));
        String[] names = new String[]{"Jose", "Angel", "Abraham", "Mayte", "Raul", "Jesus", "Juan"};
        List<String> stringList = new LinkedList<>(Arrays.asList(names));

        map.put("Jose", 912127001);
        map.put("Angel", 912127002);
        map.put("Abraham", 912127003);
        map.put("Mayte", 912127004);
        map.put("Raul", 912127005);
        map.put("Jesus", 912127006);
        map.put("Juan", 912127006);
        assertEquals(stringList.size(), map.size());

        for (Entry<String, Integer> entry : map.entries()) {
            assertTrue(stringList.contains(entry.getKey()));

            int indexOfKey = stringList.indexOf(entry.getKey());
            stringList.remove(indexOfKey);
            output = integerList.remove(indexOfKey);
            assertEquals(output.intValue(), entry.getValue().intValue());
        }
        assertTrue(stringList.isEmpty());

    }

    @Test
    void rehash() {
        map = newTestMapInstance(100);
        int n = 1000;
        for (int i = 0; i < n; i++) {
            map.put(Integer.toString(i), i);
            assertNull(output);
        }
        assertEquals(n, map.size());
        for (int i = 0; i < n; i++) {
            Integer integer = map.get(Integer.toString(i));
            assertNotNull(integer);
            assertEquals(i, integer.intValue());
        }
    }

    @Test
    void collisions() {
        map = newTestMapInstance(100);
        int n = 2000;
        int n2 = 500;

        for (int i = 0; i < n; i++)
            output = map.put(Integer.toString(i), i);

        //insert n elements [0, n)
        for (int i = n / 2; i < n; i++) {
            output = map.remove(Integer.toString(i));
            assertNotNull(output);
            assertEquals(i, output.intValue());
        }

        //remove n/2 elements
        for (int i = 0; i < n / 2; i++) {
            output = map.get(Integer.toString(i));
            assertNotNull(output);
            assertEquals(i, output.intValue());
        }

        //insert n2 new elements [n,n+n2)
        for (int i = 0; i < n2; i++) {
            map.put(Integer.toString(i + n), i + n);
        }

        //check first batch
        for (int i = 0; i < n / 2; i++) {
            Integer integer = map.get(Integer.toString(i));
            assertNotNull(integer);
            assertEquals(i, integer.intValue());
        }

        //check second batch
        for (int i = 0; i < n2; i++) {
            Integer integer = map.get(Integer.toString(i + n));
            assertNotNull(integer);
            assertEquals(i + n, integer.intValue());
        }

    }


    @Test
    void forced_collitions(){
        class EvilHashCodeObject {
            //Hashcode and Equals should be coherent:
            // if we use both id and value to compare equality
            // then we should use those same fields to generate a hashcode

            //This hashCode and equals implementation is "evil" and generates
            //unnecessary collisions in the hash table. It's very easy to
            //generate different objects with the same hash code.

            // We'll use this "evil" implementation to force collisions
            // in the map

            private Integer id;
            private Integer value;

            private EvilHashCodeObject(int id, int value){
                this.id = id;
                this.value = value;
            }

            @Override
            public int hashCode(){
                return this.id.hashCode();
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                EvilHashCodeObject badHash = (EvilHashCodeObject) o;
                return Objects.equals(id, badHash.id) &&
                        Objects.equals(value, badHash.value);
            }

            @Override
            public String toString(){
                return "BHO(id="+this.id+", v="+this.value+")";
            }
        }

        Map<EvilHashCodeObject, Integer> map1 = newTestMapInstance();
        final int N = 10;
        final int M = 5; //forced collitions per item
        List<EvilHashCodeObject> allList = new ArrayList<>(N*M);
        for (int j = 0; j < M; j++) {
            for (int i = 0; i < N; i++) {
                EvilHashCodeObject bh = new EvilHashCodeObject(i,i+1000*j);
                map1.put(bh, i+1000*j);
                allList.add(bh);
            }
        }
        assertEquals(N*M, map1.size());
        Collections.shuffle(allList);

        for (EvilHashCodeObject e : allList) {
            Integer removed = map1.remove(e);
            assertNotNull(removed);
            assertEquals(e.value, removed);
        }
        assertTrue(map1.isEmpty());

    }
}
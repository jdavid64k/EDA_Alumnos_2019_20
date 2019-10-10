package test;

import material.Position;
import material.tree.narytree.LinkedTree;

import static org.junit.jupiter.api.Assertions.*;

class LinkedTreeTest {

    private LinkedTree<Integer> tree;
    private Position<Integer>[] pos;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        tree = new LinkedTree<>();
        pos = new Position[12];
        pos[0]  = tree.addRoot(0);
        pos[1]  = tree.add(1,  pos[0]);
        pos[2]  = tree.add(2,  pos[0]);
        pos[3]  = tree.add(3,  pos[0]);
        pos[4]  = tree.add(4,  pos[0]);
        pos[5]  = tree.add(5,  pos[1]);
        pos[6]  = tree.add(6,  pos[2]);
        pos[7]  = tree.add(7,  pos[2]);
        pos[8]  = tree.add(8,  pos[3]);
        pos[9]  = tree.add(9,  pos[7]);
        pos[10] = tree.add(10, pos[7]);
        pos[11] = tree.add(11, pos[7]);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        // Not required
    }

    @org.junit.jupiter.api.Test
    void size() {
        assertEquals(12, tree.size());
        tree.remove(pos[2]);
        assertEquals(6, tree.size());
        tree.remove(pos[0]);
        assertEquals(0, tree.size());
    }

    @org.junit.jupiter.api.Test
    void isEmpty() {
        assertFalse(tree.isEmpty());
        tree.remove(pos[0]);
        assertTrue(tree.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void isInternal() {
        for (int i = 0; i < pos.length; i++) {
            if (i == 5 || i == 6 || i == 9 || i == 10 || i == 11 || i == 8 || i == 4) {
                assertFalse(tree.isInternal(pos[i]));
            } else {
                assertTrue(tree.isInternal(pos[i]));
            }
        }
    }

    @org.junit.jupiter.api.Test
    void isLeaf() {
        for (int i = 0; i < pos.length; i++) {
            if (i == 5 || i == 6 || i == 9 || i == 10 || i == 11 || i == 8 || i == 4) {
                assertTrue(tree.isLeaf(pos[i]));
            } else {
                assertFalse(tree.isLeaf(pos[i]));
            }
        }
    }

    @org.junit.jupiter.api.Test
    void isRoot() {
        assertTrue(tree.isRoot(pos[0]));
        for (int i = 1; i < pos.length; i++) {
            assertFalse(tree.isRoot(pos[i]));
        }
    }

    @org.junit.jupiter.api.Test
    void root() {
        assertEquals(pos[0], tree.root());
        for (int i = 1; i < pos.length; i++) {
            assertNotEquals(pos[i], tree.root());
        }
    }

    @org.junit.jupiter.api.Test
    void rootExcept() {
        tree.remove(pos[0]);
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> tree.root());
        assertEquals("The tree is empty", thrown.getMessage());
    }

    @org.junit.jupiter.api.Test
    void parent() {
        for (int i=1;i<=4;i++) {
            assertEquals(pos[0], tree.parent(pos[i]));
        }
        for (int i=6;i<=7;i++) {
            assertEquals(pos[2], tree.parent(pos[i]));
        }
    }

    @org.junit.jupiter.api.Test
    void parentExcept() {
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> tree.parent(pos[0]));
        assertEquals("The node has not parent", thrown.getMessage());
    }

    @org.junit.jupiter.api.Test
    void children() {
        int n = 0;
        for (Position<Integer> child : tree.children(pos[0])) {
            assertEquals(pos[0], tree.parent(child));
            assertTrue(child == pos[1] || child == pos[2] || child == pos[3] || child == pos[4]);
            n++;
        }
        assertEquals(4, n);
        n = 0;
        for (Position<Integer> child : tree.children(pos[7])) {
            assertEquals(pos[7], tree.parent(child));
            assertTrue(child == pos[9] || child == pos[10] || child == pos[11]);
            n++;
        }
        assertEquals(3, n);
        n = 0;
        for (Position<Integer> child : tree.children(pos[9])) {
            n++;
        }
        assertEquals(0, n);
    }

    @org.junit.jupiter.api.Test
    void replace() {
        tree.replace(pos[0], 500);
        assertEquals(500, (int) tree.root().getElement());
    }

    @org.junit.jupiter.api.Test
    void addRoot() {
        tree.remove(pos[0]);
        Position<Integer> p = tree.addRoot(200);
        assertEquals(p, tree.root());
    }

    @org.junit.jupiter.api.Test
    void addRootExcept() {
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> tree.addRoot(100));
        assertEquals("Tree already has a root", thrown.getMessage());
    }

    @org.junit.jupiter.api.Test
    void swapElements() {
        tree.swapElements(pos[1], pos[0]);
        assertEquals(1, (int)tree.root().getElement());
        assertEquals(0, (int)pos[1].getElement());
        assertNotEquals(pos[1], tree.root());
        assertEquals(pos[0], tree.root());
    }

    @org.junit.jupiter.api.Test
    void add() {
        Position<Integer> p100 = tree.add(100, pos[0]);
        assertEquals(pos[0], tree.parent(p100));
        int n = 0;
        for (Position<Integer> child : tree.children(pos[0])) {
            n++;
        }
        assertEquals(5, n);
        Position<Integer> p200 = tree.add(200, pos[9]);
        assertEquals(pos[9], tree.parent(p200));
        assertTrue(tree.isInternal(pos[9]));
    }

    @org.junit.jupiter.api.Test
    void remove() {
        tree.remove(pos[2]);
        assertEquals(6, tree.size());
        RuntimeException thrown;
        for (int i = 6; i <= 11 ; i++) {
            if (i == 8) continue;
            int finI = i;
            thrown = assertThrows(RuntimeException.class,
                    () -> tree.parent(pos[finI]));
            assertEquals("The node is not from this tree", thrown.getMessage());
        }

    }

    @org.junit.jupiter.api.Test
    void iterator() {
        int next = 0;
        for (Position<Integer> position : tree) {
            assertEquals(pos[next], position);
            next++;
        }
    }

    @org.junit.jupiter.api.Test
    void moveSubtree() {
        tree.moveSubtree(pos[7], pos[3]);
        assertEquals(tree.parent(pos[7]), pos[3]);
        for (Position<Integer> p : tree.children(pos[2])) {
            assertNotEquals(pos[7], p);
        }
        boolean found = false;
        for (Position<Integer> p : tree.children(pos[3])) {
            if (p == pos[7]) {
                found = true;
                break;
            }
        }
        assertTrue(found);
    }

    @org.junit.jupiter.api.Test
    void moveSubtreeExcept_Root() {
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> tree.moveSubtree(pos[0], pos[8]));
        assertEquals("Root node can't be moved", thrown.getMessage());
    }

    @org.junit.jupiter.api.Test
    void moveSubtreeExcept_Subtree() {
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> tree.moveSubtree(pos[2], pos[10]));
        assertEquals("Target position can't be a sub tree of origin", thrown.getMessage());
    }

    @org.junit.jupiter.api.Test
    void moveSubtreeExcept_Subtree_SamePositions() {
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> tree.moveSubtree(pos[4], pos[4]));
        assertEquals("Both positions are the same", thrown.getMessage());
    }


}
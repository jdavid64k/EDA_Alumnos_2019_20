package test;

import material.Position;
import material.tree.binarytree.BinaryTree;
import material.tree.binarytree.LinkedBinaryTree;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

class LinkedBinaryTreeTest {

    private LinkedBinaryTree<Character> tree;
    private Position<Character>[] pos;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        tree = new LinkedBinaryTree<>();
        pos = new Position[11];
        pos[0] = tree.addRoot('*');
        pos[1] = tree.insertLeft(pos[0], '+');
        pos[2] = tree.insertRight(pos[0], '/');
        pos[3] = tree.insertLeft(pos[1], '3');
        pos[4] = tree.insertRight(pos[1], '-');
        pos[5] = tree.insertLeft(pos[4], '5');
        pos[6] = tree.insertRight(pos[4], '1');
        pos[7] = tree.insertLeft(pos[2], '8');
        pos[8] = tree.insertRight(pos[2], '+');
        pos[9] = tree.insertLeft(pos[8], '3');
        pos[10] = tree.insertRight(pos[8], '1');
    }

    @org.junit.jupiter.api.Test
    void size() {
        assertEquals(11, tree.size());
        tree.remove(pos[3]);
        assertEquals(10, tree.size());
    }

    @org.junit.jupiter.api.Test
    void isEmpty() {
        assertFalse(tree.isEmpty());
        LinkedBinaryTree<Integer> t = new LinkedBinaryTree<>();
        assertTrue(t.isEmpty());
        t.addRoot(1);
        assertFalse(t.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void isInternal() {
        for (int i = 0; i < pos.length; i++) {
            if (i == 3 || i == 5 || i == 6 || i == 7 || i == 9 || i == 10) {
                assertFalse(tree.isInternal(pos[i]));
            } else {
                assertTrue(tree.isInternal(pos[i]));
            }
        }
    }

    @org.junit.jupiter.api.Test
    void isLeaf() {
        for (int i = 0; i < pos.length; i++) {
            if (i == 3 || i == 5 || i == 6 || i == 7 || i == 9 || i == 10) {
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
    void hasLeft() {
        assertTrue(tree.hasLeft(pos[0]));
        assertTrue(tree.hasLeft(pos[2]));
        assertFalse(tree.hasLeft(pos[3]));
        assertFalse(tree.hasLeft(pos[7]));
    }

    @org.junit.jupiter.api.Test
    void hasRight() {
        assertTrue(tree.hasRight(pos[1]));
        assertTrue(tree.hasRight(pos[8]));
        assertFalse(tree.hasRight(pos[6]));
        assertFalse(tree.hasRight(pos[10]));
    }

    @org.junit.jupiter.api.Test
    void root() {
        assertEquals(pos[0], tree.root());
    }

    @org.junit.jupiter.api.Test
    void rootExcept() {
        LinkedBinaryTree<Integer> t = new LinkedBinaryTree<Integer>();
        RuntimeException thrown = assertThrows(RuntimeException.class,
                t::root);
        assertEquals("The tree is empty", thrown.getMessage());
    }

    @org.junit.jupiter.api.Test
    void left() {
        assertEquals(pos[1], tree.left(pos[0]));
        assertEquals(pos[5], tree.left(pos[4]));
    }

    @org.junit.jupiter.api.Test
    void leftExcept() {
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> tree.left(pos[3]));
        assertEquals("No left child", thrown.getMessage());
    }

    @org.junit.jupiter.api.Test
    void right() {
        assertEquals(pos[2], tree.right(pos[0]));
        assertEquals(pos[10], tree.right(pos[8]));
    }

    @org.junit.jupiter.api.Test
    void rightExcept() {
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> tree.right(pos[6]));
        assertEquals("No right child", thrown.getMessage());
    }

    @org.junit.jupiter.api.Test
    void parent() {
        assertEquals(pos[0], tree.parent(pos[1]));
        assertEquals(pos[4], tree.parent(pos[6]));
        assertEquals(pos[8], tree.parent(pos[9]));
    }

    @org.junit.jupiter.api.Test
    void parentExcept() {
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> tree.parent(pos[0]));
        assertEquals("No parent", thrown.getMessage());
    }

    @org.junit.jupiter.api.Test
    void children() {
        int n = 0;
        for (Position<Character> c : tree.children(pos[0])) {
            assertTrue(c == pos[1] || c == pos[2]);
            n++;
        }
        assertEquals(2, n);
        n = 0;
        for (Position<Character> c : tree.children(pos[8])) {
            assertTrue(c == pos[9] || c == pos[10]);
            n++;
        }
        assertEquals(2, n);
        n = 0;
        for (Position<Character> c : tree.children(pos[5])) {
            n++;
        }
        assertEquals(0, n);
    }

    @org.junit.jupiter.api.Test
    void iterator_EmptyTree() {
        LinkedBinaryTree<Character> emptyTree = new LinkedBinaryTree<>();
        Iterator<Position<Character>> iterator = emptyTree.iterator();
        assertFalse(iterator.hasNext());
    }

    @org.junit.jupiter.api.Test
    void iterator() {
        String expected = "3+5-1*8/3+1";
        StringBuilder actual = new StringBuilder();
        for (Position<Character> p : tree) {
            actual.append(p.getElement());
        }
        assertEquals(expected, actual.toString());
    }

    @org.junit.jupiter.api.Test
    void replace() {
        tree.replace(pos[0], '9');
        assertEquals('9', tree.parent(pos[1]).getElement().charValue());
    }

    @org.junit.jupiter.api.Test
    void sibling() {
        assertEquals(pos[2], tree.sibling(pos[1]));
        assertEquals(pos[3], tree.sibling(pos[4]));
        assertEquals(pos[9], tree.sibling(pos[10]));
    }

    @org.junit.jupiter.api.Test
    void siblingExcept() {
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> tree.sibling(pos[0]));
        assertEquals("No sibling", thrown.getMessage());
    }

    @org.junit.jupiter.api.Test
    void addRoot() {
        LinkedBinaryTree<Integer> t = new LinkedBinaryTree<>();
        Position<Integer> p1 = t.addRoot(1);
        assertEquals(p1, t.root());
    }

    @org.junit.jupiter.api.Test
    void addRootExcept() {
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> tree.addRoot('e'));
        assertEquals("Tree already has a root", thrown.getMessage());
    }

    @org.junit.jupiter.api.Test
    void insertLeft() {
        Position<Character> p2 = tree.insertLeft(pos[3], '2');
        assertEquals(p2, tree.left(pos[3]));
    }

    @org.junit.jupiter.api.Test
    void insertLeftExcept() {
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> tree.insertLeft(pos[0], 'e'));
        assertEquals("Node already has a left child", thrown.getMessage());
    }

    @org.junit.jupiter.api.Test
    void insertRight() {
        Position<Character> p2 = tree.insertRight(pos[3], '2');
        assertEquals(p2, tree.right(pos[3]));
    }

    @org.junit.jupiter.api.Test
    void insertRightExcept() {
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> tree.insertRight(pos[0], 'e'));
        assertEquals("Node already has a right child", thrown.getMessage());
    }

    @org.junit.jupiter.api.Test
    void remove() {
        tree.remove(pos[3]);
        assertFalse(tree.hasLeft(pos[1]));
        tree.remove(pos[1]);
        assertEquals(pos[4], tree.left(pos[0]));
        assertEquals(pos[0], tree.parent(pos[4]));
    }

    @org.junit.jupiter.api.Test
    void removeExcept() {
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> tree.remove(pos[0]));
        assertEquals("Cannot remove node with two children", thrown.getMessage());
    }

    @org.junit.jupiter.api.Test
    void subTree() {
        BinaryTree<Character> newTree = tree.subTree(pos[2]);
        assertEquals(pos[2], newTree.root());
        assertFalse(tree.hasRight(pos[0]));
    }

    @org.junit.jupiter.api.Test
    void attachLeft() {
        LinkedBinaryTree<Character> t = new LinkedBinaryTree<>();
        Position<Character> p1 = t.addRoot('-');
        Position<Character> p2 = t.insertLeft(p1, '1');
        Position<Character> p3 = t.insertRight(p1, '2');
        tree.attachLeft(pos[5], t);
        assertEquals(pos[5], tree.parent(p1));
        assertEquals(p1, tree.left(pos[5]));
    }

    @org.junit.jupiter.api.Test
    void attachRight() {
        LinkedBinaryTree<Character> t = new LinkedBinaryTree<>();
        Position<Character> p1 = t.addRoot('-');
        Position<Character> p2 = t.insertLeft(p1, '1');
        Position<Character> p3 = t.insertRight(p1, '2');
        tree.attachRight(pos[5], t);
        assertEquals(pos[5], tree.parent(p1));
        assertEquals(p1, tree.right(pos[5]));
    }

    @org.junit.jupiter.api.Test
    void isComplete() {
        assertTrue(tree.isComplete());
        tree.remove(pos[3]);
        assertFalse(tree.isComplete());

    }


    @org.junit.jupiter.api.Test
    void swap() {
        Character e2 = pos[2].getElement();
        Character e4 = pos[4].getElement();
        tree.swap(pos[2], pos[4]);
        //Each position holds the same element
        assertSame(e2, pos[2].getElement());
        assertSame(e4, pos[4].getElement());
        //check pos1
        assertEquals(tree.parent(pos[2]), pos[1]);
        assertEquals(tree.left(pos[2]), pos[5]);
        assertEquals(tree.parent(pos[5]), pos[2]);
        assertEquals(tree.right(pos[2]), pos[6]);
        assertEquals(tree.parent(pos[6]), pos[2]);
        //check pos2
        assertEquals(tree.parent(pos[4]), pos[0]);

        assertEquals(tree.left(pos[4]), pos[7]);
        assertEquals(tree.parent(pos[7]), pos[4]);

        assertEquals(tree.right(pos[4]), pos[8]);
        assertEquals(tree.parent(pos[8]), pos[4]);
    }


}
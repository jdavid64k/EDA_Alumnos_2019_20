package material.ordereddictionary;


import material.tree.binarysearchtree.BinarySearchTree;
import material.tree.binarysearchtree.RBTree;

import java.util.Comparator;


public class RBTOrderedDict<K, V> extends AbstractTreeOrderedDict<K, V> {

	public RBTOrderedDict() {
		super();
	}

	public RBTOrderedDict(Comparator<K> keyComparator) {
		super(keyComparator);
	}
	
	@Override
	protected BinarySearchTree<Entry<K,V>> createTree() {
		return new RBTree<>();
	}
	
}



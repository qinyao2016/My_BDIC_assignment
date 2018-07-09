//
package dsa.impl;

import dsa.iface.IIterator;
import dsa.iface.INode;
import dsa.iface.ITree;
import dsa.impl.AbstractBinaryTree.BTNode;

public class AVLTree<T extends Comparable<T>> extends BinarySearchTree<T> {
	public AVLTree() {
		root = new BTNode(null,null);
		size = 1;
	}
	
   @Override
   public void insert( T element ) {
	   //if the tree is null
	   if(this.root.element == null) {
		   expandExternal(root,element);
		   return; 
	   }
	   BTNode n = (BTNode)root();
	   while(isInternal(n)) {
		   if(element.compareTo(n.element) < 0) {
			   n = n.left;
		   }else if(element.compareTo(n.element)> 0) {
			   n = n.right;
		   }else {
			   System.out.println("problem");
		   }
	    }
	   expandExternal(n,element);
	   size += 2;
	   insert_restructure(n);
	}

   @Override
   public boolean contains( T element ) {
	   INode<T> n = find(root(),element);
	   if(isInternal(n))
		   return true;
	   else {
		   return false;
	   }
   } 

   @Override
   public void remove( T element ) {
	   BTNode n = (BTNode)find(root(),element);
	   if(n == null) {
		   System.out.println("Sorry,the tree doesn't contain " + element);
		   return;
	   }
	   size -= 2;
	   //if n has at least one empty child
	   if(isExternal(right(n)) || isExternal(left(n))) {
		   if(isRoot(n)) {
			   if(isExternal(n.left)) {
				   root = n.right;
				   root.parent = null;
			   }	else {
				   root = n.left;
				   root.parent = null;
			   }
		   }
		   else if(isInternal(n.left) && isExternal(n.right)) {
			   BTNode a = n.left;
			   if(n == n.parent.right) {
				   n.parent.right = a;
				   a.parent = n.parent;
			   }else {
				   n.parent.left = a;
				   a.parent = n.parent;
			   } 
			   restructure(a.parent);

		   }
		   else{
			   BTNode a = n.right;
			   if(n == n.parent.right) {
				   n.parent.right = a;
				   a.parent = n.parent;
			   }else{
				   n.parent.left = a;		
				   a.parent = n.parent;

			   }	
			   restructure(a.parent);

		   }
	   }
	   //if both child of n are internal

	   else{
		   BTNode r = (BTNode)right(n);
		   while(r.left.element != null) {
			   r = (BTNode)left(r);
		   }
		   replace(n,r.element());
		   BTNode a = r.right;
		   if(r == r.parent.right) {
			   r.parent.right = a;
			   a.parent = r.parent;
		   }else{
			   r.parent.left = a;
			   a.parent = r.parent;
		   }	

	   
		   restructure(r.parent);
	   }
   }
  //responsible for z
  //                    y
  //                         x
   private void single_rightrotate(BTNode x) {
	   BTNode y = x.parent;
	   BTNode z = y.parent;
	   BTNode T1 = y.left;
	   if(z == root) {
		   root = y;
		   y.left = z;
		   z.parent = y;
		   z.right = T1;
		   T1.parent = z;
		   y.parent = null;
	   }else {
		   if(z.parent.right == z) {
			   y.parent = z.parent;
			   z.parent.right = y;
			   y.left = z;
			   z.parent = y;
			   z.right = T1;
			   T1.parent = z;
		   }else {
			   y.parent = z.parent;
			   z.parent.left = y;
			   y.left = z;
			   z.parent = y;
			   z.right = T1;
			   T1.parent = z;
		   
			   
		   }
	   }
   }
   //responsible for     z
   //                 y
   //              x
   private void single_leftrotate(BTNode x) {
	   BTNode y = x.parent;
	   BTNode z = y.parent;
	   BTNode T2 = y.right;
	   if(z == root) {
		   root = y;
		   y.right = z;
		   z.parent = y;
		   z.left = T2;
		   T2.parent = z;
		   y.parent = null;
	   }else {
		   if(z.parent.left == z) {
			   y.parent = z.parent;
			   z.parent.left = y;
			   y.right = z;
			   z.parent = y;
			   z.left = T2;
			   T2.parent = z;
		   }else {
			   y.parent = z.parent;
			   z.parent.right = y;
			   y.right = z;
			   z.parent = y;
			   z.left = T2;
			   T2.parent = z;
		   }
	   }
   
   }
 //responsible for z
 //                    y
 //                x
   private void double_rightrotate(BTNode x) {
	   BTNode y = x.parent;
	   BTNode z = y.parent;
	   BTNode T2 = x.right;
	   x.right = y;
	   y.parent = x;
	   y.left = T2;
	   T2.parent = y;
	   z.right = x;
	   x.parent = z;
	    //now trinode are z
		//                  x    
		//                    y
	   single_rightrotate(y);
	   
   }
   //responsible for    z
   //              y
   //                  x
   private void double_leftrotate(BTNode x) {
	   BTNode y = x.parent;
	   BTNode z = y.parent;
	   BTNode T1 = x.left;
	   x.left = y;
	   y.parent = x;
	   y.right = T1;
	   T1.parent = y;
	   z.left = x;
	   x.parent = z;
	//now trinode are    z
	//                x
	//             y
	   single_leftrotate(y);
   }
   //this is responsible for restructure after remove
   private void restructure( INode<T> a ) {
	   BTNode z = (BTNode) a;
	   if(z.parent == root)
		   return;
	   //balance check continue until the root of the tree has been checked
	   while(z != null) {
		   if(!isBalance(z)) {
			   BTNode y = find_highestchild(z);
			   BTNode x = find_highestchild(y);
			   if(z.right == y && y.left == x) {
				   double_rightrotate(x);
			   }
			   else if(z.right == y && y.right == x) {
				   single_rightrotate(x);
			   }else if(z.left == y && y.left == x) {
				   single_leftrotate(x);
			   }else {
				   double_leftrotate(x);
			   }
		   }
		   z = z.parent;
	   }
   }
   //this is responsible for restructure after insert
   private void insert_restructure( INode<T> a ) {
	   BTNode z = (BTNode) a;
	   if(z.parent == root)
		   return;
	   //balance check continue until the root of the tree has been checked
	   while(z != null) {
		   if(!isBalance(z)) {
			   BTNode y = find_highestchild(z);
			   BTNode x = find_highestchild(y);
			   if(z.right == y && y.left == x) {
				   double_rightrotate(x);
			   }else if(z.right == y && y.right == x) {
				   single_rightrotate(x);
			   }else if(z.left == y && y.left == x) {
				   single_leftrotate(x);
			   }else if(z.left == y && y.right == x){
				   double_leftrotate(x);
			   }
			   //the only difference between insert_restructure and restructure is here
			   break;
		   }
		   z = z.parent;
	   }
   }
   private boolean isBalance( INode<T> x) {
	   BTNode a = (BTNode) x;
	   if(height(this,a.left) - height(this,a.right) > 1 || height(this,a.right) - height(this,a.left) > 1) {
		   return false;
	   }else {
		   return true;
	   }
	}
   public static int height(ITree tree,INode node) {
		if(tree.isExternal(node)) {
			return 0;
		}else {
			IIterator<INode> it3 = tree.children(node);
			int maxHeight = 0;
			while(it3.hasNext()) {
				INode child = it3.next();
				int childHeight = height(tree, child);
				if(maxHeight < childHeight)
					maxHeight = childHeight;
			}
			return maxHeight + 1;
		}
	}
   public void expandExternal(INode<T> n, T e) {
		if(isInternal(n)) {
			throw new RuntimeException("You are expanding an internal node");	
		}else {
			replace(n,e);
			BTNode node = (BTNode) n;
			node.left = new BTNode(null, node);
			node.left.parent = node;
			node.right = new BTNode(null,node);
			node.right.parent = node;
			size += 2;
		}
	}
   public INode<T> find( INode<T> node, T value ) {
	   if(isExternal(node)) {
		   return node;
	   }
	   else {
		   if(node.element().compareTo(value) > 0) {
			   return find(left(node),value);
		   }else if(node.element().compareTo(value) < 0){
			   return find(right(node),value);
		   }else {
			   return node;
		   }
					 
	   }
  }
   public BTNode find_highestchild(BTNode z) {
	   if(height(this, z.left) > height(this,z.right))
		   return z.left;
	   else
		   return z.right;
	   
   }
}

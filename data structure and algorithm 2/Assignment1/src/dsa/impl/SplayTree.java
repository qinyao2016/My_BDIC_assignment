package dsa.impl;

import dsa.iface.IIterator;
import dsa.iface.INode;
import dsa.iface.ITree;
import dsa.impl.AbstractBinaryTree.BTNode;

public class SplayTree<T extends Comparable<T>> extends BinarySearchTree<T> {
   public SplayTree() {
		root = new BTNode(null,null);
		size = 1;
	}
		   
   public void insert( T value ) {
	   //if the tree is null
	   if(this.root.element == null) {
		   expandExternal(root,value); 
		   return; 
	   }
	   BTNode n = (BTNode)root();
	   while(isInternal(n)) { 
		   if(value.compareTo(n.element) < 0) {
			   n = n.left;
		   }else if(value.compareTo(n.element)> 0) {
			   n = n.right;
		   }else {
			   System.out.println("problem");
			   return;
		   }
	    }
	   expandExternal(n,value);
	   size += 2;
	   splay(n);
	}

   public boolean contains( T value ) {
	   BTNode n = (BTNode)find(root(),value);
	   if(isInternal(n)) {
		   splay(n);
		   return true;
	   }
	   else {
		   splay(n.parent);
		   return false;
	   }
   }

   public void remove( T value ) {
	   BTNode n = (BTNode)find(root(),value);
	   if(n == null)
		   return;
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
			   splay(a.parent);

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
			   splay(a.parent);

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

	   
		   splay(r.parent);
	   }
   }

 

   private void splay( INode<T> n ) {
	   BTNode x = (BTNode) n;
	   while(x != root()) {
		   if(x.parent == root()) {
			   x = zig(x);
		   }else if((x.parent.left == x && x.parent.parent.left == x.parent )|| (x.parent.right == x && x.parent.parent.right == x.parent)) {
			   x = zig_zig(x);
		   }else {
			   x = zig_zag(x);
		   }
	   }
   }
   public BTNode zig(BTNode x) {
	   BTNode y = x.parent;
	   //   y
	   //x
	   if(x.parent.left == x) {
		   BTNode T3 = x.right;
		   x.right = y;
		   y.parent = x;
		   y.left = T3;
		   T3.parent = y;
	   }
	   //y
	   //   x
	   else {
		   BTNode T2 = x.left;
		   x.left = y;
		   y.parent = x;
		   y.right = T2;
		   T2.parent = y;
	   }
	   root = x;
	   return x;
   }
   public BTNode zig_zig(BTNode x) {
	   BTNode y = x.parent;
	   BTNode z = y.parent;
	   //if z is root, firstly we should change the root of the tree to x
	   if(z == root) {
		   root = x;
		   x.parent = null;
	   }
	   //if z is not the root of the tree, firstly we should change the parent of x 
	   //because after restructure, x should be the top node among the three nodes
	   else{
		   if(z.parent.right == z) {
			   x.parent = z.parent;
			   z.parent.right = x;
		   }else {
			   x.parent = z.parent;
			   z.parent.left = x;
		   }
		}
	   //secondly, change the relation among x,y and z
	   //       z
	   //    y
	   // x
	   if(y.left == x && z.left == y ) {
		   BTNode T2 = x.right;
		   BTNode T3 = y.right;
		   x.right = y;
		   y.parent = x;
		   y.left = T2;
		   T2.parent = y;
		   y.right = z;
		   z.parent = y;
		   z.left = T3;
		   T3.parent = z;
	   }
	   //z
	   //   y
	   //       x
	   else {
		   BTNode T2 = y.left;
		   BTNode T3 = x.left;
		   x.left = y;
		   y.parent = x;
		   y.right = T3;
		   T3.parent = y;
		   y.left = z;
		   z.parent = y;
		   z.right = T2;
		   T2.parent = z;
	   }
	   return x;
   }
   public BTNode zig_zag(BTNode x){ 
	   BTNode y = x.parent;
	   BTNode z = y.parent;
	   //if z is root, firstly we should change the root of the tree to x
	   if(z == root) {
		   root = x;
		   x.parent = null;
	   }
	   //if z is not the root of the tree, firstly we should change the parent of x 
	   //because after restructure, x should be the top node among the three nodes 
	   else {
		   if(z.parent.right == z) {
			   x.parent = z.parent;
			   z.parent.right = x;
		   }else {
			   x.parent = z.parent;
			   z.parent.left = x;
		   }
	   }
	   //secondly, change the relation among x,y and z
	   //       z
	   //           y
	   //        x
	   if(z.right == y && y.left == x) {
		   BTNode T2 = x.left;
		   BTNode T3 = x.right;
		   x.left = z;
		   z.parent = x;
		   x.right = y;
		   y.parent = x;
		   z.right = T2;
		   T2.parent = z;
		   y.left = T3;
		   T3.parent = y;
	   }
	   //      z
	   //   y
	   //       x
	   else {
		   BTNode T2 = x.left;
		   BTNode T3 = x.right;
		   x.left = y;
		   y.parent = x;
		   x.right = z;
		   z.parent = x;
		   y.right = T2;
		   T2.parent = y;
		   z.left = T3;
		   T3.parent = z;
	   }
	   return x;
   }
   public void expandExternal(INode<T> n, T e) {
		if(isInternal(n)) {
			throw new RuntimeException("You are expanding an internal node");	
		}else {
			replace(n,e);
			BTNode node = (BTNode) n;
			node.left = new BTNode(null, node);
			node.right = new BTNode(null,node);
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
}

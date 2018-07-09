package dsa.impl;

import dsa.iface.IBinaryTree;
import dsa.iface.IIterator;
import dsa.iface.IList;
import dsa.iface.INode;
import dsa.iface.IQueue;

public abstract class AbstractBinaryTree<T> implements IBinaryTree<T> {

   protected BTNode root;
   protected int size;

   @Override
   public INode<T> root() {
      if ( root == null )
         throw new RuntimeException( "This tree has no root." );
      return root;
   }

   @Override
   public INode<T> parent( INode<T> n ) {
      BTNode node = (BTNode) n;
      if ( node == null )
         throw new RuntimeException( "Cannot find parent of null node." );
      return node.parent;
   }

   @Override
   public IIterator<INode<T>> children( INode<T> n ) {
      BTNode node = (BTNode) n;
      IList<INode<T>> l = new SLinkedList<INode<T>>();
      if ( isInternal( n ) ) {
         l.insertLast( node.left );
         l.insertLast( node.right );
      }
      return l.iterator();
   }

   @Override
   public boolean isInternal( INode<T> n ) {
      return hasLeft( n ) || hasRight( n );
   }

   @Override
   public  boolean isExternal( INode<T> n ) {
      return !hasLeft( n ) && !hasRight( n );
   }

   @Override
   public boolean isRoot( INode<T> n ) {
      return root == n;
   }

   @Override
   public int size() {
      return size;
   }

   @Override
   public boolean isEmpty() {
      return size == 0;
   }

   @Override
   public IIterator<T> iterator() {
      IList<T> elements = new SLinkedList<T>();
      IIterator<INode<T>> it = nodes();
      while ( it.hasNext() ) {
         INode<T> n = it.next();
         if ( isInternal( n ) ) {
            elements.insertLast( n.element() );
         }
      }
      return elements.iterator();
   }

   @Override
   public IIterator<INode<T>> nodes() {
      IQueue<INode<T>> toVisit = new LinkedQueue<INode<T>>();
      toVisit.enqueue( root() );
      IList<INode<T>> nodes = new SLinkedList<INode<T>>();
      while ( !toVisit.isEmpty() ) {
         INode<T> n = (INode<T>) toVisit.dequeue();
         nodes.insertLast( n );
         IIterator<INode<T>> it = children( n );
         while ( it.hasNext() ) {
            toVisit.enqueue( it.next() );
         }
      }
      return nodes.iterator();
   }

   @Override
   public T replace( INode<T> n, T e ) {
      T toReturn = n.element();
      ( (BTNode) n ).element = e;
      return toReturn;
   }

   @Override
   public INode<T> left( INode<T> n ) {
      BTNode node = (BTNode) n;
      if ( node.left == null )
         throw new RuntimeException( "This node does not have left child." );
      return node.left;
   }

   @Override
   public INode<T> right( INode<T> n ) {
      BTNode node = (BTNode) n;
      if ( node.right == null )
         throw new RuntimeException( "This node does not have right child." );
      return node.right;
   }

   @Override
   public boolean hasLeft( INode<T> n ) {
      return ( (BTNode) n ).left != null;
   }

   @Override
   public boolean hasRight( INode<T> n ) {
      return ( (BTNode) n ).right != null;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      buildString( root, "", sb );
      return sb.toString();
   }

   private void buildString( INode<T> n, String offset, StringBuilder sb ) {
      if ( n == null )
         return;
      sb.append( offset );
      sb.append( n.element() == null ? "[]" : n.element() );
      sb.append( "\n" );
      if ( hasLeft( n ) )
         buildString( left( n ), offset + "\t", sb );
      if ( hasRight( n ) )
         buildString( right( n ), offset + "\t", sb );
   }

   protected BTNode newNode( T element, BTNode parent ) {
      return new BTNode( element, parent );
   }

   /*
    * Inner class to represent a node that's part of a linked binary tree
    */
   protected class BTNode implements INode<T> {
      public BTNode parent;
      public BTNode left, right;
      public T element;

      public BTNode( T e, BTNode p ) {
         this( e, p, null, null );
      }

      public BTNode( T e, BTNode p, BTNode l, BTNode r ) {
         element = e;
         parent = p;
         left = l;
         right = r;
      }

      public T element() {
         return element;
      }
   }
}

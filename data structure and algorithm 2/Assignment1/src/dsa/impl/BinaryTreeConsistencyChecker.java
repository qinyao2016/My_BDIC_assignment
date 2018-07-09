package dsa.impl;
import java.util.HashSet;
import java.util.Set;

import dsa.iface.IBinaryTree;
import dsa.iface.INode;

/**
 * 
 * Check if the references in your tree are correct.
 * 
 * Will print useful messages to tell you when your tree structure is incorrect.
 *
 * How to use:
 * 
 * 1) Create a new instance (if you have a tree named "tree"):
 * 
 * BinaryTreeConsistencyChecker<Integer> cc = new BinaryTreeConsistencyChecker<>( tree );
 * 
 * 2) Print whether the references are consistent in this tree:
 * 
 * cc.check();
 * 
 * This method will not print anything if it does not find any problems.
 */
public class BinaryTreeConsistencyChecker<T> {
   private IBinaryTree<T> t;

   public BinaryTreeConsistencyChecker( IBinaryTree<T> t ) {
      this.t = t;
   }

   public boolean check() {
      Set<INode<T>> seen = new HashSet<INode<T>>();
      return isConsistent( t.root(), seen );
   }

   private boolean isConsistent( INode<T> n, Set<INode<T>> seen ) {
      if ( t.isInternal( n ) ) {
         if ( n != t.parent( t.left( n ) ) ) {
            if ( t.isExternal( t.left( n ) ) ) {
               System.err.println( "The left child of " + n.element()
                     + " is an external node, but the parent of that external node contains "
                     + t.parent( t.left( n ) ).element() );
            }
            else {
               System.err.println( "The left child of " + n.element() + " is "
                     + t.left( n ).element() + ", but " + n.element()
                     + " is not the parent of " + t.left( n ).element()
                     + " (it's " + t.parent( t.left( n ) ).element() + ")" );
            }
            return false;
         }
         else if ( n != t.parent( t.right( n ) ) ) {
            if ( t.isExternal( t.right( n ) ) ) {
               System.err.println( "The right child of " + n.element()
                     + " is an external node, but the parent of that external node contains "
                     + t.parent( t.right( n ) ).element() );
            }
            else {
               System.err.println( "The right child of " + n.element() + " is "
                     + t.right( n ).element() + ", but " + n.element()
                     + " is not the parent of " + t.right( n ).element()
                     + " (it's " + t.parent( t.right( n ) ).element() + ")" );
            }
            return false;
         }
         else if ( t.left( n ) == t.right( n ) ) {
            System.err.println( "The two children of " + n.element()
                  + " are the same." );
            return false;
         }
         else if ( seen.contains( n ) ) {
            System.err.println( "There is a loop in the tree: " + n.element()
                  + " has already been seen." );
            return false;
         }
         else {
            seen.add( n );
            return isConsistent( t.left( n ), seen )
                  && isConsistent( t.right( n ), seen );
         }
      }
      else {
         return true;
      }
      // return t.isExternal( n ) || ( n == t.parent( t.left( n ) )
      // && n == t.parent( t.right( n ) ) && t.right( n ) != t.left( n ) )
      // && isConsistent( t.left( n ), seen )
      // && isConsistent( t.right( n ), seen );
   }
}
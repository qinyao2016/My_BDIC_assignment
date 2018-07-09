package dsa.util;

import dsa.iface.IIterator;
import dsa.iface.INode;
import dsa.iface.ITree;

public class TreePrinter<T> {
   ITree<T> tree;
   String output = "";

   public TreePrinter( ITree<T> tree ) {
      this.tree = tree;
      this.visit( tree.root(), "" );
   }

   private void visit( INode<T> position, Object data ) {
      output += data.toString() + ( position.element() == null ? "[]" : position.element() ) + "\n";
      IIterator<INode<T>> it = tree.children( position );
      while ( it.hasNext() ) {
         visit( it.next(), data.toString() + "\t" );
      }
   }

   private String getString() {
      return output;
   }

   public static <T> void printTree( ITree<T> t ) {
      System.out.println( new TreePrinter<T>( t ).getString() );
   }
}

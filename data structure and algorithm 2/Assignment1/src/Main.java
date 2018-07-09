import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import dsa.impl.AVLTree;
import dsa.impl.BinaryTreeConsistencyChecker;
import dsa.impl.SplayTree;
import dsa.util.TreePrinter;
//my test file is in src folder
public class Main {
   public static void main(String[] args ) throws IOException {
	   System.out.println("__________MY AVLTREE____________");
	
	   AVLTree<Integer> tree = new AVLTree<Integer>();
	   FileInputStream inputstream = new FileInputStream("src/test.txt"); 
	   StringBuffer buffer = new StringBuffer(); 
	   String line; 
	   BufferedReader bufferreader = new BufferedReader(new InputStreamReader( inputstream)); 
	   line = bufferreader.readLine();  
	   while (line != null){ 
			Scanner a = new Scanner(line);
			String s = a.next();
			if(s.equals("R")) {
				int number = a.nextInt();
				System.out.println("remove " + number + ":");
				tree.remove(number);
				TreePrinter.printTree( tree );
		
			}
			else if(s.equals("I")) {
				int number = a.nextInt();
				System.out.println("insert " + number + ":");
				tree.insert(number);
				TreePrinter.printTree( tree );
		
			}
			else if(s.equals("C")) {
				int n = a.nextInt();
				if(tree.contains(n))
					System.out.println("The tree contains " + n + ":");
				else
					System.out.println("The tree doesn't contain " + n + ":" );
				TreePrinter.printTree( tree );
			}
			line = bufferreader.readLine(); 
		} 
	   
	   
	   
		System.out.println("__________MY SPLAYTREE____________");
		SplayTree<Integer> tree2 = new SplayTree<Integer>();
		inputstream = new FileInputStream("src/test.txt"); 
		buffer = new StringBuffer(); 
		bufferreader = new BufferedReader(new InputStreamReader( inputstream)); 
		line = bufferreader.readLine();  
		while (line != null){ 
			Scanner a = new Scanner(line);
			String s = a.next();
			if(s.equals("R")) {
				int number = a.nextInt();
				System.out.println("remove " + number + ":");
				tree2.remove(number);
				TreePrinter.printTree( tree2 );
			}
			else if(s.equals("I")) {
				int number = a.nextInt();
				System.out.println("insert " + number + ":");
				tree2.insert(number);
				TreePrinter.printTree( tree2 );
			}
			else if(s.equals("C")) {
				int n = a.nextInt();
				if(tree2.contains(n))
					System.out.println("The tree contains " + n + ":");
				else
					System.out.println("The tree doesn't contain " + n + ":" );
				TreePrinter.printTree( tree2 );
			}
			line = bufferreader.readLine(); 
		} 
//		my previous test
//		tree.insert(15);
//		TreePrinter.printTree( tree );
//
//		tree.insert(25);
//		TreePrinter.printTree( tree );
//
//		tree.insert(5);
//		TreePrinter.printTree( tree );
//
//		tree.insert(9);
//		TreePrinter.printTree( tree );
//
//		tree.remove(15);
//		TreePrinter.printTree( tree );
//
//		tree.insert(37);
//		TreePrinter.printTree( tree );
//
//		tree.insert(26);
//		TreePrinter.printTree( tree );
//		tree.insert(22);
//		TreePrinter.printTree( tree );
//		tree.insert(47);
//		TreePrinter.printTree( tree );
//		tree.insert(35);
//		TreePrinter.printTree( tree );
//
//		tree.insert(18);	
//		TreePrinter.printTree( tree );
//
//		tree.insert(20);
//		TreePrinter.printTree( tree );
//
//		tree.remove(20);
//		TreePrinter.printTree( tree );
//		BinaryTreeConsistencyChecker a = new BinaryTreeConsistencyChecker(tree);
//		a.check();
//
//		tree.remove(37);
//		TreePrinter.printTree( tree );
//
//		tree.remove(9);
//		TreePrinter.printTree( tree );
//		a.check();
//
//		tree.remove(18);
//		TreePrinter.printTree( tree );
//		tree.remove(25);
//		TreePrinter.printTree( tree );
//		tree.remove(26);
//		TreePrinter.printTree( tree );
//
//		tree.remove(22);
//		TreePrinter.printTree( tree );
//
//		tree.remove(5);
//		TreePrinter.printTree( tree );
//		tree.remove(47);
//		TreePrinter.printTree( tree );
//		tree.remove(35);
//		TreePrinter.printTree( tree );
//		tree.insert(14);
//		TreePrinter.printTree( tree );
//
//		tree.insert(7);
//		TreePrinter.printTree( tree );
//
//		tree.insert(18);
//		TreePrinter.printTree( tree );
//
//		tree.insert(36);
//		TreePrinter.printTree( tree );
//
//		tree.insert(12);
//		TreePrinter.printTree( tree );
//
//		tree.insert(15);
//		TreePrinter.printTree( tree );
//
//		tree.insert(13);
//		TreePrinter.printTree( tree );
//		tree.remove(18);
//		TreePrinter.printTree( tree );
//		tree.remove(15);
//		TreePrinter.printTree( tree );
//		tree.remove(12);
//		TreePrinter.printTree( tree );
//		tree.remove(7);
//		TreePrinter.printTree( tree );
//		tree.remove(14);
//		TreePrinter.printTree( tree );
//		tree.remove(36);
//		TreePrinter.printTree( tree );
//		tree.contains(13);
//		tree.contains(36);
//
//		tree.remove(13);		
//		TreePrinter.printTree( tree );
//
//
//


		
//		SplayTree<Integer> st = new SplayTree<Integer>();
//		st.insert( 21 );
//		TreePrinter.printTree( st );
//		a.check();
//		st.insert( 18 );
//		TreePrinter.printTree( st );
//		a.check();
//		st.insert( 14 );
//		a.check();
//	    TreePrinter.printTree( st );
//	    st.insert( 5 );
//		a.check();
//	    TreePrinter.printTree( st );
//	    st.contains( 14 );
//	    a.check();
//
//	    TreePrinter.printTree( st );
//	    st.insert( 19 );	  a.check();
//      
//	    TreePrinter.printTree( st );
//	    st.contains( 5 );		  a.check();
//  
//	    TreePrinter.printTree( st );
//	    st.remove( 19 );	  a.check();
//	    
//	    TreePrinter.printTree( st );
//	    st.insert(3);	  a.check();
//
//	    TreePrinter.printTree( st );
//	    st.remove(18);		  a.check();
//	
//	    TreePrinter.printTree( st );
//      st.remove(3);			  a.check();
//
//      TreePrinter.printTree( st );
//      st.remove(21); 		  a.check();
//	
//      TreePrinter.printTree( st );
//      st.contains(21);		  a.check();
//	
//      TreePrinter.printTree( st );		
//      st.remove(5); 		  a.check();
//      TreePrinter.printTree( st );		
//
//      st.remove(14); 		  a.check();
//      TreePrinter.printTree( st );		

      
   }
}

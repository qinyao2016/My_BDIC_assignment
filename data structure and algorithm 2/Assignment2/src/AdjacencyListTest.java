import graph.core.IEdge;
import graph.core.IGraph;
import graph.core.IIterator;
import graph.core.IList;
import graph.core.IVertex;
import graph.impl.AdjacencyListGraph;
import graph.util.DLinkedList;
public class AdjacencyListTest{
	public static void printincidentEdges(IGraph<String,Integer> g,IVertex<String> v) {
		   IList<IEdge<Integer>> incidentEdges = new DLinkedList<IEdge<Integer>>();
			  IIterator<IEdge<Integer>> it = g.incidentEdges(v);
			  while( it.hasNext() ) {
					incidentEdges.insertLast(it.next());
				}
		      while (!incidentEdges.isEmpty()) {
					System.out.print( " " + incidentEdges.remove(incidentEdges.first()).element() ); 
				}
			  System.out.println("");
	   }
	public static void printMatrix(IGraph g,String action,String variable) {
		if(action == "i")
			   System.out.println("Insert " + variable + " ");
		if(action == "r")
			   System.out.println("remove " + variable + " ");
		if(action == "re")
			   System.out.println("replace " + variable + " ");
	    System.out.print("    ");
		IIterator<IVertex> vertices = g.vertices();
		while(vertices.hasNext()) {
			   System.out.print(vertices.next().element() + " ");
		}
		System.out.println();

		vertices = g.vertices();
		while(vertices.hasNext()) {
				IIterator<IVertex> vertices2 = g.vertices();
				IVertex e = vertices.next();
				System.out.print(e.element());
				while(vertices2.hasNext()) {
					if(g.areAdjacent((IVertex)vertices2.next(),e)) {
						System.out.print(" 1  ");
					}else {
						System.out.print(" 0  ");
					}
				}
				System.out.println();
		}
		System.out.println();
	}
	public static void main( String[] args ) throws Exception {
		
		IGraph<String,Integer> g = new AdjacencyListGraph<String,Integer>();
		// create some vertices
		IVertex<String> hnl = g.insertVertex( "HNL" );
		printMatrix(g,"i","HNL");
		IVertex<String> lax = g.insertVertex( "LAX" );
		printMatrix(g,"i","LAX"); 
		IVertex<String> sfo = g.insertVertex( "SFO" );
		printMatrix(g,"i","SFO"); 
		IVertex<String> ord = g.insertVertex( "ORD" );
		printMatrix(g,"i","ORD");
		IVertex<String> dfw = g.insertVertex( "DFW" );
		printMatrix(g,"i","DFW");
		IVertex<String> lga = g.insertVertex( "LGA" );
		printMatrix(g,"i","LGA");
		IVertex<String> pvd = g.insertVertex( "PVD" );
		printMatrix(g,"i","PVD");
		IVertex<String> mia = g.insertVertex( "MIA" );
		printMatrix(g,"i","MIA");
		
		// create some edges
		IEdge<Integer> hnllax = g.insertEdge( hnl, lax, 255 );
		printMatrix(g,"i","hnllax");
		IEdge<Integer> laxsfo = g.insertEdge( lax, sfo, 337 );
		printMatrix(g,"i","laxsfo");
		IEdge<Integer> ordsfo = g.insertEdge( ord, sfo, 184 );
		printMatrix(g,"i","ordsfo");
		IEdge<Integer> laxord = g.insertEdge( lax, ord, 743 );
		printMatrix(g,"i","laxord");
		IEdge<Integer> dfwlax = g.insertEdge( dfw, lax, 233 );
		printMatrix(g,"i","dfwlax"); 
		IEdge<Integer> ordpvd = g.insertEdge( ord, pvd, 849 );
		printMatrix(g,"i","ordpvd");
		IEdge<Integer> dfwlga = g.insertEdge( dfw, lga, 137 );
		printMatrix(g,"i","dfwlga");
		IEdge<Integer> dfwmia = g.insertEdge( dfw, mia, 120 );
		printMatrix(g,"i","dfwmia");
		IEdge<Integer> lgamia = g.insertEdge( lga, mia, 899 );
		printMatrix(g,"i","lgamia");
		IEdge<Integer> lgapvd = g.insertEdge( lga, pvd, 142 );
		printMatrix(g,"i","lgapvd");
		
		
		// test for areAdjacent
		if ( g.areAdjacent( sfo,  ord ) )
		   System.out.println( "SFO and ORD adjacent: correct" );
		else
		   System.out.println( "SFO and ORD adjacent: incorrect" );
		
		// test for endVertices
		IVertex<String>[] ends = g.endVertices( laxord );
		if ( ( ends[0] == lax && ends[1] == ord ) ||
		     ( ends[1] == lax && ends[0] == ord ) )
		   System.out.println( "End vertices of LAX<->ORD: correct" );
		else
		   System.out.println( "End vertices of LAX<->ORD: incorrect" );
		
		// test for opposite
		if ( g.opposite( pvd, lgapvd ) == lga )
		   System.out.println( "Opposite of PVD along LGA<->PVD: correct" );
		else 
		   System.out.println( "Opposite of PVD along LGA<->PVD: incorrect" );
		if ( g.opposite( lax, laxord ) == ord )
		    System.out.println( "Opposite of LAX along LAX<->ORD: correct" );
		 else 
		    System.out.println( "Opposite of LAX along LAX<->ORD: incorrect" );
		
		// getting an object from the graph 
		String miaElement = mia.element();
		System.out.println( "Element of MIA is: " + miaElement );
		
		int dfwlaxElement = dfwlax.element();
		System.out.println( "Distance from DFW to LAX is: " + dfwlaxElement );
		
		// test for replace
		g.replace(lgamia, 244);
		printMatrix(g,"re","lgamia");
		g.replace(lga, "lga");

	    printMatrix(g,"re","LGA");
	    g.removeEdge(lgamia);
		printMatrix(g,"r","lgamia");

		if ( g.areAdjacent( lga,  mia ) )
	         System.out.println( "LGA and MIA adjacent: correct" );
	    else
	         System.out.println( "LGA and MIA adjacent: incorrect" );
		//remove a edge that has been removed before
	    g.removeEdge(lgamia);
	    printMatrix(g,"r","lgamia");
	    g.removeVertex(lga);       
	    printMatrix(g,"r","LGA");
	    //remove a nonexistent edge
	    g.removeEdge(lgamia);      
	    printMatrix(g,"r","lgamia");
	    g.removeVertex(pvd);      
	    printMatrix(g,"r","PVD"); 
	    //remove a nonexistent vertex
	    g.removeVertex(pvd);      
        printMatrix(g,"r","PVD");  
        
	    System.out.print("The incident edges of ORD :");
	    printincidentEdges(g,ord);
	    
	    g.removeVertex(ord);
	    printMatrix(g,"r","ORD");
	    //insert an edge whose endpoints don't exist in the graph
        g.insertEdge( ord, sfo, 184 );
	    printMatrix(g,"i","ordsfo");
	    g.removeVertex(dfw);
	    printMatrix(g,"r","DFW");
	    g.removeVertex(sfo);
	    printMatrix(g,"r","SFO");
	      
	    System.out.print("The incident edges of HNL :");
	    printincidentEdges(g,hnl);
	      
	    g.removeVertex(hnl);
	    printMatrix(g,"r","HNL");
	    g.removeVertex(lax);
	    printMatrix(g,"r","LAX");
	}
}
		


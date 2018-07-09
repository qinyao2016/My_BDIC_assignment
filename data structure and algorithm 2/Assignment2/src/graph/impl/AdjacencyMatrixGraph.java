package graph.impl;

import graph.core.IEdge;
import graph.core.IGraph;
import graph.core.IIterator;
import graph.core.IList;
import graph.core.INode;
import graph.core.IVertex;
import graph.util.DLinkedList;

public class AdjacencyMatrixGraph<V,E> implements IGraph<V,E> {
	private class AdjacencyMatrixVertex implements IVertex<V>{
		// reference to a node in the vertex list
		INode<IVertex<V>> node;

		// element stored in this vertex
		V element;
		
		//index in the matrix
		int index;

		public AdjacencyMatrixVertex(V element) {
			this.element = element;
		}

		@Override
		public V element() {
			return element;
		}
		public int index() {
			return index;
		}

		
		public String toString() {
			return element.toString();
		}
	}
	private class AdjacencyMatrixEdge implements IEdge<E> {
		// reference to a node in the edge list
		INode<IEdge<E>> node;

		// element stored in this edge
		E element;

		// the start and end vertices that this edge connects
		AdjacencyMatrixVertex start, end;

		// constructor to set the three fields
		public AdjacencyMatrixEdge(AdjacencyMatrixVertex start, AdjacencyMatrixVertex end, E element) {
			this.start = start;
			this.end = end;
			this.element = element;
		}
		public E element() {
			return element;
		}

		public String toString() {
			return element.toString();
		}
	}

	// vertex list
	private IList<IVertex<V>> vertices;

	// edge list
	public IList<IEdge<E>> edges;
	
	//2D-array adjacency array
	private IEdge<E>[][] m;
	//number of vertex;
	private int Num_of_v;
	//number of edge
	private int Num_of_e;
	public  AdjacencyMatrixGraph() {
		// create new (empty) lists of edges and vertices
		vertices = new DLinkedList<IVertex<V>>();
		edges = new DLinkedList<IEdge<E>>();
		Num_of_v = 0;
		Num_of_e = 0;
		m = new IEdge[0][0];
	}
	public IEdge[][] matrix(){
		return m;
	}
	public int count() {
		return Num_of_v;
	}
	
	@Override
	public IVertex<V>[] endVertices(IEdge<E> e) {
		// need to cast Edge type to EdgeListEdge
		AdjacencyMatrixEdge edge = (AdjacencyMatrixEdge) e;

		// create new array of length 2 that will contain
		// the edge's end vertices
		@SuppressWarnings("unchecked")
		IVertex<V>[] endpoints = new IVertex[2];
 
		// fill array
		endpoints[0] = edge.start;
		endpoints[1] = edge.end;

		return endpoints;
	}

	@Override
	public IVertex<V> opposite(IVertex<V> v, IEdge<E> e) {
		// find end points of Edge e
		IVertex<V>[] endpoints = endVertices(e);

		// return the end point that is not v
		if (endpoints[0].equals(v)) {
			return endpoints[1];
		} else if (endpoints[1].equals(v)) {
			return endpoints[0];
		}

		// Problem! e is not connected to v.
		throw new RuntimeException("Error: cannot find opposite vertex.");
	}

	@Override
	public boolean areAdjacent(IVertex<V> v, IVertex<V> w) {
		//check corresponding position in the matrix is null or not
		return m[((AdjacencyMatrixVertex)v).index()][((AdjacencyMatrixVertex)w).index()] != null ? true : false;
	}

	@Override
	public V replace(IVertex<V> v, V o) {
		AdjacencyMatrixVertex vertex = (AdjacencyMatrixVertex) v;
		// store old element that we should return
		V temp = vertex.element;

		// do the replacement
		vertex.element = o;

		// return the old value
		return temp;
	}

	@Override
	public E replace(IEdge<E> e, E o) {
		AdjacencyMatrixEdge edge = (AdjacencyMatrixEdge) e;
		E temp = edge.element;
		edge.element = o;
		return temp;
	}

	@Override
	public IVertex<V> insertVertex(V o) {
		// create new vertex
		AdjacencyMatrixVertex vertex = new AdjacencyMatrixVertex(o);
		// insert the vertex into the vertex list
		// (returns a reference to the new Node that was created)
		INode<IVertex<V>> node = vertices.insertLast(vertex);
		// store the reference to the node
		vertex.node = node;
		//index should be the index of the end of the list
		vertex.index = vertices.size() - 1;
		//increase the size of the matrix by one
		IEdge matrix[][] = new IEdge[Num_of_v + 1][Num_of_v + 1];
		for(int i = 0;i < Num_of_v;i ++) 
			for(int j = 0;j < Num_of_v ;j ++)
				matrix[i][j] = m[i][j];
		m = matrix;
		Num_of_v++;
		return vertex;
		
	}

	@Override
	public IEdge<E> insertEdge(IVertex<V> v, IVertex<V> w, E o) {
		AdjacencyMatrixVertex vertex1 = (AdjacencyMatrixVertex)v;
		AdjacencyMatrixVertex vertex2 = (AdjacencyMatrixVertex)w;
		//if end points of the edge has an index of -1,this means we have ever removed these vertices from the graph
		if(vertex1.index == -1 || vertex2.index == -1) {
			System.out.println("invalid insertion: vertex doesn't exist in the graph");
			return null;
		}
		// create new edge object
		AdjacencyMatrixEdge edge = new AdjacencyMatrixEdge((AdjacencyMatrixVertex) v, (AdjacencyMatrixVertex) w, o);

		// insert into the edge list and store the reference to the node
		// in the edge object
		INode<IEdge<E>> n = edges.insertLast(edge);
		edge.node = n;
		//store the reference to the corresponding position in the matrix
		m[((AdjacencyMatrixVertex)v).index()][((AdjacencyMatrixVertex)w).index()] = edge;
		m[((AdjacencyMatrixVertex)w).index()][((AdjacencyMatrixVertex)v).index()] = edge;
		Num_of_e++;
		return edge;
	}

	@Override
	public V removeVertex(IVertex<V> v) {
		AdjacencyMatrixVertex vertex = (AdjacencyMatrixVertex) v;
		//if vertex has an index of -1,this means we have ever removed the vertex from the graph
		if(vertex.index == -1) {
			System.out.println("Invalid remove: vertex doesn't exist in the graph");
			return null;
		}
		// first find all incident edges and remove those
		IList<IEdge<E>> incidentEdges = new DLinkedList<IEdge<E>>();
		IIterator<IEdge<E>> it = incidentEdges(v);
		while( it.hasNext() ) {
				incidentEdges.insertLast(it.next());
		}
		while (incidentEdges.size() != 0) {
			removeEdge(incidentEdges.first().element());
			incidentEdges.remove(incidentEdges.first()); 
		}
		vertices.remove(vertex.node);
		//decrease the size of matrix by 1
		IEdge matrix[][] = new IEdge[Num_of_v - 1][Num_of_v - 1]; 
		int p = 0,q = 0;
		for(int i = 0;i < Num_of_v;i ++) {
			if(i != vertex.index) {
				for(int j = 0;j < Num_of_v ;j ++) {
					if(j != vertex.index) 
						matrix[p][q++] = m[i][j];
				}
				q = 0;
				p++;
			}
		}
		m = matrix;
		//change the index of the vertices in the vertex list
		INode<IVertex<V>> a = vertices.next(vertex.node);
		for(int i = vertex.index + 1;i < Num_of_v;i++) {
			AdjacencyMatrixVertex m = (AdjacencyMatrixVertex)a.element();
			m.index--;
			a = vertices.next(a);
		}
		Num_of_v--;
		//after we remove a vertex from the graph, we change the index of it to -1,which shows it is not in the graph anymore
		vertex.index = -1;
		return v.element();
	}

	@Override
	public E removeEdge(IEdge<E> e) {
		AdjacencyMatrixEdge edge = (AdjacencyMatrixEdge) e;
		//if end points of the edge has an index of -1,this means we have ever removed these vertices from the graph
		if(edge.start.index() == -1 || edge.end.index() == -1) {
			System.out.println("Invalid remove: vertex doesn't exist in the graph");
			return null;
		}
		//if corresponding position in the matrix is null, this means we are removing the same edge twice
		if(m[edge.start.index()][edge.end.index()] == null) {
			System.out.println("this edge doen't exist in the graph");
			return null;
		}
		//remove vertex from the vertices list
		INode a = edges.remove(edge.node);
		//set the corresponding position in the matrix to null
		m[edge.start.index()][edge.end.index()] = null;
		m[edge.end.index()][edge.start.index()] = null;
		Num_of_e--;
		return edge.element;
	}

	@Override
	public IIterator<IEdge<E>> incidentEdges(IVertex<V> v) {
		IList<IEdge<E>> list = new DLinkedList<IEdge<E>>();
		//iterate through the corresponding column in the matrix
		for(int i = 0;i < Num_of_v;i ++) {
			if(m[((AdjacencyMatrixVertex)v).index][i] != null) {
				list.insertLast(m[((AdjacencyMatrixVertex)v).index][i]);
			}
		}
		return list.iterator();
	}

	@Override
	public IIterator<IVertex<V>> vertices() {
		return vertices.iterator();
	}

	@Override
	public IIterator<IEdge<E>> edges() {
		return edges.iterator();
	}
	
}

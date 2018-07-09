package graph.impl;

import graph.core.IEdge;
import graph.core.IGraph;
import graph.core.IIterator;
import graph.core.IList;
import graph.core.INode;
import graph.core.IVertex;
import graph.util.DLinkedList;

public class AdjacencyListGraph<V,E> implements IGraph<V,E> {
	private class AdjacencyListVertex implements IVertex<V> {
		// reference to a node in the vertex list
		INode<IVertex<V>> node;

		// element stored in this vertex
		V element;
		
		//each vertex has a list of incident edges
		IList<IEdge<E>> Incident_Edge;
		public AdjacencyListVertex(V element) {
			this.element = element;
			Incident_Edge = new DLinkedList<IEdge<E>>();
		}

		@Override
		public V element() {
			return element;
		}

		public String toString() {
			return element.toString();
		}
	}
	private class AdjacencyListEdge implements IEdge<E> {
		// reference to a node in the edge list
		INode<IEdge<E>> node;
		// element stored in this edge
		E element;

		// the start and end vertices that this edge connects
		AdjacencyListVertex start, end;
		//reference to a node in the start vertex's incident_edge list
		INode<IEdge<E>> node1;
		//reference to a node in the end vertex's incident_edge list
		INode<IEdge<E>> node2;
		// constructor to set the three fields
		public AdjacencyListEdge(AdjacencyListVertex start, AdjacencyListVertex end, E element) {
			this.start = start;
			this.end = end;
			this.element = element;
		}

		@Override
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
	private IList<IEdge<E>> edges;
	
	public AdjacencyListGraph() {
		// create new (empty) lists of edges and vertices
		vertices = new DLinkedList<IVertex<V>>();
		edges = new DLinkedList<IEdge<E>>();
	}
	@Override
	public IVertex<V>[] endVertices(IEdge<E> e) {
		// need to cast Edge type to AdjacencyListEdge
		AdjacencyListEdge edge = (AdjacencyListEdge) e;
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
		IList<IEdge<E>> n = ((AdjacencyListVertex)v).Incident_Edge;
		IList<IEdge<E>> m = ((AdjacencyListVertex)w).Incident_Edge;
		if(n.size() > m.size()) {
			INode<IEdge<E>> first = m.first();
			while(first != null) {
				if (((AdjacencyListEdge)(first.element())).start == v && ((AdjacencyListEdge)(first.element())).end == w)
					return true;

				if (((AdjacencyListEdge)(first.element())).start == w && ((AdjacencyListEdge)(first.element())).end == v)
					return true;
				
				first = m.next(first);
			}
			return false;
		}else {
			INode<IEdge<E>> first = n.first();
			while(first != null) {
				if (((AdjacencyListEdge)(first.element())).start == v && ((AdjacencyListEdge)(first.element())).end == w)
					return true;

				if (((AdjacencyListEdge)(first.element())).start == w && ((AdjacencyListEdge)(first.element())).end == v)
					return true;
				
				first = n.next(first);
			}
			return false;
		}
	}

	@Override
	public V replace(IVertex<V> v, V o) {
		AdjacencyListVertex vertex = (AdjacencyListVertex) v;
		V temp = vertex.element;
		vertex.element = o;
		return temp;
	}

	@Override
	public E replace(IEdge<E> e, E o) {
		AdjacencyListEdge edge = (AdjacencyListEdge) e;
		E temp = edge.element;
		edge.element = o;
		return temp;
	}

	@Override
	public IVertex<V> insertVertex(V o) {
		// create new vertex
		AdjacencyListVertex vertex = new AdjacencyListVertex(o);

		// insert the vertex into the vertex list
		// (returns a reference to the new Node that was created)
		INode<IVertex<V>> node = vertices.insertLast(vertex);

		// this reference must be stored in the vertex,
		// to make it easier to remove the vertex later.
		vertex.node = node;

		// return the new vertex that was created
		return vertex;
	}

	@Override
	public IEdge<E> insertEdge(IVertex<V> v, IVertex<V> w, E o) {
		AdjacencyListVertex a = (AdjacencyListVertex) v;
		AdjacencyListVertex b = (AdjacencyListVertex) w;
		//in case of insert an edge whose end points are not in the graph
		if(a.node == null || b.node == null) {
			System.out.println("invalid insertion: vertex doesn't exist in the graph");
			return null;
		}
		// create new edge object
		AdjacencyListEdge edge = new AdjacencyListEdge((AdjacencyListVertex) v, (AdjacencyListVertex) w, o);
		// insert into the edge list and store the reference to the node
		// in the edge object
		INode<IEdge<E>> n = edges.insertLast(edge);
		edge.node = n;
		
		//insert the vertex into v's incident_edge
		// (returns a reference to the new Node that was created)
		INode<IEdge<E>> n1 = a.Incident_Edge.insertLast(edge);
		//store the reference to the node in the edge object
		edge.node1 = n1;
		
		//insert the vertex into w's incident_edge
		// (returns a reference to the new Node that was created)
		INode<IEdge<E>> n2 = b.Incident_Edge.insertLast(edge);
		//store the reference to the node in the edge object
		edge.node2 = n2;
		return edge;
	}

	@Override
	public V removeVertex(IVertex<V> v) {
		AdjacencyListVertex vertex = (AdjacencyListVertex) v;
		//in case of move a vertex twice
		if(vertex.node == null) {
			System.out.println("Invalid remove: vertex doesn't exist in the graph");
			return null;
		}
		// first find all incident edges and remove those
		IList<IEdge<E>> incidentEdges = new DLinkedList<IEdge<E>>();
		IIterator<IEdge<E>> it = incidentEdges(v);
		while( it.hasNext() )
			incidentEdges.insertLast(it.next());
		while (!incidentEdges.isEmpty())
			removeEdge(incidentEdges.remove(incidentEdges.first()));

		// now we can remove the vertex from the vertex list
		vertices.remove(vertex.node);
		//after remove, change the reference to null,so if we try to remove the same vertex again, it will return null
		vertex.node = null;
		// return the element of the vertex that was removed
		return vertex.element;
	}

	@Override
	public E removeEdge(IEdge<E> e) {
		AdjacencyListEdge edge = (AdjacencyListEdge) e;
		//in case of move a edge twice
		if(edge.start == null || edge.end == null) {
			System.out.println("Invalid remove");
			return null;
		}
		// remove edge from edge list 
		edges.remove(edge.node);
		// remove edge from incident_edge's start vertex 
		(edge.start.Incident_Edge).remove(edge.node1);
		// remove edge from incident_edge's end vertex 
		(edge.end.Incident_Edge).remove(edge.node2);
		//after remove,change start vertex and end vertex to null, so if we try to remove the same edge twice, it will return null
		edge.start = null;
		edge.end = null;
		// return the element of the edge that was removed
		return edge.element;
	}

	@Override 
	public IIterator<IEdge<E>> incidentEdges(IVertex<V> v) {
		//add all nodes in incident_edge to list and return the iterator of list
		AdjacencyListVertex a = (AdjacencyListVertex) v;
		IList<IEdge<E>> list = new DLinkedList<IEdge<E>>();
		INode<IEdge<E>> start = a.Incident_Edge.first();
		while (start != null) {
			list.insertLast(start.element());
			start = a.Incident_Edge.next(start);
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

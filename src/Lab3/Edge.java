package Lab3;

public class Edge {
	Node from;
	Node to;

	public Edge(Node from, Node to) {
		this.from = from;
		this.to = to;
	}

	public Node getFrom() {
		return from;
	}

	public Node getTo() {
		return to;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj.getClass() == Edge.class) {
			Edge edge = (Edge) obj;
			boolean s1 = edge.getFrom().equals(this.getFrom()) && edge.getTo().equals(this.getTo());
			boolean s2 = edge.getFrom().equals(this.getTo()) && edge.getTo().equals(this.getFrom());
			if (s1 || s2)
				return true;
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		return from.getName().hashCode()^to.getName().hashCode();
	}
	
//	public static void main(String[] args) {
//		Edge a=new Edge(new Node("a", 0, 0), new Node("b", 1, 1));
//		Edge b=new Edge(new Node("b", 1, 1),new Node("a", 0, 0));
//		System.out.println(a.hashCode());
//		System.out.println(b.hashCode());
//		String s1="a";
//		String s2="a";
//		System.out.println(s1.hashCode());
//		System.out.println(s2.hashCode());
//	}
	
}

package Lab4;

public class Edge{
	private Node from;
	private Node to;

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
			if(edge.getFrom().equals(this.getFrom()) && edge.getTo().equals(this.getTo()))
				return true;
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		return from.getName().hashCode()+to.getName().hashCode();
	}
	
//	public static void main(String[] args) {
//		Edge a=new Edge(new Node("a", 0, 0), new Node("b", 1, 1));
//		Edge b=new Edge(new Node("a", 0, 0),new Node("b", 1, 1));
//		System.out.println(a.hashCode());
//		System.out.println(b.hashCode());
//		
//		System.out.println(a.equals(b));
//		String s1="a";
//		String s2="a";
//		System.out.println(s1.hashCode());
//		System.out.println(s2.hashCode());
//	}
	
}

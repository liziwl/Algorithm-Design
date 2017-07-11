package Lab5;

public class Edge implements Comparable<Edge> {
	private Node from;
	private Node to;
	private double weight;

	public Edge() {
		this.from = null;
		this.to = null;
		weight = Integer.MAX_VALUE;
	}
	
	public Edge(Node from, Node to) {
		this.from = from;
		this.to = to;
		weight = buildWeight(from, to);
	}

	public Edge(Node from, Node to, double w) {
		this.from = from;
		this.to = to;
		weight = w;
	}

	public Node getFrom() {
		return from;
	}

	public Node getTo() {
		return to;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public double buildWeight(Node a, Node b) {
		return Math
				.sqrt(Math.pow(a.getX_offset() - b.getX_offset(), 2) + Math.pow(a.getY_offset() - b.getY_offset(), 2));
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
			if ((s1 || s2) && edge.weight == this.weight)
				return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return from.getName().hashCode() + to.getName().hashCode();
	}

	@Override
	public int compareTo(Edge o) {
		if (this.weight < o.weight)
			return -1;
		else if (this.weight == o.weight)
			return 0;
		else
			return 1;
	}

	// public static void main(String[] args) {
	// Edge a=new Edge(new Node("a", 0, 0), new Node("b", 1, 1));
	// Edge b=new Edge(new Node("a", 0, 0),new Node("b", 1, 1));
	// System.out.println(a.hashCode());
	// System.out.println(b.hashCode());
	//
	// System.out.println(a.equals(b));
	// String s1="a";
	// String s2="a";
	// System.out.println(s1.hashCode());
	// System.out.println(s2.hashCode());
	// }

}

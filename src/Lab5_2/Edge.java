package Lab5_2;

public class Edge {// ±ﬂ¿‡
	private Node vertex1, vertex2;
	private int weight;

	public Edge(Node vertex1, Node vertex2, int weight) {
		this.vertex1 = vertex1;
		this.vertex2 = vertex2;
		this.weight = weight;
	}

	public Node getVertex1() {
		return vertex1;
	}

	public Node getVertex2() {
		return vertex2;
	}

	public int getWeight() {
		return weight;
	}
}

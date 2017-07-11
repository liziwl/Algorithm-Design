package Lab5_2;

import java.util.LinkedList;
import java.util.List;

public class Graph4 {// ��Ȩ����ͼ
	private int number;// �ڵ���
	private List<Node> nodes;// �ڵ�����
	private List<Edge> edges;// ������

	public Graph4() {// ����һ��ͼ
		number = 0;
		nodes = new LinkedList<>();
		edges = new LinkedList<>();
	}

	public Graph4(int number) {// ������number���ڵ㵫�ޱߵ�ͼ
		this.number = number;
		nodes = new LinkedList<>();
		edges = new LinkedList<>();
		for (int i = 0; i < number; i++)
			nodes.add(new Node((i + 1) + ""));
	}

	public int getNumber() {// ���ͼ�нڵ���
		return number;
	}

	public List<Node> getNodes() {// ���ͼ�нڵ�
		return nodes;
	}

	public List<Edge> getEdges() {// ���ͼ�еı�
		return edges;
	}

	public void addNode(Node node) {// ���һ�������ڵ�
		nodes.add(node);
		number++;
	}

	public void addEdge(int index1, int index2, int weight) {// ����ڵ����������һ����
		if (index1 < 0 || index1 > number || index2 < 0 || index2 > number || index1 == index2) // ��������±��Ƿ�Խ��
			return;
		edges.add(new Edge(nodes.get(index1), nodes.get(index2), weight));
		resetRoot(nodes.get(index1), nodes.get(index2));
	}

	public void addEdge(Node node1, Node node2, int weight) {// ����ڵ���������һ����
		if (nodes.indexOf(node1) == nodes.indexOf(node2))
			return;
		if (!nodes.contains(node1)) // ��ԭͼ����������Ľڵ����
			this.addNode(node1);// ����ͼ��
		if (!nodes.contains(node2))
			this.addNode(node2);
		edges.add(new Edge(node1, node2, weight));
		resetRoot(node1, node2);
	}

	public void addEge(Edge edge) {// ���һ���߶���
		if (!nodes.contains(edge.getVertex1())) // ��ԭͼ�����ڸñߵĽڵ�
			this.addNode(edge.getVertex1());// ����ͼ��
		if (!nodes.contains(edge.getVertex2()))
			this.addNode(edge.getVertex2());
		edges.add(edge);
		resetRoot(edge.getVertex1(), edge.getVertex2());
	}

	private void resetRoot(Node node1, Node node2) {// ��������ӱ�ʱ���ã���������ĸ��ڵ㣨Ĭ��Ϊ��ԭ���ڵ���������С����Ϊ���ڵ�Ĺ�ͬ���ڵ㣩
		Node root1 = node1.getRoot();
		Node root2 = node2.getRoot();
		if (nodes.indexOf(root1) < nodes.indexOf(root2))
			node2.setRoot(root1);
		else if (nodes.indexOf(root1) > nodes.indexOf(root2))
			node1.setRoot(root2);
	}

	public Node getNode(int index) {// ��ȡָ��������Ӧ�Ľڵ����
		if (index < 0 || index > number)
			return null;
		return nodes.get(index);
	}

	public boolean contains(Node node) {// ͼ���Ƿ����ĳ�ڵ�
		return nodes.contains(node);
	}

}

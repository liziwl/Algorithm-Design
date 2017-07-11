package Lab5_2;

import java.util.LinkedList;
import java.util.List;

public class Graph4 {// 有权无向图
	private int number;// 节点数
	private List<Node> nodes;// 节点数组
	private List<Edge> edges;// 边数组

	public Graph4() {// 建立一空图
		number = 0;
		nodes = new LinkedList<>();
		edges = new LinkedList<>();
	}

	public Graph4(int number) {// 建立有number个节点但无边的图
		this.number = number;
		nodes = new LinkedList<>();
		edges = new LinkedList<>();
		for (int i = 0; i < number; i++)
			nodes.add(new Node((i + 1) + ""));
	}

	public int getNumber() {// 获得图中节点数
		return number;
	}

	public List<Node> getNodes() {// 获得图中节点
		return nodes;
	}

	public List<Edge> getEdges() {// 获得图中的边
		return edges;
	}

	public void addNode(Node node) {// 添加一个独立节点
		nodes.add(node);
		number++;
	}

	public void addEdge(int index1, int index2, int weight) {// 输入节点索引以添加一条边
		if (index1 < 0 || index1 > number || index2 < 0 || index2 > number || index1 == index2) // 检查输入下标是否越界
			return;
		edges.add(new Edge(nodes.get(index1), nodes.get(index2), weight));
		resetRoot(nodes.get(index1), nodes.get(index2));
	}

	public void addEdge(Node node1, Node node2, int weight) {// 输入节点对象以添加一条边
		if (nodes.indexOf(node1) == nodes.indexOf(node2))
			return;
		if (!nodes.contains(node1)) // 若原图不存在输入的节点对象
			this.addNode(node1);// 加入图中
		if (!nodes.contains(node2))
			this.addNode(node2);
		edges.add(new Edge(node1, node2, weight));
		resetRoot(node1, node2);
	}

	public void addEge(Edge edge) {// 添加一条边对象
		if (!nodes.contains(edge.getVertex1())) // 若原图不存在该边的节点
			this.addNode(edge.getVertex1());// 加入图中
		if (!nodes.contains(edge.getVertex2()))
			this.addNode(edge.getVertex2());
		edges.add(edge);
		resetRoot(edge.getVertex1(), edge.getVertex2());
	}

	private void resetRoot(Node node1, Node node2) {// 对两点添加边时调用，更新两点的根节点（默认为将原根节点中索引最小的设为两节点的共同根节点）
		Node root1 = node1.getRoot();
		Node root2 = node2.getRoot();
		if (nodes.indexOf(root1) < nodes.indexOf(root2))
			node2.setRoot(root1);
		else if (nodes.indexOf(root1) > nodes.indexOf(root2))
			node1.setRoot(root2);
	}

	public Node getNode(int index) {// 获取指定索引对应的节点对象
		if (index < 0 || index > number)
			return null;
		return nodes.get(index);
	}

	public boolean contains(Node node) {// 图中是否存在某节点
		return nodes.contains(node);
	}

}

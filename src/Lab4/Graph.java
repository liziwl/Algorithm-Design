package Lab4;

import java.util.*;
import java.util.Map.Entry;

public class Graph {
	// 无权邻接表
	private Map<Node, List<Node>> adjacent;//邻接表
	private Map<Node, List<Node>> inverseAdj;//逆邻接表
	private List<Node> topological_sort;//输出列表
	private HashSet<Edge> remainEdge;// 已经访问
	// 邻接矩阵法
	private double[][] adjacentMat;// 邻接矩阵
	private HashSet<Node> NodeList;//节点集
	private boolean[][] isVisitedMat;// 邻接访问矩阵
	private int nNodes;

	public Graph() {
		adjacent = new HashMap<>();
		inverseAdj = new HashMap<>();
		NodeList = new HashSet<>();
		topological_sort = new LinkedList<>();
		remainEdge = new HashSet<>();
	}

	public Graph(int size) {
		nNodes = size;
		NodeList = new HashSet<>();
		adjacentMat = new double[size][size];
		isVisitedMat = new boolean[size][size];
		remainEdge = new HashSet<>();
		topological_sort = new LinkedList<>();
		for (int i = 0; i < isVisitedMat.length; i++) {
			for (int j = 0; j < isVisitedMat[i].length; j++) {
				isVisitedMat[i][j] = false;
				if (i == j) {
					isVisitedMat[i][j] = true;
				}
				adjacentMat[i][j] = 0;
			}
		}
	}

	// 创建有向图
	@SuppressWarnings("unchecked")
	public void buildGraph(HashSet<Edge> DAG, int mode) {
		// 1为邻接表模式，2为矩阵模式
		switch (mode) {
		case 1:
			Iterator<Edge> it_node = DAG.iterator();
			while (it_node.hasNext()) {
				Edge edge = (Edge) it_node.next();
				NodeList.add(edge.getFrom());
				NodeList.add(edge.getTo());
			}

			Iterator<Node> node = NodeList.iterator();
			while (node.hasNext()) {
				Node node2 = (Node) node.next();
				adjacent.put(node2, new LinkedList<Node>());
				inverseAdj.put(node2, new LinkedList<Node>());
			}

			Iterator<Edge> iterator = DAG.iterator();
			while (iterator.hasNext()) {
				Edge edge = (Edge) iterator.next();
				Node from = edge.getFrom();
				Node to = edge.getTo();
				adjacent.get(from).add(to);
				inverseAdj.get(to).add(from);
			}
			remainEdge = (HashSet<Edge>) DAG.clone();
			break;
		case 2:
			Iterator<Edge> it_node2 = DAG.iterator();
			while (it_node2.hasNext()) {
				Edge edge = (Edge) it_node2.next();
				NodeList.add(edge.getFrom());
				NodeList.add(edge.getTo());
			}

			Iterator<Node> node2 = NodeList.iterator();
			int counter = 0;
			while (node2.hasNext()) {
				Node n = (Node) node2.next();
				n.setId(counter++);
			}

			Iterator<Edge> iterator2 = DAG.iterator();
			while (iterator2.hasNext()) {
				Edge edge = (Edge) iterator2.next();
				Node from = edge.getFrom();
				int x = findID(from);
				Node to = edge.getTo();
				int y = findID(to);
				adjacentMat[x][y] = 1;
			}
			break;
		default:
			break;
		}
	}
	
	//查找对应编号
	public int findID(Node n) {
		Iterator<Node> iterator = NodeList.iterator();
		while (iterator.hasNext()) {
			Node node = (Node) iterator.next();
			if (node.equals(n))
				return node.getId();
		}
		return -1;
	}

	//是否入读为0
	public boolean isInDegree0(int node) {
		int len = adjacentMat.length;
		for (int i = 0; i < len; i++) {
			if (adjacentMat[i][node] == 1) {
				return false;
			}
		}
		return true;
	}

	//是否出度为0
	public boolean isOutDegree0(int node) {
		int len = adjacentMat.length;
		for (int i = 0; i < len; i++) {
			if (adjacentMat[node][i] == 1) {
				return false;
			}
		}
		return true;
	}

	// 初始化有环测试集
	public HashSet<Edge> loop() {
		HashSet<Edge> test = new HashSet<>();
		Node n1 = new Node("1", 2, 6);
		Node n2 = new Node("2", 1, 4);
		Node n3 = new Node("3", 3, 4);
		Node n4 = new Node("4", 0, 2);

		test.add(new Edge(n1, n2));
		test.add(new Edge(n2, n3));
		test.add(new Edge(n3, n1));
		test.add(new Edge(n4, n2));
		return test;
	}

	// 测试集初始化DAG
	public HashSet<Edge> testDAG() {
		HashSet<Edge> test = new HashSet<>();
		Node n1 = new Node("1", 2, 6);
		Node n2 = new Node("2", 1, 4);
		Node n3 = new Node("3", 3, 4);
		Node n4 = new Node("4", 0, 2);
		Node n5 = new Node("5", 2, 2);
		Node n6 = new Node("6", 2, 0);
		Node n7 = new Node("7", 4, 6);
		Node n8 = new Node("8", 4, 2);

		test.add(new Edge(n1, n2));
		test.add(new Edge(n1, n3));
		test.add(new Edge(n2, n3));
		test.add(new Edge(n2, n5));
		test.add(new Edge(n3, n5));
		test.add(new Edge(n2, n4));
		test.add(new Edge(n4, n5));
		test.add(new Edge(n5, n6));
		test.add(new Edge(n3, n7));
		test.add(new Edge(n3, n8));
		test.add(new Edge(n7, n8));
		return test;
	}

	// 测试集初始化（邻接表法）
	public Graph testMoudle() {
		Graph test = new Graph();
		test.buildGraph(testDAG(), 1);
		return test;
	}

	// 测试集初始化（邻接表法）
	public Graph testLoop() {
		Graph test = new Graph();
		test.buildGraph(loop(), 1);
		return test;
	}

	// 测试集初始化（邻接矩阵）
	public Graph testMoudle2() {
		Graph test = new Graph(8);
		test.buildGraph(testDAG(), 2);
		return test;
	}

	public void Kahn(int mode) {
		switch (mode) {
		case 1:
			topological_sort.clear();
			List<Node> out = new LinkedList<>();
			Queue<Node> degree0 = new LinkedList<>();
			Iterator<Entry<Node, List<Node>>> iterator = inverseAdj.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<Lab4.Node, java.util.List<Lab4.Node>> entry = (Map.Entry<Lab4.Node, java.util.List<Lab4.Node>>) iterator
						.next();
				if (entry.getValue().isEmpty()) {
					degree0.add(entry.getKey());
				}
			}
			while (!degree0.isEmpty()) {
				Node top = degree0.poll();
				out.add(top);
				List<Node> neigbhour = adjacent.get(top);
				for (Node nei : neigbhour) {
					remainEdge.remove(new Edge(top, nei));
					inverseAdj.get(nei).remove(top);
					if (inverseAdj.get(nei).isEmpty())
						degree0.add(nei);
				}
				neigbhour.clear();
			}
			topological_sort = out;
			break;
		case 2:
			topological_sort.clear();
			List<Node> out2 = new LinkedList<>();
			Queue<Node> degree0_queue = new LinkedList<>();
			// col
			int len = adjacentMat.length;
			for (int i = 0; i < len; i++) {
				if (isInDegree0(i)) {
					degree0_queue.add(ID2Node(i));
				}
			}
			while (!degree0_queue.isEmpty()) {
				Node n = degree0_queue.poll();
				out2.add(n);
				int row = findID(n);
				for (int i = 0; i < len; i++) {
					if (adjacentMat[row][i] == 1) {
						adjacentMat[row][i] = 0;
						if (isInDegree0(i)) {
							degree0_queue.add(ID2Node(i));
						}
					}
				}
			}
			topological_sort = out2;
			break;
		default:
			break;
		}
	}

	public void DFS(int mode) {
		switch (mode) {
		case 1:
			topological_sort.clear();
			Queue<Node> degree0 = new LinkedList<>();
			Iterator<Entry<Node, List<Node>>> iterator = adjacent.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<Lab4.Node, java.util.List<Lab4.Node>> entry = (Map.Entry<Lab4.Node, java.util.List<Lab4.Node>>) iterator
						.next();
				if (entry.getValue().isEmpty()) {
					degree0.add(entry.getKey());
				}
			}
			while (!degree0.isEmpty()) {
				Node top = degree0.poll();
				visit(top);
			}
			break;
		case 2:
			topological_sort.clear();
			Queue<Node> degree0_queue = new LinkedList<>();
			// col
			int len = adjacentMat.length;
			for (int i = 0; i < len; i++) {
				if (isOutDegree0(i)) {
					degree0_queue.add(ID2Node(i));
				}
			}
			while (!degree0_queue.isEmpty()) {
				Node top = degree0_queue.poll();
				visit2(top);
			}
			break;
		default:
			break;
		}
	}

	public Node ID2Node(int id) {
		Iterator<Node> iterator = NodeList.iterator();
		while (iterator.hasNext()) {
			Node node = (Node) iterator.next();
			if (node.getId() == id)
				return node;
		}
		return null;
	}

	//Map递归算法
	public void visit(Node n) {
		if (!topological_sort.contains(n)) {
			List<Node> list = inverseAdj.get(n);
			for (Node node : list) {
				visit(node);
			}
			topological_sort.add(n);
		}
	}
	
	//矩阵递归算法
	public void visit2(Node n) {
		int id = findID(n);
		if (!topological_sort.contains(n)) {
//			topological_sort.add(n);
			for (int i = 0; i < adjacentMat.length; i++) {
				if (adjacentMat[i][id] == 1) {
					visit2(ID2Node(i));
				}
			}
			topological_sort.add(n);
		}
	}

	public List<Node> getTopological_sort() {
		return topological_sort;
	}
	
	//打印输出列表
	public static void printList(List<Node> list) {
		System.out.println("Name\tX\tY");
		for (Node node : list) {
			System.out.printf("%s\t%s\t%s\n", node.getName(), node.getX_offset(), node.getY_offset());
		}
	}
	
	//检查是否有环
	public boolean haveLoop() {
		Kahn(1);
		return !remainEdge.isEmpty();
	}

	public static void main(String[] args) {
		System.out.println("Kahn by Map");
		Graph simulate = new Graph();
		simulate = simulate.testMoudle();
		simulate.Kahn(1);
		printList(simulate.getTopological_sort());
		System.out.println();

		System.out.println("DFS by Map");
		Graph simulate2 = new Graph();
		simulate2 = simulate2.testMoudle();
		simulate2.DFS(1);
		printList(simulate2.getTopological_sort());
		System.out.println();

		System.out.println("Kahn by Matrix");
		Graph simulate3 = new Graph(8);
		simulate3 = simulate3.testMoudle2();
		simulate3.Kahn(2);
		printList(simulate3.getTopological_sort());
		System.out.println();

		System.out.println("DFS by Matrix");
		Graph simulate4 = new Graph(8);
		simulate4 = simulate4.testMoudle2();
		simulate4.DFS(2);
		printList(simulate4.getTopological_sort());
		System.out.println();
		
		System.out.println("Loop cheaked by Map");
		Graph simulate5 = new Graph();
		simulate5 = simulate5.testLoop();
		System.out.println("Is have loop?\n"+simulate5.haveLoop());

	}

}
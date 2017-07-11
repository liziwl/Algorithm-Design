package Lab3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

public class Graph {
	private Map<Node, Map<Node, Double>> graph;// 权重邻接表图
	private Map<Node, Double> rootDist;// 最短距离
	private HashSet<Edge> isVisited;// 已经访问边
	// 邻接矩阵法
	private List<Node[]> NodeList;//邻接矩阵
	private double weightMat[][];//邻接权重矩阵
	private boolean[][] isVisitedMat;//邻接访问矩阵
	private int nNodes;

	public Graph() {
		graph = new HashMap<>();
		rootDist = new HashMap<>();
		isVisited = new HashSet<>();
	}

	public Graph(int size) {
		nNodes = size;
		weightMat = new double[size][size];
		isVisitedMat = new boolean[size][size];
		for (int i = 0; i < weightMat.length; i++) {
			for (int j = 0; j < weightMat[i].length; j++) {
				weightMat[i][j] = Double.MAX_VALUE;
				isVisitedMat[i][j] = false;
				if (i == j) {
					weightMat[i][j] = 0;
					isVisitedMat[i][j] = true;
				}
			}
		}
		NodeList = new ArrayList<Node[]>();
	}

	// 创建权重图
	public void buildGraph(Node node, Node[] adjacent) {
		Map<Node, Double> adj_map = new HashMap<Node, Double>();
		for (Node adj : adjacent) {
			double dist = Math.sqrt(Math.pow(node.getX_offset() - adj.getX_offset(), 2)
					+ Math.pow(node.getY_offset() - adj.getY_offset(), 2));
			adj_map.put(adj, dist);
		}
		graph.put(node, adj_map);
	}

	// 创建权重图
	public void buildGraph2(List<Node[]> adjacent) {
		for (Node[] nodes : adjacent) {
			Node curr = nodes[0];
			int line = nodes[0].getId() - 1;
			for (int i = 1; i < nodes.length; i++) {
				int col = nodes[i].getId() - 1;
				Node temp = nodes[i];
				double dist = Math.sqrt(Math.pow(curr.getX_offset() - temp.getX_offset(), 2)
						+ Math.pow(curr.getY_offset() - temp.getY_offset(), 2));
				weightMat[line][col] = dist;
			}
		}
	}

	// 测试集初始化（邻接表法）
	public Graph testMoudle() {
		Graph test = new Graph();
		Node n1 = new Node("1", 2, 6);
		Node n2 = new Node("2", 1, 4);
		Node n3 = new Node("3", 3, 4);
		Node n4 = new Node("4", 0, 2);
		Node n5 = new Node("5", 2, 2);
		Node n6 = new Node("6", 2, 0);
		Node n7 = new Node("7", 4, 6);
		Node n8 = new Node("8", 4, 2);

		Node[] l1 = { n2, n3 };
		Node[] l2 = { n1, n3, n5, n4 };
		Node[] l3 = { n1, n2, n5, n7, n8 };
		Node[] l4 = { n2, n5 };
		Node[] l5 = { n2, n3, n4, n6 };
		Node[] l6 = { n5 };
		Node[] l7 = { n3, n8 };
		Node[] l8 = { n3, n7 };

		test.buildGraph(n1, l1);
		test.buildGraph(n2, l2);
		test.buildGraph(n3, l3);
		test.buildGraph(n4, l4);
		test.buildGraph(n5, l5);
		test.buildGraph(n6, l6);
		test.buildGraph(n7, l7);
		test.buildGraph(n8, l8);
		return test;
	}

	// 测试集初始化(矩阵法)
	public Graph testMoudle2() {
		Graph test = new Graph(8);
		Node n1 = new Node(1, "1", 2, 6);
		Node n2 = new Node(2, "2", 1, 4);
		Node n3 = new Node(3, "3", 3, 4);
		Node n4 = new Node(4, "4", 0, 2);
		Node n5 = new Node(5, "5", 2, 2);
		Node n6 = new Node(6, "6", 2, 0);
		Node n7 = new Node(7, "7", 4, 6);
		Node n8 = new Node(8, "8", 4, 2);

		Node[] l1 = { n1, n2, n3 };
		Node[] l2 = { n2, n1, n3, n5, n4 };
		Node[] l3 = { n3, n1, n2, n5, n7, n8 };
		Node[] l4 = { n4, n2, n5 };
		Node[] l5 = { n5, n2, n3, n4, n6 };
		Node[] l6 = { n6, n5 };
		Node[] l7 = { n7, n3, n8 };
		Node[] l8 = { n8, n3, n7 };

		List<Node[]> listadj = new ArrayList<>();
		listadj.add(l1);
		listadj.add(l2);
		listadj.add(l3);
		listadj.add(l4);
		listadj.add(l5);
		listadj.add(l6);
		listadj.add(l7);
		listadj.add(l8);

		test.NodeList = listadj;
		test.buildGraph2(listadj);

		return test;
	}

	public void rmNeighbor(Node cur) {
		Map<Node, Double> weight = graph.get(cur);
		Iterator<Entry<Node, Double>> iterator = weight.entrySet().iterator();
		while (iterator.hasNext()) {
			Node neighbor = iterator.next().getKey();
			Edge e = new Edge(neighbor, cur);
			if (isVisited.contains(e))
				isVisited.remove(e);
		}
	}

	// 计算并打印表
	public void printDist(Node root) {
		// 如无此根节点报错,退出
		if (!graph.containsKey(root)) {
			System.err.println("No such input root in graph.");
			return;
		}
		Queue<Node> layer = new LinkedList<Node>();
		layer.add(root);
		rootDist.put(root, 0.0);
		while (!layer.isEmpty()) {
			Node current = layer.poll();
			double dist = rootDist.get(current);
			Map<Node, Double> weight = graph.get(current);
			Iterator<Entry<Node, Double>> iterator = weight.entrySet().iterator();
			while (iterator.hasNext()) {
				// 将所有相邻节点入队
				Node neighbor = iterator.next().getKey();
				if (isVisited.contains(new Edge(neighbor, current)))
					continue;// 如已经访问过这条边，跳过这个边
				layer.add(neighbor);
				isVisited.add(new Edge(neighbor, current));
				if (rootDist.containsKey(neighbor)) {
					// 计算新的值
					double compare = weight.get(neighbor) + dist;
					if (rootDist.get(neighbor) > compare) {
						rootDist.put(neighbor, compare);
						rmNeighbor(neighbor);
					}
				} else {
					rootDist.put(neighbor, weight.get(neighbor) + dist);
				}
			}
		}

		Iterator<Entry<Node, Double>> it_print = rootDist.entrySet().iterator();
		System.out.printf("From Node %s\n", root.getName());
		while (it_print.hasNext()) {
			Map.Entry<Lab3.Node, java.lang.Double> entry = (Map.Entry<Lab3.Node, java.lang.Double>) it_print.next();
			System.out.printf("To Node: %s in %.2f.\n", entry.getKey().getName(), entry.getValue());
		}
	}

	public boolean iscontained(Node node) {
		for (Node[] n : NodeList) {
			if (n[0].equals(node)) {
				return true;
			}
		}
		return false;
	}

	public int findID(Node node) {
		for (Node[] n : NodeList) {
			if (n[0].equals(node))
				return n[0].getId();
		}
		return Integer.MIN_VALUE;
	}

	public Node ID2Node(int id) {
		if (id > nNodes || id <= 0) {
			return null;
		}
		for (Node[] n : NodeList) {
			if (n[0].getId() == id)
				return n[0];
		}
		return null;
	}

	public boolean isVisited(Node from, Node to) {
		return isVisitedMat[findID(from) - 1][findID(to) - 1] || isVisitedMat[findID(to) - 1][findID(from) - 1];
	}

	public void markVisited(Node from, Node to) {
		isVisitedMat[findID(from) - 1][findID(to) - 1] = true;
		isVisitedMat[findID(to) - 1][findID(from) - 1] = true;
	}

	public void UnMark(Node from, Node to) {
		if (from.equals(to))
			return;
		isVisitedMat[findID(from) - 1][findID(to) - 1] = false;
		isVisitedMat[findID(to) - 1][findID(from) - 1] = false;
	}

	// 计算并打印表
	public void printDist2(Node root) {
		// 如无此根节点报错,退出
		if (!iscontained(root)) {
			System.err.println("No such input root in graph.");
			return;
		}
		Queue<Node> layer = new LinkedList<Node>();
		double[] weight = weightMat[findID(root) - 1];
		layer.add(root);
		// 检查这个节点已经连接所有其他节点
		boolean finished = true;
		for (double d : weight) {
			if (d == Double.MAX_VALUE) {
				finished = false;
				break;
			}
		}
		if (finished) {
			System.out.printf("From Node %s\n", root.getName());
			for (int i = 0; i < weight.length; i++) {
				System.out.printf("To Node: %s in %.2f.\n", ID2Node(i + 1).getName(), weight[i]);
			}
			return;
		}

		while (!layer.isEmpty()) {
			Node current = layer.poll();
			double dist = weight[findID(current) - 1];
			double[] nearWeight = weightMat[findID(current) - 1];
			for (int i = 0; i < nearWeight.length; i++) {
				if (nearWeight[i] != Double.MAX_VALUE) {
					Node neighbor = ID2Node(i + 1);
					if (isVisited(current, neighbor))
						continue;
					layer.add(neighbor);
					markVisited(current, neighbor);
					double old = weight[findID(neighbor) - 1];
					double nw = dist + nearWeight[i];
					weight[findID(neighbor) - 1] = nw < old ? nw : old;
					if (old != Double.MAX_VALUE) {
						if (nw < old) {
							weight[findID(neighbor) - 1] = nw;
							UnMark(current, neighbor);
						}
					} else
						weight[findID(neighbor) - 1] = nw;
				}
			}
		}

		System.out.printf("From Node %s\n", root.getName());
		for (int i = 0; i < weight.length; i++) {
			System.out.printf("To Node: %s in %.2f.\n", ID2Node(i + 1).getName(), weight[i]);
		}
	}

	public static void main(String[] args) {
		
		System.out.println("Result of Map metold:");
		Graph simulate = new Graph();
		simulate = simulate.testMoudle();
		Node startNoode = new Node("3", 3, 4);
		simulate.printDist(startNoode);

//		Edge a = new Edge(new Node("a", 0, 0), new Node("b", 1, 1));
//		Edge b = new Edge(new Node("b", 1, 1), new Node("a", 0, 0));
//		simulate.isVisited.add(a);
//		System.out.println(simulate.isVisited.contains(a));

		System.out.println("\nResult of Matrix metold:");
		Graph simulate2 = new Graph(8);
		simulate2 = simulate2.testMoudle2();
		simulate2.printDist2(startNoode);
		System.out.println();
	}

}
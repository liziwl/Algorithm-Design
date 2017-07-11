package Lab5_2;

import java.util.LinkedList;
import java.util.List;

public class MST {
	static int number = 8;// 节点数
	static Graph4 graph;

	public static void main(String[] args) {
		Graph4 tree1, tree2;
		graph = new Graph4(number);
		graph.addEdge(0, 1, 1);
		graph.addEdge(0, 2, 2);
		graph.addEdge(1, 2, 4);
		graph.addEdge(1, 3, 5);
		graph.addEdge(1, 4, 7);
		graph.addEdge(2, 4, 8);
		graph.addEdge(2, 6, 3);
		graph.addEdge(2, 7, 4);
		graph.addEdge(3, 4, 6);
		graph.addEdge(4, 5, 5);
		graph.addEdge(6, 7, 7);
		System.out.println("Initial graph:");
		printTree(graph);

		tree1 = Prim(graph);
		System.out.println("\nUse Prim algorithm:");
		printTree(tree1);
		tree2 = Kruskal(graph);
		System.out.println("\nUse Kruskal algorithm:");
		printTree(tree2);

	}

	public static Graph4 Prim(Graph4 graph) {// Prim算法
		int minWeight;// 新加边的权重
		Edge newEdge = null;// 新加的边
		Graph4 tree = new Graph4();
		tree.addNode(graph.getNode((int) (Math.random() * number)));// 随机选择图中一节点加入树中
		while (tree.getNumber() < graph.getNumber() && !getPrimEdges(tree, graph).isEmpty()) {// 若图中节点未全部加入树中且仍有可加边
			minWeight = Integer.MAX_VALUE;
			for (Edge edge : getPrimEdges(tree, graph))// 遍历可添加的的边，找权值最小的边
				if (edge.getWeight() < minWeight) {// 找到权值更小的边
					minWeight = edge.getWeight();
					newEdge = edge;
				}
			tree.addEdge(newEdge.getVertex1(), newEdge.getVertex2(), newEdge.getWeight());
		}
		return tree;
	}

	private static List<Edge> getPrimEdges(Graph4 tree, Graph4 graph) {// 获得Prim算法中每次遍历时的可加边（一端点在树中，一端点不在）
		List<Edge> edges = new LinkedList<>();// 可添加的边的数组
		for (Edge edge : graph.getEdges()) {// 遍历图中所有边
			if ((tree.contains(edge.getVertex1()) && !tree.contains(edge.getVertex2()))
					|| (!tree.contains(edge.getVertex1()) && tree.contains(edge.getVertex2())))
				edges.add(edge);
		}
		return edges;
	}

	public static Graph4 Kruskal(Graph4 graph) {// Kruskal算法
		Edge newEdge;// 新加的边
		Graph4 tree = new Graph4();
		List<Edge> sortedEdges = getSortedEdges(graph);// 获取按权值从小到大排序后的边数组
		while (tree.getEdges().size() < graph.getNumber() - 1 && !sortedEdges.isEmpty()) {// 若树未连通且仍有可加边
			newEdge = sortedEdges.remove(0);// 取出图中未加入树中的权值最小边
			if (!canAdd(newEdge)) // 若该边加入树中后树仍为无圈图
				tree.addEge(newEdge);
		}
		return tree;
	}

	private static List<Edge> getSortedEdges(Graph4 graph) {// 获取图的边数组按权值从小到大排序后的数组
		int i, j;
		List<Edge> edges = new LinkedList<>();
		for (Edge edge : graph.getEdges())// （深度拷贝）边数组
			edges.add(new Edge(edge.getVertex1(), edge.getVertex2(), edge.getWeight()));
		for (i = 1; i < edges.size(); i++) {// 按权值从小到大对数组排序（插入排序）
			Edge minEdge = edges.get(i);
			for (j = i; j > 0 && edges.get(j - 1).getWeight() > minEdge.getWeight(); j--)
				edges.set(j, edges.get(j - 1));
			edges.set(j, minEdge);
		}
		return edges;
	}

	private static boolean canAdd(Edge newEdge) {// 判断新边是否可加入图中（若加入后图仍无环，则可加）
		if (newEdge.getVertex1().getRoot().equals(newEdge.getVertex2().getRoot())) // 若新边两顶点在原图中有相同根节点
			return false;// 不可加
		return true;
	}

	public static void printTree(Graph4 tree) {// 打印树（图）
		if (tree.getEdges().size() == 0) {
			System.out.printf("Chose a bad node%s to start, no MST generated\n", tree.getNode(0).getName());
			return;
		}
		System.out.print("Nodes:");
		for (Node node : tree.getNodes())
			System.out.printf(" %s", node.getName());
		System.out.println("\nEdges:\nnode -weight- node");
		List<Edge> edges = tree.getEdges();
		for (int i = 0; i < edges.size(); i++) {
			System.out.printf("%4s   -%2d-   %4s\n", edges.get(i).getVertex1().getName(), edges.get(i).getWeight(),
					edges.get(i).getVertex2().getName());
		}
	}

}

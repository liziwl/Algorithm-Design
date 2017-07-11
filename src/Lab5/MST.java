package Lab5;

import java.util.*;

public class MST {
	// 邻接表
	// K算法变量
	private Map<Node, List<Node>> adjacent;// 邻接表
	private LinkedHashSet<Edge> mst;// 输出集
	private HashSet<Node> nodeSet;// 节点集
	private HashSet<HashSet<Node>> NodeSets;// 节点集
	private PriorityQueue<Edge> remainEdge;// 已经访问

	// P算法变量
	private HashSet<Node> inMST;// 已经进入节点

	public MST() {
		adjacent = new HashMap<>();
		mst = new LinkedHashSet<>();
		nodeSet = new HashSet<>();
		NodeSets = new HashSet<>();
		remainEdge = new PriorityQueue<>();
		inMST = new HashSet<>();
	}

	// 测试集初始化
	public static HashSet<Edge> testMST() {
		HashSet<Edge> test = new HashSet<>();
		Node n1 = new Node("1");
		Node n2 = new Node("2");
		Node n3 = new Node("3");
		Node n4 = new Node("4");
		Node n5 = new Node("5");
		Node n6 = new Node("6");
		Node n7 = new Node("7");
		Node n8 = new Node("8");

		test.add(new Edge(n1, n2, 1));
		test.add(new Edge(n1, n3, 2));
		test.add(new Edge(n3, n2, 4));
		test.add(new Edge(n3, n7, 3));
		test.add(new Edge(n3, n8, 4));
		test.add(new Edge(n7, n8, 7));
		test.add(new Edge(n3, n5, 8));
		test.add(new Edge(n2, n5, 7));
		test.add(new Edge(n4, n2, 5));
		test.add(new Edge(n4, n5, 6));
		test.add(new Edge(n5, n6, 5));
		return test;
	}

	// 创建邻接表
	public void buildGraph(HashSet<Edge> graph) {
		mst = new LinkedHashSet<>();
		Iterator<Edge> it_node = graph.iterator();
		// 遍历所有节点
		while (it_node.hasNext()) {
			Edge edge = (Edge) it_node.next();
			nodeSet.add(edge.getFrom());
			nodeSet.add(edge.getTo());
		}
		// 遍历所有节点创建邻接表
		Iterator<Node> node = nodeSet.iterator();
		while (node.hasNext()) {
			Node node2 = node.next();
			adjacent.put(node2, new LinkedList<Node>());

			HashSet<Node> temp = new HashSet<>();
			temp.add(node2);
			NodeSets.add(temp);
		}
		// 将所有相邻放入邻接表
		Iterator<Edge> iterator = graph.iterator();
		while (iterator.hasNext()) {
			Edge edge = (Edge) iterator.next();
			Node from = edge.getFrom();
			Node to = edge.getTo();
			adjacent.get(from).add(to);
			remainEdge.add(edge);
		}
	}

	public boolean isSameSet(Node key1, Node key2) {
		HashSet<Node> s1 = new HashSet<>();
		HashSet<Node> s2 = new HashSet<>();
		Iterator<HashSet<Node>> it1 = NodeSets.iterator();
		while (it1.hasNext()) {
			s1 = it1.next();
			if (s1.contains(key1))
				break;
		}

		Iterator<HashSet<Node>> it2 = NodeSets.iterator();
		while (it2.hasNext()) {
			s2 = it2.next();
			if (s2.contains(key2))
				break;
		}

		return s1 == s2;
	}

	public boolean Union(Node key1, Node key2) {
		HashSet<Node> s1 = new HashSet<>();
		HashSet<Node> s2 = new HashSet<>();
		Iterator<HashSet<Node>> it1 = NodeSets.iterator();
		while (it1.hasNext()) {
			s1 = it1.next();
			if (s1.contains(key1))
				break;
		}

		Iterator<HashSet<Node>> it2 = NodeSets.iterator();
		while (it2.hasNext()) {
			s2 = it2.next();
			if (s2.contains(key2))
				break;
		}

		if (s1 == s2)
			return true;
		else if (s1.size() == 0 || s2.size() == 0)
			return false;
		else {
			NodeSets.remove(s1);
			NodeSets.remove(s2);
			Iterator<Node> iterator = s2.iterator();
			while (iterator.hasNext()) {
				Node node = iterator.next();
				s1.add(node);
			}
			NodeSets.add(s1);
			return true;
		}
	}

	public void Kruskal() {
		mst = new LinkedHashSet<>();// 重置
		while (!remainEdge.isEmpty()) {
			Edge edge = remainEdge.poll();
			if (!isSameSet(edge.getFrom(), edge.getTo())) {
				mst.add(edge);
				Union(edge.getFrom(), edge.getTo());
			}
		}
	}

	public void Prim() {
		mst = new LinkedHashSet<>();// 重置
		int size = nodeSet.size();
		Iterator<Node> it = nodeSet.iterator();
		Node node = it.next();
		inMST.add(node);
		while (mst.size() + 1 < size) {
			// 最小生成树边数+1 = 节点数
			Edge e_min = new Edge();
			Iterator<Edge> iterator = remainEdge.iterator();
			// Prim + heap (remainEdge) 实现
			while (iterator.hasNext()) {
				Edge edge = iterator.next();
				boolean s1 = inMST.contains(edge.getFrom()) && !inMST.contains(edge.getTo());
				boolean s2 = !inMST.contains(edge.getFrom()) && inMST.contains(edge.getTo());
				// 如果新边小，更新
				if ((s1 || s2) && e_min.getWeight() > edge.getWeight())
					e_min = edge;
			}
			mst.add(e_min);
			inMST.add(e_min.getFrom());
			inMST.add(e_min.getTo());
		}
	}

	public void printMST() {
		Iterator<Edge> iterator = mst.iterator();
		while (iterator.hasNext()) {
			Edge edge = iterator.next();
			System.out.printf("%s - %s\n", edge.getFrom().getName(), edge.getTo().getName());
		}
	}

	public static void main(String[] args) {
		
		System.out.println("Result of Kruskal:");
		MST kru = new MST();
		kru.buildGraph(testMST());
		long start1 = System.nanoTime();
		kru.Kruskal();
		long end1 = System.nanoTime();
		System.out.println("Usage of time in ns: " + (end1 - start1));
		kru.printMST();

		System.out.println("\nResult of Prim:");
		MST pri = new MST();
		pri.buildGraph(testMST());
		long start2 = System.nanoTime();
		pri.Prim();
		long end2 = System.nanoTime();
		System.out.println("Usage of time in ns: " + (end2 - start2));
		pri.printMST();
		
		long[] time = new long[2];
		for (int i = 0; i < 100; i++) {
			MST kru2 = new MST();
			kru2.buildGraph(testMST());
			long start3 = System.nanoTime();
			kru2.Kruskal();
			long end3 = System.nanoTime();
			time[0]+=end3-start3;

			MST pri2 = new MST();
			pri2.buildGraph(testMST());
			long start4 = System.nanoTime();
			pri2.Prim();
			long end4 = System.nanoTime();
			time[1]+=end4-start4;
		}
		System.out.println("\n100 times in average");
		System.out.println("Kruskal Time: "+time[0]/100);
		System.out.println("Prim Time: "+time[1]/100);
	}
}
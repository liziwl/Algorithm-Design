package Lab5_2;

import java.util.LinkedList;
import java.util.List;

public class MST {
	static int number = 8;// �ڵ���
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

	public static Graph4 Prim(Graph4 graph) {// Prim�㷨
		int minWeight;// �¼ӱߵ�Ȩ��
		Edge newEdge = null;// �¼ӵı�
		Graph4 tree = new Graph4();
		tree.addNode(graph.getNode((int) (Math.random() * number)));// ���ѡ��ͼ��һ�ڵ��������
		while (tree.getNumber() < graph.getNumber() && !getPrimEdges(tree, graph).isEmpty()) {// ��ͼ�нڵ�δȫ���������������пɼӱ�
			minWeight = Integer.MAX_VALUE;
			for (Edge edge : getPrimEdges(tree, graph))// ��������ӵĵıߣ���Ȩֵ��С�ı�
				if (edge.getWeight() < minWeight) {// �ҵ�Ȩֵ��С�ı�
					minWeight = edge.getWeight();
					newEdge = edge;
				}
			tree.addEdge(newEdge.getVertex1(), newEdge.getVertex2(), newEdge.getWeight());
		}
		return tree;
	}

	private static List<Edge> getPrimEdges(Graph4 tree, Graph4 graph) {// ���Prim�㷨��ÿ�α���ʱ�Ŀɼӱߣ�һ�˵������У�һ�˵㲻�ڣ�
		List<Edge> edges = new LinkedList<>();// ����ӵıߵ�����
		for (Edge edge : graph.getEdges()) {// ����ͼ�����б�
			if ((tree.contains(edge.getVertex1()) && !tree.contains(edge.getVertex2()))
					|| (!tree.contains(edge.getVertex1()) && tree.contains(edge.getVertex2())))
				edges.add(edge);
		}
		return edges;
	}

	public static Graph4 Kruskal(Graph4 graph) {// Kruskal�㷨
		Edge newEdge;// �¼ӵı�
		Graph4 tree = new Graph4();
		List<Edge> sortedEdges = getSortedEdges(graph);// ��ȡ��Ȩֵ��С���������ı�����
		while (tree.getEdges().size() < graph.getNumber() - 1 && !sortedEdges.isEmpty()) {// ����δ��ͨ�����пɼӱ�
			newEdge = sortedEdges.remove(0);// ȡ��ͼ��δ�������е�Ȩֵ��С��
			if (!canAdd(newEdge)) // ���ñ߼������к�����Ϊ��Ȧͼ
				tree.addEge(newEdge);
		}
		return tree;
	}

	private static List<Edge> getSortedEdges(Graph4 graph) {// ��ȡͼ�ı����鰴Ȩֵ��С��������������
		int i, j;
		List<Edge> edges = new LinkedList<>();
		for (Edge edge : graph.getEdges())// ����ȿ�����������
			edges.add(new Edge(edge.getVertex1(), edge.getVertex2(), edge.getWeight()));
		for (i = 1; i < edges.size(); i++) {// ��Ȩֵ��С������������򣨲�������
			Edge minEdge = edges.get(i);
			for (j = i; j > 0 && edges.get(j - 1).getWeight() > minEdge.getWeight(); j--)
				edges.set(j, edges.get(j - 1));
			edges.set(j, minEdge);
		}
		return edges;
	}

	private static boolean canAdd(Edge newEdge) {// �ж��±��Ƿ�ɼ���ͼ�У��������ͼ���޻�����ɼӣ�
		if (newEdge.getVertex1().getRoot().equals(newEdge.getVertex2().getRoot())) // ���±���������ԭͼ������ͬ���ڵ�
			return false;// ���ɼ�
		return true;
	}

	public static void printTree(Graph4 tree) {// ��ӡ����ͼ��
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

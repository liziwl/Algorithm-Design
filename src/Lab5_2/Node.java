package Lab5_2;

public class Node {// 节点类
	private String name;
	private Node root;
	private double dis;// 距离起点的距离
	private int x, y;
	private boolean passed;
	
	public Node(){
		root = this;
	}
	
	public Node(String name) {
		this.name = name;
		root = this;
		passed = false;
	}

	public Node(String name, int x, int y) {
		this.name = name;
		this.x = x;
		this.y = y;
	}

	public String getName() {
		return name;
	}

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

	public double getDis() {
		return dis;
	}

	public void setDis(Double dis) {
		this.dis = dis;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void passed(){
		passed = true;
	}
	
	public boolean hasPassed(){
		return passed;
	}
	
}

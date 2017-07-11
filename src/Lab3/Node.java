package Lab3;

public class Node {
	private int id;
	private String name;
	private int x_offset;
	private int y_offset;

	public Node(String name, int x, int y) {
		id = Integer.MIN_VALUE;
		this.name = name;
		x_offset = x;
		y_offset = y;
	}

	public Node(int id, String name, int x, int y) {
		this.id = id;
		this.name = name;
		x_offset = x;
		y_offset = y;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getX_offset() {
		return x_offset;
	}

	public int getY_offset() {
		return y_offset;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj.getClass() == Node.class) {
			Node node = (Node) obj;
			if (node.name.equals(this.name) && node.x_offset == this.x_offset && node.y_offset == this.y_offset)
				return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return name.hashCode() + (x_offset ^ y_offset);
	}

}
package Lab9;

public class Pair implements Comparable<Pair> {
	private Point p1;
	private Point p2;
	private double dist;

	public static void printPair(Pair pair) {
		System.out.printf("(%d, %d, %d)--%.2f--(%d, %d, %d)\n", pair.p1.getX(), pair.p1.getY(), pair.p1.getZ(),
				pair.getDist(), pair.p2.getX(), pair.p2.getY(), pair.p2.getZ());
	}

	public Pair(Point p1, Point p2) {
		this.p1 = p1;
		this.p2 = p2;
		dist = Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2)
				+ Math.pow(p1.getZ() - p2.getZ(), 2));
	}

	public Point getP1() {
		return p1;
	}

	public Point getP2() {
		return p2;
	}

	public double getDist() {
		return dist;
	}

	public boolean less(Pair pair) {
		return this.dist <= pair.dist;
	}

	@Override
	public int hashCode() {
		return (int) (p1.hashCode() + p2.hashCode() + dist);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj.getClass() == Pair.class) {
			Pair pair = (Pair) obj;
			boolean s1 = this.getP1().equals(pair.getP1()) && this.getP2().equals(pair.getP2());
			boolean s2 = this.getP1().equals(pair.getP2()) && this.getP2().equals(pair.getP1());
			if ((s1 || s2) && this.getDist() == pair.getDist())
				return true;
		}
		return false;
	}

	@Override
	public int compareTo(Pair o) {
		if (this.getDist() < o.getDist())
			return -1;
		else if (this.getDist() == o.getDist())
			return 0;
		else
			return 1;
	}
}

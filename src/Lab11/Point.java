package Lab11;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Point {
	private double x;
	private double y;

	@Override
	public String toString() {
		return String.format("(%.2f, %.2f)", x, y);
	}

	public static List<Point> deepClone(List<Point> list) {
		List<Point> out = new ArrayList<>();
		Iterator<Point> iterator = list.iterator();
		while (iterator.hasNext()) {
			Point point = (Point) iterator.next();
			out.add(point);
		}
		return out;
	}

	public static void printPoint(Point point) {
		System.out.printf("(%d, %d)\n", point.x, point.y);
	}

	public Point(double x) {
		this.x = x;
		y = 0;
	}

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	@Override
	public int hashCode() {
		return Double.hashCode(x) + Double.hashCode(y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj.getClass() == Point.class) {
			Point point = (Point) obj;
			return point.getX() == this.getX() && point.getY() == this.getY();
		}
		return false;
	}
}

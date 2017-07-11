package Lab9;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Point {
	private int x;
	private int y;
	private int z;

	public static double distanceX(Point p1, double X) {
		return Math.abs(p1.x - X);
	}
	
	public static double distanceY(Point p1, Point p2) {
		return Math.abs(p1.y - p2.y);
	}
	
	public static double distance(Point p1, Point p2) {
		return Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2)
		+ Math.pow(p1.getZ() - p2.getZ(), 2));
	}

	public static List<Point> deepClone(List<Point> list) {
		List<Point> out=new ArrayList<>();
		Iterator<Point> iterator=list.iterator();
		while (iterator.hasNext()) {
			Point point = (Point) iterator.next();
			out.add(point);
		}
		return out;
	}

	public static void printPoint(Point point) {
		System.out.printf("(%d, %d, %d)\n", point.x, point.y, point.z);
	}

	public Point(int x) {
		this.x = x;
		y = 0;
		z = 0;
	}

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
		z = 0;
	}

	public Point(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	@Override
	public int hashCode() {
		return x + y + z;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj.getClass() == Point.class) {
			Point point = (Point) obj;
			return point.getX() == this.getX() && point.getY() == this.getY() && point.getZ() == this.getZ();
		}
		return false;
	}
}

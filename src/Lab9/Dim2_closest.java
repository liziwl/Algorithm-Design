package Lab9;

import java.util.*;

public class Dim2_closest {
	public Pair Closest_Pair(List<Point> p) {
		List<Point> px = Point.deepClone(p);
		Collections.sort(px, new Dim2CompareX());
		List<Point> py = Point.deepClone(p);
		Collections.sort(py, new Dim2CompareY());

		Pair pair = Closest_Pair_Rec(px, py);
		return pair;
	}

	public Pair Closest_Pair_Rec(List<Point> px, List<Point> py) {
		if (px.size() <= 3) {
			if (px.size() == 2)
				return new Pair(px.get(0), px.get(1));
			if (px.size() == 3) {
				Pair p1 = new Pair(px.get(0), px.get(1));
				Pair p2 = new Pair(px.get(1), px.get(2));
				Pair p3 = new Pair(px.get(0), px.get(2));

				if (p1.less(p2)) {
					if (p1.less(p3))
						return p1;
					else
						return p3;
				} else {
					if (p2.less(p3))
						return p2;
					else
						return p3;
				}
			}
		}
		int mid = px.size() / 2;
		List<Point> qx = px.subList(0, mid);
		List<Point> rx = px.subList(mid, px.size());

		List<Point> qy = Point.deepClone(qx);
		Collections.sort(qy, new Dim2CompareY());
		List<Point> ry = Point.deepClone(rx);
		Collections.sort(ry, new Dim2CompareY());

		Pair q = Closest_Pair_Rec(qx, qy);
		Pair r = Closest_Pair_Rec(rx, ry);

		double delta = Math.min(q.getDist(), r.getDist());
		int splitX = qx.get(qx.size() - 1).getX();

		List<Point> sy = neighbour(py, splitX, delta);
		double d_min = Double.MAX_VALUE;
		Pair min = null;

		for (Point point : sy) {
			int index = sy.indexOf(point);
			if (point.getX() >= splitX)
				continue;// 过滤分割线左侧点
			for (int i = index + 1; i < sy.size(); i++) {
				if (Point.distanceY(sy.get(i), point) > delta)
					break;// 过滤y+方向绝对大于范围点
				if (d_min > Point.distance(point, sy.get(i))) {
					min = new Pair(point, sy.get(i));
					d_min = min.getDist();
				}
			}
			for (int i = index - 1; i >= 0; i--) {
				if (Point.distanceY(sy.get(i), point) > delta)
					break;// 过滤y-方向绝对大于范围点
				if (d_min > Point.distance(point, sy.get(i))) {
					min = new Pair(point, sy.get(i));
					d_min = min.getDist();
				}
			}
		}

		if (d_min < delta)
			return min;
		else if (q.getDist() <= r.getDist())
			return q;
		else
			return r;

	}

	public List<Point> neighbour(List<Point> py, int x, double delta) {
		List<Point> out = new ArrayList<>();
		Iterator<Point> iterator = py.iterator();
		while (iterator.hasNext()) {
			Point point = (Point) iterator.next();
			if (Point.distanceX(point, x) <= delta)
				out.add(point);
		}
		Collections.sort(out, new Dim2CompareY());
		return out;
	}
	
	public static long test(int size,int bound) {
		Random random = new Random();
		List<Point> test = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			Point temp = new Point(random.nextInt(bound), random.nextInt(bound));
			test.add(temp);
		}
		Dim2_closest find_min = new Dim2_closest();
		long start=System.currentTimeMillis();
		Pair min = find_min.Closest_Pair(test);
		long end=System.currentTimeMillis();
		Pair.printPair(min);
		return end-start;
	}

	public static void main(String[] args) {
//		Random random = new Random();
//		List<Point> test = new ArrayList<>();
//		for (int i = 0; i < 10; i++) {
//			Point temp = new Point(random.nextInt(1000), random.nextInt(1000));
//			// Point.printPoint(temp);
//			test.add(temp);
//		}
//		Dim2_closest find_min = new Dim2_closest();
//		Pair min = find_min.Closest_Pair(test);
//
//		Iterator<Point> iterator = test.iterator();
//		while (iterator.hasNext()) {
//			Point point = (Point) iterator.next();
//			Point.printPoint(point);
//		}
//		Pair.printPair(min);
		
		for (int i = 10; i < 100000000; i=i*10) {
			long time=test(i, i*10);
			System.out.printf("Size: %d Time: %d\n",i,time);
		}
	}
}

class Dim2CompareX implements Comparator<Point> {

	@Override
	public int compare(Point o1, Point o2) {
		if (o1.getX() == o2.getX())
			return 0;
		else if (o1.getX() < o2.getX())
			return -1;
		else
			return 0;
	}

}

class Dim2CompareY implements Comparator<Point> {

	@Override
	public int compare(Point o1, Point o2) {
		if (o1.getY() == o2.getY())
			return 0;
		else if (o1.getY() < o2.getY())
			return -1;
		else
			return 0;
	}

}
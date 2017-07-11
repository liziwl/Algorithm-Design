package Lab9;

import java.util.*;

public class Dim3_closest {
	public Pair Closest_Pair(List<Point> p) {
		List<Point> px = Point.deepClone(p);
		Collections.sort(px, new Dim3CompareX());
		List<Point> py = Point.deepClone(p);
		Collections.sort(py, new Dim3CompareY());
		List<Point> pz = Point.deepClone(p);
		Collections.sort(pz, new Dim3CompareZ());

		Pair pair = Closest_Pair_Rec(px, py,pz);
		return pair;
	}
	
	public Pair Closest_Pair_Rec(List<Point> px, List<Point> py,List<Point> pz) {
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
	}
}

class Dim3CompareX implements Comparator<Point> {

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

class Dim3CompareY implements Comparator<Point> {

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

class Dim3CompareZ implements Comparator<Point> {

	@Override
	public int compare(Point o1, Point o2) {
		if (o1.getZ() == o2.getZ())
			return 0;
		else if (o1.getZ() < o2.getZ())
			return -1;
		else
			return 0;
	}

}
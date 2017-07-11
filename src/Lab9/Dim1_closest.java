package Lab9;

import java.util.*;

public class Dim1_closest {
	public Pair Closest_Pair(List<Point> p) {
		Collections.sort(p, new Dim1Compare());
		Pair pair = Closest_Pair_Rec(p);
		return pair;
	}

	public Pair Closest_Pair_Rec(List<Point> p) {
		if (p.size() <= 3) {
			if (p.size() == 2)
				return new Pair(p.get(0), p.get(1));
			if (p.size() == 3) {
				Pair p1 = new Pair(p.get(0), p.get(1));
				Pair p2 = new Pair(p.get(1), p.get(2));
				return p1.compareTo(p2) < 1 ? p1 : p2;
			}
		}

		int mid = p.size() / 2;
		List<Point> ox = p.subList(0, mid);
		List<Point> rx = p.subList(mid, p.size());

		Pair q = Closest_Pair_Rec(ox);
		Pair r = Closest_Pair_Rec(rx);

		double delta = Math.min(q.getDist(), r.getDist());

		Pair mid_pair = new Pair(ox.get(mid-1), rx.get(0));

		if (mid_pair.getDist() <= delta)
			return mid_pair;
		else if (q.compareTo(r) < 1)
			return q;
		else
			return r;
	}
	
	public static void main(String[] args) {
		Random random=new Random();
		List<Point> test= new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			Point temp=new Point(random.nextInt(1000));
//			Point.printPoint(temp);
			test.add(temp);
		}
		Dim1_closest find_min=new Dim1_closest();
		Pair min=find_min.Closest_Pair(test);
		
		Iterator<Point> iterator = test.iterator();
		while (iterator.hasNext()) {
			Point point = (Point) iterator.next();
			Point.printPoint(point);
		}
		
		Pair.printPair(min);
	}
}

class Dim1Compare implements Comparator<Point> {

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
package Lab11;

import java.io.*;
import java.util.*;

public class SLS {
	public static void main(String[] args) {
		Point p1 = new Point(1, 1);
		Point p2 = new Point(1.2, 1.8);
		Point p3 = new Point(2, 2);
		double c = 1;
		System.out.println("c=" + c);
		List<Point> test1 = new ArrayList<>();
		test1.add(p1);
		test1.add(p2);
		test1.add(p3);

		printLineList(segmented_least_squares(test1, c));

		for (int i = 0; i < 20; i++) {
			System.out.printf("c=%.2f, size=%d\n", i / 10.0, test_module(i / 10.0));
		}
	}

	public static int test_module(double c) {
		// 文件测试
		List<Point> test2 = new ArrayList<>();
		File file = new File("input_point.txt");

		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String raw_data = "";// 原始数据
			while ((raw_data = bufferedReader.readLine()) != null) {
				String[] data = raw_data.split(",");// 数据规格化处理
				Point temp = new Point(Double.valueOf(data[0]), Double.valueOf(data[1]));
				test2.add(temp);
			}
			bufferedReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Line> out2 = new ArrayList<>();
		out2 = segmented_least_squares(test2, c);
		// printLineList(out2);
		// if (c == 1) {
		// System.out.println("backtrace");
		// printLineList(backtrack(out2));
		// }
		return backtrack(out2).size();

	}

	// 打印列表
	public static void printLineList(List<Line> list) {
		Iterator<Line> iterator = list.iterator();
		while (iterator.hasNext()) {
			Line line = (Line) iterator.next();
			System.out.println(line);
		}
	}

	public static List<Line> segmented_least_squares(List<Point> p, double c) {
		// n用p的size代替
		List<Line> out = new ArrayList<>();
		double[][] e = new double[p.size()][p.size()];
		if (p.size() == 1) {
			out.add(new Line(0, 0));
			return out;
		}
		if (p.size() == 2) {
			out.add(new Line(0, 1));
			return out;
		}

		for (int j = 0; j < p.size(); j++) {
			for (int i = 0; i <= j; i++) {
				if (i == j) {
					e[i][j] = 0;
					continue;
				}
				LS temp = LS.ls_coefficient(p, i, j);
				e[i][j] = clac_e(temp, p, i, j);
			}
		}

		double[] min = new double[p.size()];
		min[0] = 0;
		Line[] lineList = new Line[p.size()];
		lineList[0] = null;

		for (int j = 1; j < min.length; j++) {
			double t_min = Double.MAX_VALUE;
			for (int i = 0; i < j; i++) {
				double temp = e[i][j] + c + min[i];
				if (temp < t_min) {
					t_min = temp;
					lineList[j] = new Line(i, j);
				}
			}
			min[j] = t_min;
		}
		// System.out.println("end");
		return Arrays.asList(lineList);
	}

	// 计算误差
	public static double clac_e(LS coeff, List<Point> p, int from, int end) {
		double out = 0;
		for (int i = from; i <= end; i++) {
			double temp = p.get(i).getY() - coeff.a * p.get(i).getX() - coeff.b;
			out += Math.pow(temp, 2);
		}
		return out;
	}

	// 回溯
	public static List<Line> backtrack(List<Line> list) {
		if (list.size() == 0) {
			return null;
		}
		LinkedList<Line> out = new LinkedList<Line>();
		out.push(list.get(list.size() - 1));
		for (int i = list.size() - 1; i >= 0; i--) {
			if (list.get(i) == null)
				continue;
			if (out.peek().contain(list.get(i)))
				continue;
			else if (out.peek().isLinked(list.get(i)))
				out.push(list.get(i));
			else
				continue;
		}
		return out;
	}
}

class LS {
	// y = ax+b
	final double a;
	final double b;

	public LS(double a, double b) {
		this.a = a;
		this.b = b;
	}

	// 分段计算直线系数y=ax+b
	public static LS ls_coefficient(List<Point> p, int from, int end) {
		double len = end - from + 1;
		double avg_x = 0;
		double avg_y = 0;
		double sum_xy = 0;
		double sum_x2 = 0;

		for (int i = from; i <= end; i++) {
			avg_x += p.get(i).getX();
			avg_y += p.get(i).getY();
			sum_xy += p.get(i).getX() * p.get(i).getY();
			sum_x2 += Math.pow(p.get(i).getX(), 2);
		}

		avg_x = avg_x / len;
		avg_y = avg_y / len;

		double a = (sum_xy - len * avg_x * avg_y) / (sum_x2 - len * Math.pow(avg_x, 2));
		double b = avg_y - a * avg_x;
		return new LS(a, b);
	}
}

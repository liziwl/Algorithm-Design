package Lab6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class CachedLRU {
	private LinkedList<Integer> poolList;

	private int size;
	private int current;
	private int young;
	private int old;
	private int insert;

	private int strike;// 击中数
	private int fail;// 未集中数

	public CachedLRU(int size, double R) {
		current = 0;
		this.size = size;
		old = (int) (size * R);
		young = size - old;
		insert = young;
		poolList = new LinkedList<>();
		strike = 0;
		fail = 0;
	}

	public boolean isYoungFull() {
		return current >= young;
	}

	public boolean isFull() {
		return current >= size;
	}

	public boolean isOldFull() {
		return current - young >= old;
	}

	public int[] add_naive(int x) {
		int drop = -1;
		if (poolList.remove((Integer) x)) {
			poolList.addFirst(x);
			strike += 1;
		} else {
			fail += 1;
			if (!isYoungFull()) {
				poolList.addFirst(x);
				current = current + 1;
			} else {
				if (isOldFull()) {
					drop = poolList.removeLast();
					poolList.add(insert, x);
				} else {
					poolList.add(insert, x);
					current = current + 1;
				}
			}
		}

		if (drop == -1) {
			int[] temp1 = {};
			return temp1;
		} else {
			int[] temp2 = { drop };
			return temp2;
		}
	}

	public int[] add_naive(int[] x) {
		ArrayList<Integer> drop = new ArrayList<>();
		for (int i = 0; i < x.length; i++) {
			int[] d = add_naive(x[i]);
			if (d.length != 0)
				drop.add(d[0]);
		}

		int[] out = new int[drop.size()];
		int count = 0;
		Iterator<Integer> iterator = drop.iterator();
		while (iterator.hasNext()) {
			Integer integer = (Integer) iterator.next();
			out[count++] = integer;
		}
		return out;
	}

	public void print_naive() {
		System.out.println("From new to old:");
		int count = 0;
		Iterator<Integer> iterator = poolList.iterator();
		while (iterator.hasNext()) {
			Integer x = (Integer) iterator.next();
			System.out.print(x + " ");
			count++;
			if (count == insert) {
				System.out.print("***M*** ");
			}
		}
		System.out.println();
	}

	public double rate() {
		double up = strike;
		double down = strike + fail;
		return up / down;
	}

	public void printRate_Drop(int[] drop) {
		System.out.println("Droped List: " + Arrays.toString(drop));
		System.out.printf("Strike Rate: %.4f\n", rate());
	}

	public void printRate() {
		System.out.printf("Strike Rate: %.4f\n", rate());
	}

	public static int[] RandomArray(int size, int upperBound) {
		int[] rand = new int[size];
		Random random = new Random();
		for (int i = 0; i < rand.length; i++) {
			rand[i] = random.nextInt(upperBound + 1);
		}
		return rand;
	}

	public static void main(String[] args) {
		CachedLRU test1 = new CachedLRU(8, 0.375);
		int[] s1 = { 1, 3, 2, 1, 5, 4, 3 };
		int[] s2 = { 7, 6, 8 };

		int[] out = {};
		out = test1.add_naive(s1);
		test1.print_naive();
		test1.printRate_Drop(out);

		out = test1.add_naive(s2);
		test1.print_naive();
		test1.printRate_Drop(out);

		out = test1.add_naive(6);
		test1.print_naive();
		test1.printRate_Drop(out);

		out = test1.add_naive(5);
		test1.print_naive();
		test1.printRate_Drop(out);

		out = test1.add_naive(9);
		test1.print_naive();
		test1.printRate_Drop(out);

		// out = test1.add_naive(10);
		// test1.print_naive();
		// test1.printRate_Drop(out);

		CachedLRU test2 = new CachedLRU(100, 0.375);
		long st1=System.currentTimeMillis();
		test2.add_naive(RandomArray(1000, 800));
		long end1=System.currentTimeMillis();
		test2.print_naive();
		test2.printRate();
		System.out.println("Time Usage in ms: "+(end1-st1));

		LRU test3 = new LRU(100, 0.375);
		long st2=System.currentTimeMillis();
		test3.add_naive(RandomArray(1000, 800));
		long end2=System.currentTimeMillis();
		test3.print_naive();
		test3.printRate();
		System.out.println("Time Usage in ms: "+(end2-st2));
	}
}

package Lab6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.Stack;

public class LRU {
	private LinkedList<Integer> poolList;
	private LinkedHashSet<Integer> poolSet;
	private int size;
	private int current;

	private int strike;// 击中数
	private int fail;// 未集中数

	public LRU(int size, double R) {
		current = 0;
		this.size = size;
		poolList = new LinkedList<>();
		poolSet = new LinkedHashSet<>();
		strike = 0;
		fail = 0;
	}

	public int[] add_naive(int x) {
		int drop = -1;
		if (poolList.remove((Integer) x)) {
			poolList.addFirst(x);
			strike += 1;
		} else {
			if (isFull()) {
				drop = poolList.removeLast();
				poolList.addFirst(x);
				fail += 1;
			} else {
				poolList.addFirst(x);
				current += 1;
				fail += 1;
			}
		}

		if (drop == -1) {
			int[] temp11 = {};
			return temp11;
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

	public int[] add_hash(int x) {
		int drop = -1;
		if (poolSet.remove((Integer) x)) {
			poolSet.add(x);
			strike += 1;
		} else {
			if (isFull()) {
				Iterator<Integer> iterator = poolSet.iterator();
				int first = iterator.next();
				drop = first;
				poolSet.remove(first);
				poolSet.add(x);
				fail += 1;
			} else {
				poolSet.add(x);
				fail += 1;
				current += 1;
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

	public int[] add_hash(int[] x) {
		ArrayList<Integer> drop = new ArrayList<>();
		for (int i = 0; i < x.length; i++) {
			int[] d = add_hash(x[i]);
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

	public boolean isFull() {
		return current >= size;
	}

	public double rate() {
		double up = strike;
		double down = strike + fail;
		return up / down;
	}

	public void printRate() {
		System.out.printf("Strike Rate: %.4f\n\n", rate());
	}

	public void printRate_Drop(int[] drop) {
		System.out.println("Droped List: " + Arrays.toString(drop));
		System.out.printf("Strike Rate: %.4f\n", rate());
	}

	public void print_naive() {
		System.out.println("From new to old:");
		Iterator<Integer> iterator = poolList.iterator();
		while (iterator.hasNext()) {
			Integer x = (Integer) iterator.next();
			System.out.print(x + " ");
		}
		System.out.println();
	}

	public void print_hash() {
		System.out.println("From new to old:");
		Iterator<Integer> it1 = poolSet.iterator();
		Stack<Integer> stack = new Stack<>();
		while (it1.hasNext()) {
			Integer x = (Integer) it1.next();
			stack.push(x);
		}
		while (!stack.isEmpty())
			System.out.print(stack.pop() + " ");

		System.out.println();
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
		// LinkedList
		LRU test1 = new LRU(8, 0.375);
		int[] s1 = { 1, 3, 2, 1, 5, 4, 3 };
		int[] s2 = { 7, 6, 8 };
		int[] s3 = { 6, 5 };

		System.out.println("LinkedList");
		int[] out = {};
		out = test1.add_naive(s1);
		test1.print_naive();
		test1.printRate_Drop(out);

		out = test1.add_naive(s2);
		test1.print_naive();
		test1.printRate_Drop(out);

		out = test1.add_naive(s3);
		test1.print_naive();
		test1.printRate_Drop(out);

		out = test1.add_naive(9);
		test1.print_naive();
		test1.printRate_Drop(out);

		// LinkedHashSet
		LRU test2 = new LRU(8, 0.375);

		System.out.println("\nLinkedHashSet");
		int[] out2 = {};
		out2 = test2.add_hash(s1);
		test2.print_hash();
		test2.printRate_Drop(out2);

		out2 = test2.add_hash(s2);
		test2.print_hash();
		test2.printRate_Drop(out2);

		out2 = test2.add_hash(s3);
		test2.print_hash();
		test2.printRate_Drop(out2);

		out2 = test2.add_hash(9);
		test2.print_hash();
		test2.printRate_Drop(out2);

	}
}

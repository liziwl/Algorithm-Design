package Lab8;

import java.util.Arrays;

public class sortCount {

	public rtANDList Sort_and_Count(int[] list) throws Exception {
		if (list.length == 1)
			return new rtANDList(list, 0);

		int mid = list.length / 2;
		int[] listA = subList(0, mid, list);
		int[] listB = subList(mid, list.length, list);
		rtANDList a = Sort_and_Count(listA);
		rtANDList b = Sort_and_Count(listB);
		rtANDList out = Merge_and_Count(a.getList(), b.getList());

		return new rtANDList(out.getList(), out.getInverse() + a.getInverse() + b.getInverse());
	}

	public rtANDList Merge_and_Count(int[] listA, int[] listB) {
		int[] out = new int[listA.length + listB.length];//声明合并后空间
		int count = 0;//out索引
		int r = 0;//逆序数
		int i = 0;//A索引
		int j = 0;//B索引
		if (listA[listA.length - 1] <= listB[0]) {
			// 优化1：如果A的最后一位<B的第一位，直接进行输出
			while (i < listA.length) {
				out[count++] = listA[i];
				i++;
			}
			while (j < listB.length) {
				out[count++] = listB[j];
				j++;
			}
		} else {
			while (i < listA.length && j < listB.length) {
				if (listA[i] < listB[j]) {
					out[count++] = listA[i];
					i++;
				} else {
					out[count++] = listB[j];
					j++;
					r += listA.length - i;
				}
			}
			while (i < listA.length) {
				out[count++] = listA[i];
				i++;
			}
			while (j < listB.length) {
				out[count++] = listB[j];
				j++;
			}
		}
		return new rtANDList(out, r);
	}

	public int[] subList(int from, int to, int[] list) throws Exception {
		if (from > to)
			throw new Exception("From is smaller than to.");
		if (from < 0 || to > list.length)
			throw new Exception("Out of bound.");
		int[] out = new int[to - from];
		for (int i = 0; i < to - from; i++) {
			out[i] = list[from + i];
		}
		return out;
	}

	// 带opt为实现优化2策略的方法，减少内存使用
	public rtANDList Sort_and_Count_Opt(int[] list) {
		int[] aux = new int[list.length];
		return Sort_and_Count_Opt(list, aux, 0, list.length - 1);
	}

	public rtANDList Sort_and_Count_Opt(int[] list, int[] aux, int low, int high) {
		if (high - low + 1 == 1)
			return new rtANDList(list, 0);
		int mid = low + (high - low) / 2;
		rtANDList A = Sort_and_Count_Opt(list, aux, low, mid);// 返回有序的list
		rtANDList B = Sort_and_Count_Opt(list, aux, mid + 1, high);// 返回有序的list
		rtANDList R = Merge_and_Count_Opt(list, aux, low, mid, high);// 返回有序的list

		rtANDList out = new rtANDList(R.getList(), A.getInverse() + B.getInverse() + R.getInverse());
		return out;
	}

	public rtANDList Merge_and_Count_Opt(int[] list, int[] aux, int low, int mid, int high) {
		// aux缓存
		// list有2段有序序列待合并
		for (int i = low; i <= high; i++) {
			aux[i] = list[i];
		}

		if (list[mid] <= list[mid + 1]) {
			// 优化1：如果前段小于后段直接输出
			return new rtANDList(list, 0);
		} else {
			int r = 0;// 逆序数
			int i = low;// A的首元素
			int j = mid + 1;// B的首元素
			int counter = low;// list索引
			while (i <= mid && j <= high) {//合并
				if (aux[i] <= aux[j]) {
					list[counter++] = aux[i++];
				} else {
					list[counter++] = aux[j++];
					r += mid + 1 - i;
				}
			}
			//剩余元素复制
			while (i <= mid) {
				list[counter++] = aux[i++];
			}
			while (j <= high) {
				list[counter++] = aux[j++];
			}
			return new rtANDList(list, r);
		}
	}

	public static void main(String[] args) {
		sortCount test = new sortCount();
		int[] a = { 1, 3, 4, 2, 5 };
		// int[] a = { 5, 4, 3, 2, 1 };
		// 只优化1
		try {
			rtANDList out = test.Sort_and_Count(a);
			System.out.println(Arrays.toString(out.getList()));
			System.out.println(out.getInverse());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 优化1和2
		rtANDList out2 = test.Sort_and_Count_Opt(a);
		System.out.println(Arrays.toString(out2.getList()));
		System.out.println(out2.getInverse());
	}
}

//打包类，用作返回2个对象
class rtANDList {
	private int[] list;
	private int inverse;

	public rtANDList(int[] list, int val) {
		this.list = list;
		inverse = val;
	}

	public int[] getList() {
		return list;
	}

	public int getInverse() {
		return inverse;
	}
}
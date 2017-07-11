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
		int[] out = new int[listA.length + listB.length];//�����ϲ���ռ�
		int count = 0;//out����
		int r = 0;//������
		int i = 0;//A����
		int j = 0;//B����
		if (listA[listA.length - 1] <= listB[0]) {
			// �Ż�1�����A�����һλ<B�ĵ�һλ��ֱ�ӽ������
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

	// ��optΪʵ���Ż�2���Եķ����������ڴ�ʹ��
	public rtANDList Sort_and_Count_Opt(int[] list) {
		int[] aux = new int[list.length];
		return Sort_and_Count_Opt(list, aux, 0, list.length - 1);
	}

	public rtANDList Sort_and_Count_Opt(int[] list, int[] aux, int low, int high) {
		if (high - low + 1 == 1)
			return new rtANDList(list, 0);
		int mid = low + (high - low) / 2;
		rtANDList A = Sort_and_Count_Opt(list, aux, low, mid);// ���������list
		rtANDList B = Sort_and_Count_Opt(list, aux, mid + 1, high);// ���������list
		rtANDList R = Merge_and_Count_Opt(list, aux, low, mid, high);// ���������list

		rtANDList out = new rtANDList(R.getList(), A.getInverse() + B.getInverse() + R.getInverse());
		return out;
	}

	public rtANDList Merge_and_Count_Opt(int[] list, int[] aux, int low, int mid, int high) {
		// aux����
		// list��2���������д��ϲ�
		for (int i = low; i <= high; i++) {
			aux[i] = list[i];
		}

		if (list[mid] <= list[mid + 1]) {
			// �Ż�1�����ǰ��С�ں��ֱ�����
			return new rtANDList(list, 0);
		} else {
			int r = 0;// ������
			int i = low;// A����Ԫ��
			int j = mid + 1;// B����Ԫ��
			int counter = low;// list����
			while (i <= mid && j <= high) {//�ϲ�
				if (aux[i] <= aux[j]) {
					list[counter++] = aux[i++];
				} else {
					list[counter++] = aux[j++];
					r += mid + 1 - i;
				}
			}
			//ʣ��Ԫ�ظ���
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
		// ֻ�Ż�1
		try {
			rtANDList out = test.Sort_and_Count(a);
			System.out.println(Arrays.toString(out.getList()));
			System.out.println(out.getInverse());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// �Ż�1��2
		rtANDList out2 = test.Sort_and_Count_Opt(a);
		System.out.println(Arrays.toString(out2.getList()));
		System.out.println(out2.getInverse());
	}
}

//����࣬��������2������
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
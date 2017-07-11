package Lab1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class GS {
	// ƫ�ñ�����
	public static int[][] Prefer_initiate(int n) {
		int[][] pre = new int[n][n];

		List<Integer> generator = new ArrayList<Integer>();
		for (int i = 1; i <= n; i++) {
			generator.add(i);
		}

		for (int i = 0; i < n; i++) {
			Collections.shuffle(generator);
			Object[] temp = generator.toArray();
			for (int j = 0; j < n; j++) {
				pre[i][j] = (int) temp[j];
			}
		}
		return pre;
	}

	public static void printPrefer(int[][] prefer) {
		for (int i = 0; i < prefer.length; i++) {
			System.out.print("[");
			for (int j = 0; j < prefer.length; j++) {
				System.out.print(prefer[i][j] + ",");
			}
			System.out.println("]");
		}
	}

	public static void main(String args[]) {
		int person = 10000;// ������Ů������ȣ���Ů�ֱ�����
		long time1=System.currentTimeMillis();
		
		// ������ƫ�ó�ʼ��
		System.out.println("Man");
		int man_prefer[][] = Prefer_initiate(person);
//		printPrefer(man_prefer);
		
		// ����Ůƫ�ó�ʼ��
		System.out.println("Woman");
		int woman_prefer[][] = Prefer_initiate(person);
//		printPrefer(woman_prefer);
		System.out.println("end initiate");
		
		// woman,man
		Map<Integer, Integer> gs = new HashMap<Integer, Integer>();
		// man,woman
		boolean[][] proposed = new boolean[person][person];
		
		// �����ʼ��
		for (int i = 0; i < proposed.length; i++) {
			for (int j = 0; j < proposed.length; j++) {
				proposed[i][j] = false;
			}
		}

		// ��ʼ��������
		Queue<Integer> freeman = new LinkedList<Integer>();
		for (int i = 1; i <= person; i++) {
			freeman.add(i);
		}

		while (!freeman.isEmpty()) {
			// ѡ������һ������ m �����ɵ��һ�û��ÿ��Ů�˶������
			int man = freeman.poll();
			int m2w = -1;
			// �� m2w �� m �����ȱ��� m ��û���������������Ů��
			for (int i = 0; i < man_prefer.length; i++) {
				int temp_w=man_prefer[man-1][i];//�ó�������ߵ�
				if (proposed[man - 1][temp_w-1] == false) {
					//����Ƿ�����
					m2w = temp_w;
					break;
				}
			}
			if(m2w==-1)
				continue;
			// if w �����ɵ�
			if (!gs.containsKey(m2w)) {
				gs.put(m2w, man);
				proposed[man-1][m2w-1]=true;
			} else {
				int man2 = gs.get(m2w);
				for (int i = 0; i < woman_prefer.length; i++) {
					//w����m
					if (woman_prefer[m2w - 1][i] == man) {
						gs.remove(m2w);
						gs.put(m2w, man);
						proposed[man-1][m2w-1]=true;
						freeman.add(man2);
						break;
					}
					//w����man2��ԭ��
					if (woman_prefer[m2w - 1][i] == man2) {
						freeman.add(man);
						proposed[man-1][m2w-1]=true;
						break;
					}
				}
			}

		}
		System.out.println("end matching");

		Iterator iter = gs.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
			System.out.printf("Man%d match Woman%d,\n", val, key);
		}
		System.out.printf("%d men and %dwomen forms %d couples\n", person, person, gs.size());
		long time2=System.currentTimeMillis();
		System.out.println("Time usage:"+(double)(time2-time1)/1000000);
	}

}

package Lab2_1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Matching {
	static int n;
	static ArrayList<Person> men, women;
	static Queue<Person> freemen;

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

		System.out.print("n = ");
		n = in.nextInt();
		initialPeople();
		printLists();
		GSMatching();
		printResult();
		in.close();
	}

	public static void initialPeople() {
		men = new ArrayList<>();
		women = new ArrayList<>();
		freemen = new LinkedList<>();
		for (int i = 0; i < n; ++i) {
			men.add(new Person("Man" + (i + 1)));
			women.add(new Person("Woman" + (i + 1)));
		}
		for (Person m : men) {
			freemen.add(m);
			m.initialPrefer(women);
		}
		for (Person w : women) {
			w.initialPrefer(men);
		}
	}

	public static void printLists() {
		System.out.println("\nMen's perference list:");
		for(Person m:men){
			System.out.printf("%s: ",m.getName());
			for(Person w:m.getPrefer().keySet())
				System.out.printf("%s, ",w.getName());
			System.out.println();
		}
		System.out.println("\nWomen's perference list:");
		for(Person w:women){
			System.out.printf("%s: ",w.getName());
			for(Person m:w.getPrefer().keySet())
				System.out.printf("%s, ",m.getName());
			System.out.println();
		}
	}

	public static void GSMatching() {
		Person man, woman, pre;
		String Mname, Wname;
		while (freemen.size()>0) {
			man=freemen.poll();
			Mname=man.getName();
			System.out.printf("\nMatch for %s:\n",Mname);
			while ((woman=man.searchNext())!=null) {
				Wname =woman.getName();
				System.out.printf("He choose %s\n",Wname);
				if (woman.isSingle()) {
					System.out.printf("She has no husband\n");
					woman.matchWith(man);
					System.out.printf("%s and %s become a couple\n",Mname, Wname);
					break;
				}else {
					if (woman.conpareWith(man).equals(man)) {
						pre=woman.getMate();
						System.out.printf("She perfers %s to her husband (%s)\n",Mname,pre.getName());
						freemen.add(pre);
						System.out.printf("%s becomes single\n",pre.getName());
						woman.breakup();
						woman.matchWith(man);
						System.out.printf("And %s, %s become a couple\n",Mname,Wname);
						break;
					}else {
						System.out.printf("She prefers her husband\n");
						System.out.printf("%s is still single\n",Mname);
						continue;
					}
				}
			}
		}
	}

	public static void printResult() {
		System.out.println("\nMatched couple(s): ");
		for(Person m:men){
			System.out.printf("%s - %s\n",m.getName(),m.getMate().getName());
		}
	}
}

package Lab2_1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Person {
	public String name;
	public Person mate;
	public boolean isSingle;
	public Map<Person, Integer> prefer;
	Iterator<Person> it_prefer;// 当前求婚的位置

	public Person(String name) {
		this.name = name;
		isSingle = true;
	}

	public String getName() {
		return name;
	}

	public Person getMate() {
		return mate;
	}

	public boolean isSingle() {
		return isSingle;
	}

	public Map<Person, Integer> getPrefer() {
		return prefer;
	}

	public void initialPrefer(ArrayList<Person> opp) {
		prefer = new LinkedHashMap<>();
		@SuppressWarnings("unchecked")
		ArrayList<Person> people = (ArrayList<Person>) opp.clone();
		Collections.shuffle(people);
		for (int i = 0; i < opp.size(); ++i) {
			prefer.put(people.remove(0), i);
		}
		it_prefer = prefer.keySet().iterator();
	}

	public Person searchNext() {
		if (it_prefer.hasNext()) {
			return it_prefer.next();
		} else {
			return null;
		}
	}

	public void matchWith(Person mate) {
		if (prefer.containsKey(mate)) {
			this.mate = mate;
			isSingle = false;
			mate.mate = this;
			mate.isSingle = false;
		} else
			System.out.printf("Cannot find %s\n", mate.getName());
		;
	}

	public void breakup() {
		mate.mate = null;
		mate.isSingle = true;
		mate = null;
		isSingle = true;
	}

	public Person conpareWith(Person newPerson) {
		if (prefer.get(newPerson) < prefer.get(getMate()))
			return newPerson;
		return getMate();
	}
}

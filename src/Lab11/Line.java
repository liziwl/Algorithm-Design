package Lab11;

public class Line {
	private int from;
	private int to;

	public Line(int i, int j) {
		from = i;
		to = j;
	}

	public int getFrom() {
		return from;
	}

	public int getTo() {
		return to;
	}

	public boolean contain(Line o) {
		if (this.from <= o.from && this.to >= o.to)
			return true;
		else
			return false;
	}

	public boolean isLinked(Line o) {
		if (o.to==this.from)
			return true;
		else
			return false;
	}

	@Override
	public String toString() {
		return String.format("(%d, %d)", from, to);
	}
}

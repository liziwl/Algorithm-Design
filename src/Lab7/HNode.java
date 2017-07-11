package Lab7;

public class HNode implements Comparable<HNode>{
	private byte value;
	private boolean valid;
	private double probability;
	private HNode leftNode;
	private HNode rightNode;
	
	public HNode(double probability,HNode left,HNode right) {
		value=Byte.MIN_VALUE;
		valid=false;
		this.probability=probability;
		leftNode=left;
		rightNode=right;
	}
	
	public HNode(byte value,double probability) {
		valid=true;
		this.value=value;
		this.probability=probability;
		leftNode=null;
		rightNode=null;
	}

	public byte getValue() {
		return value;
	}

	public double getProbability() {
		return probability;
	}

	public HNode getLeftNode() {
		return leftNode;
	}

	public HNode getRightNode() {
		return rightNode;
	}

	public boolean isValid() {
		return valid;
	}
	
	@Override
	public int compareTo(HNode o) {
		if(this.getProbability()<o.probability)
			return -1;
		else if (this.getProbability()==o.probability) 
			return 0;
		else 
			return 1;
	}
	
}

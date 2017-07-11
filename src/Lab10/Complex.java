package Lab10;

public class Complex {
	final double x, y;

	public Complex(double real, double imaginary) {
		this.x = real;
		this.y = imaginary;
	}
	
	public Complex(double real) {
		this.x = real;
		this.y = 0;
	}

	public double real() {
		return x;
	} // ����ʵ��

	public double imaginary() {
		return y;
	} // �����鲿

	public double magnitude() {
		return Math.sqrt(x * x + y * y);
	} // ����ģ��

	//����С��λ�����������ж��Ƿ�Ϊ0
	public boolean isZero(int pow) {
		if (pow < 0) throw new IllegalArgumentException();

	    long factor = (long) Math.pow(10, pow);
	    double rx=Math.round(factor*x);
	    double ry=Math.round(factor*y);
	    
	    return rx/factor==0&&ry/factor==0;
	    
	}
	
	@Override
	public String toString() {
		if (y<0) 	
			return String.format("(%.2f%.2fi)", x,y);
		else if(y>0)
			return String.format("(%.2f+%.2fi)", x,y);
		else
			return String.format("(%.2f)", x);
	}

	// �����������
	public static Complex add(Complex a, Complex b) {
		return new Complex(a.x + b.x, a.y + b.y);
	}

	// this��������һ���������
	public Complex add(Complex a) {
		return new Complex(this.x + a.x, this.y + a.y);
	}

	// �����������
	public static Complex minus(Complex a, Complex b) {
		return new Complex(a.x - b.x, a.y - b.y);
	}

	// this��������һ���������
	public Complex minus(Complex a) {
		return new Complex(this.x - a.x, this.y - a.y);
	}

	// �����������
	public static Complex multiply(Complex a, Complex b) {
		return new Complex(a.x * b.x - a.y * b.y, a.x * b.y + a.y * b.x);
	}

	// this����������һ���������
	public Complex multiply(Complex a) {
		return new Complex(x * a.x - y * a.y, x * a.y + y * a.x);
	}

	// ŷ����ʽ��õ�λ����
	public static Complex stdComplex(double k, double n) {
		return new Complex(Math.cos(-2 * Math.PI * k / n), Math.sin(-2 * Math.PI * k / n));
	}
	
//	public static void main(String[] args) {
//		System.out.println(0.00==0);
//	}
}
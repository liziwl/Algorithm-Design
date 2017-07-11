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
	} // 返回实部

	public double imaginary() {
		return y;
	} // 返回虚部

	public double magnitude() {
		return Math.sqrt(x * x + y * y);
	} // 返回模长

	//根据小数位数四舍五入判断是否为0
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

	// 两个复数相加
	public static Complex add(Complex a, Complex b) {
		return new Complex(a.x + b.x, a.y + b.y);
	}

	// this对象与另一个复数相加
	public Complex add(Complex a) {
		return new Complex(this.x + a.x, this.y + a.y);
	}

	// 两个复数相加
	public static Complex minus(Complex a, Complex b) {
		return new Complex(a.x - b.x, a.y - b.y);
	}

	// this对象与另一个复数相加
	public Complex minus(Complex a) {
		return new Complex(this.x - a.x, this.y - a.y);
	}

	// 两个复数相乘
	public static Complex multiply(Complex a, Complex b) {
		return new Complex(a.x * b.x - a.y * b.y, a.x * b.y + a.y * b.x);
	}

	// this对象与另外一个复数相乘
	public Complex multiply(Complex a) {
		return new Complex(x * a.x - y * a.y, x * a.y + y * a.x);
	}

	// 欧拉公式获得单位向量
	public static Complex stdComplex(double k, double n) {
		return new Complex(Math.cos(-2 * Math.PI * k / n), Math.sin(-2 * Math.PI * k / n));
	}
	
//	public static void main(String[] args) {
//		System.out.println(0.00==0);
//	}
}
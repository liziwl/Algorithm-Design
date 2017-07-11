package Lab10;

import java.util.*;

public class FFT {
	//可以根据base改进空间使用
	//但是目前先实现方法，不管优化。
	//频域变换
	public List<Complex> time2frequency(List<Complex> sample_time) throws Exception{
		int size=sample_time.size();
		if ((size&(size-1))!=0) 
			throw new Exception("The size do not match.");
		if (size==1)
			return sample_time;
		List<Complex> even=time2frequency(getEvenIndex(sample_time)); 
		List<Complex> odd=time2frequency(getOddIndex(sample_time)); 
		Complex[] out=new Complex[size];
		for (int k=0; k<size/2;k++ ) {
			Complex right=Complex.multiply(Complex.stdComplex(k, size), odd.get(k));
			out[k]=Complex.add(even.get(k), right);
			out[k+size/2]=Complex.minus(even.get(k), right);
		}
		return Arrays.asList(out);
	}

	//返回奇数索引
	public static List<Complex> getOddIndex(List<Complex> sample_time){
		List<Complex> out=new ArrayList<Complex>();
		for (int i=1; i<sample_time.size(); i+=2) {
			out.add(sample_time.get(i));
		}
		return out;
	}

	//返回偶数索引
	public static List<Complex> getEvenIndex(List<Complex> sample_time){
		List<Complex> out=new ArrayList<Complex>();
		for (int i=0; i<sample_time.size(); i+=2) {
			out.add(sample_time.get(i));
		}
		return out;
	}
	
	//打印列表
	public static void printList(List<Complex> list) {
		Iterator<Complex> iterator= list.iterator();
		int counter=0;
		while (iterator.hasNext()) {
			Complex complex = (Complex) iterator.next();
			System.out.print(counter++);
			System.out.print("\t");
			System.out.print(complex+"\n");
		}
		System.out.println();
	}
	
	//去除为0的点，并打印
	public static void printListXzero(List<Complex> list) throws IllegalArgumentException{
		Iterator<Complex> iterator= list.iterator();
		int counter=0;
		while (iterator.hasNext()) {
			Complex complex = (Complex) iterator.next();
			if (complex.isZero(2)) {
				counter++;
				continue;
			}
			System.out.print(counter++);
			System.out.print("\t");
			System.out.print(complex+"\n");
		}
		System.out.println();
	}
	
	public static List<Complex> function_generator(double sample_rate,double basis,double magnitude,double frequency, double shift){
		Complex[] list=new Complex[(int)sample_rate];
		for (int i = 0; i < list.length; i++) {
			double time=i/sample_rate;
			list[i]=new Complex(basis+magnitude*Math.sin(2*Math.PI*frequency*time+shift));
//			System.out.println(list[i]);
		}
		return Arrays.asList(list);
	}
	
	public static List<Complex> function_adder(List<Complex> a, List<Complex> b) throws Exception {
		Complex[] list=new Complex[a.size()];
		if (a.size()!=b.size()) 
			throw new Exception("Size can not match.");
		for (int i = 0; i < list.length; i++) {
			list[i]=Complex.add(a.get(i),b.get(i));
		}
		return Arrays.asList(list);
	}
	
	public static void main(String[] args) {
		List<Complex> test=new ArrayList<>();
		test.add(new Complex(0));
		test.add(new Complex(1));
		test.add(new Complex(0));
		test.add(new Complex(-1));
		FFT trans=new FFT();
		try {
			List<Complex> out=trans.time2frequency(test);
			printList(out);
			List<Complex> sample=function_adder(function_generator(256, 2, 3, 50, 0),function_generator(256, 0, 1.5, 75, 0));
			List<Complex> out2=trans.time2frequency(sample);
			printListXzero(out2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

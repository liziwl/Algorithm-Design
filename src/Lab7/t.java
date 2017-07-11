package Lab7;

import java.math.BigInteger;
import java.util.Arrays;

public class t {

	public static void main(String[] args) {
		String b = "10110101";
		byte[] bval = new BigInteger(b, 2).toByteArray();
		System.out.println(Arrays.toString(bval));
		
		byte b1 = (byte) -129;
		String s1 = String.format("%8s", Integer.toBinaryString(b1 & 0xFF)).replace(' ', '0');
		System.out.println(s1); // 10000001

		byte b2 = (byte) 2;
		String s2 = String.format("%8s", Integer.toBinaryString(b2 & 0xFF)).replace(' ', '0');
		System.out.println(s2); // 00000010
		
		String aString="abcdefg";
		System.out.println(aString.substring(2, 7));
		System.out.println(aString.length());
		System.out.println(aString+"a");
	}

}

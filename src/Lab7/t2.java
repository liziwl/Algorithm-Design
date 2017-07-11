package Lab7;

import java.math.BigInteger;
import java.util.Arrays;

public class t2 {
	public static void main(String[] args) {
		byte a=(byte) -75;
//		String s1 = String.format("%8s", Integer.toBinaryString(a & 0xFF)).replace(' ', '0');
		String s1 = String.format("%8s", Integer.toBinaryString(a & 0xFF)).replace(' ', '0');
		
//		try {
//			System.out.println(string2byte(s));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		// byte b = Byte.parseByte(s, 2);
		// System.out.println(b);
		// char a=s.charAt(2);
		// byte c=(byte) (a-48);
		// System.out.println(c);
		// byte[] bval = new BigInteger(s, 2).toByteArray()
		// byte b = Byte.parseByte(s, 2);
		// System.out.println(b);
		// char v = '0';
		// System.out.println(v == (char) 48);

		// byte[] bytes = s.getBytes();
		// StringBuilder binary = new StringBuilder();
		// for (byte b : bytes)
		// {
		// int val = b;
		// for (int i = 0; i < 8; i++)
		// {
		// binary.append((val & 128) == 0 ? 0 : 1);
		// val <<= 1;
		// }
		// binary.append(' ');
		// }
		// System.out.println("'" + s + "' to binary: " + binary);
		//
		// System.out.println(Integer.parseInt("01100110", 2));
	}

	public static byte string2byte(String bits8) throws Exception {
		if (bits8.length() != 8)
			throw new Exception("Bits do not match.");
		if (bits8.charAt(0) == (char) (49)) {
			// 第一位是1
			char[] schar = bits8.toCharArray();
			schar[0] = (char) (48);
			String temp = new String(schar);

			byte b1 = Byte.parseByte(temp, 2);
			byte b2 = (byte) (b1 ^ 128);

			return b2;
		} else {
			// 第一位是0
			return Byte.parseByte(bits8, 2);
		}
	}
}

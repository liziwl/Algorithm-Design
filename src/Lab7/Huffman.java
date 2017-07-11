package Lab7;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;

public class Huffman {
	private PriorityQueue<HNode> queue;
	private int[] byteCount;
	private HashMap<Byte, String> enMap;
	private HashMap<String, Byte> deMap;

	public Huffman() {
		queue = new PriorityQueue<>();
		byteCount = new int[257];
		// 0-255每个byte的出现次数，256存总次数
		enMap = new HashMap<>();
		deMap = new HashMap<>();
	}
	
	//编码根据文件生成的字节数组，生成霍夫曼树和映射
	public void setByteFrequency(byte[] array) {
		for (int i = 0; i < array.length; i++) {
			byteCount[array[i] + 128] += 1;
			byteCount[256] += 1;
		}
		init();
		System.out.println("Completed init");
		encode();
		System.out.println("Completed coding Huffman Tree");
		codeMap();
		System.out.println("Completed coding Huffman Map");
	}

	//生成概率数组，索引是加128偏移的byte值
	public void init() {
		for (int i = 0; i < byteCount.length - 1; i++) {
			if (byteCount[i] != 0) {
				double temp = ((double) byteCount[i]) / ((double) byteCount[256]);
				queue.add(new HNode((byte) (i - 128), temp));
			}
		}
	}

	//根据概率数组初始化
	public void encode() {
		HNode e1, e2, e;
		while (queue.size() != 1) {
			e1 = queue.remove();
			e2 = queue.remove();
			e = new HNode(e1.getProbability() + e2.getProbability(), e2, e1);
			queue.add(e);
		}
	}

	//生成霍夫曼映射
	public void codeMap() {
		String code = "";
		HNode root = queue.peek();
		codeMap(root, code);
	}

	public void codeMap(HNode parent, String code) {
		if (parent == null)
			return;
		if (!parent.isValid()) {
			codeMap(parent.getLeftNode(), code + "0");
			codeMap(parent.getRightNode(), code + "1");
		} else {
			enMap.put(parent.getValue(), code);
			deMap.put(code, parent.getValue());
			codeMap(parent.getLeftNode(), code + "0");
			codeMap(parent.getRightNode(), code + "1");
		}
	}

	//压缩文件
	public byte[] encodeFile(byte[] file) throws Exception {
		StringBuffer temp = new StringBuffer("");
		for (int i = 0; i < file.length; i++) {
			temp.append(enMap.get(file[i]));
		}
		String data = temp.toString();
		int remainer = data.length() % 8;
		if (remainer != 0) {
			for (int i = 8 - remainer; i > 0; i--) {
				data = data + "0";
			}
		}
		byte[] bval = string2byteArray(data);
		System.out.println("Completed coding the compressed the file");
		return bval;
	}
	
	//将01字符串转化为byte数值
	public static byte[] string2byteArray(String bits) throws Exception  {
		if(bits.length()%8!=0)
			throw new Exception("Bits do not match.");
		int length=bits.length()/8;
		byte[] out=new byte[length];
		for (int i = 0; i < length; i++) {
			out[i]=string2byte(bits.substring(i*8, i*8+8));
		}
		return out;
	}
	
	public static byte string2byte(String bits8) throws Exception {
		if(bits8.length()!=8)
			throw new Exception("Bits do not match.");
		if (bits8.charAt(0) == (char) (49)) {
			// 第一位是1
			char[] schar = bits8.toCharArray();
			schar[0] = (char) (48);
			String temp = new String(schar);
			
			byte b1=Byte.parseByte(temp, 2);
			byte b2=(byte) (b1^128);
			
			return b2;
		}else {
			//第一位是0
			return  Byte.parseByte(bits8, 2);
		}
	}
	
	//解压文件
	public byte[] decodeFile(byte[] file) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < file.length; i++) {
			String s1 = String.format("%8s", Integer.toBinaryString(file[i] & 0xFF)).replace(' ', '0');
			buffer.append(s1);
		}

		String zip = buffer.toString();
		int start = 0;
		int end = 0;
		ArrayList<Byte> temp = new ArrayList<>();
		while (start < zip.length() && end <= zip.length()) {
			if (deMap.containsKey(zip.substring(start, end))) {
				temp.add(deMap.get(zip.substring(start, end)));
				start = end;
			} else {
				end += 1;
			}
		}
		System.out.println("Completed coding the decompressed file");
		return convertArrayList(temp);
	}

	public static byte[] convertArrayList(ArrayList<Byte> list) {
		byte[] out = new byte[list.size()];
		Iterator<Byte> iterator = list.iterator();
		int counter = 0;
		while (iterator.hasNext()) {
			Byte current = (Byte) iterator.next();
			out[counter++] = current;
		}
		return out;
	}
	
	//压缩
	public void compress(String path) throws Exception {
		File file = new File(path);
		byte[] data = Files.readAllBytes(file.toPath());
		System.out.println("Completed reading the origin file: "+file.getPath());
		byte[] compress = encodeFile(data);
		
		File file2 = new File(path+".cmp");
		file2.createNewFile();
		FileOutputStream stream=new FileOutputStream(file2);
		stream.write(compress);
		System.out.println("Completed writing the compression: "+file2.getPath());
		stream.close();
	}

	//解压
	public void decompress(String path) throws IOException {
		File file = new File(path);
		byte[] data = Files.readAllBytes(file.toPath());
		System.out.println("Completed reading the compressed file: "+file.getPath());
		byte[] com = decodeFile(data);
		
		String path2=path.substring(0, path.length()-4);
		File file2 = new File(rename(path2));
		file2.createNewFile();
		FileOutputStream stream=new FileOutputStream(file2);
		stream.write(com);
		System.out.println("Completed writing the decompressed file: "+file2.getPath());
		stream.close();
	}
	
	public static String rename(String path) {
		int index=-1;
		for (int i = path.length()-1; i >=0; i--) {
			if (path.charAt(i)=='.') {
				index=i;
				break;
			}
		}
		return path.substring(0, index)+"DP"+path.substring(index);
	}
	public static void main(String[] args) {
		File file = new File("War and Peace by graf Leo Tolstoy.txt");
		System.out.println("Start to zip the file: "+file.getPath());
		Huffman huffman = new Huffman();
		try {
			byte[] data = Files.readAllBytes(file.toPath());
			huffman.setByteFrequency(data);
			huffman.compress(file.getPath());
			huffman.decompress(file.getPath()+".cmp");
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}

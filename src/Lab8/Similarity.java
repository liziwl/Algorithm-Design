package Lab8;

import java.io.*;
import java.util.*;

public class Similarity {
	public static void main(String[] args) {
		File file = new File("msd_1m.txt");
		File output = new File("SimilarityMetric.txt");
		ArrayList<userHistory> uid_songs = new ArrayList<>();//用户点击数据
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			sortCount counter = new sortCount();//逆序数计数器类
			rtANDList inverse;//声明计算器返回的打包类
			String raw_data = "";//原始数据
			// int line = 1;
			while ((raw_data = bufferedReader.readLine()) != null) {
				// System.out.println("line " + line + ": " + raw_data);
				// line++;
				String[] data = raw_data.split(",");//数据规格化处理

				userHistory temp = new userHistory(Integer.valueOf(data[0]));
				song[] hitList = new song[10];
				for (int i = 0; i < 10; i++) {
					hitList[i] = new song(i + 1, Integer.valueOf(data[i + 1]));
				}

				temp.setSongs(hitList);

				temp.markRank();//根据试听数赋值排名
				int[] ranks = temp.ranktoarray();//返回排名列表，按歌曲号索引
				// System.out.print(Arrays.toString(ranks));

				inverse = counter.Sort_and_Count_Opt(ranks);//返回打包类
				int inv = inverse.getInverse();//返回逆序数
				temp.setInverse(inv);//设定用户逆序数
				// System.out.println("\t逆序数: "+inv);
				uid_songs.add(temp);

				FileWriter fileWriter = new FileWriter(output);
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

				Collections.sort(uid_songs, new invCompare());//根据逆序数比较器排序
				Iterator<userHistory> iterator = uid_songs.iterator();
				while (iterator.hasNext()) {
					userHistory item = (userHistory) iterator.next();
					String writing = String.format("User %d: %s, Inverse:%d\r\n", item.getUid(),
							Arrays.toString(item.ranktoarray()), item.getInverse());
					bufferedWriter.write(writing);
				}
				bufferedWriter.flush();
				bufferedReader.close();
				bufferedWriter.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class invCompare implements Comparator<userHistory> {

	@Override
	public int compare(userHistory o1, userHistory o2) {
		if (o1.getInverse() == o2.getInverse())
			return 0;
		else if (o1.getInverse() < o2.getInverse())
			return -1;
		else
			return 1;
	}

}

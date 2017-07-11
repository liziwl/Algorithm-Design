package Lab8;

import java.io.*;
import java.util.*;

public class Similarity {
	public static void main(String[] args) {
		File file = new File("msd_1m.txt");
		File output = new File("SimilarityMetric.txt");
		ArrayList<userHistory> uid_songs = new ArrayList<>();//�û��������
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			sortCount counter = new sortCount();//��������������
			rtANDList inverse;//�������������صĴ����
			String raw_data = "";//ԭʼ����
			// int line = 1;
			while ((raw_data = bufferedReader.readLine()) != null) {
				// System.out.println("line " + line + ": " + raw_data);
				// line++;
				String[] data = raw_data.split(",");//���ݹ�񻯴���

				userHistory temp = new userHistory(Integer.valueOf(data[0]));
				song[] hitList = new song[10];
				for (int i = 0; i < 10; i++) {
					hitList[i] = new song(i + 1, Integer.valueOf(data[i + 1]));
				}

				temp.setSongs(hitList);

				temp.markRank();//������������ֵ����
				int[] ranks = temp.ranktoarray();//���������б�������������
				// System.out.print(Arrays.toString(ranks));

				inverse = counter.Sort_and_Count_Opt(ranks);//���ش����
				int inv = inverse.getInverse();//����������
				temp.setInverse(inv);//�趨�û�������
				// System.out.println("\t������: "+inv);
				uid_songs.add(temp);

				FileWriter fileWriter = new FileWriter(output);
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

				Collections.sort(uid_songs, new invCompare());//�����������Ƚ�������
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

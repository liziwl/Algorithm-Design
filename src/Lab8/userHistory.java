package Lab8;

import java.util.Arrays;

public class userHistory {
	private int uid;
	private song[] songs;
	private int inverse;

	public userHistory(int uid) {
		this(uid, null);
	}

	public userHistory(int uid, song[] songs) {
		this.uid = uid;
		this.songs = songs;
	}

	public userHistory(int uid, song[] songs,int inverse) {
		this.uid = uid;
		this.songs = songs;
		this.inverse=Integer.MAX_VALUE;
	}
	
	public void setSongs(song[] songs) {
		this.songs = songs;
	}

	public void setInverse(int inverse) {
		this.inverse = inverse;
	}
	
	public song[] getSongs() {
		return songs;
	}

	public int getUid() {
		return uid;
	}

	public int getInverse() {
		return inverse;
	}
	
	public void markRank() {
		Sort(1);// 按播放数降序标号
		int count = 1;
		int last = songs[0].getHit_count();
		for (int i = 0; i < songs.length; i++) {
			if (songs[i].getHit_count() == last) {
				songs[i].setRank(count);
			} else {
				songs[i].setRank(++count);
				last = songs[i].getHit_count();
			}
		}
		Sort(2);// 恢复歌曲索引升序
	}

	public int[] ranktoarray() {
		int[] out = new int[songs.length];
		for (int i = 0; i < songs.length; i++) {
			out[i] = songs[i].getRank();
		}
		return out;
	}

	public song[] Sort(int mode) {
		// mode1：播放数降序
		// mode2：歌曲索引升序
		song[] aux = new song[songs.length];
		return Sort(mode, songs, aux, 0, songs.length - 1);
	}

	public song[] Sort(int mode, song[] list, song[] aux, int low, int high) {
		if (high - low + 1 == 1)
			return list;
		int mid = low + (high - low) / 2;
		song[] A = Sort(mode, list, aux, low, mid);// 返回有序的list
		song[] B = Sort(mode, list, aux, mid + 1, high);// 返回有序的list
		song[] R = Merge(mode, list, aux, low, mid, high);

		return R;
	}

	public song[] Merge(int mode, song[] list, song[] aux, int low, int mid, int high) {
		// aux有序
		// list输出
		for (int i = low; i <= high; i++) {
			aux[i] = list[i];
		}

		switch (mode) {
		case 1:// 播放数降序
			if (list[mid].getHit_count() >= list[mid + 1].getHit_count()) {
				// 优化1：如果前段大于后段直接输出
				break;
			} else {
				int i = low;// A的首元素
				int j = mid + 1;// B的首元素
				int counter = low;// list索引
				while (i <= mid && j <= high) {
					if (aux[i].getHit_count() >= aux[j].getHit_count())
						list[counter++] = aux[i++];
					else
						list[counter++] = aux[j++];
				}
				while (i <= mid)
					list[counter++] = aux[i++];
				while (j <= high)
					list[counter++] = aux[j++];

				break;
			}

		case 2:// 歌曲索引升序
			if (list[mid].getId() <= list[mid + 1].getId()) {
				// 优化1：如果前段小于后段直接输出
				break;
			} else {
				int i = low;// A的首元素
				int j = mid + 1;// B的首元素
				int counter = low;// list索引
				while (i <= mid && j <= high) {
					if (aux[i].getId() <= aux[j].getId())
						list[counter++] = aux[i++];
					else
						list[counter++] = aux[j++];
				}
				while (i <= mid)
					list[counter++] = aux[i++];

				while (j <= high)
					list[counter++] = aux[j++];
				break;
			}
		}
		return list;
	}

	// public static void main(String[] args) {
	// userHistory test = new userHistory(1);
	// int[] arr = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	// System.out.println(Arrays.toString(arr));
	// song[] brr = new song[10];
	// for (int i = 0; i < arr.length; i++) {
	// brr[i] = new song(i + 1, arr[i]);
	// }
	// test.setSongs(brr);
	// test.setSongs(test.Sort(1));
	// for (int i = 0; i < brr.length; i++) {
	// System.out.print(test.getSongs()[i].getHit_count() + ",");
	// }
	// }

}

class song {
	private int id;
	private int hit_count;
	private int rank;

	public song(int id, int hit_count) {
		this.id = id;
		this.hit_count = hit_count;
	}

	public song(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public int getHit_count() {
		return hit_count;
	}

	public int getRank() {
		return rank;
	}

	public void setHit_count(int hit_count) {
		this.hit_count = hit_count;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}
}
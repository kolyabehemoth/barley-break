package com.app_name.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import android.graphics.Point;

public class LikeModel {
	public static final int size = 4;
	private int[][] arr;
	private Point p = new Point();
	private int countP;

	public LikeModel() {
		arr = new int[size][];
		for (int i = 0; i < size; i++) {
			arr[i] = new int[size];
		}
	}

	private void fillRandom() {
		Random r = new Random();
		List<Integer> l = new ArrayList<Integer>();
		for (int i = 0; i < size * size - 1; i++) {
			Integer temp = r.nextInt(size * size - 1) + 1;
			if (!l.contains(temp)) {
				l.add(temp);
			} else {
				i--;
			}
		}
		countP = getCountArr();
		convertListToArray2D(l);
	}

	private int getCountArr() {
		int count = 0;
		for (int k = 0; k < size; k++) {
			for (int p = 0; p < size && (k != size - 1 || p != size - 1); p++) {
				for (int i = k; i < size; i++) {
					for (int j = i == k ? p : 0; j < size
							&& (i != size - 1 || j != size - 1); j++) {
						if (arr[k][p] > arr[i][j]) {
							count++;
						}
					}
				}
			}
		}
		return count;
	}

	private void convertListToArray2D(List<Integer> l) {
		int j = 0;
		for (int i = 0; i < l.size(); i++) {
			int t = i - j * size;
			arr[j][t] = l.get(i);
			if ((i + 1) % size == 0 && i != 0) {
				j++;
			}
		}
		arr[size - 1][size - 1] = 0;
		p.x = size - 1;
		p.y = p.x;
	}

	public boolean checkResolvePossibility() {
		countP = getCountArr();
		return countP % 2 == 0 && countP != 0;
	}

	public boolean isResolved() {
		countP = getCountArr();
		return countP == 0;
	}

	public void fill() {
		fillRandom();
		while (!checkResolvePossibility()) {
			fillRandom();
		}
	}

	/*
	 * 0 - move top, 1 - move right, 2 - move down, 3 - move left
	 */
	public boolean move(int side) {
		switch (side) {
		case 0:
			if (p.x != 0) {
				arr[p.x][p.y] = arr[p.x - 1][p.y];
				arr[p.x - 1][p.y] = 0;
				p.x--;
				return true;
			}
			break;
		case 1:
			if (p.y != size - 1) {
				arr[p.x][p.y] = arr[p.x][p.y + 1];
				arr[p.x][p.y + 1] = 0;
				p.y++;
				return true;
			}
			break;
		case 2:
			if (p.x != size - 1) {
				arr[p.x][p.y] = arr[p.x + 1][p.y];
				arr[p.x + 1][p.y] = 0;
				p.x++;
				return true;
			}
			break;
		case 3:
			if (p.y != 0) {
				arr[p.x][p.y] = arr[p.x][p.y - 1];
				arr[p.x][p.y - 1] = 0;
				p.y--;
				return true;
			}
			break;
		}
		return false;
	}

	public final int[][] getArr() {
		return arr;
	}

	public Point getPoint() {
		return p;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				sb.append(arr[i][j] + "  ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
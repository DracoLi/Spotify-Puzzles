package com.draken.spotify;

import java.io.BufferedInputStream;
import java.util.Scanner;

public class Lottery {

	public static void main(String[] args) {
		String charsetName = "UTF-8";
		Scanner scanner = new Scanner(new BufferedInputStream(System.in), charsetName);
		
		while (scanner.hasNextLine()) {
			int m = scanner.nextInt();
			int n = scanner.nextInt();
			int t = scanner.nextInt();
			int p = scanner.nextInt();
		}
	}
}

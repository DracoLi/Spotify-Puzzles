package com.draken.spotify;

/***
 * Spotify Lottery Puzzle
 * @author Draco Li
 */
import java.io.BufferedInputStream;
import java.util.HashMap;
import java.util.Scanner;

public class Lottery {

	public static void main(String[] args) {
		String charsetName = "UTF-8";
		Scanner scanner = new Scanner(new BufferedInputStream(System.in), charsetName);
		while (scanner.hasNextLine()) {
			String[] val = scanner.nextLine().split(" ");
			int m = Integer.parseInt(val[0]);
			int n = Integer.parseInt(val[1]);
			int t = Integer.parseInt(val[2]);
			int p = Integer.parseInt(val[3]);
			
			// Uses the getting losses method
			double winProb = 0;
			double total = choose(m, n);
			
			// NOTE: conversion to float is needed else decimal will result in 0
			int winsNeeded = (int)Math.ceil((float)p / t);
			if (winsNeeded < n - winsNeeded) {
				// Count losses only if we have less losses
				int totalLosses = 0;
				for (int wins = 0; wins < winsNeeded; wins++) {
					// m - p is # of others in the competition
					// n - wins is the amount of wins we need starting from 0.
					totalLosses += choose(p, wins) * choose(m-p, n - wins);
				}
				winProb = (1.0 - totalLosses / total);
			}else { 
				// Count wins if our min wins is close to max wins
				int totalWins = 0;
				for (int wins = winsNeeded; wins < (Math.min(n, p) + 1); wins++) {
					// m - p is # of others in the competition
					// n - wins is the amount of wins we need starting from 0.
					totalWins += choose(p, wins) * choose(m-p, n - wins);
				}
				winProb = totalWins / total;
			}
			
			System.out.printf("%.10f\n", winProb);
		}
	}
	
	private static double choose(int n, int k) {
		if (k < 0 || k > n) return 0;	// Special cases for choose
		
		if (k > n-k) k = n-k;
		double result = 1;
		for (int i = 0; i < k; i++) {
			result *= (n - i);
			result = result / (i + 1);
		}
		
		return result;
	}
}

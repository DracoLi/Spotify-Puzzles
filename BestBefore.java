package com.draken.spotify;

import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * Parses best before dates into a standard date format
 * 
 * @author dracoli
 */
public class BestBefore {
	
	public static int[] datesInMonth = { 31, 28, 31, 30, 31, 
	                                     30, 31, 31, 30, 31, 
	                                     30, 31 };
	
	private static boolean isLeapYear(int year) {
		if (year % 4 == 0) {
			if (year % 100 == 0 && year % 400 != 0) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}
	
	private static boolean isValidDate(int[] dates) {
		// Check if day is valid in month
		int maxDay = datesInMonth[dates[1]-1];
		if (isLeapYear(dates[0]) && dates[1] == 2) maxDay = 29;
		if (dates[2] > maxDay) {
			// If day is invalid we switch with year if year is not determined
			return false;		
		}
		return true;
	}
	
	private static int[] getDate(int[] dateTokens, int yearIndex) {
		
		int dates[] = new int[3];
		
		// Fix shortened year
		dates[0] = dateTokens[yearIndex];
		if (dates[0] < 100) {
			dates[0] += 2000;
		}
				
		// Find the best month
		int month = Integer.MAX_VALUE;
		int monthIndex = -1;
		for (int i = 0; i < dateTokens.length; i++) {
			if (i == yearIndex) continue;	// Skip year
			int thisDate = dateTokens[i];
					
			// Find smallest valid month
			if (thisDate  <= 12 && thisDate < month) {
				month = thisDate;
				monthIndex = i;
			}
		}
				
		// Check if we found something
		if (month == Integer.MAX_VALUE || month == 0) return null;
		dates[1] = month;
		
		// Find the best day
		int day = Integer.MAX_VALUE;
		for (int i = 0; i < dateTokens.length; i++) {
			if (i == monthIndex || i == yearIndex) continue;
			int thisDate = dateTokens[i];
			if (thisDate <= 31 && thisDate < day) {
				day = thisDate;
			}
		}
		
		// Check if we found a day
		if (day == Integer.MAX_VALUE || day == 0) return null;
		dates[2] = day;
		
		return dates;
	}
	
	public static String getValidDate(String date) {
		
		int[] dateTokens = new int[3];
		int dateLength = 0;
		for (String s : date.split("/")) {
			
			// Too much arguments
			if (dateLength == 3) return null;
			
			// Parse date
			try {
				dateTokens[dateLength] = Integer.parseInt(s);
			} catch (NumberFormatException e) {
				return null;
			}
			
			dateLength++;
		}
		
		// Not enough arguments
		if (dateLength < 3) {
			return null;
		}
		
		boolean isYearDetermined = false;
		
		// Find a possible year or earliest year
		int year = Integer.MAX_VALUE;
		int yearIndex = -1;
		for (int i = 0; i < dateTokens.length; i++) {
			int thisDate = dateTokens[i];
			
			// Get the best match!
			if (thisDate > 31) {
				isYearDetermined = true;
				year = thisDate;
				yearIndex = i;
				break;
			}
			
			// Get ealiest date! (smallest number)
			if (thisDate < year) {
				year= thisDate;
				yearIndex = i;
			}
		}
		
		// Check errors - year not found or year.length == 3
		if (year == Integer.MAX_VALUE ||
				String.valueOf(year).length() == 3) {
			return null;
		}
		
		int[] dates = getDate(dateTokens, yearIndex);
		
		// Now check if our values our correct
		if (dates == null || !isValidDate(dates)) {
			if (isYearDetermined) return null;
			
			// Since smallest year dont work, get next best year Index
			int nextYearIndex = getHigher(dateTokens, yearIndex);
			if (nextYearIndex == -1) return null; 
			dates = getDate(dateTokens, nextYearIndex);
			if (dates == null || !isValidDate(dates) ) {
				
				// Since second smallest year don't work, get next best year (last)
				nextYearIndex = getHigher(dateTokens, nextYearIndex);
				if (nextYearIndex == -1) return null; 
				dates = getDate(dateTokens, nextYearIndex);
				
				// Check if we got a date and its valid
				if (dates == null || !isValidDate(dates)) return null;
			}
		}
		
		
		// Build our valid string
		return buildDateString(dates);
	}
	
	private static int indexOf(int[] dates, int value) {
		for (int i = 0; i < dates.length; i++) {
			if (dates[i] == value) return i;
		}
		return -1;
	}
	
	private static String buildDateString(int[] dates) {
		StringBuilder sb = new StringBuilder();
		sb.append(dates[0]);
		sb.append("-");
		if (dates[1] < 10) {
			sb.append("0" + dates[1]);
		} else {
			sb.append(dates[1]);
		}
		sb.append("-");
		if (dates[2] < 10) {
			sb.append("0" + dates[2]);
		} else {
			sb.append(dates[2]);
		}
		return sb.toString();
	}
	
	private static int getHigher(int[] dates, int baseIndex) {
		int baseVal = dates[baseIndex];
		int resultIndex = -1;
		for (int i = 0; i < dates.length; i++) {
			if (dates[i] > baseVal) {
				// Only update our next higher index if first time or when new val is smaller
				if (resultIndex == -1 || dates[resultIndex] > dates[i]) {
					resultIndex = i;
				}
			}
		}
		return resultIndex;
	}
	public static void main(String[] args) {
		String charsetName = "UTF-8";
		Scanner scanner = new Scanner(new BufferedInputStream(System.in), charsetName);
		
		while (scanner.hasNextLine()) {
			String input = scanner.nextLine();
			String validInput = getValidDate(input);
			
			if (validInput == null) {
				System.out.println(input + " is illegal");
			}else {
				System.out.println(validInput);
			}
		}
	}
}

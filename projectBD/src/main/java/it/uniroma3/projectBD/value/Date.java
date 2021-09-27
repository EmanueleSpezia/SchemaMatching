package it.uniroma3.projectBD.value;

import java.io.Serializable;

/**
 * 
 * A date with day/month/year attributes
 *
 */
public class Date implements Comparable<Date>, Serializable {

	static final private long serialVersionUID = -8002444610039998674L;

	private int day;
	private int month;
	private int year;

	/* MGC */
	public Date(int day, int month, int year) {
		this.day = day;
		this.month = month;
		this.year = year;
	}

	public Date(int month, int year) {
		this(-1,month,year);
	}
	
	public Date(int year) {
		this(-1,-1,year);
	}

	public int getDay() {
		return day;
	}
	
	public int getMonth() {
		return month;
	}

	public int getYear() {
		return year;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj==null || !(obj instanceof Date)) {
			return false;
		}
		
		final Date that = (Date)obj;
		return this.day==that.day && this.month==that.month && this.year==that.year;
	}

	@Override
	public int compareTo(Date that) {
		int cmp = this.getYear()-that.getYear();
		if (cmp==0)
			cmp = this.getMonth()-that.getMonth();
		if (cmp==0)
			cmp = this.getDay()-that.getDay();
		return cmp;
	}
	
	public long daysFromYear(int fromYear) {
		long result = 0;
		result += this.getYear()-fromYear * 365;
		if (this.getMonth()!=-1)
			result += this.getMonth() * 30;
		else
			result += 6 * 30;
		if (this.getDay()!=-1)
			result += this.getDay();
		else
			result += 15;
		return result;
	}
	
	@Override
	public String toString() {
		String result =  "";
		result +=  (this.day!=-1)?String.valueOf(this.day):"";
		result +=   (this.month!=-1) ? String.valueOf(this.month):"";
		result +=   (this.year!=-1) ? String.valueOf(this.year):"";
		return result;
	}

}

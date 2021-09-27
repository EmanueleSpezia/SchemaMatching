package it.uniroma3.projectBD.value;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.abs;

public class DateDistance {

	public static double normalizedDistance(Date date1,Date date2) {
		// 
		
		Calendar cal = Calendar.getInstance();
		cal.set(date1.getYear(), date1.getMonth(), date1.getDay(), 0, 0, 0);
		cal.set(Calendar.MILLISECOND,0);
		
		Calendar cal2 = Calendar.getInstance();
		cal2.set(date2.getYear(), date2.getMonth(), date2.getDay(), 0, 0, 0);
		cal2.set(Calendar.MILLISECOND,0);
		if(cal2.getTimeInMillis() > cal.getTimeInMillis()) {
			cal.setTime(cal2.getTime());
			cal2.set(date1.getYear(), date1.getMonth(), date1.getDay(), 0, 0, 0); 
		} 
		

		Calendar calRef = Calendar.getInstance();
		calRef.set(cal.get(Calendar.YEAR), date1.getMonth(), date1.getDay(), 0, 0, 0);
		calRef.add(Calendar.YEAR, -1);
		final long d1 = 365; 
		

	    long diffInMillies = Math.abs(cal2.getTime().getTime() - cal.getTime().getTime());
	    long value = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
	    if(value> d1)
	    	return 0;
		 
	    double result = normalize(value, 0, d1);
	    
	    if(result >1 )
	    	result = 1;
	    
		 return result; 
	}
	  
	 static double normalize(double value, double min, double max) {
	     return 1 - ((value - min) / (max - min));
	 }
	 
	 static final private double canberraDistance(double d1, double d2) {
			if (d1==0d && d2==0d) return 0d;
			if (d1==0d || d2==0d) return 1d;
			return abs(d2-d1) / ( abs(d2)+abs(d1) ); // 0<d<1
		}
}

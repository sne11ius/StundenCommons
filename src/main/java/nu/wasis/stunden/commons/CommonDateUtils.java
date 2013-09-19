package nu.wasis.stunden.commons;

import org.joda.time.DateTime;

public class CommonDateUtils {

	private CommonDateUtils() {
		// static only
	}
	
	public static boolean isSameDay(final DateTime day, final DateTime otherDay) {
		return day.getYear() == otherDay.getYear() &&
			   day.getMonthOfYear() == otherDay.getMonthOfYear() &&
			   day.getDayOfMonth() == otherDay.getDayOfMonth();
	}
	
}

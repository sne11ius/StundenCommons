package nu.wasis.stunden.commons;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;

public class DayStepValidator {

	private DateTime currentDate;

	public DayStepValidator(final DateTime begin) {
		this.currentDate = begin;
	}

	public DayStepResult step(final DateTime nextDay) {
		if (CommonDateUtils.isSameDay(currentDate, nextDay)) {
			// This is ok in our small little world...
			return new DayStepResult(true, "");
		}
		switch (currentDate.getDayOfWeek()) {
			// Fall throughs are intentional ;)
			case DateTimeConstants.MONDAY    :
			case DateTimeConstants.TUESDAY   :
			case DateTimeConstants.WEDNESDAY :
			case DateTimeConstants.THURSDAY  : {
				return checkSingleStep(nextDay, 1);
			}
			case DateTimeConstants.FRIDAY : {
				DayStepResult singleStep = null;
				for (int i = 1; i <= 3; ++i) {
					singleStep = checkSingleStep(nextDay, i);
					if (singleStep.isSuccess()) {
						return singleStep;
					}
				}
				return singleStep;
			}
			default: {
				// This should not happen
				throw new IllegalArgumentException("Uncovered weekday. Maybe they added an 8th one D:");
			}
		}
	}

	private DayStepResult checkSingleStep(final DateTime nextDay, final int stepSize) {
		final DateTime realNextDay = currentDate.plusDays(stepSize);
		final boolean isSameDay = CommonDateUtils.isSameDay(nextDay, realNextDay);
		if (!isSameDay) {
			return new DayStepResult(false, nextDay + " (" + nextDay.dayOfWeek().getAsText() + ") is not a valid successor of " + currentDate + " (" + currentDate.dayOfWeek().getAsText() + ").");
		} else {
			this.currentDate = nextDay;
			return new DayStepResult(true, "");
		}
	}
	
	public DateTime getCurrentDate() {
		return currentDate;
	}

	public static final class DayStepResult {
		private final boolean success;
		private final String message;
		
		public DayStepResult(final boolean success, final String message) {
			this.success = success;
			this.message = message;
		}

		public boolean isSuccess() {
			return success;
		}

		public String getMessage() {
			return message;
		}
	}
}

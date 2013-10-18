package com.blicksky.radiothermostat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Preconditions;

public class DayProgram {
	private List<Change> changes = new ArrayList<Change>(4);
	
	public DayProgram( final Change firstChange, final Change secondChange, final Change thirdChange, final Change fourthChange ) {
		this.changes.add(firstChange);
		this.changes.add(secondChange);
		this.changes.add(thirdChange);
		this.changes.add(fourthChange);
		
		Collections.sort( this.changes );
	}
	
	public class Change implements Comparable<Change> {
		private int hour;
		private int minute;
		private int temperature;
		
		public Change( final int hour, final int minute, final int temperature ) {
			Preconditions.checkArgument(hour >= 0 && hour <= 23, "Hour must be between 0 and 23");
			Preconditions.checkArgument(minute >= 0 && minute <= 60, "Minute must be between 0 and 60");
			Preconditions.checkArgument(temperature >= 60 && temperature <= 90, "Temperature must be between 60 and 90");
			
			this.hour = hour;
			this.minute = minute;
			this.temperature = temperature;
		}
		
		public int getHour() {
			return hour;
		}
		
		public int getMinute() {
			return minute;
		}
		
		public int getTemperature() {
			return temperature;
		}

		@Override
		public int compareTo(Change otherChange) {
			return ( (otherChange.hour * 60) + otherChange.minute ) - ( (this.hour * 60) + this.minute );
		}
	}
}
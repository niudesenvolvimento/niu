package br.edu.uniplac.niu.model.util;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class DateUtilTest {

	private int calendarMonthIndex = 0;
	private int calendarYearIndex  = 2016;

	
	@Test
	public void testGetFirstDayOfMonth() {
		Date firstDayOfMonth = DateUtil.getFirstDayOfMonth(calendarMonthIndex, calendarYearIndex);
		
		Calendar calendarAux = Calendar.getInstance();
		calendarAux.setTime( firstDayOfMonth );
		
		assertTrue( calendarAux.get(Calendar.DAY_OF_MONTH) == 1 );
		assertTrue( calendarAux.get(Calendar.MONTH) == 0 );
		assertTrue( calendarAux.get(Calendar.YEAR) == 2016 );
	}

	@Test
	public void testGetLastDayOfMonth() {
		Date lastDayOfMonth = DateUtil.getLastDayOfMonth(calendarMonthIndex, calendarYearIndex);
		
		Calendar calendarAux = Calendar.getInstance();
		calendarAux.setTime( lastDayOfMonth );
		
		assertTrue( calendarAux.get(Calendar.DAY_OF_MONTH) == 31 );
		assertTrue( calendarAux.get(Calendar.MONTH) == 0 );
		assertTrue( calendarAux.get(Calendar.YEAR) == 2016 );
	}

}

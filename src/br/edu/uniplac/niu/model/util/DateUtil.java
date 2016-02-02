package br.edu.uniplac.niu.model.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * Utilitarios para se trabalhar com datas
 * @author Vitor
 * @since 01 FEV 2015
 */
public class DateUtil {
	
	private static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";
	
	
	/**
	 * Retorno o ano atual em inteiro
	 * @return
	 */
	public static Integer getCurrentYear() {
		return Calendar.getInstance().get(Calendar.YEAR);
	}
	
	
	/**
	 * Calcula a idade a partir da data de nascimento
	 * @param birthday
	 * @return idade
	 */
	public static Integer calculateAge(Date birthDate) {
		throwIfNull(birthDate);
		
		Calendar birthCalendar = Calendar.getInstance();
		birthCalendar.setTime( birthDate );
		
		Calendar today = Calendar.getInstance();
		
		int age = today.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR);
		if (today.get(Calendar.DAY_OF_YEAR) < birthCalendar.get(Calendar.DAY_OF_YEAR)) {
			age--;
		}
		return age;
	}
	
	
	/**
	 * Validate se os parametros individuais para ano, mes e dia 
	 * formam uma data valida 
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static boolean isAValidDate(Integer year, Integer month, Integer day) {
		if (hasNull(year, month, day)) return false;

		try {
			GregorianCalendar calendarAux = new GregorianCalendar(year, month-1, day);
			if (day == calendarAux.get(Calendar.DAY_OF_MONTH)) {
				return true;
			} else {
				return false;
			}
			
		} catch(Exception e) {
			return false;
		}
	}
	
	
	/**
	 * A partir dos parametros individuais de ano, mes e dia
	 * construe um Date valido
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	public static Date buildDate(Integer year, Integer month, Integer day) {
		if (hasNull(year, month, day)) return null;
		
		if (isAValidDate(year, month, day)) {
			return new GregorianCalendar(year, month-1, day).getTime();
		} else {
			return null; 
		}
	}
	
	
	
	/**
	 * Verifica se algum parametro eh null.
	 * (para validacoes short-circuit)
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	private static boolean hasNull(Integer year, Integer month, Integer day) {
		if (year==null || month==null || day==null) {
			return true;
		} else {
			return false;
		}
	}
	
	
	/**
	 * Lanca exception se parametro vier null
	 * (para validacoes short-circuit)
	 * @param param
	 */
	private static void throwIfNull(Object param) {
		if (param==null) {
			throw new IllegalArgumentException("Parametro null passado para metodo em CalendarUtil");
		}
	}

	
	
	/**
	 * Retorna o primeiro dia do mes com hora, minutos, segundo e milisendo tratados
	 * @param calendarMonthIndex
	 * @param calendarYear
	 * @return
	 */
	public static Date getFirstDayOfMonth(int calendarMonthIndex, int calendarYear) {
		GregorianCalendar primeiroDiaDoMes = new GregorianCalendar(calendarYear, calendarMonthIndex, 1);
		return toStartOfDay( primeiroDiaDoMes.getTime() );
	}

	
	public static Date getFirstDateOfCurrentMonth() {
		int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		
		return getFirstDayOfMonth(currentMonth, currentYear);
	}

	
	/**
	 * Retorna o ultimo dia do mes tambem com os dados de tempo tratados.
	 * Faz uma jogada para obter o ultimo dia do mes (explicada no proprio codigo)
	 * @param calendarMonthIndex
	 * @param calendarYear
	 * @return
	 */
	public static Date getLastDayOfMonth(int calendarMonthIndex, int calendarYear) {
		// inicialmente cria o primeiro Dia Do Mes Seguinte...
		int calendarNextMonthIndex = ++calendarMonthIndex;
		GregorianCalendar firstDayOfNextMonth = new GregorianCalendar(calendarYear, calendarNextMonthIndex, 1);
		// entao subtrai um dia
		firstDayOfNextMonth.add(Calendar.DAY_OF_MONTH, -1);
		Calendar lastDayOfMonth = firstDayOfNextMonth;
		
		return toEndOfDay( lastDayOfMonth.getTime() );
	}
	
	
	public static Date getLastDayOfCurrentMonth() {
		int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		
		return getLastDayOfMonth(currentMonth, currentYear);
	}
	
	
	
	
	
	/**
	 * Retorna uma data com hora, minuto, segundo e milisegundo zerados.
	 * @param date
	 * @return
	 */
	public static Date toStartOfDay(Date date) {
		Calendar dCal = Calendar.getInstance();
		dCal.setTime(date);
		dCal.set(Calendar.HOUR_OF_DAY, 0);
		dCal.set(Calendar.MINUTE, 0);
		dCal.set(Calendar.SECOND, 0);
		dCal.set(Calendar.MILLISECOND, 0);

		return dCal.getTime();
	}

	/**
	 * Retorna uma data com hora, minuto, segundo e milisegundos com o final do dia.
	 * @param date
	 * @return
	 */
	public static Date toEndOfDay(Date date) {
		Calendar dCal = Calendar.getInstance();
		dCal.setTime(date);
		dCal.set(Calendar.HOUR_OF_DAY, 23);
		dCal.set(Calendar.MINUTE, 59);
		dCal.set(Calendar.SECOND, 59);
		dCal.set(Calendar.MILLISECOND, 999);
		return dCal.getTime();
	}
	
}

package br.edu.uniplac.niu.controller.helper;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Objeto que representa uma op��o para combos de Mes e Ano
 * @author Vitor
 * @since 17 JUN 2015
 */
public class ComboMesAno {
	
	private final int idxMes;//do Calendar
	
	private final int idxAno;
	
	private static final String[] ARRAY_CALENDAR_MES = new String[12];

	/**
	 * Usa os indices do Calendar
	 */
	static {
		ARRAY_CALENDAR_MES[0] ="Janeiro";
		ARRAY_CALENDAR_MES[1] ="Fevereiro";
		ARRAY_CALENDAR_MES[2] ="Março";
		ARRAY_CALENDAR_MES[3] ="Abril";
		ARRAY_CALENDAR_MES[4] ="Maio";
		ARRAY_CALENDAR_MES[5] ="Junho";
		ARRAY_CALENDAR_MES[6] ="Julho";
		ARRAY_CALENDAR_MES[7] ="Agosto";
		ARRAY_CALENDAR_MES[8] ="Setembro";
		ARRAY_CALENDAR_MES[9] ="Outubro";
		ARRAY_CALENDAR_MES[10]="Novembro";
		ARRAY_CALENDAR_MES[11]="Dezembro";
	}
	
	
	

	public ComboMesAno(int idxMes, int idxAno) {
		super();
		this.idxMes = idxMes;
		this.idxAno = idxAno;
	}
	
	
	public ComboMesAno(Calendar cal) {
		this.idxMes = cal.get(Calendar.MONTH);
		this.idxAno = cal.get(Calendar.YEAR);
	}

	
	
	//acessores...
	public int getIdxMes() {
		return idxMes;
	}
	public int getIdxAno() {
		return idxAno;
	}

	@Override
	public String toString() {
		return "ComboMesAno [idxMes=" + idxMes + ", idxAno=" + idxAno + "]";
	}


	public String getDescricao() {
		return String.format("%s / %s", ARRAY_CALENDAR_MES[idxMes], idxAno);
	}
	
	
	
	
	
	/**
	 * Codifica mes e ano, colocando uma virgula entre eles.
	 * Assim ser� posivel decodificar
	 * @return
	 */
	public String getCodigo() {
		return String.format("%s,%s", idxMes, idxAno);
	}
	
	
	/**
	 * Decodifica o mes extraindo a primeira posicao 
	 * @param codigo
	 * @return
	 */
	public static int decodificarMes(String codigo) {
		String mesStr = codigo.split(",")[0];
		return Integer.parseInt(mesStr);
	}

	
	/**
	 * Decodifica o ano extraindo a segundo posicao
	 * @param codigo
	 * @return
	 */
	public static int decodificarAno(String codigo) {
		String anoStr = codigo.split(",")[1];
		return Integer.parseInt(anoStr);
	}
	
	
	/**
	 * Decodifica como primeiro dia do mes
	 * @param codigo
	 * @return
	 */
	public static Date decodificarComoPrimeiroDiaDoMes(String codigo) {
		int ano = decodificarAno(codigo);
		int mes = decodificarMes(codigo);
		GregorianCalendar calendar = new GregorianCalendar(ano, mes, 1);
		return calendar.getTime();
	}
	
	
	/**
	 * Decodifica como ultimo dia do mes
	 * @param codigo
	 * @return
	 */
	public static Date decodificarComoUltimoDiaDoMes(String codigo) {
		int ano = decodificarAno(codigo);
		int mes = decodificarMes(codigo);
		
		//cria o primeiro dia do mes seguinte e subtrai um dia
		int proximoMes = ++mes;
		GregorianCalendar calendar = new GregorianCalendar(ano, proximoMes, 1);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		
		return calendar.getTime();
	}
	
	
}


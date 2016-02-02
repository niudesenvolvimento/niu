package br.edu.uniplac.niu.controller.helper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.edu.uniplac.niu.model.util.DateUtil;

/**
 * Helper para preencher os valores de mes e ano a partir de constante
 * @author Vitor
 * @since 18 JUN 2015
 */
@ManagedBean(name="comboMesAnoHelper")
@ViewScoped
public class ComboMesAnoHelper implements Serializable {
	
	private static final int ANO_INICIAL = 2016;
	private static final int MES_INICIAL = Calendar.JANUARY;
	
	
	
	private List<ComboMesAno> listaComboMesAno;
	
	
	public ComboMesAnoHelper() {
		listaComboMesAno = new ArrayList<>();
		
		Calendar calendarVar = Calendar.getInstance();
		calendarVar.setTime( DateUtil.getFirstDayOfMonth( MES_INICIAL, ANO_INICIAL) );
		
		Calendar calendarHoje = Calendar.getInstance();
		
		while ( calendarVar.before(calendarHoje)) {
			ComboMesAno combo = new ComboMesAno( calendarVar );
			listaComboMesAno.add( combo );
			
			//incrementa em um mes
			calendarVar.add(Calendar.MONTH, 1);
		}
	}
	
	

	
	//acessores...
	private static final long serialVersionUID = 3179232163763718931L;
	
	public List<ComboMesAno> getListaComboMesAno() {
		return listaComboMesAno;
	}
	

	
	//utils
	
	
	
	

}

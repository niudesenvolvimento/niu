package br.edu.uniplac.niu.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.chart.PieChartModel;

import br.edu.uniplac.niu.controller.helper.ComboMesAno;
import br.edu.uniplac.niu.controller.util.JSFUtil;
import br.edu.uniplac.niu.model.entity.Chamado;
import br.edu.uniplac.niu.model.entity.enumeration.ChamadoStatus;
import br.edu.uniplac.niu.model.service.ChamadoService;
import br.edu.uniplac.niu.model.service.UsuarioService;
import br.edu.uniplac.niu.model.util.DateUtil;

/**
 * Controller para relatorios de chamados
 * @author vitor.figueiredo
 * @since 06 jan 2016
 */
@ManagedBean(name="chaRelController")
@ViewScoped
public class ChamadoRelatorioController implements Serializable {

	@EJB ChamadoService chamadoService;
	
	@EJB UsuarioService usuarioService;
	
	
	private List<Chamado> chamados;
	
	//filtros
	private Date filtroDataInicial;
	private Date filtroDataFinal;
	private String filtroCodigoMesAno;
	private String filtroCriadoPor;
	private List<ChamadoStatus> filtrosStatus;


	//graficos...
	private PieChartModel chartModelCriadoPor;
	private PieChartModel chartModelStatus;
	private PieChartModel chartModelCategoria;
	private PieChartModel chartModelPrioridade;

	
	
	
	@PostConstruct void init() {
		populateFiltrosDatasCriadoPor();
	}
	

	
	
	private void populateFiltrosDatasCriadoPor() {
		this.filtroDataInicial = DateUtil.getFirstDateOfCurrentMonth();
		this.filtroDataFinal = DateUtil.getLastDayOfCurrentMonth();
	}




	//listener...
	public void onChangeComboMesAno() {
		if (filtroCodigoMesAno!=null && !filtroCodigoMesAno.trim().isEmpty()) {
			this.filtroDataInicial = ComboMesAno.decodificarComoPrimeiroDiaDoMes( filtroCodigoMesAno );
			this.filtroDataFinal   = ComboMesAno.decodificarComoUltimoDiaDoMes( filtroCodigoMesAno );
		}
	}
	
	public void onSelectDataInicial() {
		this.filtroDataFinal = filtroDataInicial;
	}
	

	
	//actions
	public void pesquisar() {
		chamados = chamadoService.pesquisarChamadoPelosFiltros(filtroCriadoPor, filtrosStatus, filtroDataInicial, filtroDataFinal);
		construirGraficos();
		JSFUtil.addMessageAboutResult(chamados);
	}
	
	
	
	private void construirGraficos() {
		Map<String, Number> mapaCriador = new TreeMap<>();
		Map<String, Number> mapaStatus = new TreeMap<>();
		Map<String, Number> mapaCategoria = new TreeMap<>();
		Map<String, Number> mapaPrioridade = new TreeMap<>();
		
		Number total = null;
		for (Chamado chamadoVar : chamados) {
			//criador
			String nomeCriador = extrairCriadoPor( chamadoVar );
			total = mapaCriador.get(nomeCriador);
			mapaCriador.put(nomeCriador, increaseTotal(total) );
			
			//status
			String descStatus = extrairStatus(chamadoVar);
			total = mapaStatus.get(descStatus);
			mapaStatus.put(descStatus, increaseTotal(total));
			
			//categoria
			String nomeCategoria = extrairNomeCategoria(chamadoVar);
			total = mapaCategoria.get(nomeCategoria);
			mapaCategoria.put(nomeCategoria, increaseTotal(total));
			
			//prioridade
			String descPrioridade = extrairDescPrioridade(chamadoVar);
			total = mapaPrioridade.get(descPrioridade);
			mapaPrioridade.put(descPrioridade, increaseTotal(total));
		}

		chartModelCriadoPor = construirPieCharModelDefault(mapaCriador);
		chartModelStatus = construirPieCharModelDefault(mapaStatus);
		chartModelCategoria = construirPieCharModelDefault(mapaCategoria);
		chartModelPrioridade = construirPieCharModelDefault(mapaPrioridade);
	}


	
	private PieChartModel construirPieCharModelDefault(Map<String, Number> mapa) {
		PieChartModel pieChartModel = new PieChartModel(mapa);
		pieChartModel.setLegendPosition("e");
		pieChartModel.setShowDataLabels(true);
		pieChartModel.setSliceMargin(3);
		pieChartModel.setShadow(false);
		pieChartModel.setDiameter(200);
		return pieChartModel;
	}

	private String extrairDescPrioridade(Chamado chamadoVar) {
		return chamadoVar.getPrioridade().getDescricao();
	}
	
	private String extrairNomeCategoria(Chamado chamadoVar) {
		return chamadoVar.getCategoria().getNome();
	}
	
	private String extrairStatus(Chamado chamadoVar) {
		return chamadoVar.getStatus().getDescricao();
	}

	private String extrairCriadoPor(Chamado chamadoVar) {
		return chamadoVar.getCriadoPor();
	}

	private Number increaseTotal(Number total) {
		if (total==null) {
			return 1;
		} else {
			return total.longValue() + 1;
		}
	}





	//acessores...
	private static final long serialVersionUID = -4390822836516237530L;
	public List<Chamado> getChamados() {
		return chamados;
	}
	public Date getFiltroDataInicial() {
		return filtroDataInicial;
	}
	public void setFiltroDataInicial(Date filtroDataInicial) {
		this.filtroDataInicial = filtroDataInicial;
	}
	public Date getFiltroDataFinal() {
		return filtroDataFinal;
	}
	public void setFiltroDataFinal(Date filtroDataFinal) {
		this.filtroDataFinal = filtroDataFinal;
	}
	public String getFiltroCodigoMesAno() {
		return filtroCodigoMesAno;
	}
	public void setFiltroCodigoMesAno(String filtroCodigoMesAno) {
		this.filtroCodigoMesAno = filtroCodigoMesAno;
	}
	public String getFiltroCriadoPor() {
		return filtroCriadoPor;
	}
	public void setFiltroCriadoPor(String filtroCriadoPor) {
		this.filtroCriadoPor = filtroCriadoPor;
	}
	public List<ChamadoStatus> getFiltrosStatus() {
		return filtrosStatus;
	}
	public void setFiltrosStatus(List<ChamadoStatus> filtrosStatus) {
		this.filtrosStatus = filtrosStatus;
	}
	public PieChartModel getChartModelCriadoPor() {
		return chartModelCriadoPor;
	}
	public PieChartModel getChartModelStatus() {
		return chartModelStatus;
	}
	public PieChartModel getChartModelCategoria() {
		return chartModelCategoria;
	}
	public PieChartModel getChartModelPrioridade() {
		return chartModelPrioridade;
	}
	

}

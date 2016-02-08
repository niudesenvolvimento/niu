package br.edu.uniplac.niu.controller.converter;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import br.edu.uniplac.niu.model.entity.ChamadoCategoria;
import br.edu.uniplac.niu.model.service.ChamadoService;

@ManagedBean(name="chamadoCategoriaConverter")
public class ChamadoCategoriaConverter implements Converter {

	@EJB ChamadoService service;
	
	@Override
	public Object getAsObject(FacesContext ctx, UIComponent comp, String txt) {
		if (txt==null && !txt.trim().isEmpty()) {
			return null;
		}
		Integer id = Integer.parseInt( txt );
		ChamadoCategoria categoria = service.buscarChamadoCategoriaPeloId(id);
		return categoria;
	}

	@Override
	public String getAsString(FacesContext ctx, UIComponent comp, Object obj) {
		if (obj==null) {
			return null;
		}
		ChamadoCategoria categoria = (ChamadoCategoria) obj;
		return String.valueOf( categoria.getId() );
	}

}

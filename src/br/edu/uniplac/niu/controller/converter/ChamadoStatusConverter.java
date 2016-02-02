package br.edu.uniplac.niu.controller.converter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import br.edu.uniplac.niu.model.entity.enumeration.ChamadoStatus;

/**
 * Converter para o enum ChamadoStatus
 * @author vitor.figueiredo
 * @since 06 jan 2016
 */
@ManagedBean(name="chamadoStatusConverter")
@ViewScoped
public class ChamadoStatusConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext ctx, UIComponent comp, String txt) {
		if (txt==null || txt.trim().isEmpty()) {
			return null;
		}
		ChamadoStatus status = ChamadoStatus.valueOf(txt);
		return status;
	}

	@Override
	public String getAsString(FacesContext ctx, UIComponent comp, Object obj) {
		if (obj==null) {
			return null;
		}
		ChamadoStatus status = (ChamadoStatus) obj;
		return status.name();
	}

}

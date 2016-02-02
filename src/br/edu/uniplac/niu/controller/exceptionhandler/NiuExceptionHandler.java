package br.edu.uniplac.niu.controller.exceptionhandler;

import javax.faces.FacesException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import br.edu.uniplac.niu.controller.util.JSFUtil;
import br.edu.uniplac.niu.model.exception.NegocioException;

/**
 * Manipulador de Exception do JSF2. 
 * @author Solkam
 * @since 02 FEV 2015
 */
public class NiuExceptionHandler extends ExceptionHandlerWrapper {
		
		private ExceptionHandler wrapped;
		
		public NiuExceptionHandler(ExceptionHandler wrapped) {
			this.wrapped = wrapped;
		}
		
		@Override
		public ExceptionHandler getWrapped() {
			return this.wrapped;
		}
		

		/**
		 * Metodo que efetivamente manipula a exception.
		 */
		@Override
		public void handle() throws FacesException {
			//iterage para pegar todas as exceptions geradas
			for (ExceptionQueuedEvent event : getUnhandledExceptionQueuedEvents() ) {
				
				ExceptionQueuedEventContext context = event.getContext();
			
				Throwable throwable = context.getException();   // tipo FacesException
				
				Throwable essenciaException = getAlternativeRootCause(throwable);
				
				if (essenciaException instanceof NegocioException) {
					JSFUtil.addErrorMessage( essenciaException );
				
				} else {
					JSFUtil.addFatalMessage("Erro inesperado", essenciaException);
				}
					

			}
		}

		/**
		 * Simulando chamadas recursivas, entra nas exception internas at�
		 * encontrar ou null ou a mesma externa.
		 * @param t
		 * @return
		 */
		private Throwable getAlternativeRootCause(Throwable t) {
			Throwable result = t;
			Throwable previousResult = null;

			while (result.getCause() != null && !result.equals(previousResult)) {
				previousResult = result;
				result = result.getCause();
			}
			return result;
		}


		
	}

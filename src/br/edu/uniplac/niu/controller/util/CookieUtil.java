package br.edu.uniplac.niu.controller.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Utilitário para Cookies
 * @author vitor
 * @since 05 jan 2016
 */
public class CookieUtil {
	
	private static final String COOKIE_CRIADO_POR = "CRIADO_POR";
	
	
	/**
	 * Recupera o cookie 'criadoPor'
	 * @return
	 */
	public static String getCookieCriadoPor() {
		HttpServletRequest request = JSFUtil.getHttpServletRequest();
		
		Cookie[] cookies = request.getCookies();
		if (cookies!=null) {
			for (Cookie cookie : cookies) {
				if (COOKIE_CRIADO_POR.equals( cookie.getName() )) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
	
	
	/**
	 * Define um novo cookie 'criadoPor'
	 * @param criadoPor
	 */
	public static void setCookieCriadoPor(String criadoPor) {
		HttpServletResponse response = JSFUtil.getHttpServletResponse();
		Cookie cookie = new Cookie(COOKIE_CRIADO_POR, criadoPor);
		response.addCookie(cookie );
	}

}

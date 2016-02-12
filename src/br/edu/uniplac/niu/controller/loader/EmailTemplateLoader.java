package br.edu.uniplac.niu.controller.loader;

import java.io.DataInputStream;
import java.io.InputStream;

/**
 * Carregador dos templates de email
 * @author vitor.figueiredo
 *
 */
public class EmailTemplateLoader {
	private static final String EMAIL_TEMPLATES_FOLDER = "/resources/";
	private static final String EMAIL_TEMPLATE_EXTENSION = "html";

	private final Class<?> clazz;
	

	
	public EmailTemplateLoader(Class<?> clazz) {
		super();
		this.clazz = clazz;
	}



	
	public String loadAsString(String basename) {
		String completePath = buildCompletePath( basename );
		return loadFileAsString( completePath, clazz );
	}




	private String buildCompletePath(String filename) {
		String completeFilePath = String.format("%s%s.%s", EMAIL_TEMPLATES_FOLDER, filename, EMAIL_TEMPLATE_EXTENSION);
		return completeFilePath;
	}
	
	
	private String loadFileAsString(String filePath, Class<?> clazz) {
		try (
			InputStream inputStream = clazz.getResourceAsStream(filePath); 
			DataInputStream dis = new DataInputStream(inputStream);	
			) {
			
			byte[] dataInBytes = new byte[dis.available()];
			dis.readFully( dataInBytes );
			String content = new String(dataInBytes, 0, dataInBytes.length);
			return content;
			
		} catch (Exception e) {
			throw new RuntimeException( e );
		}
	}
	

}

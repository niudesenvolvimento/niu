package br.edu.uniplac.niu.controller.security;

import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import br.edu.uniplac.niu.model.exception.InfraException;

/**
 * Realiza autenticação usando o ActiveDirectory
 * @author vitor.figueiredo
 * @since 07 MAR 2016
 */
public class ActiveDirectoryAutenticator {
	
	private static final String DOMAIN_URL = "ldap://uni-srv-dc01:389";
	private static final String DOMAIN_NAME = "UNIPLAC";
	
	
	public boolean isAutenticate(String username, String pass) {
        
		String domainUsername = String.format("%s\\%s", DOMAIN_NAME, username);
        
        Hashtable<String, String> env = new Hashtable<>();  
        env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, DOMAIN_URL);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, domainUsername);
        env.put(Context.SECURITY_CREDENTIALS, pass);  

        DirContext dirContext = null;
        try  {  
           dirContext = new InitialDirContext(env);
           return true;
           
        } catch (AuthenticationException e) {  
	        return false;  
	        
        } catch (NamingException e) {  
        	throw new InfraException("Autenticação via AD falhou", e);  
        
        } finally {
        	closeContext(dirContext);
        }
	}
	

	private void closeContext(DirContext dirContext) {
		try {
			if (dirContext!=null) {
				dirContext.close();
			}
		} catch (NamingException e) {
			//nao precisa fazer nada
		}
	}

}

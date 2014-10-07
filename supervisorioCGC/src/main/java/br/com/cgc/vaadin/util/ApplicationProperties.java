package br.com.cgc.vaadin.util;

import java.net.URL;
import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

/**
 * Recupera e disponibiliza informações do <code>MANIFEST.MF</code> para o aplicativo.
 * 
 * @author CGC Automação
 */
public class ApplicationProperties {
	
	private Manifest manifest;
	
	private static ApplicationProperties instance;
	
	private ApplicationProperties(Manifest manifest){
		this.manifest = manifest;
	}
	
	static {
		try {
			Enumeration<URL> resources = ApplicationProperties.class.getClassLoader().getResources("META-INF/MANIFEST.MF");
			while (resources.hasMoreElements()) {
		    	Manifest manifest = new Manifest(resources.nextElement().openStream());
		    	if ("CGC Automação".equalsIgnoreCase(manifest.getMainAttributes().getValue("Built-By"))) { 
		    		instance = new ApplicationProperties(manifest);
		    		break;
		    	}
			}
	    } catch (Exception e) {
	    	System.err.println("Não foi possível ler o manifest do projeto: "+e.getMessage());
	    }
	}
	
	public static String getTitulo(){
		return checkProperty("Implementation-Title");
	}
	
	public static String getVersao(){
		return checkProperty("Implementation-Version");
	}
	
	public static String getBuild(){
		return checkProperty("Implementation-Build");
	}
	
	public static String getDesenvolvidoPor(){
		return checkProperty("Built-By");
	}
	
	public static String getSite(){
		return checkProperty("Implementation-Site");
	}
	
	private static String checkProperty(String p) {
		if (instance == null)
			return "";
		
		return instance.getManifestProperty(p);
	}
	
	private String getManifestProperty(String property) {
		Attributes attributes = manifest.getMainAttributes();
		return attributes.getValue(property);
	}
	
	public static URL getURLLogo() { 
        URL imageURL = ApplicationProperties.class.getClassLoader().getResource("logo_cgc.png");  
        return imageURL;  
    }
}

package com.hsarman.radonrad.serverchecker.webservice;

import java.util.List;
import java.util.concurrent.Executors;

import javax.xml.transform.Source;
import javax.xml.ws.Endpoint;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import com.hsarman.radonrad.serverchecker.utils.Statics;

public class WebServiceServer {
private static WebServiceServer instance;
private WebServiceServer() {
	instance=this;
	
}
public static WebServiceServer get_instance() {
	if(instance==null) {
		new WebServiceServer();
	}
	return instance;
}


public void StartServer() {
	
	String bindingURI = "http://0.0.0.0:"+Statics.WEBSERVICE_PORT+"/StatusWebService";

	if(Statics.WEBSERVICE_TYPE=="soap") {
	   StatusWebServiceSoap webServicesoap = new  StatusWebServiceSoap();
     System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
     System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
     System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
     System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");

     Statics.KOKO_ENDPOINT=Endpoint.publish(bindingURI, webServicesoap);
     
    
     Statics.KOKO_ENDPOINT.setExecutor(Executors.newFixedThreadPool(10));
    System.out.println("SOAP!!!! Server started at: " + bindingURI);
	}
	else if(Statics.WEBSERVICE_TYPE=="rest") {
		
		 ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
	        context.setContextPath("/");

	        ResourceConfig resourceConfig = new ResourceConfig();
	        resourceConfig.register(new CORSFilter());
	       // context.setHandler(resourceConfig);
	        
	        Server jettyServer = new Server(Statics.WEBSERVICE_PORT);
	        jettyServer.setHandler(context);
	        
	       
	        ServletHolder jerseyServlet = context.addServlet(
	             org.glassfish.jersey.servlet.ServletContainer.class, "/*");
	        jerseyServlet.setInitOrder(0);
	       
	        // Tells the Jersey Servlet which REST service/class to load.
	        jerseyServlet.setInitParameter(
	           "jersey.config.server.provider.classnames",
	           StatusWebServiceRest.class.getCanonicalName());
	        
	      

	        try {
	            jettyServer.start();
	            jettyServer.join();
	        } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
	            jettyServer.destroy();
	        }
			  
			  System.out.println("REST!!!! Server started at: " + bindingURI);
			
			
	}
}

}

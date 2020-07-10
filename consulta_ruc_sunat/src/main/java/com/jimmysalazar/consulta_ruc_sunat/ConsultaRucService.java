package com.jimmysalazar.consulta_ruc_sunat;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;

import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlHiddenInput;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class ConsultaRucService {
	
	WebClient webCLiente = new WebClient(); //Navegador 
	HtmlPage htmlPage = null; // Pagina a cargar
	
	final String BASE_PATH = "D:/captcha/"; 
	final String URL_WEB = "https://e-consultaruc.sunat.gob.pe/cl-ti-itmrconsruc/frameCriterioBusqueda.jsp";
	protected String currentIMG = "";
	
	 
    private void configurarCliente() {
    	try {
    		System.out.println("********* Configurando cliente web *********");
    		
    		webCLiente.getOptions().setJavaScriptEnabled(true);
    		webCLiente.getOptions().setCssEnabled(false);
    		webCLiente.getCookieManager().setCookiesEnabled(true);
    		webCLiente.setAjaxController(new NicelyResynchronizingAjaxController());
    		webCLiente.getOptions().setThrowExceptionOnScriptError(false);
    		webCLiente.getOptions().setThrowExceptionOnFailingStatusCode(false);
    		webCLiente.getOptions().setPrintContentOnFailingStatusCode(false);
    		
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }
    
    private void cargarPagina(String url) {
    	try {
    		System.out.println("********* Cargando página *********");
    		htmlPage = webCLiente.getPage(url);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }
    
    private void inicializarCaptcha() {
    	try {
    		
    		System.out.println("********* GENERANDO CAPTCHA*********");
    		
    		HtmlImage image = htmlPage.<HtmlImage>getFirstByXPath("//img[@src='captcha?accion=image']");
    		ImageReader img = image.getImageReader();
    		BufferedImage buf = img.read(0);
    		
    		// Generando la imagen
    		currentIMG = BASE_PATH + UUID.randomUUID() + ".jpg";
    		File outputfile = new File(currentIMG);
    		ImageIO.write(buf, "jpg", outputfile);
            
            System.out.println("********* CAPTCHA GENERADO: " + currentIMG + " *********");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }
    
    private void consultarSunat(String codigoCaptcha) {
    	try {
    		
			System.out.println("********* CONSULTANDO A SUNAT *********");
			
    		HtmlForm htmlForm = htmlPage.getElementByName("mainForm");
    		HtmlTextInput input = htmlForm.getInputByName("codigo");
    		input.setText(codigoCaptcha);
    		
    		// Eliminando imagen del captcha
        	File imagen = new File(currentIMG);
            if(imagen.exists())
            	imagen.delete();
    		
    		HtmlHiddenInput hidden = (HtmlHiddenInput)htmlForm.getInputByName("accion");
    		hidden.setValueAttribute("consPorRuc");
    		
    		HtmlHiddenInput hidden2 = (HtmlHiddenInput)htmlForm.getInputByName("nroRuc");
    		String ruc = "10742776137";
    		hidden2.setValueAttribute(ruc);
    		
    		HtmlButton boton = (HtmlButton)htmlPage.createElement("button");
    		boton.setAttribute("type", "submit");
    		htmlForm.appendChild(boton);
    		
    		htmlPage = boton.click();
    		
    		webCLiente.waitForBackgroundJavaScript(10000);
    		htmlPage = (HtmlPage)webCLiente.getCurrentWindow().getEnclosedPage();
    		
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    }
    
    private void mostrarRespuestaSunat() {
    	
    	// MOSTRANDO DATA
    	String resultado = "";
    	String razonSocial = "";
    	String tipoContribuyente = "";
    	String domicilioFiscal = "";
    	
    	try {
			HtmlTable htmlTable = (HtmlTable)htmlPage.getByXPath("//table[@class='form-table']").get(2);
			
			for (int i = 0; i < 14; i++) {
				HtmlTableRow tr = htmlTable.getRow(i);
				for (int j = 0; j < 2; j++) {
					
					HtmlTableCell td = tr.getCell(j);
					resultado +=  td.asText() + "\n";
					
					if(i==0 && j==1) {
						String[] parts = td.asText().split("-");
						razonSocial = parts[1].trim();
					}
					if(i==1 && j==1) {
						tipoContribuyente = td.asText().trim();
					}
					if(i==7 && j==1) {
						domicilioFiscal = td.asText().trim();
					}
					
				}
			}
			
			//System.out.println(resultado);
			System.out.println("********** RESPUESTA SUNAT *********");
			
			System.out.println("Razon S:" + razonSocial);
			System.out.println("Tipo Contribuyente:" + tipoContribuyente);
			System.out.println("Domicilio Fiscal:" + domicilioFiscal);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
    	
    }
    
    public void generarCaptcha() {
    	
    	configurarCliente();
    	cargarPagina(URL_WEB);
    	inicializarCaptcha();
    	
    }
    
    public void realizarConsulta( String codigoCaptcha ) {
    	
    	consultarSunat(codigoCaptcha);
    	mostrarRespuestaSunat();
        
    }

}

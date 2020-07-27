package com.jimmysalazar.consulta_ruc_sunat;

import java.util.Scanner;

/**
 * MAIN CLASS
 *
 */
public class App {

	// VERSION 2
	public static void main(String[] args) {
		
		String miRuc = "20508972734";
		//String miRuc = "10742776137";
		
		ConsultaRucServiceV2 servicio = new ConsultaRucServiceV2();
		Respuesta rpta = servicio.consultarRuc(miRuc);
		
		System.out.println("Razon S:" + rpta.getRazonSocial());
		System.out.println("Tipo Contribuyente:" + rpta.getTipoContribuyente());
		System.out.println("Domicilio Fiscal:" + rpta.getDireccionFiscal());
		
	}

	
	// VERSION 1
	/*
    public static void main( String[] args )
    {        
        ConsultaRucServiceV1 servicio = new ConsultaRucServiceV1();
        
        servicio.generarCaptcha();
        
		// INGRESANDO CAPTCHA MANUALMENTE 
        System.out.println("*********** INGRESE EL CAPTCHA ***********");
		Scanner scanner = new Scanner(System. in);
        String codigoCaptcha = scanner. nextLine();
        
        servicio.realizarConsulta(codigoCaptcha);
        
    }
	*/

}

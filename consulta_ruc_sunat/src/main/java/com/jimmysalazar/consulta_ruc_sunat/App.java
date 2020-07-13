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
		
		ConsultaRucServiceV2 servicio = new ConsultaRucServiceV2();
		servicio.consultarRuc(miRuc);

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

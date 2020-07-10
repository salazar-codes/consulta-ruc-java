package com.jimmysalazar.consulta_ruc_sunat;

import java.util.Scanner;

/**
 * MAIN CLASS
 *
 */
public class App 
{
	
    public static void main( String[] args )
    {        
        ConsultaRucService servicio = new ConsultaRucService();
        
        servicio.generarCaptcha();
        
		// INGRESANDO CAPTCHA MANUALMENTE 
        System.out.println("*********** INGRESE EL CAPTCHA ***********");
		Scanner scanner = new Scanner(System. in);
        String codigoCaptcha = scanner. nextLine();
        
        servicio.realizarConsulta(codigoCaptcha);
        
    }
    
}

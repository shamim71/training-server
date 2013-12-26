package com.versacomllc.training.service;

import java.io.ByteArrayOutputStream;


/**
 * Define the service interface for customer registration management.
 * 
 * @author Shamim Ahmmed
 * 
 */
public interface ReportingService {

	  public ByteArrayOutputStream getCertificatePDFAsStream(String name, String course, String date, String serial);
	  
	
	
}

package com.versacomllc.training.service.impl;

import java.io.ByteArrayOutputStream;
import java.net.MalformedURLException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.URIResolver;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.apache.fop.servlet.ServletContextURIResolver;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.versacomllc.training.service.ReportingService;

/**
 * Default implementation to get a PDF.
 * 
 * Uses code from the following sources
 * 
 * <pre>
 * http://xmlgraphics.apache.org/fop/0.95/embedding.html#basics
 * http://johnbokma.com/mexit/2010/02/11/
 * </pre>
 * 
 * @author Shamim
 * 
 */
@Service
public class ReportingServiceImpl implements ReportingService {

	@Autowired
	ServletContext context;

	private FopFactory fopFactory;
	private TransformerFactory tFactory;

	final Logger logger = Logger.getLogger(getClass());

	protected URIResolver uriResolver;

	@Autowired
	private Properties appProperties;

	@Override
	public ByteArrayOutputStream getCertificatePDFAsStream(String name, String course, String date,String serial) {
	    // Setup a buffer to obtain the content (note: we use the apache
	    // commons variant for better performance)
	    ByteArrayOutputStream out = new ByteArrayOutputStream();

	    // Set up a uri resolver to the servlet context
	    uriResolver = new ServletContextURIResolver(context);

	    try {
	      // Setup FOP to create a PDF and send it to the out
	      // ByteArrayOutputStream object
	      Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, out);

	      Source xsltSrc = uriResolver.resolve(
	          "servlet-context:/WEB-INF/fop/output.xsl", null);

	      Transformer transformer = tFactory.newTransformer(xsltSrc);

	      // Make sure the XSL transformation's result is piped through to FOP
	      Result res = new SAXResult(fop.getDefaultHandler());

		// Plug in the data XML (we build it from scratch)
	      Source xmlSrc = getInputXMLAsStream(name, course,date,serial);

	      // Start the transformation and rendering process
	      transformer.transform(xmlSrc, res);
	    }
	    catch (Exception e) {
	      logger.error(e);
	      e.printStackTrace(System.err);
	    }

	    return out;
	}


	  /**
	   * <pre>
	   * Example output:
	   * 
	   * {@code
	   * <?xml version="1.0"?>
	   *     <example>
	   *       <heading>Hello, barcode world!</heading>
	   *       <barcode>123456789012</barcode>
	   *     </example>
	   * }
	   * </pre>
	   * 
	   * @param message
	   *          {@code<heading />}
	   * @param id
	   *          {@code<barcode />}
	   * @return
	   */
	  private Source getInputXMLAsStream(String name, String course, String date,String serial) {
	    Document document = null;
	    try {
	      document = DocumentBuilderFactory.newInstance().newDocumentBuilder()
	          .newDocument();
	    }
	    catch (ParserConfigurationException e) {
	      logger.error(e);
	      e.printStackTrace();
	    }

	    Element rootElement = document.createElement("example");
	    Element nameElement = document.createElement("name");
	    Element courseElement = document.createElement("course");
	    Element dateElement = document.createElement("date");
	    Element barcodeElement = document.createElement("serial");
	    
	    nameElement.setTextContent(name);
	    courseElement.setTextContent(course);
	    dateElement.setTextContent(date);
	    barcodeElement.setTextContent(serial);
	    
	    rootElement.appendChild(nameElement);
	    rootElement.appendChild(courseElement);
	    rootElement.appendChild(dateElement);
	    rootElement.appendChild(barcodeElement);
	    
	    document.appendChild(rootElement);

	    Source xmlSource = new DOMSource(document);

	    return xmlSource;
	  }

	  @PostConstruct
	  public void init() throws ServletException {
	    fopFactory = FopFactory.newInstance();
	    tFactory = TransformerFactory.newInstance();
	    FOUserAgent foUserAgent = fopFactory.newFOUserAgent();

	    // hacky stuff to get fop to find assets on the servlet context path
	    String appPath = context.getRealPath(""); // root of web app
	    try {
	      fopFactory.setBaseURL(appPath);
	      foUserAgent.setBaseURL(fopFactory.getBaseURL());
	    }
	    catch (MalformedURLException e) {
	      logger.error(e);
	      e.printStackTrace();
	    }
	  }

	}


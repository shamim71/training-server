package com.versacomllc.training.controller;

import static com.versacomllc.training.common.CommonConstants.ERROR_CODE_INVALID_MESSAGE_STRUCTURE;
import static com.versacomllc.training.common.CommonConstants.ERROR_CODE_NULL_POINTER_ENCOUNTERED;
import static com.versacomllc.training.common.CommonConstants.ERROR_CODE_NUMBER_FORMAT_INVALID;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.exc.UnrecognizedPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.versacomllc.training.message.ErrorMessage;
import com.versacomllc.training.message.GenericMessageResponse;




/**
 * This class contains common methods and exception handler methods that will be
 * used all controller layer classes
 * 
 * @author Shamim Ahmmed
 * 
 */
public abstract class AbstractWSController {

  protected final Logger logger = Logger.getLogger(getClass());

  @Autowired
  Jaxb2Marshaller jaxb2Marshaller;

  /**
   * Write the object as JSON String
   * 
   * @param response
   * @param message
   * @throws HttpMessageNotWritableException
   * @throws IOException
   */
  protected void writeObjectAsJsonString(HttpServletResponse response,
      GenericMessageResponse<?> message)
      throws HttpMessageNotWritableException, IOException {
    MappingJacksonHttpMessageConverter converter = new MappingJacksonHttpMessageConverter();
    response.setContentType(CONTENT_TYPE_JSON);
    HttpOutputMessage outputMessage = new ServletServerHttpResponse(response);

    converter.write(message, new MediaType("application", "json"),
        outputMessage);
  }

  protected boolean versionExist(String clientVersion, String objectVersion) {
    String serverVersion = "application/" + objectVersion;
    if (clientVersion.equalsIgnoreCase(serverVersion)) {
      return true;
    }
    if (clientVersion.toLowerCase().contains(objectVersion.toLowerCase())) {
      return true;
    }
    return false;
  }

  /**
   * Write the object as JSON string provided by the content type.
   * 
   * @param response
   * @param message
   * @param contentType
   * @throws HttpMessageNotWritableException
   * @throws IOException
   */
  protected void writeObjectAsJsonString(HttpServletResponse response,
      GenericMessageResponse<?> message, String contentType)
      throws HttpMessageNotWritableException, IOException {
    MappingJacksonHttpMessageConverter converter = new MappingJacksonHttpMessageConverter();
    response.setContentType("application/" + contentType);
    HttpOutputMessage outputMessage = new ServletServerHttpResponse(response);

    converter.write(message, new MediaType("application", contentType),
        outputMessage);
  }

  protected void writeObjectAsJsonString(HttpServletResponse response,
      Object message) throws HttpMessageNotWritableException, IOException {
    MappingJacksonHttpMessageConverter converter = new MappingJacksonHttpMessageConverter();
    response.setContentType(CONTENT_TYPE_JSON);
    HttpOutputMessage outputMessage = new ServletServerHttpResponse(response);

    converter.write(message, new MediaType("application", "json"),
        outputMessage);
  }

  protected void writeObjectAsXMLString(HttpServletResponse response,
      Object message) throws HttpMessageNotWritableException, IOException {
    MarshallingHttpMessageConverter converter = new MarshallingHttpMessageConverter();
    converter.setMarshaller(jaxb2Marshaller);
    response.setContentType(CONTENT_TYPE_XML);
    HttpOutputMessage outputMessage = new ServletServerHttpResponse(response);

    converter
        .write(message, new MediaType("application", "xml"), outputMessage);
  }



  /**
   * Transform the NullPointerException to standard HTTP style JSON format
   * 
   * @param ex
   *          the NullPointerException instance
   * @param response
   *          the HttpServletResponse instance
   * 
   * @throws HttpMessageNotWritableException
   * @throws IOException
   */
  @ExceptionHandler(NullPointerException.class)
  public void handleNullPointerException(NullPointerException ex,
      HttpServletResponse response) throws HttpMessageNotWritableException,
      IOException {

    logger.error("NullPointer Exception : ", ex);

    GenericMessageResponse<?> msg = new GenericMessageResponse<String>();
    msg.setResult(null);
    String warning = "We have encountered a problem during processing your request, "
        + ex.getMessage();
    msg.setError(new ErrorMessage(ERROR_CODE_NULL_POINTER_ENCOUNTERED, warning));
    this.writeObjectAsJsonString(response, msg, INTERNAL_SERVER_ERROR.value());
  }

  /**
   * Transform the HttpMessageNotReadableException to standard HTTP style JSON
   * format
   * 
   * @param ex
   *          the NullPointerException instance
   * @param response
   *          the HttpServletResponse instance
   * 
   * @throws HttpMessageNotWritableException
   * @throws IOException
   */
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public void handleInvalidMessagePerceived(HttpMessageNotReadableException ex,
      HttpServletResponse response) throws HttpMessageNotWritableException,
      IOException {

    logger.error("HttpMessageNotReadable Exception : ", ex);

    GenericMessageResponse<?> msg = new GenericMessageResponse<String>();
    msg.setResult(null);
    String warning = " Please check your request message format and try again";

    if (ex.getCause() instanceof UnrecognizedPropertyException) {
      UnrecognizedPropertyException uex = (UnrecognizedPropertyException) ex
          .getCause();
      warning = "Message property '" + uex.getUnrecognizedPropertyName()
          + "' is not recognized";
    }

    logger.error(ex.getMessage(), ex);

    msg.setError(new ErrorMessage(ERROR_CODE_INVALID_MESSAGE_STRUCTURE, warning));
    this.writeObjectAsJsonString(response, msg, BAD_REQUEST.value());
  }

  /**
   * Transform the NumberFormatException into a standard HTTP style response
   * message along with application level error code
   * 
   * @param ex
   *          the NumberFormatException instance
   * @param response
   *          the HttpServletResponse instance
   * 
   * @throws HttpMessageNotWritableException
   * @throws IOException
   */
  @ExceptionHandler(NumberFormatException.class)
  public void invalidNumberFormatRecognized(NumberFormatException ex,
      HttpServletResponse response) throws HttpMessageNotWritableException,
      IOException {

    logger.error("NumberFormatException : ", ex);

    GenericMessageResponse<?> msg = new GenericMessageResponse<String>();
    msg.setResult(null);
    String warning = "Invalid input encountered, " + ex.getMessage();
    msg.setError(new ErrorMessage(ERROR_CODE_NUMBER_FORMAT_INVALID, warning));
    this.writeObjectAsJsonString(response, msg, BAD_REQUEST.value());
  }

  /**
   * Write an instance of GenericMessageResponse into the response along with
   * the status in the response header
   * 
   * @param response
   *          the HttpServletResponse instance
   * @param message
   *          the GenericMessageResponse instance
   * @param status
   *          the HTTP status code
   * 
   * @throws HttpMessageNotWritableException
   * @throws IOException
   */
  protected void writeObjectAsJsonString(HttpServletResponse response,
      GenericMessageResponse<?> message, int status)
      throws HttpMessageNotWritableException, IOException {
    MappingJacksonHttpMessageConverter converter = new MappingJacksonHttpMessageConverter();
    response.setContentType(CONTENT_TYPE_JSON);
    response.setStatus(status);
    HttpOutputMessage outputMessage = new ServletServerHttpResponse(response);

    converter.write(message, new MediaType("application", "json"),
        outputMessage);
  }

  private static final String CONTENT_TYPE_JSON = "application/json";
  private static final String CONTENT_TYPE_XML = "application/xml";
}

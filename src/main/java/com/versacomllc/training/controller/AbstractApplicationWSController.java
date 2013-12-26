package com.versacomllc.training.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.versacomllc.training.common.ApplicationStatusCodes;
import com.versacomllc.training.exception.ServiceException;
import com.versacomllc.training.message.response.GenericResponse;

public abstract class AbstractApplicationWSController extends
		AbstractWSController {

	private static final String CONTENT_TYPE_JSON = "application/json";

	/**
	 * Write object as json string and set the Http status code inside the
	 * response according to our status code.
	 * 
	 * @param response
	 *            HttpServletResponse instance
	 * @param message
	 *            GenericResponse instance
	 * @throws HttpMessageNotWritableException
	 * @throws IOException
	 */
	protected final void writeObjectAsJsonString(
			final HttpServletResponse response, final GenericResponse<?> message)
			throws HttpMessageNotWritableException, IOException {
		MappingJacksonHttpMessageConverter converter = new MappingJacksonHttpMessageConverter();
		response.setContentType(CONTENT_TYPE_JSON);

		HttpStatus status = ApplicationStatusCodes.httpStatusValue(message
				.getStatus_code());

		response.setStatus(status.value());
		HttpOutputMessage outputMessage = new ServletServerHttpResponse(
				response);

		converter.write(message, new MediaType("application", "json"),
				outputMessage);
	}

	protected final void writeAsJson(final HttpServletResponse response,
			final GenericResponse<?> message)
			throws HttpMessageNotWritableException, IOException {
		MappingJacksonHttpMessageConverter converter = new MappingJacksonHttpMessageConverter();
		response.setContentType(CONTENT_TYPE_JSON);

		response.setStatus(message.getHttpStatus().value());
		HttpOutputMessage outputMessage = new ServletServerHttpResponse(
				response);

		converter.write(message, new MediaType("application", "json"),
				outputMessage);
	}

	/**
	 * Write object as json string including a custom HttpStatus code.
	 * 
	 * @param response
	 *            HttpServletResponse instance
	 * @param message
	 *            message object
	 * @param status
	 *            HTTPStatus of this message
	 * @throws HttpMessageNotWritableException
	 * @throws IOException
	 */
	protected final void writeObjectAsJsonString(
			final HttpServletResponse response, final Object message,
			final HttpStatus status) throws HttpMessageNotWritableException,
			IOException {
		MappingJacksonHttpMessageConverter converter = new MappingJacksonHttpMessageConverter();
		response.setContentType(CONTENT_TYPE_JSON);
		response.setStatus(status.value());
		HttpOutputMessage outputMessage = new ServletServerHttpResponse(
				response);

		converter.write(message, new MediaType("application", "json"),
				outputMessage);
	}

	/**
	 * Transform the HttpMessageNotReadableException to standard HTTP style JSON
	 * format.
	 * 
	 * @param ex
	 *            the NullPointerException instance
	 * @param response
	 *            the HttpServletResponse instance
	 * 
	 * @throws HttpMessageNotWritableException
	 * @throws IOException
	 */
	@Override
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public final void handleInvalidMessagePerceived(
			final HttpMessageNotReadableException ex,
			final HttpServletResponse response)
			throws HttpMessageNotWritableException, IOException {

		logger.debug("HttpMessageNotReadable Exception : ", ex);

		GenericResponse<?> msg = new GenericResponse<String>(null,
				ApplicationStatusCodes.INVALID_REQUEST);

		logger.error(ex.getMessage(), ex);

		this.writeObjectAsJsonString(response, msg,
				ApplicationStatusCodes.INVALID_REQUEST.httpStatus());
	}

	/**
	 * Transform the NullPointerException to standard HTTP style JSON format.
	 * 
	 * @param ex
	 *            the NullPointerException instance
	 * @param response
	 *            the HttpServletResponse instance
	 * 
	 * @throws HttpMessageNotWritableException
	 * @throws IOException
	 */
	@Override
	@ExceptionHandler(NullPointerException.class)
	public final void handleNullPointerException(final NullPointerException ex,
			final HttpServletResponse response)
			throws HttpMessageNotWritableException, IOException {

		logger.debug("NullPointer Exception : ", ex);


		GenericResponse<String>  res = new GenericResponse<String>(ex.getMessage(),
				ApplicationStatusCodes.SERVER_ERROR);
		
		this.writeAsJson(response, res);
		
	}

	/**
	 * Transform the NotImplementedException to standard HTTP style JSON format.
	 * 
	 * @param ex
	 *            the NotImplementedException instance
	 * @param response
	 *            the HttpServletResponse instance
	 * @throws HttpMessageNotWritableException
	 * @throws IOException
	 */
	@ExceptionHandler(NotImplementedException.class)
	public final void handleNotImplementedException(
			final NotImplementedException ex, final HttpServletResponse response)
			throws HttpMessageNotWritableException, IOException {
		logger.debug("NotImplemented Exception: ", ex);

		GenericResponse<String>  res = new GenericResponse<String>(ex.getMessage(),
				ApplicationStatusCodes.INVALID_REQUEST);
		
		this.writeAsJson(response, res);
	}

	/**
	 * Transform the service related exception into a standard HTTP style
	 * exception along with application level error code and error message.
	 * 
	 * @param ex
	 *            the instance of GiftServiceException
	 * @param response
	 *            the HttpServletResponse instance
	 * 
	 * @throws HttpMessageNotWritableException
	 * @throws IOException
	 */
	@ExceptionHandler(ServiceException.class)
	public void handleServiceException(final ServiceException ex,
			final HttpServletResponse response)
			throws HttpMessageNotWritableException, IOException {

		logger.debug("Service Exception : ", ex);

		GenericResponse<String>  res = new GenericResponse<String>(ex.getMessage(),
				ApplicationStatusCodes.INVALID_REQUEST);
		
		this.writeAsJson(response, res);

	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public void duplicateRecordCheck(DataIntegrityViolationException ex,
			HttpServletResponse response)
			throws HttpMessageNotWritableException, IOException {

		logger.error("Duplicate record  : ", ex);

		String warning = "Record already exist, " + ex.getMessage();

		GenericResponse<String> message = new GenericResponse<String>(warning,
				ApplicationStatusCodes.INVALID_REQUEST);
		this.writeAsJson(response, message);
	}
}

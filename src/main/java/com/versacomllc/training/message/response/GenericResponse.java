package com.versacomllc.training.message.response;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.http.HttpStatus;

import com.versacomllc.training.common.ApplicationStatusCodes;
import com.versacomllc.training.exception.ServiceException;

/**
 * This class represents a generic message response, used as a result message
 * format for all server side actions.
 * 
 * result contains the result of the query, status_code/message contain a
 * description where the request was successful or had errors.
 * 
 * @author
 * 
 * @param <T>
 */
@XmlRootElement(name = "Response")
public final class GenericResponse<T> {

	private String status_code;
	private T result;
	private String status_message;
	
	
	private HttpStatus httpStatus;
	
	@JsonIgnore
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public GenericResponse() {

	}

	/**
	 * Init GenericResponse with ApplicationStatusCode.
	 * 
	 * @param result
	 *            result of the call
	 * @param status
	 *            status code of the call
	 */
	public GenericResponse(final T result, final ApplicationStatusCodes status) {
		this.result = result;
		this.status_code = String.valueOf(status.value());
		this.status_message = status.message();
		this.httpStatus = status.httpStatus();
	}

	/**
	 * Initialize response with ServiceException.
	 * 
	 * @param e
	 *            exception
	 */
	public GenericResponse(final ServiceException e) {
		this.status_code = e.getErrorCode();
		this.status_message = e.getMessage();
	}

	/**
	 * @return the status_code
	 */
	public String getStatus_code() {
		return status_code;
	}

	/**
	 * @return the result
	 */
	public T getResult() {
		return result;
	}

	/**
	 * @return the status_message
	 */
	public String getStatus_message() {
		return status_message;
	}

	/**
	 * @param status_code
	 *            the status_code to set
	 */
	public void setStatus_code(final String status_code) {
		this.status_code = status_code;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(final T result) {
		this.result = result;
	}

	/**
	 * @param status_message
	 *            the status_message to set
	 */
	public void setStatus_message(final String status_message) {
		this.status_message = status_message;
	}

	@Override
	public String toString() {
		StringBuffer stringResult = new StringBuffer();
		stringResult.append("Code: ").append(status_code).append("Message: ")
				.append(status_message);
		if (result != null) {
			stringResult.append("Result: ").append(result.toString());
		}

		return stringResult.toString();
	}
}

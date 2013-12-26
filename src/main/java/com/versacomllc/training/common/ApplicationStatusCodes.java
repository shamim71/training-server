package com.versacomllc.training.common;

import static com.versacomllc.training.common.CommonConstants.ERROR_CODE_NOT_ALLOWED;
import static com.versacomllc.training.common.CommonConstants.ERROR_CODE_NOT_AUTHORIZED;
import static com.versacomllc.training.common.CommonConstants.ERROR_CODE_RESOURCE_DUPLICATE;
import static com.versacomllc.training.common.CommonConstants.ERROR_CODE_RESOURCE_NOT_FOUND;
import static com.versacomllc.training.common.CommonConstants.ERROR_MESSAGE_MESSAGE_FORMAT_INVALID;
import static com.versacomllc.training.common.CommonConstants.ERROR_MESSAGE_NOT_ALLOWED;
import static com.versacomllc.training.common.CommonConstants.ERROR_MESSAGE_NOT_AUTHORIZED;
import static com.versacomllc.training.common.CommonConstants.ERROR_MESSAGE_NULL_POINTER_ENCOUNTERED;
import static com.versacomllc.training.common.CommonConstants.ERROR_MESSAGE_RESOURCE_DUPLICATE;
import static com.versacomllc.training.common.CommonConstants.ERROR_MESSAGE_RESOURCE_NOT_FOUND;
import static com.versacomllc.training.common.CommonConstants.SUCCESS_CODE_REMOVED;
import static com.versacomllc.training.common.CommonConstants.SUCCESS_CODE_UPDATED;
import static com.versacomllc.training.common.CommonConstants.SUCCESS_MESSAGE_CREATED;
import static com.versacomllc.training.common.CommonConstants.SUCCESS_MESSAGE_REMOVED;
import static com.versacomllc.training.common.CommonConstants.SUCCESS_MESSAGE_UPDATED;

import org.springframework.http.HttpStatus;

import com.versacomllc.training.exception.ServiceException;

/**
 * Definition of application level error codes.
 * 
 * @author
 * 
 */
public enum ApplicationStatusCodes {

	/**
	 * Access to this resource is not allowed because of missing authentication.
	 */
	NOT_AUTHORIZED(ERROR_CODE_NOT_AUTHORIZED, ERROR_MESSAGE_NOT_AUTHORIZED,
			HttpStatus.UNAUTHORIZED),

	/** Access to this method is not allowed (e.g. not implemented. */
	NOT_ALLOWED(ERROR_CODE_NOT_ALLOWED, ERROR_MESSAGE_NOT_ALLOWED,
			HttpStatus.METHOD_NOT_ALLOWED),

	/** Message invalid, e.g. values not set. */
	INVALID_MESSAGE(CommonConstants.ERROR_CODE_MESSAGE_FORMAT_INVALID,
			ERROR_MESSAGE_MESSAGE_FORMAT_INVALID, HttpStatus.BAD_REQUEST),

	/** Request could not be fulfilled. */
	INVALID_REQUEST(CommonConstants.ERROR_CODE_BAD_REQUEST,
			CommonConstants.ERROR_MESSAGE_BAD_REQUEST, HttpStatus.BAD_REQUEST),

	/** Resource has been created. */
	SUCCESS_CREATED("501", SUCCESS_MESSAGE_CREATED, HttpStatus.CREATED),

	/** Internal problems like Nullpointer during request. */
	SERVER_ERROR(CommonConstants.ERROR_CODE_NULL_POINTER_ENCOUNTERED,
			ERROR_MESSAGE_NULL_POINTER_ENCOUNTERED,
			HttpStatus.INTERNAL_SERVER_ERROR),

	/**
	 * The requested resource was not found (unspecific, e.g. for updating
	 * resources).
	 */
	ERROR_NOTFOUND(ERROR_CODE_RESOURCE_NOT_FOUND,
			ERROR_MESSAGE_RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND),

	/**
	 * The requested resource already exists (generic response for duplicate
	 * entries).
	 */
	ERROR_DUPLICATE(ERROR_CODE_RESOURCE_DUPLICATE,
			ERROR_MESSAGE_RESOURCE_DUPLICATE, HttpStatus.CONFLICT),

	/** Resource has been removed. */
	SUCCESS_REMOVED(SUCCESS_CODE_REMOVED, SUCCESS_MESSAGE_REMOVED,
			HttpStatus.OK),

	/** Resource has been updated. */
	SUCCESS_UPDATED(SUCCESS_CODE_UPDATED, SUCCESS_MESSAGE_UPDATED,
			HttpStatus.OK);

	private final int value;
	private final String message;
	private HttpStatus httpStatus;

	private ApplicationStatusCodes(final String code, final String message,
			final HttpStatus status) {
		this.value = Integer.valueOf(code);
		this.message = message;
		this.httpStatus = status;
	}

	/**
	 * Return application level error code.
	 * 
	 * @return error code
	 */
	public int value() {
		return this.value;
	}

	/**
	 * Return application level error message.
	 * 
	 * @return error message
	 */
	public String message() {
		return this.message;
	}

	/**
	 * Return HttpStatus code assigned to this application error code.
	 * 
	 * @return HttpStatus
	 */
	public HttpStatus httpStatus() {
		return this.httpStatus;
	}

	public ServiceException getException() {
		return new ServiceException(this.message, String.valueOf(this.value),
				this.httpStatus.value());
	}

	/**
	 * Convert the provided application status to the assigned HttpStatus.
	 * 
	 * @param applicationStatus
	 *            internal application status
	 * @return HttpStatus
	 */
	public static HttpStatus httpStatusValue(final String applicationStatus) {
		int appStatus = Integer.valueOf(applicationStatus);
		for (ApplicationStatusCodes status : values()) {
			if (status.value == appStatus) {
				return status.httpStatus;
			}
		}
		throw new IllegalArgumentException("No matching constant for ["
				+ applicationStatus + "]");
	}

}

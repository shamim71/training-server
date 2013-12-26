package com.versacomllc.training.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * This class contains common constants used in service and controller classes
 * 
 * @author Shamim Ahmmed
 * 
 */
public class CommonConstants {

  /** List of error code from Gift service */
  public static final String ERROR_CODE_MESSAGE_FORMAT_INVALID = "600";
  public static final String ERROR_CODE_INVALID_JSON = "601";
  public static final String ERROR_CODE_BAD_REQUEST = "602";


  public static final String ERROR_CODE_NUMBER_FORMAT_INVALID = "700";
  public static final String ERROR_CODE_NULL_POINTER_ENCOUNTERED = "701";

  public static final String ERROR_CODE_INVALID_MESSAGE_STRUCTURE = "702";



  /** List of error message for gift web service */
  public static final String ERROR_MESSAGE_FORMAT_INVALID = "Message format could not be recognized";
  public static final String ERROR_MESSAGE_INVALID_JSON = "Invalid JSON encountered during request processing";
  public static final String ERROR_MESSAGE_BAD_REQUEST = "Request could not be recognized";

  public static final String ERROR_MESSAGE_CUSTOMER_NOT_EXIST = "Customer does not exist";

  public static final int ERROR_CODE_NOT_AUTHORIZED_INT = 401;
  public static final String ERROR_CODE_NOT_AUTHORIZED = "401";
  public static final String ERROR_MESSAGE_NOT_AUTHORIZED = "Not authorized";

  public static final int ERROR_CODE_NOT_ALLOWED_INT = 405;
  public static final String ERROR_CODE_NOT_ALLOWED = "405";
  public static final String ERROR_MESSAGE_NOT_ALLOWED = "Method not allowed";

  public static final int ERROR_CODE_MESSAGE_FORMAT_INVALID_INT = 600;
  public static final String ERROR_MESSAGE_MESSAGE_FORMAT_INVALID = "One of the required IDs is not set";


  public static final String ERROR_MESSAGE_NULL_POINTER_ENCOUNTERED = "We have encountered a problem during processing your request.";





  public static final int ERROR_CODE_RESOURCE_DUPLICATE_INT = 798;
  public static final String ERROR_CODE_RESOURCE_DUPLICATE = "798";
  public static final String ERROR_MESSAGE_RESOURCE_DUPLICATE = "Resource already exists on the server";

  public static final int ERROR_CODE_RESOURCE_NOT_FOUND_INT = 799;
  public static final String ERROR_CODE_RESOURCE_NOT_FOUND = "799";
  public static final String ERROR_MESSAGE_RESOURCE_NOT_FOUND = "Resource was not found on the server";

  public static final int SUCCESS_CODE_CREATED_INT = 800;
  public static final String SUCCESS_CODE_CREATED = "800";
  public static final String SUCCESS_MESSAGE_CREATED = "Resource has been created on the server";

  public static final int SUCCESS_CODE_REMOVED_INT = 801;
  public static final String SUCCESS_CODE_REMOVED = "801";
  public static final String SUCCESS_MESSAGE_REMOVED = "Resource has been removed from the server";

  public static final int SUCCESS_CODE_UPDATED_INT = 802;
  public static final String SUCCESS_CODE_UPDATED = "802";
  public static final String SUCCESS_MESSAGE_UPDATED = "Resource has been updated on the server";

  public static final DateFormat DATE_FORMAT = new SimpleDateFormat(
      "yyyy-MM-dd HH:mm:ss");

  


  public static enum TraningStatus {
    /** Successfully completed */
    COMPLETED,

    /** Transaction failed */
    FAILED,

    /** Transaction pending */
    PENDING
  }


  public static final String STANDARD_TIME_ZONE = "UTC";

  public static final TimeZone UTC_TIME_ZONE = TimeZone
      .getTimeZone(STANDARD_TIME_ZONE);

  public static final DateFormat US_DATEFORMAT = new SimpleDateFormat("MM/dd/yyyy");
}

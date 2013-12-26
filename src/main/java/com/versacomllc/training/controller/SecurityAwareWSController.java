package com.versacomllc.training.controller;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.ServletContextAware;

import com.versacomllc.training.common.ApplicationStatusCodes;
import com.versacomllc.training.common.CommonConstants;
import com.versacomllc.training.exception.ServiceException;
import com.versacomllc.training.message.response.GenericResponse;


/**
 * Security aware WS controller used to validate customer authentication before
 * processing data.
 * 
 * Will check the security context for Authentication objects and validate them
 * against the data being changed if enabled.
 * 
 * @author
 * 
 */
public class SecurityAwareWSController extends AbstractApplicationWSController
    implements ServletContextAware {

  private ServletContext context;


  /**
   * Retrieve the customer ID for the current authenticated user.
   * 
   * @return current authenticated user or null if none is authenticated
   */
  protected final String getCustomerId() {

    Authentication authentication = SecurityContextHolder.getContext()
        .getAuthentication();

    if (authentication == null || authentication.getPrincipal() == null) {
      return null;
    }

    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    if (userDetails == null) {
      return null;
    }

    return userDetails.getUsername();
  }

  /**
   * Validate the customer ID against the one in the current security context.
   * 
   * @param customerId
   *          customer id to validate
   */
  protected final void validateCustomer(final String customerId) {
    final boolean enabledSecurity = isSecurityEnabled();
    if (!enabledSecurity) {
      return;
    }

    String customerIdFromContext = getCustomerId();

    if (StringUtils.isEmpty(customerIdFromContext)
        || !customerIdFromContext.equals(customerId)) {
      logger.debug("Customer id from context :" + customerIdFromContext);
      logger.debug("Customer id from request :" + customerId);
      throw ApplicationStatusCodes.NOT_AUTHORIZED.getException();
    }
  }




  /**
   * Transform the service related exception into a standard HTTP style
   * exception along with application level error code and error message.
   * 
   * @param ex
   *          the instance of GiftServiceException
   * @param response
   *          the HttpServletResponse instance
   * 
   * @throws HttpMessageNotWritableException
   * @throws IOException
   */
  @ExceptionHandler(ServiceException.class)
  public final void handleServiceException(final ServiceException ex,
      final HttpServletResponse response)
      throws HttpMessageNotWritableException, IOException {

    logger.debug("Service Exception : ", ex);

    GenericResponse<?> msg = new GenericResponse<String>(ex);

    if (msg.getStatus_code().equals(
    		CommonConstants.ERROR_CODE_NOT_AUTHORIZED)) {
      this.writeObjectAsJsonString(response, msg,
          ApplicationStatusCodes.NOT_AUTHORIZED.httpStatus());
    }
    else {
      super.handleServiceException(ex, response);
    }
  }

  /**
   * Check the context if security is enabled.
   * 
   * @return true if enabled
   */
  protected final boolean isSecurityEnabled() {
    final String secConfig = this.context.getInitParameter("enabledSecurity");
    boolean enabledSecurity = false;
    if (!StringUtils.isEmpty(secConfig)) {
      enabledSecurity = Boolean.parseBoolean(secConfig);
    }
    return enabledSecurity;
  }

  @Override
  public final void setServletContext(final ServletContext servletContext) {
    this.context = servletContext;
  }

}

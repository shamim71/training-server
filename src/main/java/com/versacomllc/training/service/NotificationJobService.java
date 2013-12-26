package com.versacomllc.training.service;

/**
 * Service interface for restaurant synchronization
 * 
 * @author Shamim Ahmmed
 * 
 */
public interface NotificationJobService {

  /**
   * Synchronize restaurant back end system with MO database
   */
  public void notifyReceivers();

}

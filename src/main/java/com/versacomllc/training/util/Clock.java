package com.versacomllc.training.util;

/**
 * Wrapper for Time/Date related values. Don't call System.currentTimeMillis
 * directly! Mock this singleton class in your unit tests
 * 
 * @author 
 * 
 */
public final class Clock {

  /** Singleton. */
  private Clock() {

  }

  private static Clock instance = null;
  private long fixedTime = 0L;

  /**
   * Get a new clock to retrieve the current time.
   * 
   * @return current clock
   */
  public static Clock getInstance() {
    if (instance == null) {
      instance = new Clock();
    }
    return instance;
  }

  /**
   * Get the current time in milis. If the fixed time was previously set to
   * zero, it returns the current time in milis.
   * 
   * @return current time
   */
  public long currentTimeMillis() {
    if (fixedTime == 0L) {
      return System.currentTimeMillis();
    }
    else {
      return fixedTime;
    }
  }

  /**
   * Set the current time to a fixed value (for testing purposes).
   * 
   * @param time
   *          time to set in milis
   */
  public void setFixedTimeMillis(final long time) {
    fixedTime = time;
  }

  /**
   * Reset the clock from a fixed time to return the real current time again.
   */
  public void resetFixedTimeMillis() {
    fixedTime = 0L;
  }
}

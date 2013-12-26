package com.versacomllc.training.util;


import java.security.SecureRandom;

import org.apache.commons.lang.RandomStringUtils;

public class Identifier {
  private static SecureRandom random = new SecureRandom();

  /**
   * Generate a fast and unique (56 million possible values) but
   * cryptographically-unsuitable random value
   * 
   * @return A random alphanumeric value of length 6
   * 
   */


  public static String next() {
    // Alphanumeric possibilities [a-z A-Z 0-9] = 26 + 26 + 10 = 62
    // 62 possibilities ^ 6 characters = 56 800 235 584 possible unique values
    return (next(6));
  }

  /**
   * Generate a fast and unique (62 ^ numberOfChars possible values) but
   * cryptographically-unsuitable random value
   * 
   * @return A random alphanumeric value of length <code>numberOfChars<code>
   */
  public static String next(int numberOfChars) {
    String value = RandomStringUtils.random(numberOfChars, 0, 0, true, true,
        null, random);
    return value;
  }
}

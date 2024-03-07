package org.projects.tradingservice.core;

public enum OrderType {
  /**
   * Order that gets executed at current market price
   */
  MARKET,

  /**
   * Order that gets executed with a price provided as part of the order
   */
  LIMIT
}

package org.projects.tradingservice.core;

public class MarketOrder extends Order {

  public MarketOrder(String id, Stock stock, OrderSide orderSide, int quantity, long timestampInMillis) {
    super(id, stock, OrderType.MARKET, orderSide, quantity, timestampInMillis);
  }

}

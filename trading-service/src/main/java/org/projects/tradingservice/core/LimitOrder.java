package org.projects.tradingservice.core;

public class LimitOrder extends Order {
  private final float _limitPrice;
  public LimitOrder(String id, Stock stock, OrderSide orderSide, float limitPrice, int quantity, long timestampInMillis) {
    super(id, stock, OrderType.LIMIT, orderSide, quantity, timestampInMillis);
    _limitPrice = limitPrice;
  }

  public float getLimitPrice() {
    return _limitPrice;
  }

  @Override
  public String toString() {
    return "LimitOrder{" + "_limitPrice=" + _limitPrice + " , " + super.toString() +'}';
  }
}

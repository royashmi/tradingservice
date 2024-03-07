package org.projects.tradingservice.core;

public class Order {

  private final String _id;
  private final Stock _stock;
  private final OrderSide _orderSide;
  private final OrderType _orderType;
  private int _quantity;
  private final long _timestampInMillis;

  public Order(String id, Stock stock, OrderType orderType, OrderSide orderSide, int quantity, long timestampInMillis) {
    _id = id;
    _stock = stock;
    _orderSide = orderSide;
    _orderType = orderType;
    _quantity = quantity;
    _timestampInMillis = timestampInMillis;
  }

  public Stock getStock() {
    return _stock;
  }

  public OrderType getOrderType() {
    return _orderType;
  }

  public OrderSide getOrderSide() {
    return _orderSide;
  }

  public int getQuantity() {
    return _quantity;
  }

  public long getTimestampInMillis() {
    return _timestampInMillis;
  }

  public String getId() {
    return _id;
  }

  public void setQuantity(int quantity) {
    _quantity = quantity;
  }

  @Override
  public String toString() {
    return "Order{" +  "_id=" + _id + "_stock=" + _stock + ", _orderSide=" + _orderSide + ", _orderType=" + _orderType + ", _quantity="
        + _quantity + ", _timestampInMillis=" + _timestampInMillis + '}';
  }
}

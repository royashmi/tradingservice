package org.projects.tradingservice.core;

public class OrderExecutionStatus {
  private final String _orderId;
  private final OrderStatus _orderStatus;
  private final float _orderExecutionPrice;
  private final float _tradedQuantity;

  public OrderExecutionStatus(String orderId, OrderStatus orderStatus, float orderExecutionPrice, float tradedQuantity) {
    _orderId = orderId;
    _orderStatus = orderStatus;
    _orderExecutionPrice = orderExecutionPrice;
    _tradedQuantity = tradedQuantity;
  }

  public String getOrderId() {
    return _orderId;
  }

  public OrderStatus getOrderStatus() {
    return _orderStatus;
  }

  public float getOrderExecutionPrice() {
    return _orderExecutionPrice;
  }

  public float getTradedQuantity() {
    return _tradedQuantity;
  }
}

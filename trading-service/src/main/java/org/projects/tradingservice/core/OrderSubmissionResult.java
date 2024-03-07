package org.projects.tradingservice.core;

public class OrderSubmissionResult {
  private final String _orderId;
  private final OrderStatus _orderStatus;

  public OrderSubmissionResult(String orderId, OrderStatus orderStatus) {
    _orderId = orderId;
    _orderStatus = orderStatus;
  }
}

package org.projects.tradingservice.core;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;


public class OrderBook {
  private final Stock _stock;
  private final SortedSet<Order> _buyOrders;
  private final SortedSet<Order> _sellOrders;
  private final SortedSet<MarketOrder> _buyMarketOrders;
  private final SortedSet<MarketOrder> _sellMarketOrders;

  public OrderBook(Stock stock) {
    _stock = stock;
    _buyOrders = new TreeSet<>(new OrderComparator());
    _sellOrders = new TreeSet<>(new OrderComparator());
    _buyMarketOrders = new TreeSet<>(new MarketOrderComparator());
    _sellMarketOrders = new TreeSet<>(new MarketOrderComparator());
  }

  public Stock getStock() {
    return _stock;
  }

  public void addOrder(Order order) {
if (order.getOrderType() == OrderType.MARKET) {
      addMarketOrder((MarketOrder) order);
    } else {
      addLimitOrder(order);
    }
  }

  public void addMarketOrder(MarketOrder order) {
    if (order.getOrderSide() == OrderSide.BUY) {
      _buyMarketOrders.add(order);
    } else {
      _sellMarketOrders.add(order);
    }
  }

  public void addLimitOrder(Order order) {
    if (order.getOrderSide() == OrderSide.BUY) {
      _buyOrders.add(order);
    } else {
      _sellOrders.add(order);
    }
  }

  public OrderExecutionStatus executeOrder(Order order) {
    if (order.getOrderType() == OrderType.MARKET) {
      throw new UnsupportedOperationException("Market orders not supported for execution");
    }

    SortedSet<Order> ordersToMatch;
    if (order.getOrderSide() == OrderSide.BUY) {
      ordersToMatch = _sellOrders;
    } else {
      ordersToMatch = _buyOrders;
    }

    for (Order orderToMatch : ordersToMatch) {
      // Match incoming order with the top of the book which is orderToMatch

      // Is price statisfied for the orderToMatch?
      LimitOrder limitOrderToMatch = (LimitOrder) orderToMatch;
      boolean priceMatch = order.getOrderSide() == OrderSide.BUY ? ((LimitOrder)order).getLimitPrice() >= limitOrderToMatch.getLimitPrice()
          : ((LimitOrder) order).getLimitPrice() <= limitOrderToMatch.getLimitPrice();
      System.out.println("PriceMatch: " + priceMatch + " Order: " + order.getId() + " OrderToMatch: " + orderToMatch.getId());
      int filledQuantity = 0;
      if (priceMatch) {
        System.out.println("Inside PriceMatch");
        int quantity = Math.min(order.getQuantity(), orderToMatch.getQuantity());
        filledQuantity = quantity;
        orderToMatch.setQuantity(orderToMatch.getQuantity() - quantity);
        order.setQuantity(order.getQuantity() - quantity);
        if (orderToMatch.getQuantity() == 0) {
          ordersToMatch.remove(orderToMatch);
        }
        OrderStatus orderStatus = order.getQuantity() == 0 ? OrderStatus.FILLED : OrderStatus.PART_FILLED;
        if (quantity != 0) {
          System.out.println("TRADE EXECUTION: Order " + order.getId() + " matched with " + orderToMatch.getId() +
              " for quantity " + quantity + " at price " + limitOrderToMatch.getLimitPrice());
        }
        return new OrderExecutionStatus(order.getId(), orderStatus, ((LimitOrder) orderToMatch).getLimitPrice(), filledQuantity);
      }
    }
    return new OrderExecutionStatus(order.getId(), OrderStatus.OPEN, 0, 0);
  }

  public void printOrderBook() {
    System.out.println("Buy Limit Orders:");
    for (Order order : _buyOrders) {
      System.out.println(order);
    }
    System.out.println("Sell Limit Orders:");
    for (Order order : _sellOrders) {
      System.out.println(order);
    }
    System.out.println("Book TOP");
    System.out.println(_buyOrders.first());
    System.out.println(_sellOrders.first());
  }

  public void printMarketOrderBook() {
    System.out.println("Buy Market Orders:");
    for (Order order : _buyMarketOrders) {
      System.out.println(order);
    }
    System.out.println("Sell Market Orders:");
    for (Order order : _sellMarketOrders) {
      System.out.println(order);
    }
    System.out.println("Book TOP");
    System.out.println(_buyMarketOrders.first());
    System.out.println(_sellMarketOrders.first());
  }

  static class OrderComparator implements Comparator<Order> {
    @Override
    public int compare(Order order1, Order order2) {
      // If both orders are Limit orders
      LimitOrder limitOrder1 = (LimitOrder) order1;
      LimitOrder limitOrder2 = (LimitOrder) order2;

      // Compare based on price
      int priceComparison = Double.compare(limitOrder1.getLimitPrice(), limitOrder2.getLimitPrice());
      if (priceComparison != 0) {
        // If prices are different, return the comparison result
        if (limitOrder1.getOrderSide() == OrderSide.BUY) {
          return -priceComparison; // Higher price first
        } else {
          return priceComparison; // Lower price first
        }
      } else {
        return Long.compare(limitOrder1.getTimestampInMillis(), limitOrder2.getTimestampInMillis());
      }
    }
  }

  static class MarketOrderComparator implements Comparator<MarketOrder> {
    @Override
    public int compare(MarketOrder order1, MarketOrder order2) {
      return Long.compare(order1.getTimestampInMillis(), order2.getTimestampInMillis());
    }
  }

}

package org.projects.tradingservice.core;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Exchange {
  private final List<Stock> _stocks;
  private final Map<String, OrderBook> _orderBooks;
  private final Map<String, OrderExecutionStatus> _orderExecutionStatuses;
  private ExecutorService _executor;
  private final LinkedList<Order> _orderQueue;
  private boolean _isShutDown = false;


  public Exchange(List<Stock> stocks) {
    _stocks = stocks;
    _orderBooks = new ConcurrentHashMap<>(stocks.size());
    _orderExecutionStatuses = new ConcurrentHashMap<>();
    _orderQueue = new LinkedList<>();
    initOrderBooks();
    initOrderExecutor();
  }

  public OrderSubmissionResult submitOrder(Order order) {
    OrderBook orderBook = _orderBooks.computeIfAbsent(order.getStock().getTicker(),
        stock -> new OrderBook(order.getStock()));
    orderBook.addOrder(order);
    _orderQueue.add(order);
    return new OrderSubmissionResult(order.getId(), OrderStatus.OPEN);
  }

  public void shutDown() {
    //TODO: Gracefully shutdown the executor
    //TODO: Drain the order queue & reject new orders
    _isShutDown = true;
    _executor.shutdown();
  }

  public OrderExecutionStatus getExecutionStatus(String orderId) {
    if (_orderExecutionStatuses.containsKey(orderId)) {
      return _orderExecutionStatuses.get(orderId);
    }
    throw new RuntimeException("Invalid order id");
  }

  private void initOrderBooks() {
    for (Stock stock : _stocks) {
      _orderBooks.put(stock.getTicker(), new OrderBook(stock));
    }
  }

  private void initOrderExecutor() {
    _executor = Executors.newSingleThreadExecutor();
    _executor.submit(new Executor());
  }

  class Executor implements Runnable {

    @Override
    public void run() {
      System.out.println("Initialize Order Executor..(single threaded)");
      do {
        try {
          //System.out.println("Processing next order from queue..");
          //System.out.println("Size: " + _orderQueue.size());
          Order order = _orderQueue.poll();
          if (order != null) {
            //System.out.println("Processing order " + order.getId() + " from queue..");
            OrderBook orderBook = _orderBooks.get(order.getStock().getTicker());
            OrderExecutionStatus status = orderBook.executeOrder(order);
            _orderExecutionStatuses.put(order.getId(), status);

            // Add the order back to the queue if it is not completely filled
            if (status.getOrderStatus() == OrderStatus.PART_FILLED || status.getOrderStatus() == OrderStatus.OPEN) {
              _orderQueue.add(order);
            }
          }
        } catch (Exception e) {
          System.out.println("Error processing order: " + e.getMessage());
        }
      } while (!_isShutDown);
      System.out.println("Exchange shut down.. Shut down order executor..");
    }
  }

}

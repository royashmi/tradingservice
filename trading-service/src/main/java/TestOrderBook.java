package org.projects.tradingservice.core;

public class TestOrderBook {

  public static void main(String[] args) {
    testOrderBookAddition();
  }

  public static void testOrderBookAddition() {
    Stock stock = new Stock("AAPL", 100);
    OrderBook orderBook = new OrderBook(stock);
    orderBook.addOrder(new LimitOrder("1", stock, OrderSide.BUY, 100, 15, 10));
    orderBook.addOrder(new LimitOrder("2", stock, OrderSide.BUY, 100, 20, 12));
    orderBook.addOrder(new LimitOrder("3", stock, OrderSide.BUY, 102, 11,13));
    orderBook.addOrder(new LimitOrder("4", stock, OrderSide.BUY, 102, 12, 9));

    orderBook.addOrder(new MarketOrder("5", stock, OrderSide.BUY, 102, 9));
    orderBook.addOrder(new MarketOrder("6", stock, OrderSide.BUY, 103, 10));

    orderBook.addOrder(new LimitOrder("7", stock, OrderSide.SELL, 104, 15, 10));
    orderBook.addOrder(new LimitOrder("8", stock, OrderSide.SELL, 104, 20, 11));
    orderBook.addOrder(new LimitOrder("9", stock, OrderSide.SELL, 105, 11,13));
    orderBook.addOrder(new LimitOrder("10", stock, OrderSide.SELL, 103, 12, 14));

    orderBook.addOrder(new MarketOrder("11", stock, OrderSide.SELL, 102, 9));
    orderBook.addOrder(new MarketOrder("12", stock, OrderSide.SELL, 103, 10));

    System.out.println("Order Book: ");
    orderBook.printOrderBook();

    System.out.println("\n\nMarket Order Book: ");
    orderBook.printMarketOrderBook();
  }
}

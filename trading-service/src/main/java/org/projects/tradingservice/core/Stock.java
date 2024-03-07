package org.projects.tradingservice.core;

public class Stock {
  private final String _ticker;

  private float price;

  public Stock(String ticker, float price) {
    _ticker = ticker;
    this.price = price;
  }

  public String getTicker() {
    return _ticker;
  }

  public float getPrice() {
    return price;
  }

  public void updatePrice(float price) {
    this.price = price;
  }
}

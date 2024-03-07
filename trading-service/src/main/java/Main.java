import java.util.ArrayList;
import java.util.List;
import org.projects.tradingservice.core.Exchange;
import org.projects.tradingservice.core.LimitOrder;
import org.projects.tradingservice.core.OrderSide;
import org.projects.tradingservice.core.Stock;


public class Main {
  public static void main(String[] args) {
    List<Stock> stocks = new ArrayList<>();
    Stock appleStock = new Stock("AAPL", 100.2f);
    stocks.add(appleStock);
    Exchange exchange = new Exchange(stocks);
    exchange.submitOrder(new LimitOrder("1", appleStock, OrderSide.SELL, 100.2f, 20, 11));
    exchange.submitOrder(new LimitOrder("2", appleStock, OrderSide.BUY, 100.3f, 5, 12));
    exchange.submitOrder(new LimitOrder("3", appleStock, OrderSide.BUY, 100.4f, 10, 13));
  }
}
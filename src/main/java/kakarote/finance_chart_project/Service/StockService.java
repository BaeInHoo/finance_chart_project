package kakarote.finance_chart_project.Service;

import jakarta.annotation.PostConstruct;
import kakarote.finance_chart_project.Entity.Stock;
import kakarote.finance_chart_project.Repo.StockRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {
    @Autowired
    private StockRepo stockRepository;

    @PostConstruct
    public void initStocks() {
        Stock stock1 = new Stock();
        stock1.setSymbol("AAPL");
        stock1.setName("Apple Inc.");
        stock1.setPrice(150.0);
        stockRepository.save(stock1);

        Stock stock2 = new Stock();
        stock2.setSymbol("GOOGL");
        stock2.setName("Alphabet Inc.");
        stock2.setPrice(2800.0);
        stockRepository.save(stock2);
    }

    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public Stock getStockBySymbol(String symbol) {
        return stockRepository.findBySymbol(symbol);
    }
}

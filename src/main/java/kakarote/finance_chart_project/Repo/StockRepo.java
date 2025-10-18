package kakarote.finance_chart_project.Repo;

import kakarote.finance_chart_project.Entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepo extends JpaRepository<Stock,Long> {
    Stock findBySymbol(String symbol);
}

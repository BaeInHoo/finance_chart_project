package kakarote.finance_chart_project.Service;

import kakarote.finance_chart_project.Entity.Portfolio;
import kakarote.finance_chart_project.Entity.Stock;
import kakarote.finance_chart_project.Entity.Member;
import kakarote.finance_chart_project.Repo.PortfolioRepo;
import kakarote.finance_chart_project.Repo.StockRepo;
import kakarote.finance_chart_project.Repo.MemberRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TradingService {
    @Autowired
    private MemberRepo memberRepository;
    @Autowired
    private StockRepo stockRepository;
    @Autowired
    private PortfolioRepo portfolioRepository;

    @Transactional
    public void buyStock(String username, String symbol, int quantity) {
        Member member = memberRepository.findByUsername(username);
        Stock stock = stockRepository.findBySymbol(symbol);

        if (member == null || stock == null) {
            throw new IllegalArgumentException("User or Stock not found");
        }

        double totalCost = stock.getPrice() * quantity;
        if (member.getBalance() < totalCost) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        member.setBalance(member.getBalance() - totalCost);
        memberRepository.save(member);

        Portfolio portfolio = portfolioRepository.findByMember(member).stream()
                .filter(p -> p.getStock().getSymbol().equals(symbol))
                .findFirst()
                .orElse(new Portfolio());
        portfolio.setMember(member);
        portfolio.setStock(stock);
        portfolio.setQuantity(portfolio.getQuantity() + quantity);
        portfolioRepository.save(portfolio);
    }

    @Transactional
    public void sellStock(String username, String symbol, int quantity) {
        Member member = memberRepository.findByUsername(username);
        Stock stock = stockRepository.findBySymbol(symbol);

        if (member == null || stock == null) {
            throw new IllegalArgumentException("User or Stock not found");
        }

        Portfolio portfolio = portfolioRepository.findByMember(member).stream()
                .filter(p -> p.getStock().getSymbol().equals(symbol))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Stock not in portfolio"));

        if (portfolio.getQuantity() < quantity) {
            throw new IllegalArgumentException("Insufficient stock quantity");
        }

        portfolio.setQuantity(portfolio.getQuantity() - quantity);
        member.setBalance(member.getBalance() + stock.getPrice() * quantity);

        if (portfolio.getQuantity() == 0) {
            portfolioRepository.delete(portfolio);
        } else {
            portfolioRepository.save(portfolio);
        }
        memberRepository.save(member);
    }

    public List<Portfolio> getPortfolio(String username) {
        Member member = memberRepository.findByUsername(username);
        return portfolioRepository.findByMember(member);
    }
}

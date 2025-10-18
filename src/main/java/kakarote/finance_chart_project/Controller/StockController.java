package kakarote.finance_chart_project.Controller;

import kakarote.finance_chart_project.Service.StockService;
import kakarote.finance_chart_project.Service.TradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StockController {
    @Autowired
    private StockService stockService;
    @Autowired
    private TradingService tradingService;

    @GetMapping("/stocks")
    public String listStocks(Model model) {
        model.addAttribute("stocks", stockService.getAllStocks());
        return "stocks";
    }

    @GetMapping("/portfolio")
    public String viewPortfolio(Model model, Authentication authentication) {
        String username = authentication.getName();
        model.addAttribute("portfolio", tradingService.getPortfolio(username));
        return "portfolio";
    }

    @PostMapping("/buy")
    public String buyStock(@RequestParam String symbol, @RequestParam int quantity, Authentication authentication) {
        tradingService.buyStock(authentication.getName(), symbol, quantity);
        return "redirect:/portfolio";
    }

    @PostMapping("/sell")
    public String sellStock(@RequestParam String symbol, @RequestParam int quantity, Authentication authentication) {
        tradingService.sellStock(authentication.getName(), symbol, quantity);
        return "redirect:/portfolio";
    }
}

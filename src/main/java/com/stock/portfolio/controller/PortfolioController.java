package com.stock.portfolio.controller;

import com.stock.portfolio.model.TradeRequest;
import com.stock.portfolio.model.UserPortfolio;
import com.stock.portfolio.service.PortfolioService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/stocks")
public class PortfolioController {

    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @GetMapping("/portfolio/{username}")
    public UserPortfolio getPortfolio(@PathVariable String username) {
        return portfolioService.getPortfolio(username);
    }

    @PostMapping("/buy/{username}")
    public UserPortfolio buyStock(@PathVariable String username, @RequestBody TradeRequest trade) {
        return portfolioService.buyStock(username, trade);
    }

    @PostMapping("/sell/{username}")
    public UserPortfolio sellStock(@PathVariable String username, @RequestBody TradeRequest trade) {
        return portfolioService.sellStock(username, trade);
    }
}

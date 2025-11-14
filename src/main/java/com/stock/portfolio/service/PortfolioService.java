package com.stock.portfolio.service;

import com.stock.portfolio.model.TradeRequest;
import com.stock.portfolio.model.UserPortfolio;
import com.stock.portfolio.entity.UserEntity;
import com.stock.portfolio.entity.HoldingEntity;
import com.stock.portfolio.repository.UserRepository;
import com.stock.portfolio.repository.HoldingRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service
public class PortfolioService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private HoldingRepository holdRepo;

    // Stock price map
    private Map<String, Double> stockPrices = Map.of(
            "Tata Motors", 709.60,
            "Axis", 1175.60,
            "Britania", 5549.50,
            "ICICI", 1429.00,
            "HCL Tech", 1641.50,
            "Jio Financial Services", 283.55,
            "Bharat Electronics", 384.80,
            "Nestle India", 2398.0
    );

    // ---------------------------
    // GET PORTFOLIO
    // ---------------------------
    public UserPortfolio getPortfolio(String username) {

        // Fetch or create user
        UserEntity user = userRepo.findById(username)
                .orElseGet(() -> userRepo.save(new UserEntity(username)));

        // Fetch holdings
        List<HoldingEntity> holdings = holdRepo.findByUsername(username);

        // Convert DB → response model
        UserPortfolio portfolio = new UserPortfolio(username);
        portfolio.setBalance(user.getBalance());

        holdings.forEach(h ->
                portfolio.getHoldings().put(h.getStock(), h.getQuantity())
        );

        return portfolio;
    }

    // ---------------------------
    // BUY STOCK
    // ---------------------------
    public UserPortfolio buyStock(String username, TradeRequest trade) {

        if (trade.getQuantity() <= 0)
            throw new RuntimeException("Please enter valid quantity");

        if (!stockPrices.containsKey(trade.getStock()))
            throw new RuntimeException("Stock not registered in NSE or BSE");

        UserPortfolio portfolio = getPortfolio(username);

        double price = stockPrices.get(trade.getStock());
        double cost = trade.getQuantity() * price;

        if (cost > portfolio.getBalance())
            throw new RuntimeException("Insufficient balance");

        // Update balance
        double newBalance = portfolio.getBalance() - cost;
        portfolio.setBalance(newBalance);

        // Update holdings in memory
        portfolio.getHoldings().merge(trade.getStock(), trade.getQuantity(), Integer::sum);

        // Update DB
        userRepo.save(new UserEntity(username, newBalance));

        HoldingEntity h = holdRepo.findByUsernameAndStock(username, trade.getStock())
                .orElse(new HoldingEntity(username, trade.getStock(), 0));
        h.setQuantity(portfolio.getHoldings().get(trade.getStock()));
        holdRepo.save(h);

        return portfolio;
    }

    // ---------------------------
    // SELL STOCK
    // ---------------------------
    public UserPortfolio sellStock(String username, TradeRequest trade) {

        if (trade.getQuantity() <= 0)
            throw new RuntimeException("Please enter valid quantity");

        UserPortfolio portfolio = getPortfolio(username);

        if (!portfolio.getHoldings().containsKey(trade.getStock()))
            throw new RuntimeException("You don't own this stock");

        int owned = portfolio.getHoldings().get(trade.getStock());

        if (owned < trade.getQuantity())
            throw new RuntimeException("Not enough quantity to sell");

        double price = stockPrices.get(trade.getStock());
        double revenue = price * trade.getQuantity();

        // Update balance
        double newBalance = portfolio.getBalance() + revenue;
        portfolio.setBalance(newBalance);

        // Update holdings
        int remaining = owned - trade.getQuantity();
        if (remaining == 0)
            portfolio.getHoldings().remove(trade.getStock());
        else
            portfolio.getHoldings().put(trade.getStock(), remaining);

        // Update DB balance
        userRepo.save(new UserEntity(username, newBalance));

        // Update or delete holding row
        HoldingEntity h = holdRepo.findByUsernameAndStock(username, trade.getStock())
                .orElseThrow(() -> new RuntimeException("DB Error: holding not found"));

        if (remaining == 0) {
            holdRepo.delete(h);
        } else {
            h.setQuantity(remaining);
            holdRepo.save(h);
        }

        return portfolio;
    }
}

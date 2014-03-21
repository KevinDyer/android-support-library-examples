
package com.l2a.craps.player;

import java.util.ArrayList;
import java.util.List;

import com.l2a.craps.Table;
import com.l2a.craps.bet.Bet;

abstract class BasePlayer implements Player {
    private int mAmount;

    protected BasePlayer(int amount) {
        mAmount = amount;
    }

    @Override
    public void addAmount(int amount) {
        mAmount += amount;
    }

    @Override
    public boolean takeAmount(int amount) {
        if (amount > mAmount) {
            return false;
        }

        mAmount -= amount;
        return true;
    }

    @Override
    public boolean canPlay(Table table) {
        return canAfford(table.getTableMinimum());
    }

    protected boolean canAfford(int amount) {
        return canAfford(new ArrayList<Bet>(), amount);
    }

    protected boolean canAfford(List<Bet> bets, int amount) {
        int pending = amount;
        for (Bet bet : bets) {
            pending += bet.getAmount();
        }
        return (pending <= getAmount());
    }

    protected int getAmount() {
        return mAmount;
    }
}

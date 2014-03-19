
package com.l2a.craps.player;

import java.util.ArrayList;
import java.util.List;

import com.l2a.craps.Table;
import com.l2a.craps.bet.Bet;
import com.l2a.craps.bet.PassLineBet;

public class PassLinePlayer implements Player {
    private int mAmount;
    private PassLineBet mPassLineBet;

    public PassLinePlayer(int amount) {
        mAmount = amount;
    }

    @Override
    public List<Bet> getBets(Table table) {
        List<Bet> bets = new ArrayList<Bet>();

        if (table.isPointEstablished()) {
            return bets;
        }

        if (hasPassLineBet()) {
            return bets;
        }

        int amount = table.getTableMinimum();
        if (!canAfford(bets, amount)) {
            return bets;
        }

        mPassLineBet = new PassLineBet(this, amount);
        bets.add(mPassLineBet);

        return bets;
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

    protected boolean canAfford(List<Bet> bets, int amount) {
        int pending = amount;
        for (Bet bet : bets) {
            pending += bet.getAmount();
        }
        return (pending <= getAmount());
    }

    private boolean hasPassLineBet() {
        if (null == mPassLineBet) {
            return false;
        } else if (mPassLineBet.hasLost()) {
            return false;
        }
        return true;
    }

    public boolean canPlay() {
        return hasPassLineBet() || getAmount() >= 5;
    }

    public boolean getHasBet() {
        return hasPassLineBet();
    }

    public int getAmount() {
        return mAmount;
    }
}

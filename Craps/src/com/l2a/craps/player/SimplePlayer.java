
package com.l2a.craps.player;

import java.util.ArrayList;
import java.util.List;

import com.l2a.craps.Table;
import com.l2a.craps.bet.Bet;
import com.l2a.craps.bet.PassLineBet;

public class SimplePlayer implements Player {
    private int mAmount = 100;

    private PassLineBet mPassLineBet;

    @Override
    public List<Bet> getBets(Table table) {
        List<Bet> bets = new ArrayList<Bet>();

        if (!hasPassLineBet() && !table.isPointEstablished()) {
            int tableMinimum = table.getTableMinimum();
            if (mAmount >= tableMinimum) {
                mPassLineBet = new PassLineBet(this, tableMinimum);
                bets.add(mPassLineBet);
            }
        }

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

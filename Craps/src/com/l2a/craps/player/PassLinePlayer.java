
package com.l2a.craps.player;

import java.util.ArrayList;
import java.util.List;

import com.l2a.craps.Table;
import com.l2a.craps.bet.Bet;
import com.l2a.craps.bet.PassLineBet;
import com.l2a.craps.dice.Roll;

public class PassLinePlayer extends BasePlayer {
    private PassLineBet mPassLineBet;

    public PassLinePlayer(int amount) {
        super(amount);
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
    public void adjustBets(Table table, Roll roll) {
    }

    @Override
    public boolean canPlay(Table table) {
        return super.canPlay(table) || hasPassLineBet();
    }

    private boolean hasPassLineBet() {
        if (null == mPassLineBet) {
            return false;
        } else if (mPassLineBet.hasLost()) {
            return false;
        }
        return true;
    }
}


package com.l2a.craps.player;

import java.util.ArrayList;
import java.util.List;

import com.l2a.craps.Table;
import com.l2a.craps.bet.Bet;
import com.l2a.craps.bet.PlaceBet;
import com.l2a.craps.bet.PlaceBet.Odds;
import com.l2a.craps.dice.Roll;

public abstract class PlaceBetPlayer extends BasePlayer {
    private List<PlaceBet> mPlaceBets = new ArrayList<PlaceBet>();

    public PlaceBetPlayer(int amount) {
        super(amount);
    }

    @Override
    public List<Bet> getBets(Table table) {
        clearLostBets();
        return new ArrayList<Bet>();
    }

    @Override
    public boolean canPlay(Table table) {
        boolean hasPlaceBet = false;

        for (PlaceBet placeBet : mPlaceBets) {
            hasPlaceBet = hasPlaceBet || hasPlaceBetOnNumber(placeBet.getNumber());
        }

        return super.canPlay(table) || hasPlaceBet;
    }

    @Override
    public void adjustBets(Table table, Roll roll) {
        if (table.isPointEstablished()) {
            int point = table.getPoint();
            for (PlaceBet placeBet : mPlaceBets) {
                if (placeBet.getNumber() == point) {
                    // Turn this bet off
                    placeBet.turnOff();
                } else {
                    placeBet.turnOn();
                }
            }
        } else {
            for (PlaceBet placeBet : mPlaceBets) {
                placeBet.turnOff();
            }
        }
    }

    protected void addPlaceBetOnNumber(List<Bet> bets, Table table, int number) {
        if (hasPlaceBetOnNumber(number)) {
            return;
        }

        Odds odds = PlaceBet.getOdds(number);
        int amount = odds.getAmount(table.getTableMinimum());

        if (!canAfford(bets, amount)) {
            return;
        }

        PlaceBet placeBet = new PlaceBet(this, amount, number);
        mPlaceBets.add(placeBet);
        bets.add(placeBet);
    }

    protected boolean hasPlaceBetOnNumber(int number) {
        for (PlaceBet placeBet : mPlaceBets) {
            if (number == placeBet.getNumber()) {
                return true;
            }
        }
        return false;
    }

    private void clearLostBets() {
        List<Bet> remove = new ArrayList<Bet>();
        for (Bet bet : mPlaceBets) {
            if (bet.hasLost()) {
                remove.add(bet);
            }
        }
        mPlaceBets.removeAll(remove);
    }
}

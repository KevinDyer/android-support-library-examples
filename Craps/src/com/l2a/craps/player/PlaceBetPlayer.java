
package com.l2a.craps.player;

import java.util.ArrayList;
import java.util.List;

import com.l2a.craps.Table;
import com.l2a.craps.bet.Bet;
import com.l2a.craps.bet.PlaceBet;
import com.l2a.craps.bet.PlaceBet.Odds;

public class PlaceBetPlayer extends PassLinePlayer {
    private List<PlaceBet> mPlaceBets = new ArrayList<PlaceBet>();

    public PlaceBetPlayer(int amount) {
        super(amount);
    }

    @Override
    public List<Bet> getBets(Table table) {
        List<Bet> bets = super.getBets(table);

        clearLostBets();

        // If point is on and no place bets
        if (!table.isPointEstablished()) {
            return bets;
        }

        addPlaceBetOnNumber(bets, table, 5);
        addPlaceBetOnNumber(bets, table, 6);
        addPlaceBetOnNumber(bets, table, 8);
        addPlaceBetOnNumber(bets, table, 9);

        return bets;
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

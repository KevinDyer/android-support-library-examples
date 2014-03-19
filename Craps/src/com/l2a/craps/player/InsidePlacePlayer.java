
package com.l2a.craps.player;

import java.util.ArrayList;
import java.util.List;

import com.l2a.craps.Table;
import com.l2a.craps.bet.Bet;

public class InsidePlacePlayer extends SimplePlayer {
    private List<Bet> mInsidePlace = new ArrayList<Bet>();

    @Override
    public List<Bet> getBets(Table table) {
        List<Bet> bets = super.getBets(table);

        // TODO If point is on and no place bets
        if (!table.isPointEstablished()) {
            return bets;
        }

        clearLostBets();
        if (hasPlaceBets()) {
            return bets;
        }

        // TODO place the inside bets
        int point = table.getPoint();
        int[] insideNumbers = {
                5, 6, 8, 9
        };
        for (int number : insideNumbers) {
            if (point == number) {
                
            }
        }

        return bets;
    }

    private boolean hasPlaceBets() {
        return !mInsidePlace.isEmpty();
    }

    private void clearLostBets() {
        List<Bet> remove = new ArrayList<Bet>();
        for (Bet bet : mInsidePlace) {
            if (bet.hasLost()) {
                remove.add(bet);
            }
        }
        mInsidePlace.removeAll(remove);
    }
}

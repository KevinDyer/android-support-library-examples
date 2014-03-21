
package com.l2a.craps.player;

import java.util.List;

import com.l2a.craps.Table;
import com.l2a.craps.bet.Bet;
import com.l2a.craps.dice.Roll;

public class InsidePlayer extends PlaceBetPlayer {

    public InsidePlayer(int amount) {
        super(amount);
    }

    @Override
    public List<Bet> getBets(Table table) {
        List<Bet> bets = super.getBets(table);

        if (!table.isPointEstablished()) {
            return bets;
        }

        addPlaceBetOnNumber(bets, table, 5);
        addPlaceBetOnNumber(bets, table, 6);
        addPlaceBetOnNumber(bets, table, 8);
        addPlaceBetOnNumber(bets, table, 9);

        return bets;
    }

    @Override
    public void adjustBets(Table table, Roll roll) {
    }

}

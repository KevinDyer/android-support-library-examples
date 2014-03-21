
package com.l2a.craps.player;

import java.util.List;

import com.l2a.craps.Table;
import com.l2a.craps.bet.Bet;
import com.l2a.craps.dice.Roll;

public interface Player {
    List<Bet> getBets(Table table);

    void addAmount(int amount);

    boolean takeAmount(int amount);

    boolean canPlay(Table table);

    void adjustBets(Table table, Roll roll);
}

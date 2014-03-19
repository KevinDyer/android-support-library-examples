
package com.l2a.craps.player;

import java.util.List;

import com.l2a.craps.Table;
import com.l2a.craps.bet.Bet;

public interface Player {
    List<Bet> getBets(Table table);

    void addAmount(int amount);

    boolean takeAmount(int amount);

    boolean canPlay();
}

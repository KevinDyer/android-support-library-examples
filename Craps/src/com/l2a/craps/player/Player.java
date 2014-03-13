
package com.l2a.craps.player;

import java.util.List;

import com.l2a.craps.Table;
import com.l2a.craps.bet.Bet;

public interface Player {
    public List<Bet> placeBets(Table table);

    public void win(Bet bet);

    public boolean takeAmount(int amount);

}

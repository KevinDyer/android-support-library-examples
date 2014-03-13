package com.l2a.craps.bet;

import com.l2a.craps.Table;
import com.l2a.craps.dice.Roll;

public interface Bet {
    int getAmount();
    void resolve(Table table, Roll roll);
    boolean hasLost();
}

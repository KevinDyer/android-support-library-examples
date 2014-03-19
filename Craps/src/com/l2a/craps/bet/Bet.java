
package com.l2a.craps.bet;

import com.l2a.craps.Table;
import com.l2a.craps.dice.Roll;
import com.l2a.craps.player.Player;

public interface Bet {

    void resolve(Table table, Roll roll);

    Player getPlayer();

    boolean hasLost();

    boolean hasWon();

    int getAmount();

    int getPayment();

}

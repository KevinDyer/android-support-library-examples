
package com.l2a.craps.bet;

import com.l2a.craps.Table;
import com.l2a.craps.dice.Roll;
import com.l2a.craps.player.Player;

public class PassLineBet extends BaseBet {

    public PassLineBet(Player player, int amount) {
        super(player, amount);
    }

    @Override
    public void resolve(Table table, Roll roll) {
        int value = roll.getValue();
        if (!table.isPointEstablished()) {
            if (7 == value || 11 == value) {
                won(getAmount());
            } else if (2 == value || 3 == value || 12 == value) {
                lost();
            }
        } else {
            int point = table.getPoint();
            if (value == point) {
                won(getAmount());
            } else {
                lost();
            }
        }
    }
}

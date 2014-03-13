
package com.l2a.craps.bet;

import com.l2a.craps.Table;
import com.l2a.craps.dice.Roll;
import com.l2a.craps.player.Player;

public class PassLineBet implements Bet {
    private final Player mPlayer;
    private int mAmount;
    private boolean mHasLost = false;

    @Override
    public int getAmount() {
        return mAmount;
    }

    public PassLineBet(Player player, int amount) {
        mPlayer = player;
        mAmount = amount;
    }

    @Override
    public void resolve(Table table, Roll roll) {
        int value = roll.getValue();
        if (!table.isPointEstablished()) {
            if (7 == value || 11 == value) {
                pay();
            } else if (2 == value || 3 == value || 12 == value) {
                mHasLost = true;
            }
        } else {
            int point = table.getPoint();
            if (value == point) {
                pay();
            } else {
                mHasLost = true;
            }
        }
    }

    @Override
    public boolean hasLost() {
        return mHasLost;
    }

    private void pay() {
        mPlayer.win(this);
    }
}

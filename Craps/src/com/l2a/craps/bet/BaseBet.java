
package com.l2a.craps.bet;

import com.l2a.craps.Table;
import com.l2a.craps.dice.Roll;
import com.l2a.craps.player.Player;

abstract class BaseBet implements Bet {
    private final Player mPlayer;
    private boolean mHasLost = false;
    private boolean mHasWon = false;
    private int mAmount;
    private int mPayment;

    public BaseBet(Player player, int amount) {
        mPlayer = player;
        mAmount = amount;
    }

    @Override
    public Player getPlayer() {
        return mPlayer;
    }

    @Override
    public void resolve(Table table, Roll roll) {
        clearResolveFlags();
    }

    @Override
    public boolean hasLost() {
        return mHasLost;
    }

    @Override
    public boolean hasWon() {
        return mHasWon;
    }

    @Override
    public int getAmount() {
        return mAmount;
    }

    @Override
    public int getPayment() {
        return mPayment;
    }

    private void clearResolveFlags() {
        mHasLost = false;
        mHasWon = false;
        mPayment = 0;
    }

    protected void lost() {
        mHasLost = true;
    }

    protected void won(int payment) {
        mHasWon = true;
        mPayment = payment;
    }
}

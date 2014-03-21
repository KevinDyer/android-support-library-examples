
package com.l2a.craps.bet;

import com.l2a.craps.Table;
import com.l2a.craps.dice.Roll;
import com.l2a.craps.player.Player;

public class PlaceBet extends BaseBet {
    private final int mNumber;
    private boolean mIsOn = true;

    public PlaceBet(Player player, int amount, int number) {
        super(player, amount);
        mNumber = number;
    }

    @Override
    public void resolve(Table table, Roll roll) {
        super.resolve(table, roll);

        if (!isOn()) {
            return;
        }

        int value = roll.getValue();
        if (mNumber == value) {
            Odds odds = getOdds(mNumber);
            int payment = odds.getPayment(getAmount());
            won(payment);
        } else if (7 == value) {
            lost();
        }
    }

    private boolean isOn() {
        return mIsOn;
    }

    public int getNumber() {
        return mNumber;
    }

    public void turnOn() {
        mIsOn = true;
    }

    public void turnOff() {
        mIsOn = false;
    }

    public static Odds getOdds(int number) {
        switch (number) {
        case 4:
        case 10:
            return new Odds(5, 9);
        case 5:
        case 9:
            return new Odds(5, 7);
        case 6:
        case 8:
            return new Odds(6, 7);
        }
        return null;
    }

    public static class Odds {
        private final int mIn;
        private final int mOut;

        public Odds(int in, int out) {
            mIn = in;
            mOut = out;
        }

        public int getPayment(int amount) {
            return (amount * mOut) / mIn;
        }

        public int getAmount(int minimum) {
            // TODO Auto-generated method stub
            return 0;
        }
    }

}

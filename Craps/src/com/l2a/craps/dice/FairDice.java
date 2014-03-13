
package com.l2a.craps.dice;

import java.util.Random;

public class FairDice implements Dice {
    private final Random mRandom = new Random();

    @Override
    public Roll roll() {
        return new FairDiceRoll(mRandom);
    }

    private class FairDiceRoll implements Roll {
        private final int mOne;
        private final int mTwo;

        public FairDiceRoll(Random r) {
            mOne = r.nextInt(6) + 1;
            mTwo = r.nextInt(6) + 1;
        }

        @Override
        public int getValue() {
            return mOne + mTwo;
        }
    }
}

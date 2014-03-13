
package com.l2a.craps;

import java.util.ArrayList;
import java.util.List;

import com.l2a.craps.bet.Bet;
import com.l2a.craps.dice.Dice;
import com.l2a.craps.dice.Roll;
import com.l2a.craps.player.Player;

public class Table {
    private static final int BET_TABLE_MINIMUM = 5;

    private List<Player> mPlayers;
    private List<Bet> mBets;
    private Dice mDice;

    private int mPoint = -1;

    public void playRound() {
        // Players place bets
        for (Player player : mPlayers) {
            List<Bet> bets = player.placeBets(this);
            for (Bet bet : bets) {
                placePlayerBet(player, bet);
            }
        }

        // Shooter rolls
        Roll roll = mDice.roll();

        // Resolve bets
        List<Bet> remove = new ArrayList<Bet>();
        for (Bet bet : mBets) {
            bet.resolve(this, roll);
            if (bet.hasLost()) {
                remove.add(bet);
            }
        }
        mBets.removeAll(remove);

        // Resolve table state
        resolvePoint(roll);

        // TODO Allow players to adjust?
    }

    private void placePlayerBet(Player player, Bet bet) {
        int amount = bet.getAmount();
        if (player.takeAmount(amount)) {
            mBets.add(bet);
        }
    }

    private void resolvePoint(Roll roll) {
        int value = roll.getValue();

        // Seven-out
        if (isPointEstablished()) {
            if (7 == value || getPoint() == value) {
                mPoint = -1;
            }
        } else {
            if (isPointNumber(value)) {
                mPoint = value;
            }
        }
    }

    private boolean isPointNumber(int value) {
        Integer[] pointNumbers = {
                4, 5, 6, 8, 9, 10,
        };
        return contains(pointNumbers, value);
    }

    public boolean isPointEstablished() {
        return (-1 != mPoint);
    }

    public int getPoint() {
        return mPoint;
    }

    private static <T> boolean contains(T[] items, T value) {
        for (T item : items) {
            if (value.equals(item)) {
                return true;
            }
        }
        return false;
    }

    public int getTableMinimum() {
        return BET_TABLE_MINIMUM;
    }

    public boolean addPlayer(Player player) {
        return mPlayers.add(player);
    }
}

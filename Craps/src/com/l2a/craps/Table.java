
package com.l2a.craps;

import java.util.ArrayList;
import java.util.List;

import com.l2a.craps.bet.Bet;
import com.l2a.craps.dice.Dice;
import com.l2a.craps.dice.FairDice;
import com.l2a.craps.dice.Roll;
import com.l2a.craps.player.Player;

public class Table {
    private static final int BET_TABLE_MINIMUM = 5;

    private List<Player> mPlayers = new ArrayList<Player>();
    private List<Bet> mBets = new ArrayList<Bet>();
    private Dice mDice = new FairDice();

    private static final int OFF = -1;
    private int mPoint = OFF;

    public void playRound() {
        // Place bets
        placeBets();

        // Roll the dice
        Roll roll = mDice.roll();

        // Resolve bets
        resolveBets(roll);

        // Resolve table
        resolveTable(roll);

        // TODO Adjust bets
    }

    private void placeBets() {
        for (Player player : mPlayers) {
            // Get bets from player
            List<Bet> bets = player.getBets(this);

            // Place bets
            mBets.addAll(bets);
        }
    }

    private void resolveBets(Roll roll) {
        List<Bet> lostBets = new ArrayList<Bet>();

        for (Bet bet : mBets) {
            // Resolve the bet
            bet.resolve(this, roll);

            // Check to see if it has lost
            if (bet.hasLost()) {
                lostBets.add(bet);
            } else if (bet.hasWon()) {
                Player player = bet.getPlayer();
                int payment = bet.getPayment();
                player.addAmount(payment);
            } else {
                // Do nothing
            }
        }

        // Remove lost bets
        mBets.removeAll(lostBets);
    }

    private void resolveTable(Roll roll) {
        final int value = roll.getValue();

        // Resolve point
        if (isPointEstablished()) {
            if (7 == value) {
                mPoint = OFF;
            }
        } else {
            if (isPointNumber(value)) {
                mPoint = value;
            }
        }
    }

    public boolean isPointEstablished() {
        return (OFF != mPoint);
    }

    public int getPoint() {
        return mPoint;
    }

    public int getTableMinimum() {
        return BET_TABLE_MINIMUM;
    }

    public boolean addPlayer(Player player) {
        return mPlayers.add(player);
    }

    public boolean canPlay() {
        for (Player player : mPlayers) {
            if (player.canPlay()) {
                return true;
            }
        }
        return false;
    }

    private static int[] POINT_NUMBERS = {
            4, 5, 6, 8, 9, 10,
    };

    public static boolean isPointNumber(int value) {
        for (int pointNumber : POINT_NUMBERS) {
            if (pointNumber == value) {
                return true;
            }
        }
        return false;
    }
}

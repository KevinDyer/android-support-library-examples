
package com.l2a.craps;

import com.l2a.craps.player.InsidePlayer;
import com.l2a.craps.player.PassLinePlayer;

public class Game extends Thread {
    @Override
    public void run() {
        // Create Table
        Table table = new Table();

        // Create Players
        PassLinePlayer passLinePlayer = new PassLinePlayer(100);
        InsidePlayer insidePlayer = new InsidePlayer(100);

        // Add players to table
        table.addPlayer(passLinePlayer);
        table.addPlayer(insidePlayer);

        // While table can play, play a round
        while (table.canPlay()) {
            table.playRound();
        }

        // TODO Report
    }
}

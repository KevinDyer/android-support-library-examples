
package com.l2a.craps;

import com.l2a.craps.player.SimplePlayer;

public class Game extends Thread {
    @Override
    public void run() {
        // Create Table
        Table table = new Table();
        
        // Create Players
        SimplePlayer simplePlayer = new SimplePlayer();
        
        // Add players to table
        table.addPlayer(simplePlayer);
        
        // While table can play, play a round
        while(table.canPlay()) {
            table.playRound();
        }
        
        // TODO Report
    }
}

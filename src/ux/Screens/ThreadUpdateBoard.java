package ux.Screens;

import network.Client;
import network.messages.GameRequest;

class ThreadUpdateBoard implements Runnable {
    Boolean running = true;
    Boolean suspended = false;
    private ScrGame scrGame = null;

    ThreadUpdateBoard(ScrGame scrGame) {
        this.scrGame = scrGame;
    }

    @Override
    public void run() {
        //Send request for board and update the board every 2 seconds
        while (running) {
            if (!suspended) {
                try {
                    System.out.println("Board Refreshed on board -" + this.scrGame.game.name);
                    Client.client.send(new GameRequest(scrGame.game.name), (p) -> scrGame.networkGame(p));
                    Thread.sleep(5000);
                } catch (Exception e) {
                    if (e instanceof InterruptedException) {
                        running = false;
                        break;
                    } else {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}

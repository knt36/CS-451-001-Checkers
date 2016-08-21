package ux.Screens;

import network.Client;
import network.messages.GameRequest;

public class ThreadUpdateBoard implements Runnable {
    protected ScrGame scrGame = null;
    protected boolean running = true;

    public ThreadUpdateBoard(ScrGame scrGame) {
        // TODO Auto-generated constructor stub
        this.scrGame = scrGame;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        //Send request for board and update the board every 2 seconds
        while (running) {
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

package ux.Screens;

import network.Client;
import network.messages.GameListRequest;

class ThreadRefreshGameList implements Runnable {
    private ScrMainMenu scr = null;

    ThreadRefreshGameList(ScrMainMenu scr) {
        this.scr = scr;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("refreshed game list");
            Client.client.send(new GameListRequest(Client.client.getUsername()), (p) -> scr.networkGameListRefresh(p));
            try {
                Thread.sleep(10000);

            } catch (Exception e) {
                System.out.println("Sleep failed");
            }
        }
    }

}

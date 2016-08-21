package ux.Screens;

import network.Client;
import network.messages.Ack;

class ThreadHeartBeat implements Runnable {
    Boolean running = true;
    private ScrLogin scr = null;

    ThreadHeartBeat(ScrLogin scr) {
        this.scr = scr;
    }

    @Override
    public void run() {
        while (running) {
            Client.client.send(new Ack("", true), (p) -> scr.networkHB(p));

            try {
                Thread.sleep(10000);

            } catch (Exception e) {
                System.out.println("Sleep failed");
            }
        }
    }

}

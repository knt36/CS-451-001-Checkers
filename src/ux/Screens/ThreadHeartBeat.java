package ux.Screens;

import network.Client;
import network.messages.Ack;
import network.messages.GameListRequest;

public class ThreadHeartBeat implements Runnable {
    protected boolean running = true;
    ScrLogin scr  = null;
    public ThreadHeartBeat(ScrLogin scr) {
        this.scr = scr;
    }
    @Override
    public void run() {
        while(true){
            Client.client.send(new Ack("",true), (p) -> scr.networkHB(p));

            try{
                Thread.sleep(10000);

            }catch(Exception e){
                System.out.println("Sleep failed");
            }
        }
    }

}

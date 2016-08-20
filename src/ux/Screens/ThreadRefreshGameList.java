package ux.Screens;

import game.GameList;
import network.Client;
import network.messages.Ack;
import network.messages.GameListRequest;
import network.messages.Message;
import network.messages.Packet;

public class ThreadRefreshGameList implements Runnable {

	ScrMainMenu scr  = null;
	public ThreadRefreshGameList(ScrMainMenu scr) {
		this.scr = scr;
	}
	@Override
	public void run() {
		while(true){
			System.out.println("refreshed game list");
			Client.client.send(new GameListRequest(Client.client.getUsername()), (p)->scr.networkGameListRefresh(p));
			try{
				Thread.sleep(10000);
				
			}catch(Exception e){
				System.out.println("Sleep failed");
			}
		}
	}

}

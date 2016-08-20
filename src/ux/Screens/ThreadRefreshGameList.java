package ux.Screens;

import game.GameList;
import network.Client;
import network.messages.Ack;
import network.messages.GameListRequest;
import network.messages.Packet;

public class ThreadRefreshGameList implements Runnable {

	ScrMainMenu scr  = null;
	public ThreadRefreshGameList(ScrMainMenu scr) {
		// TODO Auto-generated constructor stub
		this.scr = scr;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			System.out.println("refreshed game list");
			Client.client.send(new GameListRequest(Client.client.getUsername()), (p)->networkRefreshGameList(p));
			try{
				Thread.sleep(10000);
				
			}catch(Exception e){
				System.out.println("Sleep failed");
			}
		}
	}
	
	public void networkRefreshGameList(Packet p){
		Ack a = (Ack)p.getData();
		GameList g = (GameList)p.getData();
		if(!a.getSuccess()){
			FrameNotify fn = new FrameNotify();
			fn.add(new ScrDisconnect());
		}else {
			scr.gameList = g;
			scr.refreshGameList();
		}
		
	}

}

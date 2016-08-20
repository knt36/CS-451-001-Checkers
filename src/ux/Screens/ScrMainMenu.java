package ux.Screens;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Scrollable;

import game.Game;
import game.GameList;
import ux.Buttons.OptionButton;
import ux.Labels.BulletGameLabel;
import ux.Labels.HeaderLabel;

public class ScrMainMenu extends ScrFactory {
	protected GameList gameList = null;
	protected HeaderLabel curGameLabel = new HeaderLabel(STRINGS.CURGAMELABEL);
	//Bullets
	ScrFactory curGameArea = new ScrFactory();
	JScrollPane curGameScroll = new JScrollPane(curGameArea);


	protected HeaderLabel pubGameLabel = new HeaderLabel(STRINGS.PUBGAMELABEL);
	//Bullets
	ScrFactory pubGameArea = new ScrFactory();
	JScrollPane pubGameScroll = new JScrollPane(pubGameArea);

	protected OptionButton newGameBt = new OptionButton(STYLE.GREEN, STRINGS.NEW_GAMEBUT);
	protected OptionButton helpBt = new OptionButton(STYLE.GREEN, STRINGS.HELPBUT);
	protected OptionButton quitBt = new OptionButton(Color.red, STRINGS.QUITBUT);

	public ScrMainMenu() {
		// TODO Auto-generated constructor stub
		this.add(leftPanel());
		this.constr.gridx++;
		this.add(rightPanel());

		//Adding Button listners
		quitBt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//Exits out of program entirely
				System.exit(0);
			}
		});
		helpBt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				FrameNotify fn = new FrameNotify();
				//Not sure what goes into here since we agreed it was going to be html page
				fn.add(new JLabel("Help- not sure what is in here since we said html"));
			}
		});
		newGameBt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				FrameCreateGame fcg = new FrameCreateGame();
				fcg.add(new ScrCreateGame());
			}
		});
		
		//Start refreshing game thread
		Runnable rt = new ThreadRefreshGameList(this);
		Thread th = new Thread(rt);
		//th.start();
	}

	public ScrFactory leftPanel() {
		ScrFactory left = new ScrFactory();
		left.constr.fill = left.constr.NONE;
		left.add(newGameBt);
		left.constr.gridy++;
		left.add(helpBt);
		left.constr.gridy++;
		left.add(quitBt);
		return (left);
	}

	public ScrFactory rightPanel() {
		ScrFactory right = new ScrFactory();
		right.constr.fill = right.constr.HORIZONTAL;
		right.constr.weighty = 0;
		right.add(curGameLabel);
		right.constr.gridy++;

		//Bullets
		right.constr.fill = right.constr.BOTH;
		this.curGameArea.constr.fill = curGameArea.constr.HORIZONTAL;
		right.constr.weighty = 1;
		this.curGameScroll.setMinimumSize(new Dimension(0, 300));
		right.add(this.curGameScroll);


		right.constr.gridy++;
		right.constr.fill = right.constr.HORIZONTAL;
		right.constr.weighty = 0;
		right.add(pubGameLabel);
		right.constr.gridy++;

		//Bullets
		right.constr.fill = right.constr.BOTH;
		this.pubGameArea.constr.fill = pubGameArea.constr.HORIZONTAL;
		right.constr.weighty = 1;
		this.pubGameScroll.setMinimumSize(new Dimension(0, 300));
		right.add(this.pubGameScroll);
		
		//Add the bullets
		refreshGameList();

		return (right);
	}
	
	public void refreshGameList(){
		this.curGameArea.removeAll();
		this.pubGameArea.removeAll();
		if(this.gameList == null){
			return;
			// don't do anything since it did not return anything
		}
		this.curGameArea.constr.fill = curGameArea.constr.HORIZONTAL;
		this.curGameScroll.setMinimumSize(new Dimension(0, 300));
		for (Game g : this.gameList.current) {
			BulletGameLabel lb = new BulletGameLabel(g.name);
			lb.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					//Start the selected game
					FrameGame fg = new FrameGame();
					fg.add(new ScrGame(g));
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
		
			this.curGameArea.add(lb);
			this.curGameArea.constr.gridy++;
		}
		
		this.pubGameArea.constr.fill = pubGameArea.constr.HORIZONTAL;
		this.pubGameScroll.setMinimumSize(new Dimension(0, 300));
		for (Game g : this.gameList.pub) {
			BulletGameLabel lb = new BulletGameLabel(g.name);
			lb.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					//Start the selected game
					FrameGame fg = new FrameGame();
					fg.add(new ScrGame(g));
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
		
			this.pubGameArea.add(lb);
			this.pubGameArea.constr.gridy++;
		}
		revalidate();
		repaint();
	}

}
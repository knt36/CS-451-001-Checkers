package ux.Screens;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import ux.Buttons.GuiBoard;
import ux.Buttons.OptionButton;
import ux.Labels.HeaderLabel;
import ux.Labels.NoteLabel;

public class ScrGame extends ScrFactory{
	protected GuiBoard board = new GuiBoard();
	protected OptionButton helpBt = new OptionButton(STYLE.GREEN,STRINGS.HELPBUT);
	protected OptionButton quitBt= new OptionButton(STYLE.GREEN,STRINGS.QUITBUT);
	protected OptionButton endBt = new OptionButton(Color.red,STRINGS.ENDGAME);
	
	protected NoteLabel playerTurn = new NoteLabel("Turn: Player 1");
	
	public ScrGame() {
		// TODO Auto-generated constructor stub
		this.constr.gridheight=4;
		this.add(this.board);
		this.constr.gridheight=1;
		this.constr.gridx++;
		this.constr.fill=this.constr.NONE;
		this.add(this.playerTurn);
		this.constr.gridy++;
		this.add(helpBt);
		this.constr.gridy++;
		this.add(quitBt);
		this.constr.gridy++;
		this.add(endBt);
		this.constr.gridy++;
		endBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			    System.out.println("end button pressed");
			}
		});
		helpBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			    System.out.println("help button pressed");
			}
		});
		quitBt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
                System.out.println("quit button pressed");
                System.exit(0);
			}
		});
	}
}

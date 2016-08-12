package ux.Buttons;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

import javax.swing.BorderFactory;

import com.sun.javafx.css.Stylesheet;

import ux.Labels.NoteLabel;
import ux.Screens.STYLE;
import ux.Screens.Timing;
import game.Disk;
import javafx.scene.shape.Circle;

public class BoardButton extends ButtonFactory {
	protected int index = -1;
	protected Color c = null;
	protected boolean k = false;
	public BoardButton() {
		super(STYLE.BOARDCOLOR, "");
		// TODO Auto-generated constructor stub
		this.setBorder(BorderFactory.createLineBorder(STYLE.BOARDBORDERLINECOLOR, STYLE.BOARDBORDERSTHICK, true));
	}	
	
	//The checker peice that is on the board
	public BoardButton(Color c, boolean king){
		super(STYLE.BOARDCOLOR, "");
		// TODO Auto-generated constructor stub
		this.setBorder(BorderFactory.createLineBorder(STYLE.BOARDBORDERLINECOLOR, STYLE.BOARDBORDERSTHICK, true));
		this.c = c;
		this.k = king;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		   Graphics2D g2d = (Graphics2D)g;
		   // Assume x, y, and diameter are instance variables.
		   //Set for border
		   int x = STYLE.SIZEDISC;
		   int y = STYLE.SIZEDISC;
		   if(c != null){
			   g.setColor(Color.black);
			   Ellipse2D.Double circle1 = new Ellipse2D.Double((0-x/2)+ this.getSize().getWidth()/2,(0-y/2)+this.getSize().getHeight()/2,x,y);
			   g2d.fill(circle1);
			   g.setColor(c);
			   x = x - 5;
			   y = y - 5;
			   Ellipse2D.Double circle2 = new Ellipse2D.Double((0-x/2)+ this.getSize().getWidth()/2,(0-y/2)+this.getSize().getHeight()/2,x,y);		   //g2d.fill(circle2);
			   g2d.fill(circle2);
			   
			   //King or no King
			   if(this.k == true){
				   x = x - 10;
				   y = y - 10;
				   g.setColor(new Color(250, 216, 27));
				   Ellipse2D.Double crown = new Ellipse2D.Double((0-x/2)+ this.getSize().getWidth()/2,(0-y/2)+this.getSize().getHeight()/2,x,y);		   //g2d.fill(circle2);
				   g2d.fill(crown);
				   x = x - 7;
				   y = y - 7;
				   g.setColor(this.c);
				   Ellipse2D.Double crown2 = new Ellipse2D.Double((0-x/2)+ this.getSize().getWidth()/2,(0-y/2)+this.getSize().getHeight()/2,x,y);		   //g2d.fill(circle2);
				   g2d.fill(crown2);
			   }
		   }
	}
}

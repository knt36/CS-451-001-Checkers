package ux.Labels;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import ux.Screens.STRINGS;
import ux.Screens.STYLE;

public class BulletLabel extends LabelFactory{

	public BulletLabel(String label) {
		super(STRINGS.BULLETCHAR + label);
		// TODO Auto-generated constructor stub
		this.setFont(STYLE.BULLETLABEL_FONT);
		this.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				setForeground(STYLE.BULLETAFTCLICKCLR);
				revalidate();
				repaint();
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				setForeground(STYLE.BULLETBFRCLICKCLR);
				revalidate();
				repaint();
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
	
	}
}

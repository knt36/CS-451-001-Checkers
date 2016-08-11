package Screens;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ScrFactory extends JPanel {
	protected GridBagConstraints constr = new GridBagConstraints();
	public ScrFactory() {
		// TODO Auto-generated constructor stub
		this.constr.weightx=1;
		this.constr.weighty=1;
		this.constr.gridwidth=1;
		this.constr.gridheight=1;
		this.constr.gridx=0;
		this.constr.gridy=0;
		this.constr.fill=this.constr.BOTH;
		this.setBackground(STYLE.BACKGROUND);
		this.setBorder(BorderFactory.createLineBorder(STYLE.PANELBORDERCOLOR, STYLE.PANELBORDERSIZE, true));
		this.setLayout(new GridBagLayout());
	}
	
	@Override
	public Component add(Component comp) {
		// TODO Auto-generated method stub
		super.add(comp,this.constr);
		this.validate();
		this.repaint();
		return(comp);
	}
}

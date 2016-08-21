package ux.Screens;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ScrFactory extends JPanel {
	protected FrameMain frame = null;
	protected Map<String, Object> intent = new HashMap<>();
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
		this.revalidate();
		this.repaint();
		return(comp);
	}
}

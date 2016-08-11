package TextField;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

import Screens.STYLE;

public class TextFieldFactory extends JTextField{
	public TextFieldFactory() {
		// TODO Auto-generated constructor stub
		this.setCaretColor(Color.black);
		this.setHorizontalAlignment(JTextField.CENTER);
		this.setPreferredSize(new Dimension(50, 50));
		this.setBorder(BorderFactory.createLineBorder(STYLE.TXTFIELDBRDCOLOR, 5, true));
		this.setFont(STYLE.TEXTFIELDFONT);
	}
}

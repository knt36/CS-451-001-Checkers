package ux.Screens;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT;

public class STYLE {
	public static final Border DEFAULTBORDER = BorderFactory.createLineBorder(STYLE.PANELBORDERCOLOR, STYLE.PANELBORDERSIZE, true);

	
	public static final Color BACKGROUND = new Color(154, 213, 243);
	public static final Color OPTIONBORDER = Color.black;

	public static final int OPTIONBORDERSIZE = 4;
	public static final Color OPTIONTEXTCOLOR = Color.white;
	public static final Font OPTBUTFONT = new Font("Showcard Gothic",Font.BOLD, 30);
	public static final Color GREEN = new Color(0, 205, 29);
	
	public static final Color PANELBORDERCOLOR = Color.black;
	public static final int PANELBORDERSIZE = 4;
	
	public static final Font TITLEFONT = new Font("Showcard Gothic",Font.BOLD, 60);
	public static final Color TITLECOLOR = Color.white;
	
	public static final Color TXTFIELDBRDCOLOR = Color.GRAY;
	public static final Font TEXTFIELDFONT = new Font("Showcard Gothic",Font.BOLD, 20);
	public static final Color HINTCOLOR = new Color(222, 222, 222);
	public static final Color TEXTFIELDCOLOR = Color.BLACK;
	public static final Font HEADERLABEL_FONT = new Font("Showcard Gothic",Font.BOLD, 20);
	public static final Border HEADERBORDER = DEFAULTBORDER;


	public static final Font BULLETLABEL_FONT = new Font("Showcard Gothic",Font.PLAIN, 15);


	public static final Color BULLETAFTCLICKCLR = Color.black;


	public static final Color BULLETBFRCLICKCLR = Color.yellow;


	public static final Font NOTELABELFONT = new Font("Showcard Gothic",Font.BOLD, 20);


	public static final Color BOARDCOLOR = STYLE.GREEN;


	public static final Color BOARDBORDERLINECOLOR = Color.black;


	public static final int BOARDBORDERSTHICK = 5;


	public static final int BOARDFRAMEBORDERTK = 10;


	public static final Color BOARD_BACKGROUND_CLR = Color.white;


	public static final Color BOARDSELECTEDCLR = Color.yellow;
	
	public static final int SIZEDISC = 40;
	
	
}

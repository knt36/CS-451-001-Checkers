package ux.Buttons;

import ux.Screens.STYLE;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

class BoardButton extends ButtonFactory {
    int index = -1;
    private Color color = null;
    private boolean king = false;

    BoardButton() {
        super(STYLE.BOARDCOLOR, "");
        this.setBorder(BorderFactory.createLineBorder(STYLE.BOARDBORDERLINECOLOR, STYLE.BOARDBORDERSTHICK, true));
    }

    // The checker piece that is on the board
    BoardButton(Color color, boolean king) {
        super(STYLE.BOARDCOLOR, "");
        this.setBorder(BorderFactory.createLineBorder(STYLE.BOARDBORDERLINECOLOR, STYLE.BOARDBORDERSTHICK, true));
        this.color = color;
        this.king = king;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // Assume x, y, and diameter are instance variables.
        //Set for border
        int x = (int) (this.getSize().width * 0.9);
        int y = (int) (this.getSize().height * 0.9);
        //Get the biggest circle without ruining the circle
        if (x > y) {
            x = y;
        } else {
            y = x;
        }
        if (color != null) {
            g.setColor(Color.black);
            Ellipse2D.Double circle1 = new Ellipse2D.Double((0 - x / 2) + this.getSize().getWidth() / 2, (0 - y / 2) + this.getSize().getHeight() / 2, x, y);
            g2d.fill(circle1);
            g.setColor(color);
            x = x - 5;
            y = y - 5;
            Ellipse2D.Double circle2 = new Ellipse2D.Double((0 - x / 2) + this.getSize().getWidth() / 2, (0 - y / 2) + this.getSize().getHeight() / 2, x, y);           //g2d.fill(circle2);
            g2d.fill(circle2);

            //King or no King
            if (this.king) {
                x = x - 10;
                y = y - 10;
                g.setColor(new Color(250, 216, 27));
                Ellipse2D.Double crown = new Ellipse2D.Double((0 - x / 2) + this.getSize().getWidth() / 2, (0 - y / 2) + this.getSize().getHeight() / 2, x, y);           //g2d.fill(circle2);
                g2d.fill(crown);
                x = x - 7;
                y = y - 7;
                g.setColor(this.color);
                Ellipse2D.Double crown2 = new Ellipse2D.Double((0 - x / 2) + this.getSize().getWidth() / 2, (0 - y / 2) + this.getSize().getHeight() / 2, x, y);           //g2d.fill(circle2);
                g2d.fill(crown2);
            }
        }
    }
}

package ux.Labels;

import ux.Screens.STYLE;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class GroupBulletLabel {
    ArrayList<BulletLabel> bullets = new ArrayList<>();

    public GroupBulletLabel() {
    }

    public void add(BulletLabel bt) {
        bullets.add(bt);
        bt.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                setReleaseAll();
                bt.pressed = true;
                bt.setForeground(STYLE.BULLETBFRCLICKCLR);
                bt.revalidate();
                bt.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseClicked(MouseEvent e) {

            }

            public void setReleaseAll() {
                for (int i = 0; i < bullets.size(); i++) {
                    bullets.get(i).pressed = false;
                    bullets.get(i).setForeground(STYLE.BULLETAFTCLICKCLR);
                    bullets.get(i).revalidate();
                    bullets.get(i).repaint();
                    //System.out.println("Changing all bullets");
                }
            }
        });
    }

    public void releaseAllButton() {
        for (BulletLabel bt : this.bullets) {
            bt.pressed = false;
            bt.setForeground(STYLE.BULLETAFTCLICKCLR);
            bt.revalidate();
            bt.repaint();
        }
    }
}

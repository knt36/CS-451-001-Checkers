package ux.Labels;

import ux.Screens.STYLE;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class GroupBulletLabel {
    private ArrayList<BulletLabel> bullets = new ArrayList<>();

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
                for (BulletLabel bullet : bullets) {
                    bullet.pressed = false;
                    bullet.setForeground(STYLE.BULLETAFTCLICKCLR);
                    bullet.revalidate();
                    bullet.repaint();
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

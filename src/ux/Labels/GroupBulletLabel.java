package ux.Labels;

import ux.Screens.STYLE;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class GroupBulletLabel {
    ArrayList<BulletLabel> bullets = new ArrayList<>();

    public GroupBulletLabel() {
        // TODO Auto-generated constructor stub
    }

    public void add(BulletLabel bt) {
        bullets.add(bt);
        bt.addMouseListener(new MouseListener() {

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub
                setReleaseAll();
                bt.pressed = true;
                bt.setForeground(STYLE.BULLETBFRCLICKCLR);
                bt.revalidate();
                bt.repaint();
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

package ux.Utilities;

import ux.Labels.NoteLabel;

/**
 *
 */
public class Timing implements Runnable {

    public Boolean running;
    private NoteLabel l = null;

    public Timing(NoteLabel l) {
        this.l = l;
    }

    public void run() {
        String sec = "";
        String min = "";
        String hr = "";
        int seconds = 0;
        int hours = 0;
        int minutes = 0;
        try {
            while (running) {
                Thread.sleep(1000);
                if (seconds < 59) {
                    seconds++;
                } else if (seconds >= 59) {
                    seconds = seconds - 59;
                    minutes = minutes + 1;
                }

                if (minutes == 59 && seconds == 59) {
                    minutes = 0;
                    seconds = 0;
                    hours = hours + 1;
                }


                //make time 2 digits
                if (hours < 10) {
                    hr = "0" + Integer.toString(hours);
                } else {
                    hr = Integer.toString(hours);
                }
                if (minutes < 10) {
                    min = "0" + Integer.toString(minutes);
                } else {
                    min = Integer.toString(minutes);
                }
                if (seconds < 10) {
                    sec = "0" + Integer.toString(seconds);
                } else {
                    sec = Integer.toString(seconds);
                }

                l.setText(hr + ":" + min + ":" + sec);
                l.validate();
                l.repaint();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

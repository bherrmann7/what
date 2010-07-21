

import java.awt.FlowLayout
import java.awt.BorderLayout
import java.awt.event.ActionListener
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class MyEventQueue extends EventQueue {

    long lastActivity = System.currentTimeMillis()

    def what;

    MyEventQueue(def what) {

        Toolkit.getDefaultToolkit().getSystemEventQueue().push(this); 

        this.what = what;
        new Thread({
            while (true) {
                Thread.sleep(1000);
                int IdleSeconds = (System.currentTimeMillis() - lastActivity) / 1000;
                //println "Idle: "+IdleSeconds;

                // After 10 minutes, give up.
                if (IdleSeconds > 10 * 60) {
                    System.exit(1);
                }
            }
        } as Runnable).start();
    }

    void dispatchEvent(AWTEvent event) {
        if (event instanceof MouseEvent || event instanceof KeyEvent) {
            lastActivity = System.currentTimeMillis()
        }
        if (event instanceof KeyEvent && event.keyCode == 83 && event.getKeyModifiersText(event.modifiers) == "Ctrl") {
            what(true)
        }
        if (event instanceof KeyEvent && event.keyCode == 27) {
            what(false)
        }
        super.dispatchEvent(event);
    }
}

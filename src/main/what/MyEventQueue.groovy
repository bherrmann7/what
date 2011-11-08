package what

import java.awt.AWTEvent
import java.awt.EventQueue
import java.awt.Toolkit
import java.awt.event.KeyEvent
import java.awt.event.MouseEvent

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
            what(false)
        }
        if (event instanceof KeyEvent && event.keyCode == 27) {
            what(true)
        }
        super.dispatchEvent(event);
    }
}

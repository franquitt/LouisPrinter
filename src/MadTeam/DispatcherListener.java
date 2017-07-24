package MadTeam;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;

/**
 *
 * @author FrancoMain
 */
class DispatcherListener implements KeyEventDispatcher {

    private final MainWindow main;

    public DispatcherListener(MainWindow main) {
        this.main = main;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        if (e.getID() == KeyEvent.KEY_RELEASED) {
            if (e.isControlDown()) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_T://TRANSLATE
                        main.translate();
                        break;
                    case KeyEvent.VK_P: //PRINT
                        main.connectPrinter(true);
                        break;
                    case KeyEvent.VK_O://OPEN FILE
                        main.openFile();
                        break;
                    case KeyEvent.VK_L://Connect Printer
                        main.connectPrinter(false);
                        break;
                    default:
                        break;
                }
            }
        }
        return false;
    }
}

package com.revivius.nb.darcula;

import java.awt.Component;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicToolBarUI;

/**
 * A ToolBarUI that installs a ChangeListener on buttons to enable rollover for
 * JButtons and JToggleButtons.
 *
 * @author Revivius
 */
public class RolloverToolBarUI extends BasicToolBarUI {

    private static final String LISTENER_KEY = "ToolbarUI.ListenerKey";

    private static final ChangeListener LISTENER = new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
            AbstractButton b = (AbstractButton) e.getSource();
            boolean rollover = b.getModel().isRollover();

            b.setContentAreaFilled(rollover || b.getModel().isSelected());
            b.setBorderPainted(rollover);
        }
    };

    public static ComponentUI createUI(JComponent c) {
        return new RolloverToolBarUI();
    }

    @Override
    protected void setBorderToNonRollover(Component c) {
        if (c instanceof AbstractButton) {
            AbstractButton b = (AbstractButton) c;
            configureButton(b);
        }
    }

    @Override
    protected void setBorderToRollover(Component c) {
        if (c instanceof AbstractButton) {
            AbstractButton b = (AbstractButton) c;
            configureButton(b);
        }
    }

    @Override
    protected void setBorderToNormal(Component c) {
        if (c instanceof AbstractButton) {
            AbstractButton b = (AbstractButton) c;

            b.setBorderPainted(true);
            b.setContentAreaFilled(true);
            b.setRolloverEnabled(false);
            uninstallListener(b);
        }
    }

    private void configureButton(AbstractButton b) {
        b.setBorderPainted(false);
        b.setContentAreaFilled(false);
        b.setRolloverEnabled(true);
        installListener(b);
    }

    private void installListener(AbstractButton b) {
        Object o = b.getClientProperty(LISTENER_KEY);
        if (o == null) {
            b.addChangeListener(LISTENER);
            LISTENER.stateChanged(new ChangeEvent(b));
        }
    }

    private void uninstallListener(AbstractButton b) {
        Object o = b.getClientProperty(LISTENER_KEY);
        if (o != null) {
            b.addChangeListener(LISTENER);
        }
    }

}

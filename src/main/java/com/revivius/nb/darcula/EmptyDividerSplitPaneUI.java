package com.revivius.nb.darcula;

import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
 
/**
 * Prevents rendering of ugly dot matrix on divider.
 * @author Revivius
 */
public class EmptyDividerSplitPaneUI extends BasicSplitPaneUI {
    
    /**
     * Creates a new BasicSplitPaneUI instance
     */
    public static ComponentUI createUI(JComponent x) {
        return new EmptyDividerSplitPaneUI();
    }

    @Override
    public BasicSplitPaneDivider createDefaultDivider() {
        return new BasicSplitPaneDivider(this) {
            @Override
            public void paint(Graphics g) {
                //super.paint(g);
            }
        };
    }
    
}

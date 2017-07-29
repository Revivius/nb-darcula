/*
 * Copyright 2017 Revivius.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.revivius.nb.darcula.ui;

import com.bulenkov.darcula.ui.DarculaTreeUI;
import com.revivius.nb.darcula.options.DarculaLAFOptionsPanelController;
import com.revivius.nb.darcula.options.DarculaLAFPanel;
import java.util.prefs.Preferences;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import org.openide.util.NbPreferences;

/**
 * Overriden to workarround isSkinny() method in DarculaTreeUI. 
 * @author Revivius
 */
public class IndentAwareTreeUI extends DarculaTreeUI {

    private static final Preferences PREFS = NbPreferences.forModule(DarculaLAFPanel.class);
    
    public static ComponentUI createUI(JComponent c) {
        return new IndentAwareTreeUI();
    }

    @Override
    protected int getRowX(int row, int depth) {
        if (PREFS.getBoolean(DarculaLAFOptionsPanelController.OVERRIDE_TREE_INDENT_BOOLEAN, false)) {
            return totalChildIndent * (depth + depthOffset);
        }

        return super.getRowX(row, depth);
    }

}

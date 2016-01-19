package com.revivius.nb.darcula;

import com.bulenkov.darcula.DarculaLaf;
import java.util.prefs.Preferences;
import javax.swing.UIManager;
import org.openide.modules.ModuleInstall;
import org.openide.util.NbPreferences;

/**
 * Makes Darcula LAF available in preferred LAF combo and installs
 * DarculaLFCustoms.
 *
 * @author Revivius
 */
public class Installer extends ModuleInstall {

    @Override
    public void validate() throws IllegalStateException {
        // to make LAF available in Tools > Options > Appearance > Look and Feel
        UIManager.installLookAndFeel(new UIManager.LookAndFeelInfo("Darcula for NetBeans",
                DarculaLaf.class.getName()));

        Preferences prefs = NbPreferences.root().node("laf"); //NOI18N
        if (!prefs.getBoolean("darcula.installed", false)) { //NOI18N
            prefs.put("laf", DarculaLaf.class.getName()); //NOI18N
        }
        prefs.putBoolean("darcula.installed", true); //NOI18N

        UIManager.put("Nb.DarculaLFCustoms", new DarculaLFCustoms());
    }

}

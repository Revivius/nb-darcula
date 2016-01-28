package com.revivius.nb.darcula;

import com.bulenkov.darcula.DarculaTableHeaderBorder;
import java.awt.Component;
import java.awt.Insets;

/**
 * Increases table header insets.
 *
 * @author Revivius
 */
public class InreasedInsetsTableHeaderBorder extends DarculaTableHeaderBorder {

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(2, 2, 2, 2);
    }
}

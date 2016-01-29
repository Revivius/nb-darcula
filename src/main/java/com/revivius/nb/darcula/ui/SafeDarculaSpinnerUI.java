package com.revivius.nb.darcula.ui;

import com.bulenkov.iconloader.util.DoubleColor;
import com.bulenkov.iconloader.util.GraphicsConfig;
import com.bulenkov.iconloader.util.GraphicsUtil;
import com.bulenkov.iconloader.util.Gray;
import com.bulenkov.iconloader.util.UIUtil;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicSpinnerUI;

/**
 * ------------------------------------------------------------
 * Copy paste from original DarculaSpinnerUI to resolve #36:
 * https://github.com/Revivius/nb-darcula/issues/36
 * ------------------------------------------------------------
 *
 * @author Konstantin Bulenkov
 * @author Revivius
 */
public class SafeDarculaSpinnerUI extends BasicSpinnerUI {

    private FocusAdapter myFocusListener = new FocusAdapter() {
    @Override
    public void focusGained(FocusEvent e) {
      spinner.repaint();
    }

    @Override
    public void focusLost(FocusEvent e) {
      spinner.repaint();
    }
  };

  @SuppressWarnings({"MethodOverridesStaticMethodOfSuperclass", "UnusedDeclaration"})
  public static ComponentUI createUI(JComponent c) {
    return new SafeDarculaSpinnerUI();
  }

  @Override
  protected void replaceEditor(JComponent oldEditor, JComponent newEditor) {
    super.replaceEditor(oldEditor, newEditor);
    // #36
    if (oldEditor != null && oldEditor.getComponents().length != 0) {
      oldEditor.getComponents()[0].removeFocusListener(myFocusListener);
    }
    // #36
    if (newEditor != null && newEditor.getComponents().length != 0) {
      newEditor.getComponents()[0].addFocusListener(myFocusListener);
    }
  }

  @Override
  protected JComponent createEditor() {
    final JComponent editor = super.createEditor();
    editor.getComponents()[0].addFocusListener(myFocusListener);
    return editor;
  }

  @Override
  public void paint(Graphics g, JComponent c) {
    super.paint(g, c);
    final Border border = spinner.getBorder();
    if (border != null) {
      border.paintBorder(c, g, 0, 0, spinner.getWidth(), spinner.getHeight());
    }
  }

  @Override
  protected Component createPreviousButton() {
    JButton button = createArrow(SwingConstants.SOUTH);
    button.setName("Spinner.previousButton");
    button.setBorder(new EmptyBorder(1, 1, 1, 1));
    installPreviousButtonListeners(button);
    return button;
  }

  @Override
  protected Component createNextButton() {
    JButton button = createArrow(SwingConstants.NORTH);
    button.setName("Spinner.nextButton");
    button.setBorder(new EmptyBorder(1, 1, 1, 1));
    installNextButtonListeners(button);
    return button;
  }


  @Override
  protected LayoutManager createLayout() {
    return new LayoutManagerDelegate(super.createLayout()) {
      @Override
      public void layoutContainer(Container parent) {
        super.layoutContainer(parent);
        final JComponent editor = spinner.getEditor();
        if (editor != null) {
          final Rectangle bounds = editor.getBounds();
          editor.setBounds(bounds.x, bounds.y, bounds.width - 6, bounds.height);
        }
      }
    };
  }

  private JButton createArrow(int direction) {
    final Color shadow = UIUtil.getPanelBackground();
    final Color enabledColor = new DoubleColor(Gray._255, UIUtil.getLabelForeground());
    final Color disabledColor = new DoubleColor(Gray._200, UIUtil.getLabelForeground().darker());
    JButton b = new BasicArrowButton(direction, shadow, shadow, enabledColor, shadow) {
      @Override
      public void paint(Graphics g) {
        int y = direction == NORTH ? getHeight() - 6 : 2;
        paintTriangle(g, (getWidth() - 8)/2 - 1, y, 0, direction, SafeDarculaSpinnerUI.this.spinner.isEnabled());
      }

      @Override
      public boolean isOpaque() {
        return false;
      }

      @Override
      public void paintTriangle(Graphics g, int x, int y, int size, int direction, boolean isEnabled) {
        final GraphicsConfig config = GraphicsUtil.setupAAPainting(g);
        int mid;
        final int w = 8;
        final int h = 6;
        mid = w  / 2;

        g.setColor(isEnabled ? enabledColor : disabledColor);

        g.translate(x, y);
        switch (direction) {
          case SOUTH:
            g.fillPolygon(new int[]{0, w, mid}, new int[]{1, 1, h}, 3);
            break;
          case NORTH:
            g.fillPolygon(new int[]{0, w, mid}, new int[]{h - 1, h - 1, 0}, 3);
            break;
          case WEST:
          case EAST:
        }
        g.translate(-x, -y);
        config.restore();
      }
    };
    Border buttonBorder = UIManager.getBorder("Spinner.arrowButtonBorder");
    if (buttonBorder instanceof UIResource) {
      // Wrap the border to avoid having the UIResource be replaced by
      // the ButtonUI. This is the opposite of using BorderUIResource.
      b.setBorder(new CompoundBorder(buttonBorder, null));
    }
    else {
      b.setBorder(buttonBorder);
    }
    b.setInheritsPopupMenu(true);
    return b;
  }

  static class LayoutManagerDelegate implements LayoutManager {
    protected final LayoutManager myDelegate;

    LayoutManagerDelegate(LayoutManager delegate) {
      myDelegate = delegate;
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {
      myDelegate.addLayoutComponent(name, comp);
    }

    @Override
    public void removeLayoutComponent(Component comp) {
      myDelegate.removeLayoutComponent(comp);
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
      return myDelegate.preferredLayoutSize(parent);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
      return myDelegate.minimumLayoutSize(parent);
    }

    @Override
    public void layoutContainer(Container parent) {
      myDelegate.layoutContainer(parent);
    }
  }

}

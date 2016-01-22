package com.revivius.nb.darcula;

import com.revivius.nb.darcula.options.DarculaLAFPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.ColorUIResource;
import org.netbeans.swing.plaf.LFCustoms;
import org.openide.util.ImageUtilities;
import org.openide.util.Lookup;
import org.openide.util.NbPreferences;

/**
 * LFCustoms for Darcula LAF.
 *
 * @author Revivius
 */
public class DarculaLFCustoms extends LFCustoms {

    public static final String defaultFontName = "Dialog";
    public static final int defaultFontSize = 12;
    public static final String defaultFont = defaultFontName + " " + defaultFontSize;
    private static final String TAB_FOCUS_FILL_UPPER = "tab_focus_fill_upper"; //NOI18N
    private static final String TAB_FOCUS_FILL_LOWER = "tab_focus_fill_lower"; //NOI18N

    private static final String TAB_UNSEL_FILL_UPPER = "tab_unsel_fill_upper"; //NOI18N
    private static final String TAB_UNSEL_FILL_LOWER = "tab_unsel_fill_lower"; //NOI18N

    private static final String TAB_SEL_FILL = "tab_sel_fill"; //NOI18N

    private static final String TAB_MOUSE_OVER_FILL_UPPER = "tab_mouse_over_fill_upper"; //NOI18N
    private static final String TAB_MOUSE_OVER_FILL_LOWER = "tab_mouse_over_fill_lower"; //NOI18N

    private static final String TAB_ATTENTION_FILL_UPPER = "tab_attention_fill_upper"; //NOI18N
    private static final String TAB_ATTENTION_FILL_LOWER = "tab_attention_fill_lower"; //NOI18N

    private static final String TAB_BORDER = "tab_border"; //NOI18N      
    private static final String TAB_SEL_BORDER = "tab_sel_border"; //NOI18N
    private static final String TAB_BORDER_INNER = "tab_border_inner"; //NOI18N      

    static String CFS = CUSTOM_FONT_SIZE;

    @Override
    public Object[] createGuaranteedKeysAndValues() {
        // same color for DarculaMetalTheme getAcceleratorForeground()
        Color asfg = new ColorUIResource(187, 187, 187);
        
        Object[] result = {
            "controlShadow", new ColorUIResource(41, 43, 45),
            "controlHighlight", new ColorUIResource(70, 72, 74),
            "controlDkShadow", new ColorUIResource(41, 43, 45),
            "controlLtHighlight", new ColorUIResource(70, 72, 74),
            
            "Menu.acceleratorSelectionForeground", asfg,
            "MenuItem.acceleratorSelectionForeground", asfg,
            "CheckBoxMenuItem.acceleratorSelectionForeground", asfg,
            "RadioButtonMenuItem.acceleratorSelectionForeground", asfg
        };

        return result;
    }
    
    @Override
    public Object[] createLookAndFeelCustomizationKeysAndValues() {
 
        // stretch view tabs
        System.setProperty("winsys.stretching_view_tabs", "true");
        // stretching view tabs seems to be causing resize problems
        System.setProperty("NB.WinSys.Splitter.Respect.MinimumSize.Enabled", "false");

        Font controlFont = Font.decode(defaultFont);
        Integer in = (Integer) UIManager.get(CUSTOM_FONT_SIZE); //NOI18N
        if (in != null) {
            controlFont = Font.decode(defaultFontName + " " + in);
        }

        boolean overrideFontOption = NbPreferences.forModule(DarculaLAFPanel.class).getBoolean("overrideFont", false);
        if (overrideFontOption) {
            String fontOption = NbPreferences.forModule(DarculaLAFPanel.class).get("font", defaultFont);
            controlFont = Font.decode(fontOption);
        }
        /**
         * HtmlLabelUI sets the border color to BLUE for focused cells if
         * "Tree.selectionBorderColor" is same as background color (see lines
         * 230 - 247). Here we modify "Tree.selectionBorderColor" slightly.
         * Modification is not noticable to naked eye but enough to stop
         * HtmlLabelUI to set focused renderer border color to BLUE.
         */
        Color c = UIManager.getColor("Tree.selectionBackground");
        Color focusColor = new Color(c.getRed(), c.getGreen(), c.getBlue() + 1);

        //XXX fetch the custom font size here instead
        Object[] result = {
            //The assorted standard NetBeans metal font customizations
            CONTROLFONT, controlFont,
            SYSTEMFONT, controlFont,
            USERFONT, controlFont,
            MENUFONT, controlFont,
            WINDOWTITLEFONT, controlFont,
            LISTFONT, controlFont,
            TREEFONT, controlFont,
            PANELFONT, controlFont,
            SUBFONT, controlFont.deriveFont(Font.PLAIN, Math.min(controlFont.getSize() - 1, 6)),
            // #61395        
            SPINNERFONT, controlFont,
            
            // Bug in JDK 1.5 thru b59 - pale blue is incorrectly returned for this
            "textInactiveText", Color.GRAY, //NOI18N
            // Work around a bug in windows which sets the text area font to
            //"MonoSpaced", causing all accessible dialogs to have monospaced text
            "TextArea.font", new GuaranteedValue("Label.font", controlFont),
            
            /**
             * Use calculate border color for HtmlLabelUI.
             */
            "Tree.selectionBorderColor", focusColor,
            /**
             * HtmlLabelUI uses UIManager.getColor("text") to find background
             * color for unselected items. Make sure the background color used
             * by HtmlLabelUI is same with the LAF.
             */
            "text", new Color(60, 63, 65),
            "textText", new Color(187, 187, 187),
            "infoText", new Color(187, 187, 187),
            "OptionPane.messageForeground", new Color(187, 187, 187),
            
            "LabelUI", "com.revivius.nb.darcula.OptionsAwareLabelUI",
            "ButtonUI", "com.revivius.nb.darcula.ContentAreaAwareButtonUI",
            "Button.border", new ReducedInsetsDarculaButtonPainter(),
            "ToggleButtonUI", "com.revivius.nb.darcula.ContentAreaAwareToggleButtonUI",
            "ToggleButton.border", new ReducedInsetsDarculaButtonPainter(),
            
            "ToolBarUI", "com.revivius.nb.darcula.RolloverToolBarUI",
            "SplitPaneUI", "com.revivius.nb.darcula.EmptyDividerSplitPaneUI",
            
            "Table.background", new Color(69, 73, 74),
            "TableHeader.cellBorder", new InreasedInsetsTableHeaderBorder(),
            "Table.ascendingSortIcon", new ImageIcon(DarculaLFCustoms.class.getResource("column-asc.png")),
            "Table.descendingSortIcon", new ImageIcon(DarculaLFCustoms.class.getResource("column-desc.png")),
            
            "TitledBorder.border", BorderFactory.createLineBorder(new Color(41, 43, 45), 1),
            
            "MenuItem.acceleratorForeground", new Color(238, 238, 238),
            "CheckBoxMenuItem.acceleratorForeground", new Color(238, 238, 238),
            "RadioButtonMenuItem.acceleratorForeground", new Color(238, 238, 238),
            
            "List.background", new Color(69, 73, 74),
            
            "Tree.background", new Color(69, 73, 74),
            "Tree.closedIcon", new ImageIcon(DarculaLFCustoms.class.getResource("open.png")),
            "Tree.openIcon", new ImageIcon(DarculaLFCustoms.class.getResource("open.png")),
            
            // FileChooser icons
            "FileChooser.newFolderIcon", new ImageIcon(DarculaLFCustoms.class.getResource("newFolder.png")),
            "FileChooser.upFolderIcon", new ImageIcon(DarculaLFCustoms.class.getResource("upFolder.png")),
            "FileChooser.homeFolderIcon", new ImageIcon(DarculaLFCustoms.class.getResource("homeFolder.png")),
            "FileChooser.detailsViewIcon", new ImageIcon(DarculaLFCustoms.class.getResource("detailsView.png")),
            "FileChooser.listViewIcon", new ImageIcon(DarculaLFCustoms.class.getResource("listView.png")),
            "FileView.directoryIcon", new ImageIcon(DarculaLFCustoms.class.getResource("closed.png")),
            "FileView.fileIcon", new ImageIcon(DarculaLFCustoms.class.getResource("file.png")),
            "FileChooser.computerIcon", new ImageIcon(DarculaLFCustoms.class.getResource("computer.png")),
            "FileChooser.hardDriveIcon", new ImageIcon(DarculaLFCustoms.class.getResource("hardDrive.png")),
            "FileChooser.floppyDriveIcon", new ImageIcon(DarculaLFCustoms.class.getResource("floppyDrive.png")),
            
            // Keys taken from
            // http://alvinalexander.com/java/java-swing-uimanager-defaults
            // https://gist.github.com/itzg/5938035
            // http://thebadprogrammer.com/swing-uimanager-keys/
            "Button.font", controlFont,
            "CheckBox.font", controlFont,
            "CheckBoxMenuItem.acceleratorFont", controlFont,
            "CheckBoxMenuItem.font", controlFont,
            "ColorChooser.font", controlFont,
            "ComboBox.font", controlFont,
            "EditorPane.font", controlFont,
            "FormattedTextField.font", controlFont,
            "IconButton.font", controlFont,
            "InternalFrame.optionDialogTitleFont", controlFont,
            "InternalFrame.paletteTitleFont", controlFont,
            "InternalFrame.titleFont", controlFont,
            "Label.font", controlFont,
            "List.font", controlFont,
            "Menu.acceleratorFont", controlFont,
            "Menu.font", controlFont,
            "MenuBar.font", controlFont,
            "MenuItem.acceleratorFont", controlFont,
            "MenuItem.font", controlFont,
            "OptionPane.buttonFont", controlFont,
            "OptionPane.font", controlFont,
            "OptionPane.messageFont", controlFont,
            "Panel.font", controlFont,
            "PasswordField.font", controlFont,
            "PopupMenu.font", controlFont,
            "ProgressBar.font", controlFont,
            "RadioButton.font", controlFont,
            "RadioButtonMenuItem.acceleratorFont", controlFont,
            "RadioButtonMenuItem.font", controlFont,
            "ScrollPane.font", controlFont,
            "Slider.font", controlFont,
            "Spinner.font", controlFont,
            "TabbedPane.font", controlFont,
            //for what?
            //"TabbedPane.smallFont", controlFont,
            "Table.font", controlFont,
            "TableHeader.font", controlFont,
            "TextArea.font", controlFont,
            "TextField.font", controlFont,
            "TextPane.font", controlFont,
            "TitledBorder.font", controlFont,
            "ToggleButton.font", controlFont,
            "ToolBar.font", controlFont,
            "ToolTip.font", controlFont,
            "Tree.font", controlFont,
            "Viewport.font", controlFont,
            
        };

        replaceSearchNotFoundColor();
        replaceGlyphGutterLineColor();
        
        replaceLFCustomsTextFgColors();
        
        return result;
    }

    @Override
    public Object[] createApplicationSpecificKeysAndValues() {
        UIBootstrapValue editorTabsUI = new Windows8EditorColorings(
                "org.netbeans.swing.tabcontrol.plaf.Windows8EditorTabDisplayerUI");
        Object viewTabsUI = editorTabsUI.createShared(
                "org.netbeans.swing.tabcontrol.plaf.Windows8ViewTabDisplayerUI");
        Object propertySheetValues = new Windows8PropertySheetColorings();

        Object[] result = {
            // enable _dark postfix for resource loading
            "nb.dark.theme", Boolean.TRUE,
            "nb.wizard.hideimage", Boolean.TRUE,
            
            // main toolbar
            "Nb.MainWindow.Toolbar.Dragger", "com.revivius.nb.darcula.ToolbarXP",
            "Nb.MainWindow.Toolbar.Border", BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(41, 43, 45)),
            "Nb.ToolBar.border", BorderFactory.createEmptyBorder(),
            
            EDITOR_TAB_DISPLAYER_UI, editorTabsUI,
            VIEW_TAB_DISPLAYER_UI, viewTabsUI,
            SLIDING_BUTTON_UI, "org.netbeans.swing.tabcontrol.plaf.WinXPSlidingButtonUI",
            PROPERTYSHEET_BOOTSTRAP, propertySheetValues,
            
            SCROLLPANE_BORDER, BorderFactory.createLineBorder(new Color(41, 43, 45)),
            SCROLLPANE_BORDER_COLOR, new Color(41, 43, 45),
            
            EDITOR_TOOLBAR_BORDER, BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(41, 43, 45)),
            EDITOR_ERRORSTRIPE_SCROLLBAR_INSETS, new Insets(16, 0, 16, 0),
            
            DESKTOP_BACKGROUND, Color.RED,
            DESKTOP_BORDER, BorderFactory.createEmptyBorder(),
            WORKPLACE_FILL, Color.RED,
            
            DESKTOP_SPLITPANE_BORDER, BorderFactory.createEmptyBorder(),
            SPLIT_PANE_DIVIDER_SIZE_VERTICAL, 2,
            SPLIT_PANE_DIVIDER_SIZE_HORIZONTAL, 2,
            
            WARNING_FOREGROUND, new Color(254, 183, 24),
            ERROR_FOREGROUND, new Color(255, 102, 102),
            
            // quicksearch
            "nb.quicksearch.border", BorderFactory.createEmptyBorder(),
            
            // progress ui
            "nb.progress.cancel.icon", ImageUtilities.loadImage("org/openide/awt/resources/metal_close_rollover_dark.png", false),
            "nb.progress.cancel.icon.mouseover", ImageUtilities.loadImage("org/openide/awt/resources/metal_close_pressed_dark.png", false),
            "nb.progress.cancel.icon.pressed", ImageUtilities.loadImage("org/openide/awt/resources/metal_close_pressed_dark.png", false),
            
            // explorer views
            "nb.explorer.unfocusedSelBg", new Color(13, 41, 62),
            "nb.explorer.unfocusedSelFg", new Color(187, 187, 187),
            "nb.explorer.noFocusSelectionBackground", new Color(13, 41, 62),
            "nb.explorer.noFocusSelectionForeground", new Color(187, 187, 187),
            "ETableHeader.ascendingIcon", new ImageIcon(DarculaLFCustoms.class.getResource("column-asc.png")),
            "ETableHeader.descendingIcon", new ImageIcon(DarculaLFCustoms.class.getResource("column-desc.png")),
            
            // popup switcher
            "nb.popupswitcher.border", BorderFactory.createLineBorder(new Color(45, 45, 45)),
            
            // heapview
            "nb.heapview.border1", new Color(113, 113, 113),
            "nb.heapview.border2", new Color(91, 91, 95),
            "nb.heapview.border3", new Color(128, 128, 128),
            "nb.heapview.foreground", new Color(222, 222, 222),
            "nb.heapview.background1", new Color(53, 56, 58),
            "nb.heapview.background2", new Color(50, 66, 114),
            "nb.heapview.grid1.start", new Color(97, 95, 87),
            "nb.heapview.grid1.end", new Color(98, 96, 88),
            "nb.heapview.grid2.start", new Color(99, 97, 90),
            "nb.heapview.grid2.end", new Color(101, 99, 92),
            "nb.heapview.grid3.start", new Color(102, 101, 93),
            "nb.heapview.grid3.end", new Color(105, 103, 95),
            "nb.heapview.grid4.start", new Color(107, 105, 97),
            "nb.heapview.grid4.end", new Color(109, 107, 99),
            
            // db.dataview
            "nb.dataview.table.gridbackground", UIManager.getColor("Table.gridColor"),
            "nb.dataview.table.background", new RelativeColor(new Color(0, 0, 0), new Color(0, 0, 0), "Table.background"),
            "nb.dataview.table.altbackground", new RelativeColor(new Color(0, 0, 0), new Color(30, 30, 30), "Table.background"),
            "nb.dataview.table.sqlconstant.foreground", new Color(220, 220, 220),
            "nb.dataview.tablecell.focused", /*new RelativeColor(new Color(0, 0, 0), new Color(10, 10, 30), "Table.selectionBackground"), */ new Color(13, 41, 62),
            "nb.dataview.table.rollOverRowBackground", new RelativeColor(new Color(0, 0, 0), new Color(30, 30, 30), "Table.selectionBackground"),
            "nb.dataview.tablecell.edited.selected.foreground", new Color(241, 255, 177),
            "nb.dataview.tablecell.edited.unselected.foreground", /*new Color(0, 255, 16),*/ new Color(172, 221, 124),
            "nb.dataview.jxdatetimepicker.background", new RelativeColor(new Color(0, 0, 0), new Color(0, 0, 0), "Table.background"),
            "nb.dataview.jxdatetimepicker.foreground", new RelativeColor(new Color(0, 0, 0), new Color(0, 0, 0), "Table.foreground"),
            "nb.dataview.jxdatetimepicker.selectedBackground", new RelativeColor(new Color(0, 0, 0), new Color(0, 0, 0), "Table.selectionBackground"),
            "nb.dataview.jxdatetimepicker.selectedForeground", new RelativeColor(new Color(0, 0, 0), new Color(0, 0, 0), "Table.selectionForeground"),
            "nb.dataview.jxdatetimepicker.daysOfTheWeekForeground", new RelativeColor(new Color(0, 0, 0), new Color(0, 0, 0), "Table.background"),
            "nb.dataview.jxdatetimepicker.todayBackground", new RelativeColor(new Color(0, 0, 0), new Color(20, 20, 20), "TableHeader.background"),
            "nb.dataview.jxdatetimepicker.todayPanel.background.gradient.start", new RelativeColor(new Color(0, 0, 0), new Color(0, 0, 0), "TableHeader.background"),
            "nb.dataview.jxdatetimepicker.todayPanel.background.gradient.end", new RelativeColor(new Color(0, 0, 0), new Color(10, 10, 10), "TableHeader.background"),
            "nb.dataview.jxdatetimepicker.todayPanel.linkForeground", new RelativeColor(new Color(0, 0, 0), new Color(0, 0, 0), "TableHeader.foreground"),
            
            // form designer
            "nb.formdesigner.gap.fixed.color", new Color(112, 112, 112),
            "nb.formdesigner.gap.resizing.color", new Color(116, 116, 116),
            "nb.formdesigner.gap.min.color", new Color(104, 104, 104),
            
            // link
            "nb.html.link.foreground", new Color(125, 160, 225), //NOI18N
            "nb.html.link.foreground.hover", new Color(13, 41, 62), //NOI18N
            "nb.html.link.foreground.visited", new Color(125, 160, 225), //NOI18N
            "nb.html.link.foreground.focus", new Color(13, 41, 62), //NOI18N

            // startpage
            "nb.startpage.defaultbackground", Boolean.TRUE,
            "nb.startpage.defaultbuttonborder", Boolean.TRUE,
            "nb.startpage.bottombar.background", new Color(13, 41, 62),
            "nb.startpage.topbar.background", new Color(13, 41, 62),
            "nb.startpage.border.color", new Color(13, 41, 62),
            "nb.startpage.tab.border1.color", new Color(13, 41, 62),
            "nb.startpage.tab.border2.color", new Color(13, 41, 62),
            "nb.startpage.rss.details.color", new Color(187, 187, 187),
            "nb.startpage.rss.header.color", new Color(125, 160, 225),
            "nb.startpage.tab.imagename.selected", "org/netbeans/modules/welcome/resources/tab_selected_dark.png", //NOI18N
            "nb.startpage.tab.imagename.rollover", "org/netbeans/modules/welcome/resources/tab_rollover_dark.png", //NOI18N
            "nb.startpage.imagename.contentheader", "org/netbeans/modules/welcome/resources/content_banner_dark.png", //NOI18N
            "nb.startpage.contentheader.color1", new Color(12, 33, 61),
            "nb.startpage.contentheader.color2", new Color(16, 24, 42),
            
            // autoupdate
            "nb.autoupdate.search.highlight", new Color(13, 41, 62),
            
            // notification displayer balloon
            "nb.core.ui.balloon.defaultGradientStartColor", new Color(92, 92, 66),
            "nb.core.ui.balloon.defaultGradientFinishColor", new Color(92, 92, 66),
            "nb.core.ui.balloon.mouseOverGradientStartColor", new Color(92, 92, 66),
            "nb.core.ui.balloon.mouseOverGradientFinishColor", new Color(92, 92, 66).brighter()
        
        };

        return result;
    }

    private class Windows8EditorColorings extends UIBootstrapValue.Lazy {

        /**
         *
         * @param name
         */
        public Windows8EditorColorings(String name) {
            super(name);
        }

        @Override
        public Object[] createKeysAndValues() {
            return new Object[]{
                //selected & focused
                TAB_FOCUS_FILL_UPPER, new Color(75, 110, 175),
                TAB_FOCUS_FILL_LOWER, new Color(65, 81, 109),
                
                //no selection, no focus
                TAB_UNSEL_FILL_UPPER, new Color(84, 88, 91),
                TAB_UNSEL_FILL_LOWER, new Color(56, 58, 60),
                
                //selected, no focus
                TAB_SEL_FILL, new Color(84, 88, 91),
                
                //no selection, mouse over
                TAB_MOUSE_OVER_FILL_UPPER, new Color(114, 119, 122),
                TAB_MOUSE_OVER_FILL_LOWER, new Color(98, 101, 104),
                TAB_ATTENTION_FILL_UPPER, new Color(255, 255, 128),
                TAB_ATTENTION_FILL_LOWER, new Color(230, 200, 64),
                
                TAB_BORDER, new Color(41, 43, 45),
                TAB_SEL_BORDER, new Color(41, 43, 45),
                TAB_BORDER_INNER, new Color(70, 72, 74),
                
                //Borders for the tab control
                EDITOR_TAB_OUTER_BORDER, BorderFactory.createEmptyBorder(),
                EDITOR_TAB_CONTENT_BORDER, BorderFactory.createCompoundBorder(
                        new MatteBorder(0, 0, 1, 0, new Color(41, 43, 45)),
                        BorderFactory.createEmptyBorder(0, 1, 0, 1)
                ),
                EDITOR_TAB_TABS_BORDER, BorderFactory.createEmptyBorder(),
                VIEW_TAB_OUTER_BORDER, BorderFactory.createEmptyBorder(),
                VIEW_TAB_CONTENT_BORDER, new MatteBorder(0, 1, 1, 1, new Color(41, 43, 45)),
                VIEW_TAB_TABS_BORDER, BorderFactory.createEmptyBorder()
            };
        }
    }

    private class Windows8PropertySheetColorings extends UIBootstrapValue.Lazy {

        public Windows8PropertySheetColorings() {
            super("propertySheet");  //NOI18N
        }

        @Override
        public Object[] createKeysAndValues() {
            return new Object[]{
                PROPSHEET_BACKGROUND, new Color(69, 73, 74),
                PROPSHEET_SELECTION_BACKGROUND, new Color(75, 110, 175),
                PROPSHEET_SELECTION_FOREGROUND, Color.WHITE,
                PROPSHEET_SET_BACKGROUND, new Color(82, 85, 86),
                PROPSHEET_SET_FOREGROUND, Color.WHITE,
                PROPSHEET_SELECTED_SET_BACKGROUND, new Color(75, 110, 175),
                PROPSHEET_SELECTED_SET_FOREGROUND, Color.WHITE,
                PROPSHEET_DISABLED_FOREGROUND, new Color(161, 161, 146),
                PROPSHEET_BUTTON_FOREGROUND, new Color(187, 187, 187),};
        }
    }

    
    
    /**
     * NOT_FOUND color is hardcoded, should be taken from UIManager.
     * Use reflection as in DefaultOutlineCellRenderer. 
     */
    private static final String SEARCH_BAR_CLASS = "org.netbeans.modules.editor.search.SearchBar";
    private static final String NOT_FOUND_COLOR_FIELD = "NOT_FOUND";
    private void replaceSearchNotFoundColor() {
        replaceFieldValue(SEARCH_BAR_CLASS, NOT_FOUND_COLOR_FIELD, new Color(255, 102, 102));
    }
    
    /**
     * DEFAULT_GUTTER_LINE color is hardcoded, should be taken from UIManager.
     * Use reflection as in DefaultOutlineCellRenderer. 
     */
    private static final String GLYPH_GUUTER_CLASS = "org.netbeans.editor.GlyphGutter";
    private static final String DEFAULT_GUTTER_LINE_COLOR_FIELD = "DEFAULT_GUTTER_LINE";
    private void replaceGlyphGutterLineColor() {
        replaceFieldValue(GLYPH_GUUTER_CLASS, DEFAULT_GUTTER_LINE_COLOR_FIELD, new Color(136, 136, 136));
    }
    
    /**
     * LFCustoms.getTextFgColor() && LFCustoms.getTextFgColorHTML() uses
     * windowText. DarculaLaf does not override windowText which is initialized
     * to Color.BLACK by BasicLookAndFeel (DarculaLaf uses BasicLookAndFeel
     * with reflection)
     */
    private static final String TEXT_FG_COLOR_HTML_FIELD = "textFgColorHTML";
    private static final String TEXT_FG_COLOR_FIELD = "textFgColor";
    private void replaceLFCustomsTextFgColors() {
        replaceFieldValue(LFCustoms.class, TEXT_FG_COLOR_FIELD, new Color(187, 187, 187));
        replaceFieldValue(LFCustoms.class, TEXT_FG_COLOR_HTML_FIELD, "<font color=#bbbbbb>");
    }

    private void replaceFieldValue(String className, String fieldName, Color value) {

        Class<?> sbClass = null;
        try {
            sbClass = ClassLoader.getSystemClassLoader().loadClass(className);
        } catch (ClassNotFoundException ex) {
            try {
                sbClass = Thread.currentThread().getContextClassLoader().loadClass(className);
            } catch (ClassNotFoundException ex1) {
                try {
                    ClassLoader systemClassLoader = (ClassLoader) Lookup.getDefault().lookup(ClassLoader.class);
                    if (systemClassLoader != null) {

                        sbClass = systemClassLoader.loadClass(className);
                    }
                } catch (ClassNotFoundException ex2) {
                    Logger.getLogger(DarculaLFCustoms.class.getName()).log(Level.WARNING, null, ex2);
                }
            }
        } catch (SecurityException ex) {
            Logger.getLogger(DarculaLFCustoms.class.getName()).log(Level.WARNING, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(DarculaLFCustoms.class.getName()).log(Level.WARNING, null, ex);
        }
        
        if (sbClass == null) {
            return;
        }

        replaceFieldValue(sbClass, fieldName, value);

    }
    
    private void replaceFieldValue(Class<?> clazz, String fieldName, Object value) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            
            field.setAccessible(true);
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

            field.set(null, value);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DarculaLFCustoms.class.getName()).log(Level.WARNING, null, ex);
        } catch (NoSuchFieldException ex) {
            Logger.getLogger(DarculaLFCustoms.class.getName()).log(Level.WARNING, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(DarculaLFCustoms.class.getName()).log(Level.WARNING, null, ex);
        }
    }
    
}

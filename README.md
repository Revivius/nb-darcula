A NetBeans Look And Feel plugin using Darcula of IntelliJ IDEA.

![Darcula4NB](/../screenshots/screenshots/v1.4.png?raw=true "Darcula4NB")

Wraps [Darcula LAF](https://github.com/bulenkov/Darcula) and provides required NetBeans specific customizations. **Many thanks to Konstantin Bulenkov for open sourcing original Darcula LAF.**

The most recent stable version of this plugin is available at the NetBeans Plugin Portal as [Darcula LAF for NetBeans](http://plugins.netbeans.org/plugin/62424/darcula-laf-for-netbeans) and thus it can be installed from "Tools|Plugins|Available Plugins" from the NetBeans IDE.

## Change Log

### [1.6] - 2017-07-31
 - Plugin now works on JDK9. Thanks to **Smurfi** and **markiewb**.
 - Now it is possible to override indentation level of trees (including the one in Projects window) via Tools > Options > Appearance > Darcula Look And Feel.
 - Tab navigation with CTRL+PageUp/PageDown fixed.  Thanks to **markiewb**.
 - Hard to read colors in SQL results table, start page and notification links fixed.
 - Hard to read colors for custom HTML tags, CSS preprocessor variables and mixins in code completion popup fixed.
 - For all addressed issues (16) please see [Milestone v1.6](https://github.com/Revivius/nb-darcula/issues?q=milestone%3Av1.6+is%3Aclosed).

### [1.5] - 2016-04-09
 - Hard to read colors in HTML, XHTML, JSP and SQL code completion popups and Apache Conf (.htaccess, .conf) files fixed thank to **granella**.
 - Baseline alignment of combo boxes fixed thanks to **AlexFalappa**. Fix for toggle buttons moved to original Darcula library; they should look OK now in GUI designer too.
 - Editor tab colors for 'Same background color from the same project' feature (Tools > Options > Appearance > Document Tabs) adjusted to match Darcula LAF.
 - More color, border and icon enhancements (including background color for tree and tables, border of selected rows, icons in Profiler and C/C++ wizard).
 - For all addressed issues (14) please see [Milestone v1.5](https://github.com/Revivius/nb-darcula/issues?q=milestone%3Av1.5+is%3Aclosed).

### [1.4] - 2016-01-31
 - Fixed hard to read colors in Code Completion popup (hopefully) for all languages.
 - Disabled buttons and labels are easier to read.
 - Fixed default button behavior in dailogs (eg: alt+insert in editor to generate setters and getters).
 - It is safe to use this plugin in any NetBeans platform application now. Level used to log failed attempts to change hardcoded colors is now INFO and so no error dialog will be displayed at startup.
 - It is possible to use icon filter to invert icon colors and switch between stretched and non-stretched tabs in Tools > Options > Appearance > Darcula Look And Feel.  Thanks to **markiewb** for the contribution.
 - Improved icons for JOptionPane, thanks to **AlexFalappa** and **granella**.
 - More color, border and icon enhancements (including NetBeans Task/Issue viewer, submenu icons on MAC, Darcula Editor Theme and others).
 - For all addressed issues (27) please see [Milestone v1.4](https://github.com/Revivius/nb-darcula/issues?q=milestone%3Av1.4+is%3Aclosed).

### [1.3] - 2016-01-24
 - Bundled 'Darcula' theme (Tools > Options > Fonts&Colors). Plugin will set theme to Darcula during first restart. Many thanks to **granella** for the theme and helping with packaging it in plugin.
 - It is possible to specify font type and size within IDE via Tools > Options > Appearance > Darcula Look And Feel. Many thanks to **markiewb** for implementing this with other font related issues.
 - More color, border and icon enhancements (including diff, versioning colors and FormDesigner guidelines, new Split Pane UI thanks to [hudsonb](https://github.com/bulenkov/Darcula/pull/5)).
 - For all addressed issues (14) please see [Milestone v1.3](https://github.com/Revivius/nb-darcula/issues?q=milestone%3Av1.3+is%3Aclosed).

### [1.2] - 2016-01-19
 - Fixed dark text in Code Completion popup.
 - Removed more harsh white borders (including the ones in Options window and sliding buttons). A few remaining (such as the one in Search Results) are hardcoded and I dont know a good way of patching those.
 - Added a slight margin for buttons

### [1.1] - 2016-01-17
- Font size issue fixed. It is now possible to use --fontsize switch (either in conf file or command line) to specify desired font size, default size is 12.
- 'Darcula for NetBeans' is now in 'Preferred look and feel' drop down as requested (Tools > Options > Appearance > Look and Feel). Plugin will set LAF to Darcula during first restart. After that user can switch LAF without uninstalling/disabling the plugin.
- Colors in Options window match properly with LAF.
- Many harsh white borders (including the one of search box at top right corner of main window) removed.
- Contrasting light versions of most core icons provided (close, slide, minimize, restore etc).

### [1.0] - 2016-01-14
    

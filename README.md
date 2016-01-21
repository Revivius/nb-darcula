# Darcula LAF for NetBeans

A NetBeans Look And Feel plugin using Darcula theme from IntelliJ Idea.

Wraps Darcula LAF from:  https://github.com/bulenkov/Darcula and provides required NetBeans specific customizations. **Many thanks to Konstantin Bulenkov for open sourcing original LAF.**

## Change Log

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
    
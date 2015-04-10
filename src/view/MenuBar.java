/*
 * TCSS 305 - Winter 2014
 * Assignment 5 - Powerpaint
 */

package view;

import java.awt.BasicStroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;

import model.ShapeTool;

/**
 * Represents a menu bar with file, options, tools, and help menus.
 * @author Brandon Soto
 * @version 2/12/2014
 */
@SuppressWarnings("serial")
public final class MenuBar extends JMenuBar {

    /**
     * Message to be displayed in the About message dialog.
     */
    private static final String ABOUT_MESSAGE = "TCSS 305 PowerPaint\nWinter 2014";

    /**
     * An array representing all the stroke thickness options available in the
     * options menu. STROKES can contain integers greater than 9, but those
     * stroke options will not have mnemonics. (If you try negative numbers, they will a have 
     * a thickness of 1.)
     */
    private static final Integer[] STROKES = {1, 2, 4};

    /**
     * Button group for the shape tools found in the Tools menu. Allows all of the shape 
     * buttons belong to the same button group. 
     */
    private final ButtonGroup myShapeGroup;

    /**
     * The tool menu to be placed in the menu bar.
     */
    private final JMenu myToolMenu;

    /**
     * ShapeTool that this MenuBar shares with the drawing panel and the shape action buttons.
     */
    private final ShapeTool myCurrentTool; 
    
    /**
     * Reference to the main frame of the GUI. Used so the "About" message pane can be 
     * displayed over the main frame. 
     */
    private final JFrame myMainFrame;
    
    /**
     * Constructs a CustomMenuBar with a file, options, tools, and help menus.
     * @param theTool the ShapeTool to be used by this menu bar. 
     * @param theMainFrame the main frame of the GUI. 
     */
    public MenuBar(final ShapeTool theTool, final JFrame theMainFrame) {
        super();

        myMainFrame = theMainFrame;
        myCurrentTool = theTool;
        myShapeGroup = new ButtonGroup();
        myToolMenu = new JMenu(new MenuAction("Tools", KeyEvent.VK_T));

        setUpMenuBar();
    }
    
    /**
     * Adds the file, edit, options, tool, and help menu to the menu bar. 
     */
    private void setUpMenuBar() {
        add(getFileMenu());
        add(getOptionsMenu());
        add(myToolMenu);
        add(getTheHelpMenu());
    }

    /**
     * Returns a file menu with clear and exit menu items.
     * 
     * @return file menu with certain menu items.
     */
    private JMenu getFileMenu() {
        // File - clear, exit
        final JMenu file = new JMenu(new MenuAction("File", KeyEvent.VK_F));
        
        final JMenuItem clear = new JMenuItem(new MenuAction("Clear", KeyEvent.VK_C));
        clear.addActionListener(new ActionListener() {
            /**
             * Fires a property change event that will clear the drawing panel of drawings. 
             * @param theEvent event when the clear menu item is pressed.
             */
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                MenuBar.this.firePropertyChange("clear drawing panel", null, null);
            }
        });
        file.add(clear);
        file.addSeparator();

        final JMenuItem exit = new JMenuItem(new MenuAction("Exit", KeyEvent.VK_X));
        exit.addActionListener(new ActionListener() {
            /**
             * Fires a property change event that will close the program. 
             * @param theEvent event when the exit menu item is pressed. 
             */
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                myMainFrame.dispatchEvent(new WindowEvent(myMainFrame, 
                                                                  WindowEvent.WINDOW_CLOSING));
            }
        });
        file.add(exit);

        return file;
    }
    
    /**
     * Returns a options menu with grid menu item and a thickness menu.
     * 
     * @return options menu with certain menu items.
     */
    private JMenu getOptionsMenu() {
        // Options - grid, thickness
        final JMenu options = new JMenu(new MenuAction("Options", KeyEvent.VK_O));
        final JCheckBoxMenuItem grid =
                        new JCheckBoxMenuItem(new MenuAction("Grid", false, KeyEvent.VK_G));
        grid.addActionListener(new ActionListener() {
            // if pressed, it sets the grid's visibility to this button's state.
            // It then repaints the drawing panel.
            /**
             * Fires a property change event that will set the drawing panel's grid visibility
             * to the item's current state. 
             * @param theEvent event when the grid item is pressed. 
             */
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                final JCheckBoxMenuItem item = (JCheckBoxMenuItem) theEvent.getSource();
                MenuBar.this.firePropertyChange("set grid visibility", !item.getState(), 
                                                                             item.getState());
            }
        });

        options.add(grid);
        options.add(getThicknessSubMenu());

        return options;
    }
    

    /**
     * Adds a given shape tool to the tool menu.
     * 
     * @param theAction shape tool to be added.
     */
    public void addShapeToolToToolMenu(final Action theAction) {
        final JRadioButtonMenuItem item = new JRadioButtonMenuItem(theAction);
        myShapeGroup.add(item);
        myToolMenu.add(item);
    }

    /**
     * Adds a given color tool to the tool menu.
     * 
     * @param theAction the color tool to be added.
     */
    public void addColorTool(final Action theAction) {
        myToolMenu.add(theAction);
        myToolMenu.addSeparator();
    }

    /**
     * Returns a help menu that only contains an "About" menu item.
     * 
     * @return help menu with "About" menu item.
     */
    private JMenu getTheHelpMenu() {
        final JMenu help = new JMenu(new MenuAction("Help", KeyEvent.VK_H));
        help.add(getAboutMenuItem());

        return help;
    }

    /**
     * Returns a menu item that brings up a message dialog when clicked on.
     * 
     * @return menu item that includes information about the program.
     */
    private JMenuItem getAboutMenuItem() {
        final JMenuItem about = new JMenuItem(new MenuAction("About...", KeyEvent.VK_A));
        about.addActionListener(new ActionListener() {
            /**
             * Shows message dialog that displays the title of this program.
             * @param theEvent event when the the about button is pressed.
             */
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                JOptionPane.showMessageDialog(myMainFrame, ABOUT_MESSAGE, "About",
                                              JOptionPane.INFORMATION_MESSAGE);
            }
        });

        return about;
    }

    /**
     * Returns thickness menu that will be featured in the options menu. The
     * thickness menu features three radio buttons that represent the thickness
     * of a stroke. By default, option "1" is selected.
     * @return thickness menu for options menu.
     */
    private JMenu getThicknessSubMenu() {
        final JMenu strokeMenu = new JMenu(new MenuAction("Thickness", KeyEvent.VK_T));
        final ButtonGroup buttonGroup = new ButtonGroup();

        // for each stroke type in STROKES, add it to the button group and thickness menu
        for (final Integer strokeNum : STROKES) {
            // character is a char that represents a specific digit 
            final char character = Character.forDigit(strokeNum, Character.MAX_RADIX);
            final Action action =
                            new MenuAction(strokeNum.toString(),
                                            KeyEvent.getExtendedKeyCodeForChar(character));

            final JRadioButtonMenuItem item = new JRadioButtonMenuItem(action);
            item.addActionListener(new ActionListener() {
                /**
                 * Changes the stroke of the ShapeTool. 
                 * @param theEvent event when the menu item is pressed. 
                 */
                @Override
                public void actionPerformed(final ActionEvent theEvent) {
                    // set shape tool's current stroke to the name of the button
                    // (the button/action's name should be an integer)
                    final int thickness = Integer.valueOf((String) action.getValue("Name"));
                    myCurrentTool.setStroke(new BasicStroke(thickness));
                }

            });
            buttonGroup.add(item);
            strokeMenu.add(item);
        }
        
        return strokeMenu;
    }
    
    /**
     * Represent a convenience class to make actions for items of the menu bar.
     * @author Brandon
     * @version 2/12/2014
     */
    private class MenuAction extends AbstractAction {

        /**
         * Constructs a MenuAction object with a name and mnemonic. The action is
         * has a true selected key by default.
         * 
         * @param aName name of action.
         * @param aMnemonic mnemonic of action.
         */
        public MenuAction(final String aName, final int aMnemonic) {
            this(aName, true, aMnemonic);
        }

        /**
         * Constructs a MenuAction with a given name, selected status, and mnemonic. 
         * @param aName desired name of action. 
         * @param aStatus desired status of the action. 
         * @param aMnemonic desired mnemonic of the action. 
         */
        public MenuAction(final String aName, final boolean aStatus, final int aMnemonic) {
            super();
            putValue(Action.NAME, aName);
            putValue(Action.MNEMONIC_KEY, aMnemonic);
            putValue(Action.SELECTED_KEY, aStatus);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            // Do nothing. The components will implement anonymous listener classes. 
        }
    }

} // end CustomMenuBar class

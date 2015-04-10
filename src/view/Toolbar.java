/*
 * TCSS 305 Winter 2014
 * Assignment 5 - Powerpaint 
 */
package view;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

/**
 * Represents a custom toolbar that has certain item buttons. Namely, it contains color, 
 * pencil, line, rectangle, ellipse, undo, and redo buttons. 
 * @author Brandon Soto
 * @version 2/12/14
 *
 */
@SuppressWarnings("serial")
public final class Toolbar implements PropertyChangeListener {
    
    /**
     * String used for defining undo behavior. 
     */
    private static final String UNDO_ACTION = "Undo";
    
    /**
     * String used for defining redo behavior. 
     */
    private static final String REDO_ACTION = "Redo";
    
    private final JToolBar myToolbar; 
    
    /**
     * The button group that the CustomToggleButtons will belong to.
     */
    private final ButtonGroup myShapeGroup; 
    
    /**
     * Represents the toolbar's "Undo" button.
     */
    private final JButton myUndoButton;
    
    /**
     * Represents the toolbar's "Redo" button. 
     */
    private final JButton myRedoButton;
    
    /**
     * Constructs a CustomToolbar. A CustomToolBar contains 5 buttons in all. 
     * The toolbar includes buttons for color, pencil, line, rectangle, and ellipse tools. 
     */
    public Toolbar() {
        super();
        
        myToolbar = new JToolBar();

        myShapeGroup = new ButtonGroup();
        myUndoButton = setUpUndoButton();
        myRedoButton = setUpRedoButton();
    }
    
    /**
     * Creates the toolbar's "Undo" button. The "Redo" button has a "U" mnemonic, an icon, and
     * a "Ctrl Z" accelerator. When the button is pressed, it fires a PropertyChangeEvent that
     * should undo the user's latest change to the drawing panel. 
     * @return a fully functional "Undo" button. 
     */
    private JButton setUpUndoButton() {
        final Action undoAction = new CustomAction(UNDO_ACTION, KeyEvent.VK_U, 
                                   new ImageIcon("icons/undo.png"), "take from current list");
        final JButton undo = new JButton(undoAction);
        final KeyStroke undoStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Z, KeyEvent.CTRL_MASK);
        
        undo.getActionMap().put(UNDO_ACTION, undoAction);
        undo.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(undoStroke, UNDO_ACTION);    
        undo.setEnabled(false);
        
        return undo;
    }
    
    /**
     * Creates the toolbar's "Redo" button. The "Redo" button has a "D" mnemonic, an icon, and
     * a "Ctrl Y" accelerator. When the button is pressed, it fires a PropertyChangeEvent that
     * should redo the latest of the user's change to the drawing panel.  
     * @return a fully functional "Redo" button. 
     */
    private JButton setUpRedoButton() {
        final Action redoAction = new CustomAction(REDO_ACTION, KeyEvent.VK_D, 
                                   new ImageIcon("icons/redo.png"), "put drawing back");
        final JButton redo = new JButton(redoAction);
        final KeyStroke redoStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_MASK);
        
        redo.getActionMap().put(REDO_ACTION, redoAction);
        redo.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(redoStroke, REDO_ACTION);
        redo.setEnabled(false);
        
        return redo;
    }
    
    /**
     * Makes a button from the given action and adds that button to the toolbar. 
     * @param theActionButton action to create button from. 
     */
    public void addButtonToToolbar(final Action theActionButton) {
        final JToggleButton button = new JToggleButton(theActionButton);
        myShapeGroup.add(button);
        myToolbar.add(button);
    }
    
    /**
     * Makes a button from the given action and adds that button and a separator to the 
     * toolbar. 
     * @param theAction action to create button from. 
     */
    public void addColorTool(final Action theAction) {
        myToolbar.add(new JButton(theAction));
        myToolbar.addSeparator();
    }
    
    /**
     * Adds the undo and redo buttons to the toolbar. As it is, this method is called after
     * the color and shape buttons have been added to the toolbar. 
     */
    public void addUndoAndRedoButtonsToToolbar() {
        myToolbar.addSeparator();
        myToolbar.addSeparator();
        
        myToolbar.add(myUndoButton);
        myToolbar.add(myRedoButton);
    }

    /**
     * Depending on the event's property name, the undo and redo buttons will be enabled or 
     * disabled. 
     * @param theEvent the event that's fired. 
     */
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if (UNDO_ACTION.equals(theEvent.getPropertyName())) {
            myUndoButton.setEnabled((boolean) theEvent.getNewValue());
        } else if (REDO_ACTION.equals(theEvent.getPropertyName())) {
            myRedoButton.setEnabled((boolean) theEvent.getNewValue());
        } 
    }
    
    /**
     * A convenience class that quickly creates an action. The action should be given have a 
     * name, mnemonic, image icon, and a property name. 
     */
    private class CustomAction extends AbstractAction {
        /**
         * Represents the name of the a property name to fire. 
         */
        private final String myPropertyName;
        
        /**
         * Constructs a CustomAction with a given name, mnemonic, icon, and property change. 
         * @param aName name of action. 
         * @param aMnem mnemonic of action.
         * @param anIcon icon of action.
         * @param aChange property change to fire when the action is performed. 
         */
        public CustomAction(final String aName, final int aMnem, final ImageIcon anIcon, 
                                                                    final String aChange) {
            super();
            
            putValue(Action.NAME, aName);
            putValue(Action.MNEMONIC_KEY, aMnem);
            putValue(Action.SMALL_ICON, anIcon);
            
            myPropertyName = aChange;
        }
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            Toolbar.this.firePropertyChange(myPropertyName, null, null);
        }
    }
}

/*
 * TCSS 305 Winter 2014 
 * Assignment 5 - Powerpaint 
 */
package view;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

import model.ShapeTool;


/**
 * An abstract class that creates shape actions with a name, image icon, and a mnemonic. 
 * @author Brandon Soto
 * @version 2/17/14
 */
@SuppressWarnings("serial")
public abstract class AbstractShapeAction extends AbstractAction {
    
    /**
     * Reference to the shapetool used by this action and the drawing panel. 
     */
    private final ShapeTool myShapeTool; 
    
    /**
     * Creates an action with the given name, icon, and mnemonic. It passes the main GUI to 
     * this object. 
     * 
     * @param aName desired name of action. 
     * @param aIcon desired icon of action. 
     * @param aMnemonic desired mnemonic of action. 
     * @param theTool desired ShapeTool to perform action on. 
     */
    public AbstractShapeAction(final String aName, final ImageIcon aIcon, final int aMnemonic, 
                               final ShapeTool theTool) {
        super();
        putValue(Action.NAME, aName);
        putValue(Action.SMALL_ICON, aIcon);
        putValue(Action.MNEMONIC_KEY, aMnemonic);
        putValue(Action.SELECTED_KEY, true);
        
        myShapeTool = theTool;
    }
    
    /**
     * Returns the ShapeTool that is currently being used by this action and the drawing
     * panel. 
     * @return ShapeTool being used by this object and drawing panel. 
     */
    public ShapeTool getTool() {
        return myShapeTool; 
    }
}

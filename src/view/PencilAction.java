/*
 * TCSS 305 Winter 2014 
 * Assignment 5 - Powerpaint 
 */
package view;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.GeneralPath;

import javax.swing.ImageIcon;

import model.ShapeTool;

/**
 * Represents an action that predefines the name, icon, and mnemonic to match a pencil. 
 * @author Brandon Soto
 * @version Feb 17, 2014
 */
@SuppressWarnings("serial")
public final class PencilAction extends AbstractShapeAction {

    /**
     * Constructs an PencilAction with the name "Pencil", an image of a pencil, and a
     * mnemonic of "P". 
     * @param theTool desired ShapeTool to perform action on. 
     */
    public PencilAction(final ShapeTool theTool) {
        super("Pencil", new ImageIcon("icons/pencil.gif"), KeyEvent.VK_P, theTool);
    }

    /**
     * Changes the ShapeTool's shape to a GeneralPath and resets its start point. 
     * @param theEvent event when a component with a PencilAction is pressed. 

     */
    @Override
    public void actionPerformed(final ActionEvent theEvent) {
        final ShapeTool tool = getTool();
        
        tool.setShape(new GeneralPath());
        tool.setStartPoint(new Point());
    }
}

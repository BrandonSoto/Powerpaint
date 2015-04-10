/*
 * TCSS 305 Winter 2014 
 * Assignment 5 - Powerpaint A
 */
package view;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;

import model.ShapeTool;

/**
 * Represents an action that predefines the name, icon, and mnemonic to match a rectangle. 
 * @author Brandon Soto
 * @version Feb 17, 2014
 */
@SuppressWarnings("serial")
public final class RectangleAction extends AbstractShapeAction {
    
    /**
     * Constructs a RectangleActoin with the name "Rectangle", an image of a rectangle, and a
     * mnemonic of "R". 
     * @param theTool desired ShapeTool to perform action on. 
     */
    public RectangleAction(final ShapeTool theTool) {
        super("Rectangle", new ImageIcon("icons/rectangle_bw.gif"), KeyEvent.VK_R, theTool);
    }

    /**
     * Changes the ShapeTool's shape to a Rectangle2D.Double and resets its start point. 
     * @param theEvent event when a component with a RectangleAction is pressed. 

     */
    @Override
    public void actionPerformed(final ActionEvent theEvent) {
        final ShapeTool tool = getTool();
        
        tool.setShape(new Rectangle2D.Double());
        tool.setStartPoint(new Point());
    }

}

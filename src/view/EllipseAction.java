/*
 * TCSS 305 Winter 2014 
 * Assignment 5 - Powerpaint
 */
package view;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;

import javax.swing.ImageIcon;

import model.ShapeTool;

/**
 * Represents an action that predefines the name, icon, and mnemonic to match an ellipse. 
 * @author Brandon Soto
 * @version Feb 17, 2014
 */
@SuppressWarnings("serial")
public final class EllipseAction extends AbstractShapeAction {

    /**
     * Constructs an EllipseAction with the name "Ellipse", an image of an ellipse, and a
     * mnemonic of "E". 
     * @param theTool desired ShapeTool to perform action on. 
     */
    public EllipseAction(final ShapeTool theTool) {
        super("Ellipse", new ImageIcon("icons/ellipse_bw.gif"), KeyEvent.VK_E, theTool);
    }
    
    /**
     * Changes the ShapeTool's shape to an ellipse and resets its start point. 
     * @param theEvent event when a component with an EllipseAction is pressed. 
     */
    @Override
    public void actionPerformed(final ActionEvent theEvent) {
        final ShapeTool tool = getTool();
        
        tool.setShape(new Ellipse2D.Double());
        tool.setStartPoint(new Point());
    }

}

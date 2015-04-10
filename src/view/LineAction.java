/*
 * TCSS 305 Winter 2014 
 * Assignment 5 - Powerpaint
 */
package view;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;

import javax.swing.ImageIcon;

import model.ShapeTool;

/**
 * Represents an action that predefines the name, icon, and mnemonic to match a line. 
 * @author Brandon Soto
 * @version Feb 17, 2014
 */
@SuppressWarnings("serial")
public final class LineAction extends AbstractShapeAction {

    /**
     * Constructs a LineAction with the name "Line", an image of a line, and a * mnemonic of 
     * "L". 
     * @param theTool desired ShapeTool to perform action on. 
     */
    public LineAction(final ShapeTool theTool) {
        super("Line", new ImageIcon("icons/line_bw.gif"), KeyEvent.VK_L, theTool);
    }

    /**
     * Changes the ShapeTool's shape to a Line2D.Doubl and resets its start point. 
     * @param theEvent event when a component with a LineAction is pressed. 
     */
    @Override
    public void actionPerformed(final ActionEvent theEvent) {
        final ShapeTool tool = getTool();
        
        tool.setShape(new Line2D.Double());
        tool.setStartPoint(new Point());
    }

}

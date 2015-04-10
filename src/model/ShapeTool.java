/*
 * TCSS 305 Winter 2014 
 * Assignment 5 - Powerpaint
 */
package model;

import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.RectangularShape;

/**
 * ShapeTool is a class that stores a myShape and qualities about how that myShape should be 
 * drawn. Specifically, the myStroke, myColor, and starting point of that myShape. 
 * @author Brandon Soto
 * @version Feb 18, 2014
 */
public final class ShapeTool {
    
    /**
     * Shape of tool.
     */
    private Shape myShape;
    
    /**
     * The color that the shape should be drawn with. 
     */
    private Color myColor;
    
    /**
     * The myStroke thickness that the shape should be drawn with. 
     */
    private Stroke myStroke;
    
    /**
     * The starting point of the shape;.
     */
    private Point myStartPoint;
    
    /**
     * Constructs a shape tool with the given shape, stroke, color, starting point, and 
     * reference to the program's GUI. 
     * @param aShape desired shape of tool.
     * @param aStroke desired stroke thickness of shape.
     * @param aColor desired color of shape.
     * @param aStartPoint desired starting point of shape. 
     */
    public ShapeTool(final Shape aShape, final Stroke aStroke, final Color aColor, 
                                             final Point aStartPoint) {
        
        setShape(aShape);
        myColor = aColor; 
        myStroke = aStroke;
        myStartPoint = (Point) aStartPoint.clone();
    }
    
    /**
     * Constructs a copy of the passed ShapeTool. 
     * @param aTool desired ShapeTool to be copied. 
     */
    public ShapeTool(final ShapeTool aTool) {
        this(aTool.getShape(), aTool.getStroke(), aTool.getColor(), aTool.getStartPoint());
    }
    
    /**
     * Sets the shape of this ShapeTool to the given shape. 
     * @param aShape desired shape of ShapeTool. 
     */
    public void setShape(final Shape aShape) {
        if (aShape instanceof Line2D.Double) {
            myShape = (Shape) ((Line2D.Double) aShape).clone();
        } else if (aShape instanceof RectangularShape) {
            myShape = (Shape) ((RectangularShape) aShape).clone();
        } else { 
            myShape = (Shape) ((GeneralPath) aShape).clone(); 
        }
    }
    
    /**
     * Returns the color of this ShapeTool. 
     * @return color stored in this ShapeTool. 
     */
    public Color getColor() {
        return myColor;
    }
    
    /**
     * Sets the color of this ShapeTool to the given color.
     * @param aColor desired color of shape. 
     */
    public void setColor(final Color aColor) {
        this.myColor = aColor;
    }
    
    /**
     * Returns the stroke of this ShapeTool. 
     * @return stroke stored in this ShapeTool. 
     */
    public Stroke getStroke() {
        return myStroke;
    }
    
    /**
     * Sets the stroke of this ShapeTool to the given stroke. 
     * @param aStroke desired stroke thickness of the shape. 
     */
    public void setStroke(final Stroke aStroke) {
        this.myStroke = aStroke;
    }
    
    /**
     * Returns the starting point of the shape. 
     * @return point that the shape should start at. 
     */
    public Point getStartPoint() {
        return myStartPoint;
    }
    
    /**
     * Sets the starting point of the shape. 
     * @param aStartPoint desired starting point of the shape. 
     */
    public void setStartPoint(final Point aStartPoint) {
        this.myStartPoint = (Point) aStartPoint.clone();
    }
    
    /**
     * Returns the shape stored in this ShapeTool. 
     * @return shape stored in this ShapeTool. 
     */
    public Shape getShape() {
        return myShape;
    }
}

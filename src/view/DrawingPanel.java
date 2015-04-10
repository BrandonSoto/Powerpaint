/*
 * TCSS 305 Winter 2014
 * Assignment 5 - Powerpaint 
 */
package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import model.ShapeTool;

/**
 * Represents a drawing panel that a user can draw shapes onto.
 * @author Brandon Soto
 * @version 2/12/14
 */
@SuppressWarnings("serial")
public final class DrawingPanel extends JPanel implements PropertyChangeListener {
    
    /**
     * Size of the user's screen. 
     */
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    
    /**
     * Default width for the drawing panel.
     */
    private static final int WIDTH = 500;

    /**
     * Default height for the drawing panel.
     */
    private static final int HEIGHT = 300;
    
    /**
     * Default background color for the drawing panel. 
     */
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    
    /**
     * Default color of the grid lines. 
     */
    private static final Color GRIDELINE_COLOR = Color.GRAY;
    
    /**
     * Default gridline thickness. 
     */
    private static final Stroke GRIDLINE_THICKNESS = new BasicStroke(1);
    
    /**
     * Default grid spacing. 
     */
    private static final int GRID_SPACING = 10; 

    /**
     * String used for firing property changes concerned with "undo" behavior. 
     */
    private static final String UNDO_ACTION = "Undo";
    
    /**
     * String used for firing property changes concerned with "redo" behavior. 
     */
    private static final String REDO_ACTION = "Redo";
    
    /**
     * Refers to the current ShapeTool selected.  
     */
    private final ShapeTool myCurrentTool;
    
    /**
     * List containing all the drawings the user has created. 
     */
    private final List<ShapeTool> myCurrentDrawings; 
    
    /**
     * List containing drawings when the user presses "undo". 
     */
    private final ArrayDeque<ShapeTool> myRedoDrawings;
    
    /**
     * Determines whether the grid should be visible. 
     */
    private boolean myGridIsVisible; 
    
    /**
     * Constructs a DrawingPanel with a preferred size of 300 pixels by 300
     * pixels and sets the background color of the panel to white.
     * @param theTool desired ShapeTool for this panel. 
     */
    public DrawingPanel(final ShapeTool theTool) {
        super();
        
        myCurrentTool = theTool;
        myCurrentDrawings = new ArrayList<ShapeTool>();
        myRedoDrawings = new ArrayDeque<ShapeTool>();
        myGridIsVisible = false; 
        
        setUpPanel();
    }
    
    /**
     * Adds mouse listeners to the panel and sets its preferred size and default background. 
     */
    private void setUpPanel() {
        final DrawingPanelListener listener = new DrawingPanelListener();
        
        addMouseListener(listener);
        addMouseMotionListener(listener);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(BACKGROUND_COLOR);
    }
    
    /**
     * Sets the current state of the grid. Set as true if the grid should be shown. Otherwise, 
     * set as false. 
     * @param aStatus desired status of the grid. 
     */
    private void setGridVisibility(final boolean aStatus) {
        myGridIsVisible = aStatus; 
    }
    
    /**
     * Clears the drawing panel of all drawings. 
     */
    private void clearDrawingPanel() {
        // fire property changes to disable undo and redo buttons
        firePropertyChange(UNDO_ACTION, null, false);
        firePropertyChange(REDO_ACTION, null, false);
        
        myCurrentDrawings.clear();
        myRedoDrawings.clear();
    }
    
    /**
     * Paints a shape that matches the selected tool. 
     * @param theGraphics graphic context. 
     */
    @Override
    public void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);

        final Graphics2D graphics2D = (Graphics2D) theGraphics;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                                         RenderingHints.VALUE_ANTIALIAS_ON);
        
        firePropertyChange(UNDO_ACTION, null, !myCurrentDrawings.isEmpty());
        firePropertyChange(REDO_ACTION, null, !myRedoDrawings.isEmpty());
        
        // draw all shapes in the list of drawings
        for (final ShapeTool shape : myCurrentDrawings) {
            graphics2D.setStroke(shape.getStroke());
            graphics2D.setPaint(shape.getColor());
            graphics2D.draw(shape.getShape());
        }
        
        // if the grid is visible, draw it to the screen.
        // grid is drawn last so that it lies on top of all shapes. 
        if (myGridIsVisible) {
            drawGridToPanel(graphics2D);
        }
    }

    /**
     * Specifically listens for events that indicate when a drawing needs to be put in the redo
     * list, when a drawing needs to be taken from the redo list, when the panel needs to be 
     * cleared, and when the grid needs to be turned on.
     * @param theEvent event when a property is changed. 
     */
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        switch(theEvent.getPropertyName()) {
            case "take from current list":
                myRedoDrawings.push(myCurrentDrawings.remove(myCurrentDrawings.size() - 1));
                break;
            case "put drawing back":
                myCurrentDrawings.add(myRedoDrawings.pop());
                break;
            case "clear drawing panel":
                clearDrawingPanel();
                break;
            case "set grid visibility":
                setGridVisibility((boolean) theEvent.getNewValue());
                break;
            default:
                break;
        }
        repaint();
    }
    
    /**
     * Draws the grid to the drawing panel. 
     * @param theGraphics graphic context of this drawing panel. 
     */
    private void drawGridToPanel(final Graphics2D theGraphics) {
        int start = 0;
        final Line2D.Double gridLine = new Line2D.Double();
        
        theGraphics.setStroke(GRIDLINE_THICKNESS);
        theGraphics.setPaint(GRIDELINE_COLOR);
        
        // creates vertical lines
        while (start <= SCREEN_SIZE.width) {
            theGraphics.draw(gridLine);
            gridLine.setLine(new Point(start, 0), new Point(start, (int) SCREEN_SIZE.height));
            start += GRID_SPACING;
        }
        
        start = 0;
        
        // creates horizontal lines
        while (start <= SCREEN_SIZE.height) {
            theGraphics.draw(gridLine);
            gridLine.setLine(new Point(0, start), new Point((int) SCREEN_SIZE.width, start));
            start += GRID_SPACING;
        }
    }

    /**
     * Contains mouse listeners that the drawing panel will use. These listeners will create
     * a new shape on the drawing canvas. These shapes include GeneralPath, Rectangle2D.Double,
     * Ellipse2D.Double, and Line2D.Double shapes. 
     * @author Brandon Soto
     * @version 2/12/14
     *
     */
    private class DrawingPanelListener extends MouseInputAdapter {
        /**
         * Draws the ShapeTool's current shape with the mouse's current coordinates. 
         * @param theEvent event when the mouse is dragged on the drawing panel.
         */
        @Override
        public void mouseDragged(final MouseEvent theEvent) {
            final Point endPoint = new Point(theEvent.getX(), theEvent.getY());
             
            final Shape shape = myCurrentTool.getShape();
            final Point start = myCurrentTool.getStartPoint();
        
            if (shape instanceof Line2D.Double) {
                ((Line2D.Double) shape).setLine(new Line2D.Double(start, endPoint));
            } else if (shape instanceof RectangularShape) {
                ((RectangularShape) shape).setFrame(getFrame(start, endPoint));
            } else if (shape instanceof GeneralPath) {
                ((GeneralPath) shape).lineTo(theEvent.getX(), theEvent.getY());
            }
             
            // keep replacing the shape until the mouse is released 
            myCurrentDrawings.remove(myCurrentDrawings.size() - 1);
            myCurrentDrawings.add(myCurrentTool);
            
            repaint();
        } 

        /**
         * Returns a bounding frame to make rectangular shapes from the two given points. 
         * @param aStart starting point of rectangle.
         * @param anEnd ending point of rectangle. 
         * @return bounding rectangle for a rectangular shape. 
         */
        private Rectangle2D.Double getFrame(final Point aStart, final Point anEnd) {
            final int x = (int) Math.min(aStart.getX(), anEnd.getX());
            final int y = (int) Math.min(aStart.getY(), anEnd.getY());
            final int width = (int) Math.max(aStart.getX() - anEnd.getX(),
                                                                 anEnd.getX() - aStart.getX());
            final int height = (int) Math.max(aStart.getY() - anEnd.getY(),
                                                              anEnd.getY() - aStart.getY());
            
            return new Rectangle2D.Double(x, y, width, height);
        }

        /**
         * Initializes the ShapeTool's starting point and shape. 
         * @param theEvent event when the mouse is pressed on the drawing panel. 
         */
        @Override
        public void mousePressed(final MouseEvent theEvent) {
            final Point start = new Point(theEvent.getPoint());

            final Shape shape = myCurrentTool.getShape();

            myCurrentTool.setStartPoint(new Point(start.x, start.y));

            if (shape instanceof Line2D) {
                ((Line2D.Double) shape).setLine(start, start);
            } else if (shape instanceof RectangularShape) {
                ((RectangularShape) shape).setFrame(new Rectangle2D.Double(start.x, start.y,
                                                                           0, 0));
            } else if (shape instanceof GeneralPath) {
                myCurrentTool.setShape(new GeneralPath());
                ((GeneralPath) myCurrentTool.getShape()).moveTo(start.x, start.y);
            }
            
            myCurrentDrawings.add(myCurrentTool);

            repaint();
        }

        /**
         * Adds a copy of the ShapeTool's current shape to the current drawings list. 
         * @param theEvent event when the mouse is released on the drawing panel. 
         */
        @Override
        public void mouseReleased(final MouseEvent theEvent) {
            // replace the shape at the end of the list with a copy of the
            // current shape
            myCurrentDrawings.remove(myCurrentDrawings.size() - 1);
            myCurrentDrawings.add(new ShapeTool(myCurrentTool));
            
            myRedoDrawings.clear();
            
            repaint();

        }

        /**
         * If the ShapeTool's shape is a GeneralPath (Pencil), then the path will be be given
         * an initial point to draw to. 
         * @param theEvent event when the mouse is clicked on the drawing panel. 
         */
        @Override
        public void mouseClicked(final MouseEvent theEvent) {
            final Shape shape = myCurrentTool.getShape();

            if (shape instanceof GeneralPath) {
                ((GeneralPath) shape).lineTo(theEvent.getX(), theEvent.getY());

                myCurrentDrawings.remove(myCurrentDrawings.size() - 1);
                myCurrentDrawings.add(new ShapeTool(myCurrentTool));
                
                repaint();
            }
        }
    }

   
}

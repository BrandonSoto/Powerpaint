/*
 * TCSS 305 Winter 2014 
 * Assignment 5 - Powerpaint 
 */
package view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.geom.GeneralPath;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import model.ShapeTool;

/**
 * The main class for the PowerPaint program. This class sets up the main frame and all of its
 * components. Namely, it sets up the main frame, the drawing panel, the toolbar, and the menu 
 * bar. 
 * @author Brandon Soto
 * @version 2/12/14
 */
public final class PaintGUI {
    
    /**
     * Calls the setUPGUI method to begin the program.
     */
    public PaintGUI() {
        setUpGUI();
    }

    /**
     * Sets up the GUI by making a main frame and adding components to that
     * frame.
     */
    private void setUpGUI() {
        final JFrame mainFrame = new JFrame("TCSS 305 PowerPaint");
        mainFrame.setIconImage(new ImageIcon("icons/color_fill.png").getImage());
        
        final ShapeTool shapeTool = new ShapeTool(new GeneralPath(), new BasicStroke(1), 
                                                                  Color.BLACK, new Point());
        
        // makes menu bar, drawing panel, and toolbar
        final DrawingPanel drawingPanel = new DrawingPanel(shapeTool);
        final MenuBar menuBar = new MenuBar(shapeTool, mainFrame);
        final Toolbar toolbar = new Toolbar();
        
        // lets undo/redo buttons enable and disable at proper times
        drawingPanel.addPropertyChangeListener(toolbar);
        
        // allows the undo/redo buttons trigger an events that can add and remove  drawings 
        // from the drawing panel
        toolbar.addPropertyChangeListener(drawingPanel);
        
        // lets you enable and disable the grid by the "Grid" item in Options menu and lets 
        // the "Clear" item in the File menu clear the drawing panel of drawings
        menuBar.addPropertyChangeListener(drawingPanel);
        
        // adds synced color tools to menu and toolbar
        final ColorToolAction colorTool = new ColorToolAction(shapeTool);
        toolbar.addColorTool(colorTool);
        menuBar.addColorTool(colorTool);

        final Action[] shapeActions = {new PencilAction(shapeTool), new LineAction(shapeTool), 
            new RectangleAction(shapeTool), new EllipseAction(shapeTool)};

        // adds synced shape tools to toolbar and menu bar
        for (final Action actionTool : shapeActions) {
            toolbar.addButtonToToolbar(actionTool);
            menuBar.addShapeToolToToolMenu(actionTool);
        }
        
        // adds undo and redo buttons after the color and shape buttons 
        toolbar.addUndoAndRedoButtonsToToolbar();
       
        mainFrame.setJMenuBar(menuBar);
        mainFrame.add(drawingPanel, BorderLayout.CENTER);
        mainFrame.add(toolbar, BorderLayout.SOUTH);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
    }
} // end PaintGUI class

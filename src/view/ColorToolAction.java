/*
 * TCSS 305 Winter 2014 
 * Assignment 5 - Powerpaint 
 */
package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;

import model.ShapeTool;

/**
 * Represents an action that a color selection tool can use. 
 * @author Brandon Soto
 * @version 2/12/14
 */
@SuppressWarnings("serial")
public final class ColorToolAction extends AbstractAction {
    
    /**
     * Default width of Color icon tool. 
     */
    private static final int WIDTH = 16; 
    
    /**
     * Default height of Color icon tool. 
     */
    private static final int HEIGHT = 16; 
    
    /**
     * Default color for the JColorChooser. 
     */
    private static final Color DEFAULT_COLOR = Color.BLACK;
    
    /**
     * ShapeTool currently shared by rest of program.
     */
    private final ShapeTool myCurrentTool;
    
    /**
     * Constructs a ColorToolAction with default values. These default values include a name, 
     * image icon, and a mnemonic. The drawing panel is passed so that the color tool can 
     * change the color of the shapes to be drawn. 
     * @param theTool ShapeTool shared by the rest of the program. 
     */
    public ColorToolAction(final ShapeTool theTool) {
        super();
        putValue(Action.NAME, "Color...");
        putValue(Action.SMALL_ICON, getIcon(DEFAULT_COLOR)); 
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
        myCurrentTool = theTool;
    }

    /**
     * Opens a JColorChooser and changes the color of the action's icon to the selected color.
     * @param theEvent event when a component using a ColorToolAction is pressed.
     */
    @Override
    public void actionPerformed(final ActionEvent theEvent) {
        // get the parent component of the GUI - this should be the main JFrame
        final Component component = ((Component) theEvent.getSource()).getParent().getParent();
        final Color color = JColorChooser.showDialog(component, "Color Chooser", 
                                                                    myCurrentTool.getColor()); 
        
        if (color != null) {
            putValue(Action.SMALL_ICON, getIcon(color));
            myCurrentTool.setColor(color);
        }
    }
    
    /**
     * Returns a new icon for the color tool. The icon is a box that is filled with a specific
     * color. 
     * @param theColor the color that the icon should be. 
     * @return colored icon for the color tool. 
     */
    private static ImageIcon getIcon(final Color theColor) {
        final BufferedImage image = new BufferedImage(WIDTH, HEIGHT, 
                                                                  BufferedImage.TYPE_INT_RGB);
        final Graphics2D graphics = (Graphics2D) image.getGraphics();
        
        graphics.setPaint(theColor); 
        // the rectangle's coordinates and dimensions allow it to have a black border
        graphics.fill(new Rectangle2D.Double(1, 1, WIDTH - 2, HEIGHT - 2));
        
        return new ImageIcon(image);
    }
    
}


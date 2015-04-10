package view;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import model.ShapeTool;

public interface GUIFactory {
    
    JPanel createDrawingPanel(final ShapeTool tool);
    JToolBar createToolbar();
    JMenuBar createMenu(final ShapeTool tool, final JFrame frame);

}

package view;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import model.ShapeTool;

public final class ConcreteGUIFactory implements GUIFactory {
    
    @Override
    public JPanel createDrawingPanel(final ShapeTool tool) {
        return new DrawingPanel(tool);
    }

    @Override
    public JToolBar createToolbar() {
        return new Toolbar();
    }

    @Override
    public JMenuBar createMenu(final ShapeTool tool, final JFrame frame) {
        return new MenuBar(tool, frame);
    }

}

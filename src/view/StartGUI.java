/*
 * TCSS 305 Winter 2014
 * Assignment 5 - Powerpaint 
 */
package view;

import java.awt.EventQueue;

/**
 * This class starts a PaintGUI by creating a PaintGUI. 
 * @author Brandon Soto
 * @version 2/12/14
 */
public final class StartGUI {
    
    /**
     * Ensures that StartGUI is not instantiated. 
     */
    private StartGUI() {
        // do nothing
    }

    /**
     * Calls the PaintGUI constructor to begin the program. 
     * @param theArgs arguments from command line. 
     */
    public static void main(final String[] theArgs) {
        EventQueue.invokeLater(new Runnable() {
            /**
             * Calls the PaintGUI's constructor to run Powerpaint. 
             */
            @Override
            public void run() {
                new PaintGUI();
            }
        });
    }

}

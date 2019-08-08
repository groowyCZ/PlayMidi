package utils;


import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author groowy
 */
public class Utils {
    public static void showError(String title, String err, Component parent) {
        JOptionPane.showMessageDialog(parent, err, title, JOptionPane.ERROR_MESSAGE);
    }
    
    public static void showError(String err, Component parent) {
        JOptionPane.showMessageDialog(null, err, "MidiPlay error", JOptionPane.ERROR_MESSAGE);
    }
    public static void showInfo(String title, String err, Component parent) {
        JOptionPane.showMessageDialog(null, err, title, JOptionPane.ERROR_MESSAGE);
    }
    
    public static void showInfo(String err, Component parent) {
        JOptionPane.showMessageDialog(null, err, "MidiPlay error", JOptionPane.ERROR_MESSAGE);
    }
}
package main;

public class Main {
    public static void main(String[] args) {
        MainWindow window = new MainWindow();
        window.setLocationRelativeTo(null);
        window.setMinimumSize(window.getSize());
        window.setTitle("MidiPlay");
        window.setVisible(true);
    }
}

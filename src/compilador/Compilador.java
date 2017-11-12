package compilador;

import javax.swing.*;

public class Compilador {

    public static void main(String[] args) {
        IDE editor = new IDE();
        editor.setSize(800, 600);
        editor.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        editor.setResizable(true);
        editor.setVisible(true);
    }
}

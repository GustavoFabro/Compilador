package util;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author gustavofabro
 */
public class Button extends  JButton{
    public Button(String iconPath, String actionCommand, ActionListener al){
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setMaximumSize(new Dimension(30, 30));

        addActionListener(al);
        setIcon(new ImageIcon(getClass().getResource(iconPath)));
        setActionCommand(actionCommand);
    }
}
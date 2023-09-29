package ui.view.component;

import javax.swing.*;
import java.awt.*;

import static java.awt.Font.BOLD;
import static java.lang.Math.round;

public class HeaderLabel extends JLabel {
    public HeaderLabel(String text) {
        super(text);

        Font currentFont = getFont();
        setFont(new Font(currentFont.getName(), BOLD, round(currentFont.getSize() * 1.5f)));
    }
}

package com.logicsim;

import javax.swing.*;
import java.awt.*;

public class ImageLoader {

    static Image loadImage(String location) {
        ImageIcon imageIcon = new ImageIcon(location);
        Image image = imageIcon.getImage();
        return image;
    }
}

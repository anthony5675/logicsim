package com.logicsim;

import javax.imageio.ImageIO;
import java.awt.*;
import java.net.URL;

/**
 * Class to allow easy image retrieval
 * @author Jayden, Andre, Mitchell, Anthony
 */
public class ImageLoader {
	
	/**
	 * Static method to return an image object from a location as a String
	 * @param location == Where the image is in relation to the class
	 * @return An image object that can be used to draw
	 */
    public static Image loadImage(String location) {
    	Image image = null;
		try {
			URL url = ImageLoader.class.getResource(location);
			image = ImageIO.read(url);
		} catch(Exception e) {
		}
	
        return image;
    }
}

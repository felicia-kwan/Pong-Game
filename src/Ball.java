import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Ball extends Rectangle{
	
	Random random;
	int xspeed;
	int yspeed;
	int initialSpeed = 2;
	private BufferedImage image;

	
	Ball(int x, int y, int width, int height){
		super(x,y,width,height);
		random = new Random();
		int xDirection = random.nextInt(2);
		if(xDirection == 0) {
			xDirection--;
		}
		setXDirection(xDirection);
		
		int yDirection = random.nextInt(2);
		if(yDirection == 0) {
			yDirection--;
		}
		setYDirection(yDirection*initialSpeed);
		
		try {
			image = ImageIO.read(new File("resources/ball1.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void setXDirection(int xDirection) {
		xspeed = xDirection;
	}
	
	public void setYDirection(int yDirection) {
		yspeed = yDirection;
	}
	public void move() {
		x+= xspeed;
		y+= yspeed;
	}
	public void draw(Graphics g) {
		g.drawImage(image, x, y, null);

	}
}

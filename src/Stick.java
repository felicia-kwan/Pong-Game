import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Stick extends Rectangle{
	
	int player; //klo 1, berarti player 1 stick 1
	int yspeed; //seberapa cepet sticknya naek turun
	int speed = 10;
	
	private static String path;
	private BufferedImage image;
	
	Stick(int x, int y, int STICK_WIDTH, int STICK_HEIGHT, int player, String path){
		super(x,y,STICK_WIDTH, STICK_HEIGHT);
		this.player = player;
		this.path = path;
		
		try {
			image = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}
	
	public void keyPressed(KeyEvent e) {
		switch(player) {
		case 1:
			if(e.getKeyCode() == KeyEvent.VK_W) {
				setYDirection(-speed);
				move();
			}
			if(e.getKeyCode() == KeyEvent.VK_S) {
				setYDirection(speed);
				move();
			}
			break;
		case 2:
			if(e.getKeyCode() == KeyEvent.VK_UP) {
				setYDirection(-speed);
				move();
			}
			if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				setYDirection(speed);
				move();
			}
			break;
		}
	}
	
	public void keyReleased(KeyEvent e) {
		switch(player) {
		case 1:
			if(e.getKeyCode() == KeyEvent.VK_W) {
				setYDirection(0);
				move();
			}
			if(e.getKeyCode() == KeyEvent.VK_S) {
				setYDirection(0);
				move();
			}
			break;
		case 2:
			if(e.getKeyCode() == KeyEvent.VK_UP) {
				setYDirection(0);
				move();
			}
			if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				setYDirection(0);
				move();
			}
			break;
		}
	}
	
	public void setYDirection(int yDirection) {
		yspeed = yDirection;
	}
	
	public void move() {
		y = y + yspeed;
	}
	
	public void draw(Graphics g) {
		
		g.drawImage(image, x, y, null);
		
	}
}

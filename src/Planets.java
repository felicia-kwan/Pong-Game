import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class Planets extends JPanel {
	private BufferedImage bg;
	 private int yOffset = 0;
	  private int yDelta = 4;

	  public Planets() {
	    try {
	      bg = ImageIO.read(new File("resources/planets.png"));
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
	    
	  }

	

	  public void paint(Graphics g){
		  Graphics2D g2d = (Graphics2D) g.create();

			  g2d.drawImage(bg, 0, 0, null);

	   }

	  

	
}

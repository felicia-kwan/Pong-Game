import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Panel extends JPanel implements Runnable{
	
	static final int SCREEN_WIDTH = 1000;
	static final int SCREEN_HEIGHT = (int)(SCREEN_WIDTH * (0.5555));
	static final Dimension SCREEN_SIZE = new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT);
	static final int BALL_DIAMETER = 35;
	static final int STICK_WIDTH = 48;
	static final int STICK_HEIGHT = 100;
	int WINNING_POINT = 10;
	Thread gameThread;
	Image image;
	Graphics graphics;
	Random random;
	Stick stick1;
	Stick stick2;
	Ball ball;
	Score score;
	Image bgImage;
	Planets planet = new Planets();
	Graphics g2;
	
	Panel(){
		stickBaru();
		ballBaru();
		
		score = new Score(SCREEN_WIDTH, SCREEN_HEIGHT);
		this.setFocusable(true);
		this.addKeyListener(new AL());
		this.setPreferredSize(SCREEN_SIZE);
		
		try {
			bgImage = ImageIO.read(new File("resources/universe.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	
	public void ballBaru() {
		random = new Random();
		ball = new Ball((SCREEN_WIDTH/2)-(BALL_DIAMETER/2), random.nextInt(SCREEN_HEIGHT-BALL_DIAMETER), BALL_DIAMETER, BALL_DIAMETER);

	}
	public void stickBaru() {
		stick1 = new Stick(0,(SCREEN_HEIGHT/2)-(STICK_HEIGHT/2), STICK_WIDTH, STICK_HEIGHT, 1, "resources/pong1.png");
		stick2 = new Stick(SCREEN_WIDTH-STICK_WIDTH,(SCREEN_HEIGHT/2)-(STICK_HEIGHT/2), STICK_WIDTH, STICK_HEIGHT, 2, "resources/pong2.png");
	}
	
	public void paint(Graphics g) {
		image = createImage(getWidth(), getHeight());
		graphics = image.getGraphics();
		draw(graphics);
		g.drawImage(image, 0, 0, this);
	}
	
	public void draw(Graphics g) {
		paintComponent(g);
		planet.paint(g);
		score.draw(g);

		if(score.player1 > WINNING_POINT - 1 || score.player2 > WINNING_POINT - 1) {
			g.setColor(Color.white);
			g.drawRect(SCREEN_WIDTH/2 - 155, SCREEN_HEIGHT/2 - 55, 300, 130);
			g.fillRect(SCREEN_WIDTH/2 - 155, SCREEN_HEIGHT/2 - 55, 300, 130);
			g.setColor(Color.red);
			g.setFont(new Font("Consolas", Font.BOLD,60));
			g.drawString("Game Over", SCREEN_WIDTH/2-150, SCREEN_HEIGHT/2);
			
			g.setFont(new Font("Consolas", Font.PLAIN,35));
			if(score.player1 > WINNING_POINT - 1) {
				g.drawString("Player 1 Wins!", SCREEN_WIDTH/2 - 135, SCREEN_HEIGHT/2 + 50);
			}else if(score.player2 > WINNING_POINT - 1) {
				g.drawString("Player 2 Wins!", SCREEN_WIDTH/2 - 135, SCREEN_HEIGHT/2 + 50);

			}
			
			TimerTask task = new TimerTask() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					score.player1 = 0;
					score.player2 = 0;
				}
			};
			Timer timer = new Timer("Timer");
			
			long delay = 1000L;
			timer.schedule(task, delay);
			
		}else {
			stick1.draw(g);
			stick2.draw(g);
			ball.draw(g);
		}
		
		
	}
	public void move() {
		//biar lebi mulus gerakan sticknya
		stick1.move();
		stick2.move();
		ball.move();
	}
	public void checkCollision() {
		//biar sticknya gak keluar layar
		if(stick1.y <=0) {
			stick1.y = 0;
		}
		if(stick1.y >= (SCREEN_HEIGHT - STICK_HEIGHT)) {
			stick1.y = SCREEN_HEIGHT - STICK_HEIGHT;
		}
		
		if(stick2.y <=0) {
			stick2.y = 0;
		}
		if(stick2.y >= (SCREEN_HEIGHT - STICK_HEIGHT)) {
			stick2.y = SCREEN_HEIGHT - STICK_HEIGHT;
		}
		
		//biar bolanya bounce atas bawah edge nya
		if(ball.y <= 0) {
			ball.setYDirection(-ball.yspeed);
		}
		if(ball.y >= SCREEN_HEIGHT-BALL_DIAMETER) {
			ball.setYDirection(-ball.yspeed);
		}
		
		//biar bolanya ke bounce pas kena stick
		if(ball.intersects(stick1)) {
			ball.xspeed = Math.abs(ball.xspeed);
			ball.xspeed++; //ini bikin bolanya nambah cepet pas abis kena stick
			if(ball.yspeed > 0) {
				ball.yspeed++;
			}else {
				ball.yspeed--;
			}
			ball.setXDirection(ball.xspeed);
			ball.setYDirection(ball.yspeed);
		}
		
		if(ball.intersects(stick2)) {
			ball.xspeed = Math.abs(ball.xspeed);
			ball.xspeed++; //ini bikin bolanya nambah cepet pas abis kena stick
			if(ball.yspeed > 0) {
				ball.yspeed++;
			}else {
				ball.yspeed--;
			}
			ball.setXDirection(-ball.xspeed);
			ball.setYDirection(ball.yspeed);
		}
		
		//kasi player poin dan bkin stick baru n ball baru
		if(ball.x <= 0) {
			score.player2++;
			stickBaru();
			ballBaru();
			System.out.println("Player2 " + score.player2);
		}
		
		if(ball.x >= SCREEN_WIDTH - BALL_DIAMETER) {
			score.player1++;
			stickBaru();
			ballBaru();
			System.out.println("Player 1 " + score.player1);
		}
		
	}
	public void run() {
		//game loop
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000/amountOfTicks;
		double delta = 0;
		while(true) {
			long now = System.nanoTime();
			delta += (now - lastTime)/ns;
			lastTime = now;
			if(delta >= 1) {
				move();
				checkCollision();
				repaint();
				delta--;
			}
		}
	}
	

	
	public class AL extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			stick1.keyPressed(e);
			stick2.keyPressed(e);
		}
		public void keyReleased(KeyEvent e) {
			stick1.keyReleased(e);
			stick2.keyReleased(e);
		}
	}
	
	@Override
	  protected void paintComponent(Graphics g) {

	    super.paintComponent(g);
	        g.drawImage(bgImage, 0, 0, null);

	}
}

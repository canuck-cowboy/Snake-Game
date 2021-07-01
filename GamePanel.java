package eScape;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener 
{
	static final int screenWidth = 600;
	static final int screenHeight = 600;
	static final int unitSize = 25;
	static final int gameUnits = (screenWidth * screenHeight) / unitSize;
	static int delay = 60;
	
	final int [] x = new int[gameUnits];
	final int [] y = new int[gameUnits];
	
	int bodyParts = 6;
	int applesEaten = 0;
	int appleX;
	int appleY;
	
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	
	public GamePanel()
	{
		random = new Random ();
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.white);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	
	}
	
	public void startGame()
	{
		newApple();
		running = true;
		timer = new Timer(delay, this);
		timer.start();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		draw(g);
	}
	public void draw (Graphics g)
	{
	  if(running) {
		for (int i = 0; i < screenHeight/ unitSize; i++)
		{
			g.drawLine(i * unitSize, 0, i * unitSize, screenHeight);
			g.drawLine(0, i * unitSize, screenWidth, i * unitSize);
		}
		g.setColor(Color.RED);
		g.fillOval(appleX, appleY, unitSize, unitSize);
		
		for (int i = 0; i < bodyParts; i++) 
		{
			if (i == 0) {
				g.setColor(Color.green);
				g.fillRect(x[i], y[i], unitSize, unitSize);
			}
			else
			{
				g.setColor(Color.GREEN);
				g.fillRect(x[i], y[i], unitSize, unitSize);
			}
			g.setColor(Color.BLUE);
			g.setFont(new Font("Arial", Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: " + applesEaten, (screenWidth - metrics.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());
			
				
		}	
	  }
	  else {
		  gameOver(g);
	  }
	}
	
	public void newApple()
	{
		appleX = random.nextInt(screenWidth / unitSize) * unitSize;
		appleY = random.nextInt(screenHeight / unitSize) * unitSize;
	}
	
	public void move ()
	{
		for (int i = bodyParts; i > 0; i--) 
		{
			x[i] = x[i-1];
			y[i] = y[i -1];
		}
		switch (direction)
		{
			case 'U':
				y[0] = y[0] - unitSize;
				break;
				
			case 'D':
				y[0] = y[0] + unitSize;
				break;
				
			case 'L':
				x[0] = x[0] - unitSize;
				break;
			case 'R':
				x[0] = x[0] + unitSize;
				break;
		}
		
	}
	
	public void checkApple()
	{
		if (x[0] == appleX && y[0] == appleY)
		{
			bodyParts++;
			applesEaten++;
			delay+=5;
			newApple();
		}
	}
	
	public void checkCollisions()
	{
		for (int i = bodyParts; i > 0; i--)
		{
			// if snake eats itself
			if (x[0] == x[i] && y[0] == y[i]) {
				running = false;
			}
			
			// if head touches left border
			if (x[0] < 0) 
				running = false;
			if (y[0] < 0)
				running = false;
			// if head touches right
			if (x[0] > screenWidth)
				running = false;
			if(y[0] > screenHeight)
				running = false;
			
			if (!running)
				timer.stop();
		}
		
	}
	
	public void gameOver(Graphics g)
	{
		// Game over text
		g.setColor(Color.BLUE);
		g.setFont(new Font("Arial", Font.BOLD, 75));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Game Over", (screenWidth - metrics.stringWidth("Game Over"))/2, screenHeight/2);
		
		g.setColor(Color.BLUE);
		g.setFont(new Font("Arial", Font.BOLD, 40));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Score: " + applesEaten, (screenWidth - metrics2.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// TODO Auto-generated method stub
		if (running)
		{
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
		
	}
	
	public class MyKeyAdapter extends KeyAdapter 
	{
		@Override
		public void keyPressed(KeyEvent e)
		{
			switch(e.getKeyCode())
			{
				case KeyEvent.VK_LEFT:
					if (direction != 'R')
						direction = 'L';
					break;
				
				case KeyEvent.VK_RIGHT:
					if (direction != 'L')
						direction = 'R';
					break;
				
				case KeyEvent.VK_UP:
					if (direction != 'D')
						direction = 'U';
					break;
					
				
				case KeyEvent.VK_DOWN:
					if (direction != 'U')
						direction = 'D';
					break;
					
					
			}
		}
	}

}

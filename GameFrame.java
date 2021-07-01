package eScape;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
	
	public GameFrame ()
	{
		GamePanel panel = new GamePanel();
		this.add(panel);
		this.setTitle("Abed's Snake Game");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack(); // to fit the components
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
	
}

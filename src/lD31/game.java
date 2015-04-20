package lD31;
import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class game extends Canvas implements Runnable{
	
	private static final long serialVersionUID = -5094738333840760041L;
	static int WIDTH=850;
	static int HEIGHT = 600;
	Screen screen,sideBar;
	Sprite[] blocks = new Sprite[5];
	BonusSpawner bonusSpawner = new BonusSpawner(10000);
	Spawner spawner = new Spawner();
	ControllableAnimatedSprite player;
	public static long score=0;
	public static boolean[] key = new boolean[32767];
	
	public void run() {
		long lastTime = System.currentTimeMillis();
		long delta = System.currentTimeMillis() - lastTime;
		init();
		render();	
		while(!key[KeyEvent.VK_SPACE])
		showScreen(getClass().getClassLoader().getResource("startScreen.png"),false);
		lastTime = System.currentTimeMillis();
		delta = System.currentTimeMillis() - lastTime;
		while (delta<200){
			delta = System.currentTimeMillis() - lastTime;
		}
		while(!key[KeyEvent.VK_SPACE])
		showScreen(getClass().getClassLoader().getResource("controlScreen.png"),false);
		lastTime = System.currentTimeMillis();
		delta = System.currentTimeMillis() - lastTime;
		while (delta<200){
			delta = System.currentTimeMillis() - lastTime;
		}
		score=0;
		while(player.health>-20)
		{
			
			delta = System.currentTimeMillis() - lastTime;
			if (delta>=15)
			{

				lastTime = System.currentTimeMillis();
				render();
				score+=delta/10;
			}
		}
		while(!key[KeyEvent.VK_SPACE])
			showScreen(getClass().getClassLoader().getResource("gameOver.png"), true);
		System.exit(0);
	}
	
	private void render() {
		WIDTH = this.getWidth();
		HEIGHT = this.getHeight();
		BufferStrategy bs = getBufferStrategy();
		if (bs==null)
		{
			createBufferStrategy(2);
			requestFocus();
			return;
		}
		Graphics g = bs.getDrawGraphics();
		screen.render();
		updateSideBar();
		bonusSpawner.update(screen, player);
		spawner.update(screen);
		player.contorl(key,screen);
		Graphics g2 = screen.getGraphics();
		g2.setFont(new Font("Verdana",Font.PLAIN, 7));
		g2.drawString("Score: "+score, 10, 5);
		g.drawImage(screen,0,0,HEIGHT+10, HEIGHT+10, null);
		g.drawImage(sideBar, HEIGHT+10,0,(int) ((HEIGHT+10)/2.5),HEIGHT+10,null);
		g.dispose();
		bs.show();		
	}

	private void updateSideBar() {
		
		sideBar.getSprite(10).setWidth(19*player.health/100);
		for (int i=0; i<10; i++)
		{
			String s= null;
			if (i<player.getBlocks().size())
				s = player.getBlocks().get(i);
			if (s==null) ((Sprite)sideBar.getSprite(i)).setImg(blocks[0].getImg());		else{
				if (s.equals("box.png")) ((Sprite)sideBar.getSprite(i)).setImg(blocks[1].getImg());
				if (s.equals("dirt.png")) ((Sprite)sideBar.getSprite(i)).setImg(blocks[2].getImg()); 
				if (s.equals("rock.png")) ((Sprite)sideBar.getSprite(i)).setImg(blocks[3].getImg());
				if (s.equals("steel.png")) ((Sprite)sideBar.getSprite(i)).setImg(blocks[4].getImg());
			}
		}
		sideBar.render();
	}

	private void showScreen(URL path, boolean showScore) {
		WIDTH = this.getWidth();
		HEIGHT = this.getHeight();
		BufferStrategy bs = getBufferStrategy();
		if (bs==null)
		{
			createBufferStrategy(2);
			requestFocus();
			return;
		}
		Graphics g = bs.getDrawGraphics();
		BufferedImage img = null;
				try {
					img = ImageIO.read(path);
				} catch (IOException e) {
				}
		if (showScore){
		Graphics g2 = img.getGraphics();		
		g2.setFont(new Font("Verdana",Font.PLAIN, 7));
		g2.drawString("Your Score: "+score, 10, 65);
		}
		g.drawImage(img,0,0,WIDTH+10, HEIGHT+10, null);
		
		g.dispose();
		bs.show();		

	}

	
	
	private void init() {
		blocks[0] = new Sprite (getClass().getClassLoader().getResource("none.png"));
		blocks[1] = new Sprite (getClass().getClassLoader().getResource("box.png"));
		blocks[2] = new Sprite (getClass().getClassLoader().getResource("dirt.png"));
		blocks[3] = new Sprite (getClass().getClassLoader().getResource("rock.png"));
		blocks[4] = new Sprite (getClass().getClassLoader().getResource("steel.png"));
		
		screen = new Screen(80,80);
		screen.setBackGround(getClass().getClassLoader().getResource("backgr.png"));
		player = new ControllableAnimatedSprite(getClass().getClassLoader().getResource("player.png"), 8, 8);
		player.setPosition(38, 38);
		screen.addSprite(player);
		
		sideBar = new Screen(32,80);
		for (int i=0; i<10; i++)
		{
			Sprite temp = new Sprite(blocks[0].img);
			temp.setPosition(7+(i%2)*10, 19 + (i/2)*10);
			sideBar.addSprite(temp);
		}
		Sprite temp = new Sprite(getClass().getClassLoader().getResource("healthline.png"));
		temp.setPosition(6, 12);
		sideBar.addSprite(temp);
		
		sideBar.addSprite(getClass().getClassLoader().getResource("sidepane.png"));
		
		player.setDelta(80);
		player.setDefaultBoxCollider();
		GameObject LeftBorder = new GameObject(0, 0, 8, 80);
		LeftBorder.setDefaultBoxCollider();
		screen.addGameObject(LeftBorder);
		GameObject RightBorder = new GameObject(72, 0, 8, 80);
		RightBorder.setDefaultBoxCollider();
		screen.addGameObject(RightBorder);
		GameObject TopBorder = new GameObject(0, 0, 80, 8);
		TopBorder.setDefaultBoxCollider();
		screen.addGameObject(TopBorder);
		GameObject BotBorder = new GameObject(0, 72, 80, 8);
		BotBorder.setDefaultBoxCollider();
		screen.addGameObject(BotBorder);

	}

	private void start() {
		enableEvents(AWTEvent.KEY_EVENT_MASK);
		new Thread(this).start();		
	}
	
	public static void main(String[] args)
	{
		game Game = new game();
		Game.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		JFrame frame = new JFrame("SurvivalPuzzle");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((int)screenSize.getWidth()/2-WIDTH/2, (int)screenSize.getHeight()/2-HEIGHT/2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(Game, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(true);
		frame.setVisible(true);
		Game.start();
	}
	
	public void processEvent(AWTEvent e)
	{
		boolean down = false;
		switch(e.getID())
		{
		case KeyEvent.KEY_PRESSED:
			down = true;
		case KeyEvent.KEY_RELEASED:
			key[((KeyEvent)e).getKeyCode()] = down;
			break;
		}
	}
	
}

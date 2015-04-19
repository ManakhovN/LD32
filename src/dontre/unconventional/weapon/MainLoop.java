package dontre.unconventional.weapon;
import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Random;

import javax.swing.JFrame;

public class MainLoop extends Canvas implements Runnable{
	public static final String NAME = "game";
	private static final long serialVersionUID = 1L;
	public static final int HEIGHT = 60;
	public static final int WIDTH = 80;
	public static final int SCALE = 6;
	public int[] popularColors = {0xFF0f0fFF, 0xFF0fFF0f, 0xFFFF0f0f,
								  0xFF0fFFFF, 0xFFFF0fFF, 0xFFFFFF0f,
								  0xFF0fFF00, 0xFFFF0fFF};
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
	private BufferedImage backgr = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
	MultipleTexture texture = new MultipleTexture(this.getClass().getClassLoader().getResource("tiles.png"), 8 , 8);
	Screen screen = new Screen(((DataBufferInt)image.getRaster().getDataBuffer()).getData(), image.getWidth(),image.getHeight(),0,80,10);
	Screen background = new Screen(((DataBufferInt)backgr.getRaster().getDataBuffer()).getData(), image.getWidth(),image.getHeight(),1,160,20);
	ZombiesController zombiesController;
	public static boolean[] key = new boolean[32767];
	PlayerController playerController;
	SimplePhysicController physicController = new SimplePhysicController(screen);
	private boolean running = false;
	public void start()
	{
		running = true;
		enableEvents(AWTEvent.KEY_EVENT_MASK);
	//	Sprite sprite = new Sprite(10, 50, 8, 8, texture.parts[0]);
	//	sprite.setSimpleBitmask(new SimpleBitmask(0x00FF00));
	//	screen.addSprite(sprite);
		Sprite pixel = new Sprite(10,40, 1,1, new int[]{0xFFFF0000});
		this.physicController.addBody(pixel,0f,0.0001f,0.01f,0f, true);
		//screen.addSprite(pixel);
		MultipleTexture texture = new MultipleTexture(this.getClass().getClassLoader().getResource("character.png"), 13 , 8);
		AnimatedSprite sprite = new AnimatedSprite(40, 20,texture);
		sprite.setAnimationBounds(0, 3);
		sprite.setUpdateTime(100);
		screen.addSprite(sprite);
		PhysicBody player = new PhysicBody(sprite,0f,0.001f,0f,0f, true);
		this.physicController.addBody(player);
		playerController = new PlayerController(player);
		zombiesController = new ZombiesController(playerController, physicController,screen);
		Random r = new Random();
		
		MultipleTexture zombieTexture = new MultipleTexture(this.getClass().getClassLoader().getResource("zombie.png"), 13 , 8);
		for (int i=0; i<100; i++){
			AnimatedSprite zombie = new AnimatedSprite(40 + r.nextInt(600), 20,zombieTexture);
			PhysicBody zombieBody = new PhysicBody(zombie,0f,0.001f,0f,0f, true);
			physicController.addBody(zombieBody);
			screen.addSprite(zombie);
			zombiesController.addZombie(zombieBody);
		}
		MultipleTexture buildingTexture = new MultipleTexture(this.getClass().getClassLoader().getResource("buildings.png"), 49, 27);
		screen.addSprite(new Sprite(0, 0, 27, 49, buildingTexture.parts[0]));

		for (int i = 0; i<30; i++)
		{
			Sprite spr = new Sprite((i+1)*25, -1, 27,49, buildingTexture.parts[r.nextInt(4)+1]);
			spr.getSimpleBitmask().mask = this.popularColors[r.nextInt(popularColors.length)];
			background.addSprite(spr);
		}
		new Thread(this).start();
	}
	
	public void stop()
	{
		running = false;
	}
	
	public void run()
	{
		long lastTime = System.nanoTime();
		double unprocessed = 0;
		double nsPerTick = 1000000000.0/60;
		int frames =0;
		long lastTimer1 = System.currentTimeMillis();
			int ticks=0;	
		while(running){
			long now = System.nanoTime();
			unprocessed += (now - lastTime)/nsPerTick;
			lastTime=now;
			boolean shouldRender = true;
			while(unprocessed >=1)
			{
				ticks++;
				unprocessed -=1;
				shouldRender = true;
			}
			
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (shouldRender) {
				frames++;
				render();
			}
			if (System.currentTimeMillis() - lastTimer1>1000)
			{
				lastTimer1+=1000;
				System.out.println(ticks+" ticks "+frames + " fps");
				frames=0;
				ticks=0;
			}
		}
	}

	@SuppressWarnings("static-access")
	public void render()
	{
		BufferStrategy bs = this.getBufferStrategy();
		if (bs==null)
		{
			this.createBufferStrategy(3);
			return;
		}
		//it`s looks like we need to calculate current 2darray position as y*WIDTH+x
		Graphics g = bs.getDrawGraphics();
		background.render();
		screen.render();
		screen.clearDeadSprites(physicController);
		screen.scrollX = -playerController.player.sprite.x + this.WIDTH/2;
		background.scrollX = screen.scrollX*1.2f;
		g.drawImage(backgr, -6,-6, getWidth()+6, getHeight()+10,null);
		g.drawImage(image,-6,-6, getWidth()+6, getHeight()+10,null);
		g.dispose();
		bs.show();
		playerController.update();
		physicController.update();
		zombiesController.update();
	}
	
	public static void main(String[] args)
	{
		MainLoop game = new MainLoop();
		game.setMinimumSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		game.setMaximumSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		game.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		JFrame frame = new JFrame(MainLoop.NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(game);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		game.start();
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

package dontre.unconventional.weapon;

import java.awt.event.KeyEvent;

public class PlayerController {
	PhysicBody player = null;
	float health = 100;
	Screen screen;
	Sprite pickedStuff;
	boolean tryingToPick;
	long timer = 0;
	SimplePhysicController physicController;
	public PlayerController(PhysicBody player, SimplePhysicController physicController, Screen screen)
	{
		this.physicController = physicController;
		this.player = player;
		this.screen = screen;
	}
	
	public void update()
	{
		
		AnimatedSprite pl = ((AnimatedSprite)player.getSprite());
		pl.setAnimationBounds(pl.animationStartFrame, pl.animationStartFrame);
		if (MainLoop.key[KeyEvent.VK_RIGHT] && pl.getX()<625){
			player.velocityX = 0.1f; 
			pl.setAnimationBounds(0, 3);
			if (player.accelerationY!=0f)
				pl.setAnimationBounds(0+8, 3+8); else 
			pl.setAnimationBounds(0, 3);
		}
		if (MainLoop.key[KeyEvent.VK_LEFT] && pl.getX()>40){
			player.velocityX = -0.1f;
			if (player.accelerationY!=0f)
				pl.setAnimationBounds(4+8, 7+8); else 
			pl.setAnimationBounds(4, 7);
		}
		if (MainLoop.key[KeyEvent.VK_UP] && player.accelerationY==0f){
			player.velocityY = -0.5f;
			player.accelerationY = 0.01f;
			if (pl.animationStartFrame == 0)
				pl.setAnimationBounds(0+8, 3+8); else 
					pl.setAnimationBounds(4+8, 7+8);
		}
		if (pickedStuff!=null){
			pickedStuff.x = player.sprite.x;
			pickedStuff.y = player.sprite.y+player.sprite.height/2 - pickedStuff.height;
		}
		if (MainLoop.key[KeyEvent.VK_DOWN] && System.currentTimeMillis()-timer>1000)
		{
			if (pickedStuff !=null)
			{
				int playerWay = pl.animationStartFrame==0 || pl.animationStartFrame==8?1:-1;
				PhysicBody body = physicController.getBodyBySprite(pickedStuff);
				body.setVelocity(playerWay*0.2f,-0.2f);
				body.setAcceleration(0, 0.01f);
				timer = System.currentTimeMillis();
				if (pickedStuff.getClass() == AnimatedSpriteWithHealth.class) ((AnimatedSpriteWithHealth)pickedStuff).makeDamage(10f);
				if (pickedStuff.getClass() == SpriteWithHealth.class) ((SpriteWithHealth)pickedStuff).makeDamage(10f);
				
				pickedStuff.isThrowed = true;
				pickedStuff=null;
			} else{
				Sprite closest = FindClosestCollision(); 
				if (closest!=null){
				 timer = System.currentTimeMillis();
				 pickedStuff = closest; 
				}
			}
		}
	}
	
	public Sprite FindClosestCollision()
	{
		if (screen==null) return null;
		for (Sprite spr:screen.sprites)
		{
			if (spr.isCollidesWithBox(this.player.sprite) && spr.isPickable)
				return spr;
		}
		return null;
	}
}

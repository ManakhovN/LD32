package dontre.unconventional.weapon;

import java.awt.event.KeyEvent;

public class PlayerController {
	PhysicBody player = null;
	float health = 100;
	public PlayerController(PhysicBody player)
	{
		this.player = player;
	}
	
	public void update()
	{
		
		AnimatedSprite pl = ((AnimatedSprite)player.getSprite());
		pl.setAnimationBounds(pl.animationStartFrame, pl.animationStartFrame);
		if (MainLoop.key[KeyEvent.VK_RIGHT]){
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
	}
}

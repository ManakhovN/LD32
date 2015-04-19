package dontre.unconventional.weapon;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ZombiesController {
	List<PhysicBody> zombies = new ArrayList<PhysicBody>();
	PlayerController enemy;
	SimplePhysicController physicController;
	Screen screen;
	public ZombiesController(PlayerController enemy, SimplePhysicController physicController, Screen screen)
	{
		this.enemy = enemy;
		this.physicController = physicController;
		this.screen = screen;
	}
	public void addZombie(PhysicBody zombie)
	{
		zombies.add(zombie);
		
	}
	
	public void update()
	{
		Random r = new Random();
		Sprite spr = enemy.player.getSprite();
		for (PhysicBody zombie:zombies)
		{
			float distance = spr.getDistance(zombie.getSprite());
			if (distance>=spr.width && distance<= 100) {
				 if (spr.x - zombie.sprite.x>0)
					 {
					 zombie.velocityX = r.nextFloat()*0.025f;
					 	((AnimatedSprite)zombie.sprite).setAnimationBounds(0, 3);
					 }
				 else 
				 {
					 zombie.velocityX = r.nextFloat()*-0.025f;
					 ((AnimatedSprite)zombie.sprite).setAnimationBounds(4, 7);
				 }
				}
			if (distance<spr.width){
				enemy.health-=0.01;
//				System.out.println(enemy.health);
				enemy.player.velocityX/=2f;
			}
		}
		
		for (PhysicBody zombie:zombies)
		{
			float distance = spr.getDistance(zombie.getSprite());
			if (distance<spr.width){
				Atomizer.atomize(screen, zombie.getSprite(), physicController);
				this.zombies.remove(zombie);
				break;
			}
		}
			
	}
}

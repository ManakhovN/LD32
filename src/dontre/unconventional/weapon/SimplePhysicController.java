package dontre.unconventional.weapon;
import java.util.ArrayList;
import java.util.List;

public class SimplePhysicController {
	Screen screen;
	List<PhysicBody> physicBodies = new ArrayList<PhysicBody>();	
	
	public SimplePhysicController(Screen screen)
	{
		this.screen = screen;
	}
	
	public void addBody(Sprite sprite)
	{
		this.physicBodies.add(new PhysicBody(sprite));
	}
	
	public void addBody(PhysicBody body)
	{
		this.physicBodies.add(body);
	}
	
	public void addBody(Sprite sprite, float ax, float ay, boolean canCollide)
	{
		this.physicBodies.add(new PhysicBody(sprite,ax,ay, canCollide));
	}
	
	public void addBody(Sprite sprite, float ax, float ay, float vx, float vy, boolean canCollide)
	{
		this.physicBodies.add(new PhysicBody(sprite,ax,ay,vx,vy, canCollide));
	}
	
	public void update()
	{
		for (PhysicBody physicBody:physicBodies){
			physicBody.update();
			if (physicBody.canCollide == true)
			{		
//				int x = physicBody.getSprite().intX();
				int y = (int) (physicBody.getSprite().getY());	 //i = y*WIDTH +x => i%WIDTH == x; i/WIDTH==y
				//System.out.println(y+physicBody.sprite.getHeight());
				if (y+physicBody.sprite.getHeight()>47){
					physicBody.setAcceleration(0, 0);
					physicBody.setVelocity(0, 0);
					physicBody.sprite.isThrowed = false;
				}
			}
			if (physicBody.sprite.isThrowed && (physicBody.sprite.getClass() == AnimatedSpriteWithHealth.class ||  physicBody.sprite.getClass() ==SpriteWithHealth.class))
			{
				for (Sprite spr:screen.sprites)
				{
					if (physicBody.sprite.isCollidesWithBox(spr) && !physicBody.sprite.equals(spr))
						if (spr.getClass() == AnimatedSpriteWithHealth.class)
							((AnimatedSpriteWithHealth)spr).makeDamage(1f);
						else
						if (spr.getClass() == SpriteWithHealth.class)
							((SpriteWithHealth)spr).makeDamage(1f);
				}
			}
		}
	}
	
	public PhysicBody getBodyBySprite(Sprite spr)
	{
		for (PhysicBody body:this.physicBodies)
		{
			if (body.getSprite().equals(spr))
				return body;
		}
		return null;
	}
}

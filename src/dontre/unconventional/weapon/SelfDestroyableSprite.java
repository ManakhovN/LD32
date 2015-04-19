package dontre.unconventional.weapon;

public class SelfDestroyableSprite extends Sprite{
	long bornTime;
	long delta = 10000;
	boolean dead = false;
	public SelfDestroyableSprite(float x, float y, float width, float height,
			int[] pixels) {
		super(x, y, width, height, pixels);
		bornTime= System.currentTimeMillis();
	}
	
	public void update()
	{
		if (System.currentTimeMillis()-bornTime>delta)
			dead=true;
	}
	
	public boolean isDead()
	{
		return this.dead;
	}
}

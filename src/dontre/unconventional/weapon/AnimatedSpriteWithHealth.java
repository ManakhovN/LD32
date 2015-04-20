package dontre.unconventional.weapon;

public class AnimatedSpriteWithHealth extends AnimatedSprite{
	float health = 100;
	public AnimatedSpriteWithHealth(float x, float y, MultipleTexture texture, float health) {
		super(x, y, texture);
		this.health = health;
	}

	public AnimatedSpriteWithHealth(float x, float y, MultipleTexture texture,
			long deltaTime, float health) {
		super(x, y, texture, deltaTime);
		this.health = health;
	}

	public void makeDamage(float delta)
	{
		health-=delta;
	}
	
}

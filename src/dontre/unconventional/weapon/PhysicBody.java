package dontre.unconventional.weapon;

public class PhysicBody {
	Sprite sprite;
	float mass;
	float accelerationX=0f;
	float accelerationY=0f;
	float velocityX=0f;
	float velocityY=0f;
	boolean canCollide = false;
	public PhysicBody(Sprite sprite)
	{
		this.sprite = sprite;
		this.mass = sprite.getPixels().length;
	}
	
	public PhysicBody(Sprite sprite, float ax, float ay, boolean canCollide)
	{
		this.sprite = sprite;
		this.mass = sprite.getPixels().length;
		this.accelerationX = ax;
		this.accelerationY = ay;
		this.canCollide = canCollide;
	}
	
	public PhysicBody(Sprite sprite, float ax, float ay, float vx, float vy, boolean canCollide)
	{
		this.sprite = sprite;
		this.mass = sprite.getPixels().length;
		this.accelerationX = ax;
		this.accelerationY = ay;
		this.velocityX = vx;
		this.velocityY = vy;
		this.canCollide = canCollide;
	}
	
	public void update()
	{
		sprite.moveXY(velocityX, velocityY);
		this.velocityX+=this.accelerationX;
		this.velocityY+=this.accelerationY;
	}
	
	public void setAcceleration(float ax, float ay)
	{
		this.accelerationX = ax;
		this.accelerationY = ay;
	}
	
	public void setVelocity(float vx, float vy)
	{
		this.velocityX = vx;
		this.velocityY = vy;
	}
	
	public float getMass()
	{
		return this.mass;
	}
	
	public Sprite getSprite()
	{
		return this.sprite;
	}
}

package dontre.unconventional.weapon;

public class AnimatedSprite extends Sprite{
	MultipleTexture texture;
	int frameCount;
	int currentFrame;
	int textureWidth;
	int textureHeight;
	int animationStartFrame=0;
	int animationEndFrame=0;
	long deltaTime=0;
	long lastTime=0;
	public AnimatedSprite(float x, float y, MultipleTexture texture) {
		super(x, y, texture.partWidth, texture.partHeight, null);
		this.texture = texture;
		lastTime= System.currentTimeMillis();
		this.currentFrame = 0;
		this.pixels = texture.parts[this.currentFrame];
	}
	
	public AnimatedSprite(float x, float y, MultipleTexture texture, long deltaTime) {
		super(x, y, texture.partWidth, texture.partHeight, null);
		this.texture = texture;
		lastTime= System.currentTimeMillis();
		this.deltaTime = deltaTime;
	}
	
	public void setAnimationBounds(int animationStartFrame, int animationEndFrame)
	{
		this.animationStartFrame = animationStartFrame;
		this.animationEndFrame = animationEndFrame;
	}
	
	public void setUpdateTime(long deltaTime)
	{
		this.deltaTime = deltaTime;
	}
	
	public void update()
	{
		if (System.currentTimeMillis() - lastTime>deltaTime){
			lastTime = System.currentTimeMillis();
			this.currentFrame++;
			if (this.currentFrame<this.animationStartFrame)
				this.currentFrame = this.animationStartFrame;
			if (this.currentFrame>this.animationEndFrame)
				this.currentFrame = this.animationStartFrame;

			this.pixels = texture.parts[this.currentFrame];
		}		
	}
	
}

package dontre.unconventional.weapon;

public class Sprite extends Box{
	int[] pixels;
	SimpleBitmask simpleBitmask;
	boolean isPickable=false;
	boolean isPicked = false;
	public Sprite(float x, float y, float width, float height, int[] pixels) {
		super(x, y, width, height);
		this.pixels = pixels;
		simpleBitmask = new SimpleBitmask(0);
	}
	
	public void setSimpleBitmask(SimpleBitmask simpleBitmask)
	{
		this.simpleBitmask = simpleBitmask;
	}
	
	public SimpleBitmask getSimpleBitmask()
	{
		return this.simpleBitmask;
	}
	
	public void update()
	{
		
	}
	
	public int[] getPixels()
	{
		return pixels;
	}
	
	public void setPixels(int[] pixels)
	{
		this.pixels = pixels;
	}
	
	public void setPickable(boolean isPickable)
	{
		this.isPickable = isPickable;
	}
	
	public void Pick()
	{
		isPicked=true;
	}
	
	public void Drop()
	{
		isPicked=true;
	}
}

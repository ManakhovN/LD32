package dontre.unconventional.weapon;

public class Box extends Point{
	float width, height;
	public Box(float x, float y, float width, float height)
	{
		super(x,y);
		this.width = width;
		this.height = height;
	}
	
	public Box(float width, float height)
	{
		super(0,0);
		this.width = width;
		this.height = height;
	}
	
	public void Resize(float width, float height)
	{
		this.height = height;
		this.width = width;
	}
	
	public boolean isPointInside(Point point)
	{
		return (this.x<=point.x && point.x<=this.x+this.width && 
				this.y<=point.y && point.y<=this.y+this.height);
	}
	
	public boolean isPointInside(float x, float y)
	{
		return (this.x<=x && x<=this.x+this.width && 
				this.y<=y && y<=this.y+this.height);
	}
	
	public boolean isCollidesWithBox(Box box)
	{
		float dx = this.x - box.x;
		float dy = this.y - box.y;
		return (dx<box.width &&
				dx>-this.width &&
				dy<box.height &&
				dy>-this.height);
	}
	
	public void setWidth(float width)
	{
		this.width = x;
	}
	
	public void setHeight(float height)
	{
		this.height = height;
	}
	
	public float getWidth()
	{
		return this.width;
	}
	
	public float getHeight()
	{
		return this.height;
	}
}


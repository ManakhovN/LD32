package dontre.unconventional.weapon;

public class Point {
	float x, y;
	public Point(float x, float y)
	{
		this.x = x; 
		this.y = y;
	}
	
	public int intX()
	{
		return (int)x;
	}
	
	public int intY()
	{
		return (int)y;
	}
	
	public void moveXY(float dx, float dy)
	{
		
		this.x+=dx; this.y+=dy; 
	}
	
	public void moveY(float dy)
	{
		this.y+=dy;
	}
	
	public void moveX(float dx)
	{
		this.x+=dx;
	}
	
	public void setX(float x)
	{
		this.x = x;
	}
	
	public void setY(float x)
	{
		this.x = x;
	}
	
	public float getX()
	{
		return this.x;
	}
	
	public float getY()
	{
		return this.y;
	}
	
	public float getDistance(Point p2)
	{
		return (float) Math.sqrt((p2.x-this.x)*(p2.x-this.x) + (p2.y-this.y)*(p2.y-this.y));
	}
}

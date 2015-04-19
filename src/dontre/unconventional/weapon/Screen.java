package dontre.unconventional.weapon;
import java.util.ArrayList;
import java.util.List;

public class Screen extends Box{
	int[] buff;
	float scrollX=0, scrollY=0;
	List<Sprite> sprites = new ArrayList<Sprite>();
	int[] pixels; 
	int[] tileMap;
	int tileMapWidth;
	MultipleTexture texture = new MultipleTexture(this.getClass().getClassLoader().getResource("tiles.png"), 8 , 8);
	
	public Screen(int[] pixels, int width, int height,int tileMapType, int tileMapWidth, int tileMapHeight) {
		super(0, 0, width, height);
		this.pixels = pixels;
		this.buff = new int[pixels.length];
		this.tileMapWidth = tileMapWidth;
		getTileMap(tileMapType,tileMapWidth,tileMapHeight);
	}
	
	public void setTileMap(String name)
	{
		texture = new MultipleTexture(this.getClass().getClassLoader().getResource(name), 8 , 8);
	}
	
	public void render()
	{
		clear();
//		scrollX+=0.1f;
		if (tileMap!=null)
			for (int i=0; i<tileMap.length; i++)
			{
				drawTile(i);
			}
		for (Sprite sprite:sprites)
		{
			sprite.update();
			drawSprite(sprite);
			
		}
		loadFromBuff();
	}
	
	public void clearDeadSprites(SimplePhysicController controller) {
		for (int i=sprites.size()-1; i>=0; i--)
			if (sprites.get(i).getClass() == SelfDestroyableSprite.class)
				if (((SelfDestroyableSprite)sprites.get(i)).isDead())
					{
						controller.physicBodies.remove(sprites.get(i));
						sprites.remove(i);						
					}
	}

	public void drawTile(int index) {
		int inputWidth = (int) texture.getPartWidth();
		if (tileMap[index] == -1 || !this.isCollidesWithBox(new Box(index%tileMapWidth*inputWidth+scrollX,scrollY + index/tileMapWidth*inputWidth, this.texture.partWidth,this.texture.partHeight))) 
			return;
		int[] input = texture.parts[tileMap[index]];
			for(int i=0; i<input.length; i++){
				int px = (int) ((i%inputWidth+scrollX) + index%tileMapWidth*inputWidth);
				int py = (int) ((i/inputWidth+scrollY) + index/tileMapWidth*inputWidth); //so if we have points[i] = points[x,y] then i = y*WIDTH +x => i%WIDTH == x; i/WIDTH==y 
				if (px<width && py<height && px>0 && py>0) 
					buff[(int) (py*width + px)] = input[i]; 
			}
	}

	public void drawSprite(Sprite sprite) {
		int inputWidth = (int) sprite.width;
		int[] input = sprite.pixels;
			for(int i=0; i<input.length; i++){
				int px = (int) (i%inputWidth + sprite.x+scrollX);
				int py = (int) (i/inputWidth + sprite.y+scrollY); //so if we have points[i] = points[x,y] then i = y*WIDTH +x => i%WIDTH == x; i/WIDTH==y 
				if (py>=0 && py<this.getHeight() && px>=0 && px<this.getWidth()) 
					if (input[i]!=0xFFFF00FF) buff[(int) (py*width + px)] = sprite.simpleBitmask.UseMask(input[i]); 
			}
	}

	public void scroll(float dx, float dy)
	{
		this.scrollX+=dx;
		this.scrollY+=dy;
	}
	
	public void clear()
	{
		for (int i=0; i<pixels.length; i++){
					buff[i] = 0;
//			pixels[i] = Math.abs(((int)(Math.tan(i)*10000)+(int)scrollX*10000)%0x0000FF);	//ØÓÌ ÄËß ÀÌÈÐÀ
		}
	}
	
	public void addSprite(Sprite sprite)
	{
		this.sprites.add(sprite);
	}
	
	public void getSprite(int index)
	{
		if (index<this.sprites.size())
			this.sprites.get(index);
	}
	
	public void loadFromBuff()
	{
		for (int i=0; i<buff.length; i++)
			pixels[i] = buff[i];
	}
	
	public int[] getPixels()
	{
		return this.pixels;
	}
	
	public void getTileMap(int type, int width, int height)
	{
		this.tileMapWidth = width;
		switch(type)
		{
			case 0:tileMap = LevelGenerator.generateGround(width, height, 6);
				break;
			case 1:tileMap = LevelGenerator.fillBackGround(width, height, 2);
				break;
		}
	}
}

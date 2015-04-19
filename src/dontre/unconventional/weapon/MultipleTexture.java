package dontre.unconventional.weapon;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class MultipleTexture {
	int[][] parts;
	int widthPartsCount;
	int heightPartsCount;
	int partHeight;
	int partWidth;
	public MultipleTexture(BufferedImage img, int partHeight, int partWidth)
	{
		this.partHeight = partHeight;
		this.partWidth = partWidth;
		this.widthPartsCount = img.getWidth()/partWidth;
		this.heightPartsCount = img.getHeight()/partHeight;
		parts = new int[getPartsCount()][];
	   for (int num=0; num<getPartsCount(); num++)
				parts[num]=img.getRGB((num%this.widthPartsCount)*this.partWidth, 
						   			  (num/this.heightPartsCount)*this.partHeight, 
						   			   partWidth, partHeight, null,0,0); 
		
	}
	
	public MultipleTexture(URL path, int partHeight, int partWidth)
	{
		BufferedImage img = null;
		try {
			    img = ImageIO.read(path);
			} catch (IOException e) {
				System.out.println(e.toString());
		}
		this.partHeight = partHeight;
		this.partWidth = partWidth;
		this.widthPartsCount = img.getWidth()/partWidth;
		this.heightPartsCount = img.getHeight()/partHeight;
		parts = new int[getPartsCount()][partHeight*partWidth];
	   for (int num=0; num<getPartsCount(); num++)
		   for (int i = 0; i<partWidth; i++)
			   for (int j = 0; j<partHeight; j++){ //i = y*WIDTH +x => i%WIDTH == x; i/WIDTH==y
				   int tx =(num % this.widthPartsCount ) * this.partWidth  + i;
				   int ty = (num / this.widthPartsCount) * this.partHeight + j; 
				if (tx<img.getWidth() &&
					ty<img.getHeight()) 
					parts[num][j*partWidth + i]=img.getRGB(tx,ty);          // this shit, is now working
			   }
	}
	
	public int getPartsCount(){
		return widthPartsCount*heightPartsCount;
	}
	
	public int getWidthPartsCount()
	{
		return this.widthPartsCount;
	}
	
	public int getHeightPartsCount()
	{
		return this.heightPartsCount;
	}
	public int getPartHeight()
	{
		return this.partHeight; 
	}
	
	public int getPartWidth()
	{
		return this.partWidth;
	}
}


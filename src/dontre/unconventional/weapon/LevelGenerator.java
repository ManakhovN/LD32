package dontre.unconventional.weapon;

public class LevelGenerator {
	public static int[] generateGround(int width, int height, int groundLine)
	{
		int[] result = new int[width*height];
		for (int i=0; i<result.length; i++){
//				int px = i%width;
				int py = i/width;
				if (py==groundLine)	
				result[i] =0;
				if (py>groundLine)
					result[i] = 8;
				if (py<groundLine)
					result[i] = -1;
		}
			
		return result;
	}
	
	public static int[] fillBackGround(int width, int height, int index)
	{
		int[] result = new int[width*height];
		for (int i=0; i<result.length; i++)
			result[i] = index;
		return result;
	}	
}

package dontre.unconventional.weapon;
import java.util.HashMap;
import java.util.Map;

public class SimpleBitmask {
	int mask = 0;
	Map<Integer,Integer> colorExchanges = null;
	public SimpleBitmask(int mask)
	{
		this.mask = mask;
		colorExchanges = new HashMap<Integer, Integer>();
	}
	
	public void addColorExcanging(int col1, int col2)
	{
		colorExchanges.put(col1, col2);
	}
	
	public int UseMask(int col)
	{
		if ((colorExchanges.isEmpty() || colorExchanges.get(col)==null)&& mask!=0)
			return col&mask;
		if (colorExchanges.get(col)!=null)
			return colorExchanges.get(col);
		return col;
	}
}

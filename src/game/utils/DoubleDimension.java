package game.utils;

public class DoubleDimension {
	
	public double width = 0;
	public double height = 0;
	
	public DoubleDimension()
	{
		
	}
	
	public DoubleDimension(double width, double height)
	{
		this.width = width;
		this.height = height;
	}
	
	public DoubleDimension(DoubleDimension other)
	{
		this(other.getWidth(), other.getHeight());	
	}
	
	public DoubleDimension normalize()
	{
		if(getWidth() > getHeight()) return new DoubleDimension(1, getHeight()/getWidth());
		else if(getWidth() < getHeight()) return new DoubleDimension(getWidth()/getHeight(), 1);
		else return new DoubleDimension(1, 1);
	}
	
	public void setWidth(double width)
	{
		this.width = width;
	}
	
	public void setHeighth(double height)
	{
		this.height = height;
	}
	
	public double getWidth()
	{
		return width;
	}
	
	public double getHeight()
	{
		return height;
	}
	
	public DoubleDimension clone()
	{
		return new DoubleDimension(width, height);
	}
	
	@Override
	public String toString()
	{
		return "DoubleDimension {width="+width+",height="+height+"}";
	}
	
	public boolean equals(Object other)
	{
		return other != null && other instanceof DoubleDimension && ((DoubleDimension) other).width == width && ((DoubleDimension) other).height == height;
	}

}

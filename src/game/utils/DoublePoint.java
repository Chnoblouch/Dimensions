package game.utils;

public class DoublePoint {
	
	public double x = 0;
	public double y = 0;
	
	public DoublePoint()
	{
		
	}
	
	public DoublePoint(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public DoublePoint(DoublePoint other)
	{
		this(other.getX(), other.getY());	
	}
	
	public DoublePoint normalize()
	{
		if(getX() > getY()) return new DoublePoint(1, getY()/getX());
		else if(getX() < getY()) return new DoublePoint(getX()/getY(), 1);
		else return new DoublePoint(1, 1);
	}
	
	public double distanceDouble(DoublePoint other)
	{
		double dx = x - other.x;
		double dy = y - other.y;
		return Math.sqrt(dx * dx + dy * dy);
	}
	
	public DoublePoint distance(DoublePoint other)
	{
		return new DoublePoint(x > other.x ? x - other.x : other.x - x, y > other.y ? y - other.y : other.y - y);
	}
	
	public void setX(double x)
	{
		this.x = x;
	}
	
	public void setY(double y)
	{
		this.y = y;
	}
	
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
	
	public DoublePoint clone()
	{
		return new DoublePoint(x, y);
	}
		
	@Override
	public String toString()
	{
		return "DoublePoint {x="+x+",y="+y+"}";
	}
	
	public boolean equals(Object other)
	{
		return other != null && other instanceof DoublePoint && ((DoublePoint) other).x == x && ((DoublePoint) other).y == y;
	}

}

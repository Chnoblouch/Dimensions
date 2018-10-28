package game.utils;

public class Angles {
	
	public static double getAngle(DoublePoint from, DoublePoint to)
	{		
		return getAngle(from.x, from.y, to.x, to.y);
	}
	
	public static double getAngle(double fx, double fy, double tx, double ty)
	{		
	    double theta = Math.atan2(ty - fy, tx - fx);
	    theta += Math.PI/2.0;
	    
	    double angle = Math.toDegrees(theta);
	    if (angle < 0) angle += 360;
	    
	    return angle;
	}
	
	public static DoublePoint getMoveDirection(double angle)
	{
		double x = (float) Math.cos(Math.toRadians(angle-90));
        double y = (float) Math.sin(Math.toRadians(angle-90));
		
		return new DoublePoint(x, y);
	}

	public static int getLookDir(double angle)
	{
		if((angle > 315 && angle <= 360) || (angle > 0 && angle <= 45)) return 2;
		else if(angle > 45 && angle <= 135) return 1;
		else if(angle > 135 && angle <= 225) return 0;
		else if(angle > 225 && angle <= 315) return 3;
		
		return 2;
	}
}

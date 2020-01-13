
public class City {
	
	private double xValue;
	private double yValue;
	
	public City() {
		
	}
	
	public City(double x, double y) {
		xValue = x;
		yValue = y;
	}

	public double getxValue() {
		return xValue;
	}

	public void setxValue(double xValue) {
		this.xValue = xValue;
	}

	public double getyValue() {
		return yValue;
	}

	public void setyValue(double yValue) {
		this.yValue = yValue;
	}
	
	public double calculateDistance(City city1, City city2) {
		double value1 = city1.getxValue() - city2.getxValue();
		double value2 = city1.getyValue() - city2.getyValue();
		double distance = Math.sqrt((value1*value1) + (value2*value2));
		return distance;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(xValue);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(yValue);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		City other = (City) obj;
		if (Double.doubleToLongBits(xValue) != Double.doubleToLongBits(other.xValue))
			return false;
		if (Double.doubleToLongBits(yValue) != Double.doubleToLongBits(other.yValue))
			return false;
		return true;
	}
	
	
	
}

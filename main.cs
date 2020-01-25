using System;


public class Point {
  int x;
  int y;

	public Point(){
		this.x = 0;
		this.y = 0;
	}

  public Point(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
		return this.x;
  }

  public int getY() {
    return this.y;
  }

	public void setX(int x){
		this.x = x;
	}

	public void setY(int y){
		this.y = y;
	}

	public void print() {
			Console.WriteLine ("(" + x + ", " + y + ")");
	}
}

class MainClass {
	public static Point coord(Point a, Point b, double da, double db){
		//MAYBE JUST MUTATE AN ORIGINAL INSTEAD OF CREATING A NEW ONE EACH TIME
		double dist_ab = b.getX() - a.getX();

		double x = a.getX() + (Math.Pow(db, 2) - Math.Pow(da, 2) - Math.Pow(dist_ab, 2))/(-2 * dist_ab);
		double y = a.getY() + Math.Sqrt(Math.Pow(-2 * dist_ab * da, 2) - Math.Pow(Math.Pow(db, 2) - Math.Pow(da, 2) - Math.Pow(dist_ab, 2), 2))/(-2 * dist_ab);

		Point p = new Point((int)Math.Round(x), (int)Math.Round(y));
		return p;
	}

  public static void Main (string[] args) {
    Point sensor1 = new Point(0, 0);
		Point sensor2 = new Point(5, 0);
		double d1 = 4.4721;
		double d2 = 8.062;

		Point final = coord(sensor1, sensor2, d1, d2);

		final.print();
	}
}

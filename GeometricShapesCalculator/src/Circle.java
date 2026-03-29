public class Circle implements Shape{
    private final double radius;
    private final Point center;

    //constructor of Circle
    public Circle(double radius, Point center){
        this.radius = radius;
        this.center = center;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public Point getCenter() {
        return this.center;
    }
}

public class Rectangle implements Shape{
    private final double length;
    private final double width;
    private final Point center;

    public Rectangle(double length, double width, Point center){
        this.length = length;
        this.width = width;
        this.center = center;
    }

    @Override
    public double calculateArea() {
        return length*width;
    }

    @Override
    public double calculatePerimeter() {
        return 2*(length+width);
    }

    @Override
    public Point getCenter() {
        return this.center;
    }
}

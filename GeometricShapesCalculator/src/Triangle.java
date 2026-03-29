public class Triangle implements Shape{
    private final double side1;
    private final double side2;
    private final double side3;
    private final Point center;
    
    public Triangle(double side1, double side2, double side3, Point center){
        this.side1 = side1;
        this.side2 = side2;
        this.side3 = side3;
        this.center = center;
    }

    @Override
    public double calculatePerimeter() {
        return side1+side2+side3;
    }

    @Override
    public double calculateArea() {
        double semiPerimeter = calculatePerimeter()/2;
        double heron = semiPerimeter*(semiPerimeter-side1)*(semiPerimeter-side2)*(semiPerimeter-side3);
        return Math.sqrt(heron);
    }

    @Override
    public Point getCenter() {
        return this.center;
    }
}

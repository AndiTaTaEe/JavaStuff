public class Main {
    public static void main(String[] args) {
        Shape[] myShapes = new Shape[3];

        //creating objects
        myShapes[0] = new Circle(5.0, new Point(0,0));
        myShapes[1] = new Rectangle(4.0, 6.0, new Point(10,10));
        myShapes[2] = new Triangle(9,10,11, new Point(5,5));

        System.out.println("--SHAPES REPORT--");

        for (Shape s: myShapes){
            String type;
            if (s instanceof Circle){
                type = "Circle";
            } else if (s instanceof Rectangle) {
                type = "Rectangle";
            } else {
                type = "Triangle";
            }

            System.out.println("Shape type: " + type);
            System.out.println("Center: " + s.getCenter());

            System.out.printf("Area: %.2f\n", s.calculateArea());
            System.out.printf("Perimeter: %.2f\n", s.calculatePerimeter());
            System.out.println("------------------");
        }


        }
    }

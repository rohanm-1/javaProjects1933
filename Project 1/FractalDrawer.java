// FractalDrawer class draws a fractal of a shape indicated by user input
// Written by Rohan Modalavalasa, modal005@umn.edu
import java.awt.Color;
import java.util.Scanner;


public class FractalDrawer {
    private double totalArea=0;  // member variable for tracking the total area

    public FractalDrawer() {}  // constructor

    public double drawFractal(String type) {
        // Create a Canvas object
        Canvas drawing = new Canvas();
        // if-else statements to determine shape and appropriate method to call. Returns totalArea after
        if(type.equals("circle")){
            drawCircleFractal(150, 350, 350, Color.RED, drawing, 7);
            return totalArea;
        }
        else if(type.equals("triangle")){
            drawTriangleFractal(200, 200, 250, 450, Color.RED, drawing, 7);
            return totalArea;
        }
        else if(type.equals("rectangle")){
            drawRectangleFractal(250, 200, 275, 250, Color.RED, drawing, 7);
            return totalArea;
        }
        else 
            return totalArea;
    }

    public void drawTriangleFractal(double width, double height, double x, double y, Color c, Canvas can, int level){
        // Create Triangle Object
        Triangle t = new Triangle(x, y, width, height);
        // Color array to cycle between the indexes of the colors in the array
        Color[] colors = {Color.BLUE, Color.GREEN, Color.RED, Color.BLUE, Color.GREEN, Color.RED, Color.BLUE};
        t.setColor(c);
        can.drawShape(t);
        // Use level variable to start from the end of the color array and work backwards to cycle through the Color elements
        // width and height are halved, x and y positions are modified so they are situated at the corners of the larger triangle
        totalArea += t.calculateArea();
        if(level > 0){
            drawTriangleFractal(width * 0.5, height * 0.5, x + width, y, colors[level-1], can, level -1);
            drawTriangleFractal(width * 0.5, height * 0.5, x-width / 2, y, colors[level-1], can, level -1);
            drawTriangleFractal(width * 0.5, height * 0.5, x + width / 3.5, y - height, colors[level-1], can, level -1);
        }

    }

    public void drawCircleFractal(double radius, double x, double y, Color c, Canvas can, int level) {
        // Create Circle object and Color array with similar implementation as drawTriangleFractal
        Circle c1 = new Circle(x, y, radius);
        Color[] colors = {Color.BLUE, Color.GREEN, Color.RED, Color.BLUE, Color.GREEN, Color.RED, Color.BLUE};
        c1.setColor(c);
        can.drawShape(c1);
        totalArea += c1.calculateArea();
        // radius is halved, x and y positions are modified for each recursive call
        // such that all 4 circles are on opposite sides of the larger circle
        if(level > 0){
            drawCircleFractal(radius / 2, x + radius, y, colors[level-1], can, level-1);
            drawCircleFractal(radius / 2, x - radius, y, colors[level-1], can, level-1);
            drawCircleFractal(radius / 2, x, y + radius, colors[level-1], can, level-1);
            drawCircleFractal(radius / 2, x, y - radius, colors[level-1], can, level-1);
        }
    }

    public void drawRectangleFractal(double width, double height, double x, double y, Color c, Canvas can, int level) {
        // Create Rectangle object and Color array with similar implementation as drawCircleFractal
        Rectangle r = new Rectangle(x, y, width, height);
        Color[] colors = {Color.BLUE, Color.GREEN, Color.RED, Color.BLUE, Color.GREEN, Color.RED, Color.BLUE};
        r.setColor(c);
        can.drawShape(r);
        totalArea += r.calculateArea();
        // width and height are halved, x and y positions are modified for each recursive call
        // such that all 4 rectangles are at the corners of the larger rectangle
        if(level > 0){
            // upper right
            drawRectangleFractal(width * 0.5, height * 0.5, x+width, y-width * 0.4, colors[level-1], can, level-1);
            // upper left
            drawRectangleFractal(width * 0.5, height * 0.5, x-width * 0.5, y-width * 0.4, colors[level-1], can, level-1);
            // bottom left
            drawRectangleFractal(width * 0.5, height * 0.5, x-width * 0.5, y+height, colors[level-1], can, level-1);
            // bottom right
            drawRectangleFractal(width * 0.5, height * 0.5, x+width, y+height, colors[level-1], can, level-1);
        }
    }

    public static void main(String[] args){
        // Create scanner object for user input and create prompt
        Scanner s = new Scanner(System.in);
        System.out.print("Choose base of fractal- either \"circle\", \"triangle\", or \"rectangle\": ");
        String choice = s.nextLine();
        // Create FractalDrawer object and call drawFractal method with the users choice as the parameter.
        // Put the method call in a print statement so totalArea can be printed to console
        FractalDrawer f = new FractalDrawer();
        System.out.println("Total area of fractal is "+f.drawFractal(choice));
        s.close();
    }
}

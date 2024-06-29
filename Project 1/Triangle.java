// Written by Rohan Modalavalasa, modal005@umn.edu
import java.awt.Color;
public class Triangle {
    // Declare attributes (including Triangle color)
    // x and y positions is the position of the bottom-left corner of the Triangle
    double x_pos;
    double y_pos;
    double width;
    double height;
    Color triangle;
    public Triangle(double xpos, double ypos, double width, double height){
        // Initialize attributes
        x_pos = xpos;
        y_pos = ypos;
        this.width = width;
        this.height = height;
    }
    // Triangle calculations
    public double calculatePerimeter(){
        // First, calculate the length of one of the triangle legs
        double leg_length = Math.sqrt(Math.pow(width/2, 2)+Math.pow(height, 2));
        // Perimeter of the isosceles triangle is then 2(leg_length)+width
        return 2 * leg_length + width;
    }
    public double calculateArea(){
        return (0.5) * width * height;
    }
    // Setter methods for color, xy position, height, and width
    public void setColor(Color col){
        triangle = col;
    }
    public void setPos(double new_xpos, double new_ypos){
        x_pos = new_xpos;
        y_pos = new_ypos;
    }
    public void setHeight(double new_height){
        height = new_height;
    }
    public void setWidth(double new_width){
        width = new_width;
    }
    // Getter methods for color, x-position, y-position, height, and width
    public Color getColor(){
        return triangle;
    }
    public double getXPos(){
        return x_pos;
    }
    public double getYPos(){
        return y_pos;
    }
    public double getHeight(){
        return height;
    }
    public double getWidth(){
        return width;
    }
}

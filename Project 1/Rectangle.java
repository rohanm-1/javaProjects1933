// Written by Rohan Modalavalasa, modal005@umn.edu
import java.awt.Color;
public class Rectangle {
    // Declare attributes of a Rectangle object (including the Color)
    double x_pos; 
    double y_pos;
    double width;
    double height;
    Color rectangle;
    public Rectangle(double xpos, double ypos, double width, double height){
        // Initialize attributes
        x_pos = xpos;
        y_pos = ypos;
        this.width = width;
        this.height = height;
    }
    // Rectangle calculations
    public double calculatePerimeter(){
        return 2 * width + 2 * height;
    }
    public double calculateArea(){
        return width * height;
    }
    // Setter methods for the color, xy position, height, and width of Rectangle
    public void setColor(Color col){
        rectangle = col;
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
    // Getter methods for the color, x-position, y-position, height, and width of Rectangle
    public Color getColor(){
        return rectangle;
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

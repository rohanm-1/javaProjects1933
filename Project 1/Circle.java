// Written by Rohan Modalavalasa, modal005@umn.edu
import java.awt.Color;
public class Circle {
    // Declare attributes (including Circle color)
    double x_pos;
    double y_pos;
    double radius;
    Color circle;
    public Circle(double x_pos, double y_pos, double rad) {
        // Initialize attributes
        this.x_pos = x_pos; 
        this.y_pos = y_pos;
        radius = rad;
    }
    //Circle calculations 
    public double calculatePerimeter(){
        return 2 * Math.PI * radius; 
    }
    public double calculateArea(){
        return Math.PI * Math.pow(radius, 2);
    }
    // Setter methods for color, (x,y) position, and radius
    public void setColor(Color col){
        circle = col;
    }
    public void setPos(double new_x_pos, double new_y_pos){
        x_pos = new_x_pos;
        y_pos = new_y_pos;
    }
    public void setRadius(double new_rad){
        radius = new_rad;
    }
    //Getter methods for color, x-position, y-position, and radius
    public Color getColor(){
        return circle;
    }
    public double getXPos(){
        return x_pos;
    }
    public double getYPos(){
        return y_pos;
    }
    public double getRadius(){
        return radius;
    }
}

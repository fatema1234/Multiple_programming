package sample;


import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PlaceInfo extends Circle {
    private String placeName;
    private double positionX;
    private double positionY;

    public PlaceInfo(String placeName, double positionX, double positionY) {
        super(positionX, positionY, 10, Color.BLUE);
        this.placeName = placeName;
        this.positionX = positionX;
        this.positionY = positionY;
        setId(placeName);
    }




    public String getPlaceName() {
        return placeName;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }
}

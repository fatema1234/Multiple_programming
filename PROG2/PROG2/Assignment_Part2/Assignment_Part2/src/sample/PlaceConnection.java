package sample;

import javafx.scene.shape.Line;

public class PlaceConnection extends Line {
   private PlaceInfo start;
   private PlaceInfo end;

    public PlaceConnection(PlaceInfo start, PlaceInfo end) {
        super(start.getPositionX(), start.getPositionY(), end.getPositionX(),end.getPositionY());
    }
}

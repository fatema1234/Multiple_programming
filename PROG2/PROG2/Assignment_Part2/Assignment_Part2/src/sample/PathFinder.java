package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
//import javafx.embed.swing.SwingFXUtils;
import static java.lang.System.exit;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class PathFinder extends Application {
    private final String findPath1 = "find path";
    private final String showConnection1 = "show connection";
    private final String newConnection1 = "new connection";
    private final String changeConnection1 = "change connection";
    private final String location = "C:\\Users\\Fatema Ferdousy\\IdeaProjects\\Assignment_Part2\\src\\sample\\";

    private Scene scene;
    private Stage stage;
    private Pane center;
    private Image image;
    private ImageView imageView;
    private List<PlaceInfo> placeSet = new ArrayList<>();
    private Button[] buttonList = new Button[5];
    private Graph<PlaceInfo> places = new ListGraph<>();
    private boolean unsaved;

    @Override
    public void start(Stage primaryStage) throws Exception{
        unsaved = false;
        VBox root = new VBox(getMenuBar());
        BorderPane borderPane = getBorder();
        root.getChildren().add(borderPane);
        for(int i = 0; i < 5; i++)
            buttonList[i].setDisable(true);
        center = new Pane();
        center.setId("outputArea");
        borderPane.setCenter(center);
        scene = new Scene(root, 618, 729);
        primaryStage.setTitle("PathFinder");
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private boolean setupUnsaved(){
        if (unsaved){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.getDialogPane().setContentText("Unsaved changes, continue anyway?");
            alert.setTitle("Warning!");
            Optional<ButtonType> answer = alert.showAndWait();
            if(answer.isPresent() && answer.get() == ButtonType.CANCEL){
                return false;//save korbo.
            }

        }
        return true;//save korbo na.
    }
    private void save() throws FileNotFoundException {
        unsaved = false;
        PrintWriter printWriter = new PrintWriter(new File(location+"europa.graph"));
        printWriter.println(image.getUrl());
        String placeInfoText = "";
        boolean notFirst = false;
        ArrayList<String> connectionList = new ArrayList<>();
        for(PlaceInfo placeInfo:places.getNodes()){
            if (notFirst)
                placeInfoText+=";";
            else
                notFirst = true;
            placeInfoText+=placeInfo.getPlaceName()+";"+placeInfo.getPositionX()+";"+placeInfo.getPositionY();
            for(Edge<PlaceInfo> edge:places.getEdgesFrom(placeInfo))
            {
                connectionList.add(placeInfo.getPlaceName()+";"+edge.getDestination().getPlaceName()+";"+edge.getName()+";"+edge.getWeight());
            }

        }
        printWriter.println(placeInfoText);
        for(String s: connectionList)
            printWriter.println(s);
        printWriter.flush();
        printWriter.close();


    }
    private MenuBar getMenuBar() {
        MenuBar menuBar = new MenuBar();
        menuBar.setId("menu");
        Menu menu = new Menu("File");
        menu.setId("menuFile");
        menuBar.getMenus().add(menu);
        menu.getItems().addAll(
                setMenuItem("New Map","menuNewMap", new NewMapListener()),
                setMenuItem("Open","menuOpenFile", new OpenListener()),
                setMenuItem("Save","menuSaveFile", new SaveListener()),
                setMenuItem("Save Image","menuSaveImage", new SaveImageListener()),
                setMenuItem("Exit","menuExit", new ExitListener()) );
        return menuBar;
    }

    private MenuItem setMenuItem(String menuName,String id, EventHandler<ActionEvent> eventHandler){
        MenuItem menuItem = new MenuItem(menuName);
        menuItem.setId(id);
        menuItem.setOnAction(eventHandler);
        return  menuItem;
    }

    private BorderPane getBorder() {
        HBox upperBox = new HBox();
        BorderPane border = new BorderPane();
        buttonList[0] = setFloatButton("Find Path","btnFindPath", new PlaceClicked(findPath1));
        buttonList[1] = setFloatButton("Show Connection","btnShowConnection", new PlaceClicked(showConnection1));
        buttonList[2] = setFloatButton("New Place","btnNewPlace", new NewPlaceListener());
        buttonList[3] = setFloatButton("New Connection","btnNewConnection",new PlaceClicked(newConnection1));
        buttonList[4] = setFloatButton("Change Connection","btnChangeConnection", new PlaceClicked(changeConnection1));
        upperBox.getChildren().addAll( buttonList );
        upperBox.setAlignment(Pos.CENTER);
        upperBox.setSpacing(10);
        border.setTop(upperBox);
        return border;
    }

    private Button setFloatButton(String buttonName,String id, EventHandler<ActionEvent> eventHandler){
        Button button = new Button(buttonName);
        button.setId(id);
        button.setOnAction(eventHandler);
        return  button;
    }

    class ExitListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            if (setupUnsaved())
                exit(0);
        }
    }

    class SaveImageListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {

           /* try{
                WritableImage image = center.snapshot(null, null);
                //BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
               // ImageIO.write(bufferedImage, "png", new File("capture.png"));
            }catch (IOException e){
            }*/
        }
    }

    class SaveListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            try {
                save();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    class OpenListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            try {
                placeSet.clear();
                if (setupUnsaved()) {
                    BufferedReader reader = new BufferedReader(new FileReader(new File(location+"europa.graph")));
                    String line = reader.readLine();


                    setupEnvironment(line);
                    line = reader.readLine();
                    String[] words = line.split(";");
                    for (int i = 0; i < words.length; i += 3) {
                        PlaceInfo placeInfo = new PlaceInfo(words[i], Double.parseDouble(words[i + 1]), Double.parseDouble(words[i + 2]));
                        //placeInfo.setId(placeInfo.getPlaceName());
                       // placeInfo.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            //@Override
                            //public void handle(MouseEvent mouseEvent) {

                           // }
                       // });
                        placeInfo.setOnMouseClicked(mouseEvent -> {
                            if(placeSet.size()<2) {


                                if (placeInfo.getFill() == Color.BLUE) {
                                    placeInfo.setFill(Color.RED);
                                    placeSet.add(placeInfo);
                                    return;
                                }

                            }
                            if (placeInfo.getFill() == Color.RED) {
                                placeInfo.setFill(Color.BLUE);
                                placeSet.remove(placeInfo);
                            }
                        });
                        places.add(placeInfo);
                        setLabel(placeInfo);

                    }
                    center.getChildren().addAll(places.getNodes());
                    line = reader.readLine();
                    while (line != null) {

                        words = line.split(";");
                        Set<PlaceInfo> placeInfoSet = places.getNodes();
                        PlaceInfo start = null;
                        PlaceInfo end = null;

                        for (PlaceInfo placeInfo : placeInfoSet) {
                            if (start != null && end != null)
                                break;
                            if (placeInfo.getPlaceName().equalsIgnoreCase(words[0])) {
                                start = placeInfo;
                            }
                            if (placeInfo.getPlaceName().equalsIgnoreCase(words[1])) {
                                end = placeInfo;
                            }

                        }

                        if (start != null && end != null) {
                            if (!places.pathExists(start,end)) {
                                PlaceConnection placeConnection = new PlaceConnection(start, end);
                                placeConnection.setDisable(true);
                                places.connect(start, end, words[2], Integer.parseInt(words[3]));
                                center.getChildren().add(placeConnection);
                            }
                        }


                        line = reader.readLine();

                    }
                    //   unsavedChanges = false;
                    reader.close();
                }
            } catch (Exception e) {
                // System.out.println(e);
            }
        }
    }

    class NewMapListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            placeSet.clear();
            if (setupUnsaved())
                setupEnvironment();
            unsaved = false;
        }
    }
    private void setupEnvironment() {

        setupEnvironment("file:"+location+"europa.gif");
    }

    private void setupEnvironment(String s){
        center.getChildren().clear();
        placeSet.clear();
        places = new ListGraph<>();
        for(int i = 0; i < 5; i++)
            buttonList[i].setDisable(false);
        image = new Image(s);
        imageView = new ImageView(image);
        center.getChildren().add(imageView);
        imageView.fitWidthProperty().bind(center.widthProperty());
        imageView.fitHeightProperty().bind(center.heightProperty());
    }

//    class FindPathListener implements EventHandler<ActionEvent> {
//
//        @Override
//        public void handle(ActionEvent event) {
////            for (PlaceInfo place : places.getNodes()){
////                place.setOnMouseClicked();
////            }
//            new PlaceClicked(/*place,*/findPath1);
//            setImageView();
//        }
//    }

//    class ShowConnectionListener implements EventHandler<ActionEvent> {
//
//
//        @Override
//        public void handle(ActionEvent event) {
//            for (PlaceInfo place : places.getNodes()){
//                place.setOnMouseClicked(new PlaceClicked(/*place,*/showConnection1));
//            }
//            setImageView();
//        }
//    }

    private void setImageView(){
        imageView.setOnMouseClicked(mouseEvent -> {
            Alert alert1 = new Alert(Alert.AlertType.ERROR, "Two places must be selected");
            alert1.setTitle("Error!");
            alert1.setHeaderText(null);
            alert1.showAndWait();
        });
    }

    class NewPlaceListener implements EventHandler<ActionEvent> {
        private TextField nameField;
        @Override
        public void handle(ActionEvent event) {
            nameField = new TextField();
            scene.setCursor(Cursor.CROSSHAIR);
            buttonList[2].setDisable(true);
            imageView.setOnMouseClicked(mouseEvent -> {
                scene.setCursor(Cursor.DEFAULT);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                GridPane grid = new GridPane();
                grid.setAlignment(Pos.CENTER);
                grid.setPadding(new Insets(10));
                grid.setHgap(5);
                grid.setVgap(10);

                grid.addRow(0, new Label("Name of place:"), nameField);
                alert.setHeaderText(null);
                alert.getDialogPane().setContent(grid);
                alert.setTitle("Name");
                setNewPlaceCheck(alert,mouseEvent);
            });
        }
        private void setNewPlaceCheck(Alert alert, MouseEvent mouseEvent){
            Optional<ButtonType> answer = alert.showAndWait();
            if (answer.isPresent() && answer.get() == ButtonType.OK) {
                if (nameField.getText().isEmpty()){
                    Alert alert1 = new Alert(Alert.AlertType.ERROR, "Place name has not found");
                    alert1.showAndWait();
                    setNewPlaceCheck(alert,mouseEvent);
                    return;
                }
                PlaceInfo placeInfo = new PlaceInfo(nameField.getText(), mouseEvent.getX(),mouseEvent.getY());
                placeInfo.setId(placeInfo.getPlaceName());
                placeInfo.setOnMouseClicked(mouseEvent1 -> {
                    if(placeSet.size()<2) {


                        if (placeInfo.getFill() == Color.BLUE) {
                            placeInfo.setFill(Color.RED);
                            placeSet.add(placeInfo);
                            return;
                        }

                    }
                    if (placeInfo.getFill() == Color.RED) {
                        placeInfo.setFill(Color.BLUE);
                        placeSet.remove(placeInfo);
                    }
                });
                places.add(placeInfo);
                center.getChildren().add(placeInfo);
                setLabel(placeInfo);
                imageView.setOnMouseClicked(null);
                buttonList[2].setDisable(false);
                unsaved = true;
            }
        }
    }

    private void setLabel(PlaceInfo placeInfo){
        Label label = new Label(placeInfo.getPlaceName());
        label.setDisable(true);
        label.setLayoutX(placeInfo.getPositionX());
        label.setLayoutY(placeInfo.getPositionY()+10);
        center.getChildren().add(label);
    }

//    class NewConnectionListener implements EventHandler<ActionEvent> {
//
//        @Override
//        public void handle(ActionEvent event) {
//            for (PlaceInfo place : places.getNodes()){
//                place.setOnMouseClicked(new PlaceClicked(/*place,*/newConnection1));
//            }
//            setImageView();
//        }
//    }
//
//    class ChangeConnectionListener implements EventHandler<ActionEvent> {
//
//        @Override
//        public void handle(ActionEvent event) {
//            for (PlaceInfo place : places.getNodes()){
//                place.setOnMouseClicked(new PlaceClicked(/*place,*/changeConnection1));
//            }
//            setImageView();
//
//
//        }
//    }


    class PlaceClicked implements EventHandler<ActionEvent>{
        // private PlaceInfo clickedPlace;
        private String action;
        private TextField nameField = new TextField();
        private TextField timeField = new TextField();
        PlaceClicked(/*PlaceInfo clickedPlace,*/ String action) {
            //this.clickedPlace = clickedPlace;
            this.action = action;
        }




        private void setAlert(Alert alert){
            Optional<ButtonType> answer = alert.showAndWait();
            if (action.equalsIgnoreCase(newConnection1) || action.equalsIgnoreCase(changeConnection1)) {
                if (answer.isPresent() && answer.get() == ButtonType.OK) {
                    if (nameField.getText().isEmpty()) {
                        Alert alert1 = new Alert(Alert.AlertType.ERROR, "Name has not found");
                        alert1.showAndWait();
                        setAlert(alert);
                    }
                    else if (timeField.getText().isEmpty()) {
                        Alert alert1 = new Alert(Alert.AlertType.ERROR, "Time has not found");
                        alert1.showAndWait();
                        setAlert(alert);
                    }
                    else {
                        if (action.equalsIgnoreCase(newConnection1) ) {
                            PlaceConnection placeConnection = new PlaceConnection(placeSet.get(0), placeSet.get(1));
                            placeConnection.setDisable(true);
                            places.connect(placeSet.get(0), placeSet.get(1), nameField.getText(), Integer.parseInt(timeField.getText()));
                            center.getChildren().add(placeConnection);
                        }
                        else{
                            places.setConnectionWeight(placeSet.get(0),placeSet.get(1),Integer.parseInt(timeField.getText()));
                        }
                        unsaved = true;
                    }
                }
            }
        }

        @Override
        public void handle(ActionEvent actionEvent) {
            if (placeSet.size() == 2) {

                if (places.pathExists(placeSet.get(0), placeSet.get(1)) && action.equalsIgnoreCase(newConnection1)) {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR, "There can only be one connection between two locations");
                    alert1.setTitle("Error!");
                    alert1.setHeaderText(null);
                    alert1.showAndWait();
                }
                else if (places.getEdgeBetween(placeSet.get(0), placeSet.get(1)) == null && (action.equalsIgnoreCase(showConnection1)||action.equalsIgnoreCase(changeConnection1))) {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR, "There is no connection between two locations");
                    alert1.setTitle("Error!");
                    alert1.setHeaderText(null);
                    alert1.showAndWait();
                }
                else if(action.equalsIgnoreCase(findPath1)){
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Message");
                    alert1.setHeaderText("The Path from " + placeSet.get(0).getPlaceName() + " to " + placeSet.get(1).getPlaceName()+":");
                    List<Edge<PlaceInfo>> placeList = places.getPath(placeSet.get(0),placeSet.get(1));
                    TextArea textArea = new TextArea();
                    String text = "";
                    int total = 0;
                    if( placeList != null) {
                        for (Edge<PlaceInfo> edge : placeList) {
                            text += "to " + edge.getDestination().getPlaceName() + " by " + edge.getName() + " takes " + edge.getWeight() + "\n";
                            total += edge.getWeight();
                        }
                        text+="Total "+total;
                    }
                    else{
                        text+="There is no road between the two places\n";
                    }

                    textArea.setText(text);
                    alert1.getDialogPane().setContent(textArea);
                    alert1.showAndWait();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setHeaderText("Connection from " + placeSet.get(0).getPlaceName() + " to " + placeSet.get(1).getPlaceName());


                    GridPane grid = new GridPane();
                    grid.setAlignment(Pos.CENTER);
                    grid.setPadding(new Insets(10));
                    grid.setHgap(5);
                    grid.setVgap(10);
                    timeField.textProperty().addListener(new ChangeListener<String>() {
                        @Override
                        public void changed(ObservableValue<? extends String> observable, String oldValue,
                                            String newValue) {
                            if (!newValue.matches("\\d*")) {
                                timeField.setText(newValue.replaceAll("[^\\d]", ""));
                            }
                        }
                    });
                    grid.addRow(0, new Label("Name:"), nameField);
                    grid.addRow(1, new Label("Time:"), timeField);

                    if (action.equalsIgnoreCase(showConnection1) || action.equalsIgnoreCase(changeConnection1)) {
                        Edge<PlaceInfo> edge = places.getEdgeBetween(placeSet.get(0), placeSet.get(1));
                        nameField.setText(edge.getName());
                        timeField.setText(String.valueOf(edge.getWeight()));
                    }
                    if (action.equalsIgnoreCase(showConnection1))
                        timeField.setDisable(true);

                    if (action.equalsIgnoreCase(showConnection1) || action.equalsIgnoreCase(changeConnection1))
                        nameField.setDisable(true);
                    alert.getDialogPane().setContent(grid);
                    alert.setTitle("Connection");
                    setAlert(alert);
                }
//                    for (PlaceInfo placeInfo : placeSet)
//                        placeInfo.setFill(Color.BLUE);
                //                   placeSet.clear();
                //                  imageView.setOnMouseClicked(null);
//                    for (PlaceInfo placeInfo : places.getNodes())
//                        placeInfo.setOnMouseClicked(null);
            }
            else {
                Alert alert1 = new Alert(Alert.AlertType.ERROR, "Two places must be selected");
                alert1.setTitle("Error!");
                alert1.setHeaderText(null);
                alert1.showAndWait();
            }






        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

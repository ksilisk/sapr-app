package com.ksilisk.sapr.controller;

import com.ksilisk.sapr.dto.BarDTO;
import com.ksilisk.sapr.dto.BarLoadDTO;
import com.ksilisk.sapr.dto.BarSpecDTO;
import com.ksilisk.sapr.dto.NodeLoadDTO;
import com.ksilisk.sapr.handler.RowDeleteEventHandler;
import com.ksilisk.sapr.payload.ConstructionParameters;
import com.ksilisk.sapr.service.PreprocessorService;
import com.ksilisk.sapr.validate.ValidatorImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

import static javafx.scene.control.cell.TextFieldTableCell.forTableColumn;

public class PreprocessorController implements Initializable {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PreprocessorController.class);

    @FXML
    private TableView<BarDTO> barView;
    @FXML
    private TableColumn<BarDTO, Double> length, area;
    @FXML
    private TableColumn<BarDTO, Integer> spec;

    @FXML
    private TableView<BarSpecDTO> barSpecsView;
    @FXML
    private TableColumn<BarSpecDTO, Double> elasticMod, permisVolt;

    @FXML
    private TableView<NodeLoadDTO> nodeLoadsView;
    @FXML
    private TableColumn<NodeLoadDTO, Integer> nodeInd;
    @FXML
    private TableColumn<NodeLoadDTO, Double> nodeFx, nodeFy;

    @FXML
    private TableView<BarLoadDTO> barLoadsView;
    @FXML
    private TableColumn<BarLoadDTO, Integer> barInd;
    @FXML
    private TableColumn<BarLoadDTO, Double> barQy, barQx;

    @FXML
    private Button addBar, addBarLoad, addBarSpec, addNodeLoad;
    @FXML
    private Button delBar, delBarLoad, delBarSpec, delNodeLoad;
    @FXML
    private CheckBox left, right;
    private final FileChooser fileChooser = new FileChooser();
    private final DirectoryChooser directoryChooser = new DirectoryChooser();
    private PreprocessorService preprocessorService;

    public void draw() {
        preprocessorService.createDraw(getParameters());
    }

    public void save(MouseEvent event) {
        Button button = (Button) event.getSource();
        Stage currentStage = (Stage) button.getScene().getWindow();
        File chosenDir = directoryChooser.showDialog(currentStage);
        if (chosenDir != null) {
            preprocessorService.safe(getParameters(), chosenDir);
        }
    }

    public void upload(MouseEvent event) {
        Button button = (Button) event.getSource();
        Stage currentStage = (Stage) button.getScene().getWindow();
        File chosenFile = fileChooser.showOpenDialog(currentStage);
        if (chosenFile != null) {
            preprocessorService.upload(chosenFile);
        }
    }

    private ConstructionParameters getParameters() {
        return new ConstructionParameters(barView.getItems(), barLoadsView.getItems(), barSpecsView.getItems(),
                nodeLoadsView.getItems(), left.isSelected(), right.isSelected());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File userHomeDir = new File(System.getProperty("user.home"));
        directoryChooser.setInitialDirectory(userHomeDir);
        directoryChooser.setTitle("Chooser Directory");
        fileChooser.setInitialDirectory(userHomeDir);
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("Json extension filter", Collections.singletonList("*.json")));
        fileChooser.setTitle("Choose File");
        preprocessorService = new PreprocessorService(new ValidatorImpl());
        initColumns();
        initButtons();
    }

    private void initColumns() {
        setCellValueFactories();
        setEditable();
    }

    private void initButtons() {
        addBar.setOnMouseClicked(e -> barView.getItems().add(new BarDTO()));
        addBarSpec.setOnMouseClicked(e -> barSpecsView.getItems().add(new BarSpecDTO()));
        addNodeLoad.setOnMouseClicked(e -> nodeLoadsView.getItems().add(new NodeLoadDTO()));
        addBarLoad.setOnMouseClicked(e -> barLoadsView.getItems().add(new BarLoadDTO()));
        delNodeLoad.setOnMouseClicked(new RowDeleteEventHandler(nodeLoadsView));
        delBarLoad.setOnMouseClicked(new RowDeleteEventHandler(barLoadsView));
        delBarSpec.setOnMouseClicked(new RowDeleteEventHandler(barSpecsView));
        delBar.setOnMouseClicked(new RowDeleteEventHandler(barView));
    }

    private void setEditable() {
        // for barView
        spec.setCellFactory(forTableColumn(new IntegerStringConverter()));
        spec.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setSpecInd(e.getNewValue()));
        area.setCellFactory(forTableColumn(new DoubleStringConverter()));
        area.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setArea(e.getNewValue()));
        length.setCellFactory(forTableColumn(new DoubleStringConverter()));
        length.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setLength(e.getNewValue()));

        // barSpecs
        elasticMod.setCellFactory(forTableColumn(new DoubleStringConverter()));
        elasticMod.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setElasticMod(e.getNewValue()));
        permisVolt.setCellFactory(forTableColumn(new DoubleStringConverter()));
        permisVolt.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setPermisVolt(e.getNewValue()));

        // nodeLoads
        nodeInd.setCellFactory(forTableColumn(new IntegerStringConverter()));
        nodeInd.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setNodeInd(e.getNewValue()));
        nodeFy.setCellFactory(forTableColumn(new DoubleStringConverter()));
        nodeFy.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setNodeFy(e.getNewValue()));
        nodeFx.setCellFactory(forTableColumn(new DoubleStringConverter()));
        nodeFx.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setNodeFx(e.getNewValue()));

        // barLoads
        barInd.setCellFactory(forTableColumn(new IntegerStringConverter()));
        barInd.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setBarInd(e.getNewValue()));
        barQx.setCellFactory(forTableColumn(new DoubleStringConverter()));
        barQx.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setBarQx(e.getNewValue()));
        barQy.setCellFactory(forTableColumn(new DoubleStringConverter()));
        barQy.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setBarQy(e.getNewValue()));
    }

    private void setCellValueFactories() {
        // barView
        area.setCellValueFactory(new PropertyValueFactory<>("area"));
        length.setCellValueFactory(new PropertyValueFactory<>("length"));
        spec.setCellValueFactory(new PropertyValueFactory<>("specInd"));

        // barSpecs
        elasticMod.setCellValueFactory(new PropertyValueFactory<>("elasticMod"));
        permisVolt.setCellValueFactory(new PropertyValueFactory<>("permisVolt"));

        // nodeLoads
        nodeInd.setCellValueFactory(new PropertyValueFactory<>("nodeInd"));
        nodeFx.setCellValueFactory(new PropertyValueFactory<>("nodeFx"));
        nodeFy.setCellValueFactory(new PropertyValueFactory<>("nodeFy"));

        // barLoads
        barInd.setCellValueFactory(new PropertyValueFactory<>("barInd"));
        barQx.setCellValueFactory(new PropertyValueFactory<>("barQx"));
        barQy.setCellValueFactory(new PropertyValueFactory<>("barQy"));
    }
}

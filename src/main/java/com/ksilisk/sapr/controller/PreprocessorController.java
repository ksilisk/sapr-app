package com.ksilisk.sapr.controller;

import com.ksilisk.sapr.builder.StageBuilder;
import com.ksilisk.sapr.record.BarDTO;
import com.ksilisk.sapr.record.BarLoadDTO;
import com.ksilisk.sapr.record.BarSpecDTO;
import com.ksilisk.sapr.record.NodeLoadDTO;
import com.ksilisk.sapr.service.Draw;
import com.ksilisk.sapr.service.RowDeleter;
import com.ksilisk.sapr.service.SceneScaleHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Camera;
import javafx.scene.ParallelCamera;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.control.cell.TextFieldTableCell.forTableColumn;

public class PreprocessorController implements Initializable {
    @FXML
    private TableView<BarDTO> barView;
    @FXML
    private TableColumn<BarDTO, Double> length, area;
    @FXML
    private TableColumn<BarDTO, Integer> spec;

    @FXML
    private TableView<BarSpecDTO> barSpecs;
    @FXML
    private TableColumn<BarSpecDTO, Double> elasticMod, permisVolt;

    @FXML
    private TableView<NodeLoadDTO> nodeLoads;
    @FXML
    private TableColumn<NodeLoadDTO, Integer> nodeInd;
    @FXML
    private TableColumn<NodeLoadDTO, Double> nodeFx, nodeFy;

    @FXML
    private TableView<BarLoadDTO> barLoads;
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

    public void draw() {
        Draw draw = Draw.builder().build();
        Scene scene = new Scene(draw, 700, 500);
        Camera camera = new ParallelCamera();
        scene.setCamera(camera);
        scene.setOnKeyPressed(new SceneScaleHandler());
        Stage stage = new StageBuilder().scene(scene).build();
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initColumns();
        initButtons();
    }

    private void initColumns() {
        setCellValueFactories();
        setEditable();
    }

    private void initButtons() {
        addBar.setOnMouseClicked(e -> barView.getItems().add(new BarDTO()));
        addBarSpec.setOnMouseClicked(e -> barSpecs.getItems().add(new BarSpecDTO()));
        addNodeLoad.setOnMouseClicked(e -> nodeLoads.getItems().add(new NodeLoadDTO()));
        addBarLoad.setOnMouseClicked(e -> barLoads.getItems().add(new BarLoadDTO()));
        delNodeLoad.setOnMouseClicked(new RowDeleter(nodeLoads));
        delBarLoad.setOnMouseClicked(new RowDeleter(barLoads));
        delBarSpec.setOnMouseClicked(new RowDeleter(barSpecs));
        delBar.setOnMouseClicked(new RowDeleter(barView));
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

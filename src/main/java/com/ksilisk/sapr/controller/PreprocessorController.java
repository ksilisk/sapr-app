package com.ksilisk.sapr.controller;

import com.ksilisk.sapr.record.QRRecord;
import com.ksilisk.sapr.record.QSRecord;
import com.ksilisk.sapr.record.XCRecord;
import com.ksilisk.sapr.record.XSRecord;
import com.ksilisk.sapr.service.Draw;
import com.ksilisk.sapr.service.Node;
import com.ksilisk.sapr.service.RowDeleter;
import com.ksilisk.sapr.service.StageBuilder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.control.cell.TextFieldTableCell.forTableColumn;

public class PreprocessorController implements Initializable {
    @FXML
    private TableView<QSRecord> qsTableView;
    @FXML
    private TableView<QRRecord> qrTableView;
    @FXML
    private TableView<XCRecord> xcTableView;
    @FXML
    private TableView<XSRecord> xsTableView;
    @FXML
    private TableColumn<XCRecord, Double> eCol, bCol;
    @FXML
    private TableColumn<XSRecord, Double> lCol, aCol, xcCol;
    @FXML
    private TableColumn<QRRecord, Double> fCol;
    @FXML
    private TableColumn<QSRecord, Double> qxCol;
    @FXML
    private TableColumn<QRRecord, Integer> nCol;
    @FXML
    private TableColumn<QSRecord, Integer> n2Col;
    @FXML
    private Button addXC, addXS, addQR, addQS;
    @FXML
    private Button delXC, delXS, delQR, delQS;
    @FXML
    private CheckBox left, right;

    public void draw() {
        Draw draw = Draw.builder()
                .height(500)
                .width(700)
                .margin(100)
                .leftSupport(true)
                .rightSupport(true)
                .node(new Node(10, -10))
                .build();
        ;
        new StageBuilder().scene(new Scene(draw, 700, 500)).build().show();
//        Line line = new Line(0, 250, 500, 250);
//        Group group = new Group();
//        Scene scene = new Scene(group);
//        group.getChildren().add(new Rectangle(1, 250, 100, 500));
//        group.getChildren().add(line);
//        Stage stage = new StageBuilder()
//                .scene(scene)
//                .title("some")
//                .resizable(true)
//                .build();
//        stage.show();
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
        addQR.setOnMouseClicked(e -> qrTableView.getItems().add(new QRRecord(0, 0)));
        addQS.setOnMouseClicked(e -> qsTableView.getItems().add(new QSRecord(0, 0)));
        addXC.setOnMouseClicked(e -> xcTableView.getItems().add(new XCRecord(0, 0)));
        addXS.setOnMouseClicked(e -> xsTableView.getItems().add(new XSRecord(0, 0, 0)));
        delQR.setOnMouseClicked(new RowDeleter(qrTableView));
        delQS.setOnMouseClicked(new RowDeleter(qsTableView));
        delXC.setOnMouseClicked(new RowDeleter(xcTableView));
        delXS.setOnMouseClicked(new RowDeleter(xsTableView));
    }

    private void setEditable() {
        eCol.setCellFactory(forTableColumn(new DoubleStringConverter()));
        eCol.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setEValue(e.getNewValue()));
        bCol.setCellFactory(forTableColumn(new DoubleStringConverter()));
        bCol.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setBValue(e.getNewValue()));
        lCol.setCellFactory(forTableColumn(new DoubleStringConverter()));
        lCol.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setLValue(e.getNewValue()));
        aCol.setCellFactory(forTableColumn(new DoubleStringConverter()));
        aCol.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setAValue(e.getNewValue()));
        xcCol.setCellFactory(forTableColumn(new DoubleStringConverter()));
        xcCol.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setXCValue(e.getNewValue()));
        nCol.setCellFactory(forTableColumn(new IntegerStringConverter()));
        nCol.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setNValue(e.getNewValue()));
        fCol.setCellFactory(forTableColumn(new DoubleStringConverter()));
        fCol.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setFValue(e.getNewValue()));
        n2Col.setCellFactory(forTableColumn(new IntegerStringConverter()));
        n2Col.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setNValue(e.getNewValue()));
        qxCol.setCellFactory(forTableColumn(new DoubleStringConverter()));
        qxCol.setOnEditCommit(e -> e.getTableView().getItems().get(e.getTablePosition().getRow()).setQXValue(e.getNewValue()));
    }

    private void setCellValueFactories() {
        eCol.setCellValueFactory(new PropertyValueFactory<>("EValue"));
        bCol.setCellValueFactory(new PropertyValueFactory<>("BValue"));
        lCol.setCellValueFactory(new PropertyValueFactory<>("LValue"));
        aCol.setCellValueFactory(new PropertyValueFactory<>("AValue"));
        xcCol.setCellValueFactory(new PropertyValueFactory<>("XCValue"));
        nCol.setCellValueFactory(new PropertyValueFactory<>("NValue"));
        fCol.setCellValueFactory(new PropertyValueFactory<>("FValue"));
        n2Col.setCellValueFactory(new PropertyValueFactory<>("NValue"));
        qxCol.setCellValueFactory(new PropertyValueFactory<>("QXValue"));
    }
}

package com.selrvk.inventory;

import javafx.beans.Observable;
import javafx.beans.binding.Binding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.kordamp.bootstrapfx.scene.layout.Panel;
import javafx.beans.*;
import javafx.beans.binding.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Controller {

    @FXML
    private Button addNewProductBtn;
    @FXML
    private Button deleteProductBtn;
    @FXML
    private ScrollPane productsScrollPane;
    @FXML
    private TextField searchBar;

    private ImageView image;
    private final ObservableList<CheckBox> productsCheckBoxes = FXCollections.observableArrayList();
    private final DatabaseManager dbManager = new DatabaseManager();
    private byte[] uploadImageBytes;
    private TextField nameInput, stockInput, brandInput, shelfInput;
    
    public void initialize(){

        printProducts();

        deleteProductBtn.disableProperty().bind(Bindings.createBooleanBinding(
                () -> productsCheckBoxes.stream().noneMatch(CheckBox::isSelected),
                productsCheckBoxes.stream().map(CheckBox::selectedProperty).toArray(Observable[]::new)
        ));

    }

    public void addNewProduct(){

        Optional<ButtonType> result = initializeAddProductComponents();

        if(!nameInput.getText().isBlank() && !stockInput.getText().isBlank() && !brandInput.getText().isBlank() && !shelfInput.getText().isBlank()) {
            if (result.isPresent() && result.get() == ButtonType.OK) {

                dbManager.addNewProduct(new Product(uploadImageBytes, nameInput.getText(), Integer.parseInt(stockInput.getText()), brandInput.getText(), shelfInput.getText()));

                initialize();
            }
        }
    }

    public void deleteProduct(){

        List<CheckBox> checkBoxesToRemove = new ArrayList<>();
        dbManager.removeProduct(getSelectedProductIds());

        for(CheckBox checkBox : productsCheckBoxes){
            for(Integer id : getSelectedProductIds()){

                if(checkBox.getUserData() == id){

                    checkBoxesToRemove.add(checkBox);
                }
            }
        }

        productsCheckBoxes.removeAll(checkBoxesToRemove);
        initialize();
    }

    public List<Integer> getSelectedProductIds() {
        return productsCheckBoxes.stream()
                .filter(CheckBox::isSelected)
                .map(checkBox -> (Integer) checkBox.getUserData()) // Retrieve the ID from userData
                .collect(Collectors.toList());
    }

    public void updateProduct(){ dbManager.updateProduct(); }

    public void printProducts(){

        VBox productsVBox = new VBox(15);

        List<Product> products = dbManager.getProducts();

        for(Product product : products){

            ProductsPanel panel = new ProductsPanel(product);
            productsVBox.getChildren().add(panel);
            this.productsCheckBoxes.add(panel.getCheckBox());
        }

        productsScrollPane.setContent(productsVBox);
    }

    public Optional<ButtonType> initializeAddProductComponents(){

        Alert addNewProductAlert = new Alert(Alert.AlertType.CONFIRMATION);
        addNewProductAlert.setHeaderText("Add New Product");
        addNewProductAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        Button uploadImageBtn = new Button("Upload Image");
        uploadImageBtn.setPrefSize(100,20);

        uploadImageBtn.setOnAction(actionEvent -> {

            uploadImage(actionEvent);

        });

        this.nameInput = new TextField();
        nameInput.setPromptText("Name");

        this.stockInput = new TextField();
        stockInput.setPromptText("Stock");

        this.brandInput = new TextField();
        brandInput.setPromptText("Brand");

        this.shelfInput = new TextField();
        shelfInput.setPromptText("Shelf Location");

        GridPane gridPane = new GridPane();
        gridPane.add(uploadImageBtn, 0, 1);
        gridPane.add(nameInput, 1 , 1);
        gridPane.add(stockInput, 1 , 2);
        gridPane.add(brandInput, 2, 1);
        gridPane.add(shelfInput, 2, 2);

        addNewProductAlert.getDialogPane().setContent(gridPane);

        return addNewProductAlert.showAndWait();

    }

    public void uploadImage(ActionEvent event){

        File chosenFile;
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(

            new FileChooser.ExtensionFilter("Image Files (*.png, *.jpg)", "*.png", "*.jpg")

        );

        chosenFile = fileChooser.showOpenDialog(stage);

        if(chosenFile != null) {

            try {
                FileInputStream fis = new FileInputStream(chosenFile);
                this.uploadImageBytes = fis.readAllBytes();
                fis.close();

            } catch (IOException e) {

                System.out.println(e.getMessage());
            }

        }
    }
}

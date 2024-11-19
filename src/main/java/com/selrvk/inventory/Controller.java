package com.selrvk.inventory;

import javafx.beans.Observable;
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
import javafx.beans.binding.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Controller {

    @FXML
    private Button deleteProductBtn;
    @FXML
    private ScrollPane productsScrollPane;
    @FXML
    private TextField searchBar;
    @FXML
    private ComboBox<String> sortByComboBox;
    @FXML
    private Button ascendingBtn;
    @FXML
    private Button descendingBtn;
    @FXML
    private TextField stockMin;
    @FXML
    private TextField stockMax;

    private final DatabaseManager dbManager = new DatabaseManager();

    private List<Integer> oldCheckBoxes;
    private final ObservableList<Button> updateButtons = FXCollections.observableArrayList();
    private final ObservableList<CheckBox> productsCheckBoxes = FXCollections.observableArrayList();
    private byte[] uploadImageBytes;
    private TextField nameInput, stockInput, brandInput, shelfInput;
    private boolean ascButtonActive = true;
    private boolean initialized;

    public void initialize(){

        if(!initialized){
            sortByComboBox.setItems(FXCollections.observableArrayList("ID", "Name", "Stock", "Brand"));
            sortByComboBox.getSelectionModel().selectFirst();
            updateAscButton();
        }

        keepCheckBoxes();
        productsCheckBoxes.clear();
        printProducts();

        deleteProductBtn.disableProperty().bind(Bindings.createBooleanBinding(
                () -> productsCheckBoxes.stream().noneMatch(CheckBox::isSelected),
                productsCheckBoxes.stream().map(CheckBox::selectedProperty).toArray(Observable[]::new)
        ));
        stockMin.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            if(!newValue.matches("\\d*")){
                stockMin.setText(oldValue);
            }
        }));
        stockMax.textProperty().addListener(((observableValue, oldValue, newValue) -> {
            if(!newValue.matches("\\d*")){
                stockMax.setText(oldValue);
            }
        }));
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                printProducts();
            }
        });

        ascendingBtn.setOnAction(e -> updateDescButton());
        descendingBtn.setOnAction(e -> updateAscButton());
        sortByComboBox.setOnAction(e -> sortProducts());

        reloadCheckBoxes();
        initialized = true;
    }

    public void addNewProduct(){

        Optional<ButtonType> result = initializeAddProductComponents();

        if(uploadImageBytes != null && !nameInput.getText().isBlank() && !stockInput.getText().isBlank() && !brandInput.getText().isBlank() && !shelfInput.getText().isBlank()) {
            if (result.isPresent() && result.get() == ButtonType.OK) {

                dbManager.addNewProduct(new Product(uploadImageBytes, nameInput.getText(), Integer.parseInt(stockInput.getText()), brandInput.getText(), shelfInput.getText()));
                initialize();
            } else if(result.isPresent() && result.get() == ButtonType.CANCEL) {
                showAlert("Cancelled Request");
            }
        } else {
            showAlert("Incomplete Field");
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

    public void updateProduct(int idToUpdate){

        System.out.println("ID TO UPDATE : " + idToUpdate);
        Product product = dbManager.getProduct(idToUpdate);
        Optional<ButtonType> result = initializeUpdateProductComponents(product);

        if(result.isPresent() && result.get().equals(ButtonType.OK)){

            product.setName(nameInput.getText());
            product.setImg(uploadImageBytes);
            product.setStock(Integer.parseInt(stockInput.getText()));
            product.setBrand(brandInput.getText());
            product.setShelfLocation(shelfInput.getText());
            dbManager.updateProduct(product);
            initialize();

        } else {
            showAlert("Cancelled Request");
        }
    }

    public Optional<ButtonType> initializeUpdateProductComponents(Product product){

        ImageView imageView = new ImageView(new Image(new ByteArrayInputStream(product.getImg())));
        uploadImageBytes = product.getImg();
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        Alert addNewProductAlert = new Alert(Alert.AlertType.CONFIRMATION);
        addNewProductAlert.setHeaderText("Update Product");
        addNewProductAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        Button uploadImageBtn = new Button("Upload Image");
        uploadImageBtn.setPrefSize(100,20);

        uploadImageBtn.setOnAction(e -> imageView.setImage(uploadImage(e)));

        this.nameInput = new TextField();
        nameInput.setPromptText(product.getName());

        this.stockInput = new TextField();
        stockInput.setPromptText("" + product.getStock());

        this.brandInput = new TextField();
        brandInput.setPromptText(product.getBrand());

        this.shelfInput = new TextField();
        shelfInput.setPromptText(product.getLocation());

        GridPane gridPane = new GridPane();
        gridPane.add(imageView, 0, 1);
        gridPane.add(uploadImageBtn, 0, 2);
        gridPane.add(nameInput, 1 , 1);
        gridPane.add(stockInput, 1 , 2);
        gridPane.add(brandInput, 2, 1);
        gridPane.add(shelfInput, 2, 2);

        addNewProductAlert.getDialogPane().setContent(gridPane);
        return addNewProductAlert.showAndWait();
    }

    public void sortProducts(){

        initialize();
    }

    public void printProducts(){

        VBox productsVBox = new VBox(20);

        String query = "SELECT * FROM products WHERE name LIKE ? AND stock BETWEEN ? AND ? ORDER BY " + sortByComboBox.getValue().toLowerCase() + " " + getActiveBtn();
        List<Product> products = dbManager.getProducts(query, getSearchBar(), getMinStockFilter(), getMaxStockFilter());

        for(Product product : products){

            ProductsPanel panel = new ProductsPanel(product);
            productsVBox.getChildren().add(panel);
            this.productsCheckBoxes.add(panel.getCheckBox());
            this.updateButtons.add(panel.getUpdateButton());
            panel.getUpdateButton().setOnAction(e -> updateProduct((Integer) panel.getUpdateButton().getUserData()));
        }
        productsScrollPane.setContent(productsVBox);
    }

    public Optional<ButtonType> initializeAddProductComponents(){

        ImageView imageView = new ImageView();
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        Alert addNewProductAlert = new Alert(Alert.AlertType.CONFIRMATION);
        addNewProductAlert.setHeaderText("Add New Product");
        addNewProductAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        Button uploadImageBtn = new Button("Upload Image");
        uploadImageBtn.setPrefSize(100,20);

        uploadImageBtn.setOnAction(e -> imageView.setImage(uploadImage(e)));

        this.nameInput = new TextField();
        nameInput.setPromptText("Name");

        this.stockInput = new TextField();
        stockInput.setPromptText("Stock");

        this.brandInput = new TextField();
        brandInput.setPromptText("Brand");

        this.shelfInput = new TextField();
        shelfInput.setPromptText("Shelf Location");

        GridPane gridPane = new GridPane();
        gridPane.add(imageView, 0, 1);
        gridPane.add(uploadImageBtn, 0, 2);
        gridPane.add(nameInput, 1 , 1);
        gridPane.add(stockInput, 1 , 2);
        gridPane.add(brandInput, 2, 1);
        gridPane.add(shelfInput, 2, 2);

        addNewProductAlert.getDialogPane().setContent(gridPane);
        return addNewProductAlert.showAndWait();
    }

    public Image uploadImage(ActionEvent event){

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(

            new FileChooser.ExtensionFilter("Image Files (*.png, *.jpg)", "*.png", "*.jpg")
        );

        File chosenFile = fileChooser.showOpenDialog(stage);
        Image image = new Image(chosenFile.toURI().toString());

        try {
            FileInputStream fis = new FileInputStream(chosenFile);
            this.uploadImageBytes = fis.readAllBytes();
            fis.close();

        } catch (IOException e) {

            System.out.println(e.getMessage());
        }
        return image;
    }

    public void updateAscButton(){

        ascendingBtn.setVisible(true);
        ascendingBtn.setDisable(false);
        descendingBtn.setVisible(false);
        descendingBtn.setDisable(true);
        ascButtonActive = true;
        if(initialized){
            sortProducts();
        }
    }

    public void updateDescButton(){

        descendingBtn.setVisible(true);
        descendingBtn.setDisable(false);
        ascendingBtn.setVisible(false);
        ascendingBtn.setDisable(true);
        ascButtonActive = false;
        if(initialized){
            sortProducts();
        }
    }

    public String getActiveBtn(){

        if(ascButtonActive){
            return "ASC";
        } else {
            return "DESC";
        }
    }

    public void showAlert(String alertType){

        if(alertType.equals("Incomplete Field")){

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Incomplete Fields");
            alert.showAndWait();
        } else if (alertType.equals("Cancelled Request")) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Cancelled Request");
            alert.showAndWait();
        }
    }

    public void applyFilters(){
        initialize();
    }

    public String getSearchBar(){

        if(searchBar.getText().isBlank()){
            return "%";
        } else {
            return "%" + searchBar.getText() + "%";
        }
    }

    public int getMinStockFilter(){

        if(stockMin.getText().isBlank()){
            return 0;
        } else {
            return Integer.parseInt(stockMin.getText());
        }
    }

    public int getMaxStockFilter(){

        if(stockMax.getText().isBlank()){
            return 9999999;
        } else {
            return Integer.parseInt(stockMax.getText());
        }
    }

    public void keepCheckBoxes(){

        this.oldCheckBoxes = getSelectedProductIds();
    }

    public void reloadCheckBoxes(){

        for(CheckBox checkBoxes : productsCheckBoxes){
            for(Integer checkBoxesToKeep : oldCheckBoxes){

                if(checkBoxes.getUserData() == checkBoxesToKeep){
                    checkBoxes.setSelected(true);
                }
            }
        }
    }

    public void setUsername(String username){
        dbManager.setUsername(username);
    }

    public void setPassword(String password){
        dbManager.setPassword(password);
    }
}

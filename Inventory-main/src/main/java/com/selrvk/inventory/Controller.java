package com.selrvk.inventory;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.beans.binding.*;

//import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.sql.Date;
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
    @FXML
    private Button pendingOrdersButton;
    @FXML
    private Button orderHistoryButton;
    @FXML
    private Button createOrderButton;
    @FXML
    private Button cancelOrderButton;
    @FXML
    private Button confirmOrderButton;

    @FXML private AnchorPane create_order_labelsPane;

    private final DatabaseManager dbManager = new DatabaseManager();

    private List<Integer> oldCheckBoxes;
    private final List<TextField> createOrderStockTextField = FXCollections.observableArrayList();
    private final List<OrdersProducts> createOrderProducts = FXCollections.observableArrayList();
    private final ObservableList<Button> updateButtons = FXCollections.observableArrayList();
    private final ObservableList<CheckBox> productsCheckBoxes = FXCollections.observableArrayList();
    //private byte[] uploadImageBytes;
    private TextField nameInput, stockInput, srpInput, buyingPriceInput, manufacturerInput, sellingPriceInput ,customerNameInput;
    private boolean ascButtonActive = true;
    private boolean initialized;

    /*
    Initializes components including:
        sortByComboBox
        CheckBoxes
        deleteProductBtn
        stockMin, stockMax
        searchBar
        ascendingBtn, descendingBtn
     - Called by default
     */
    public void initialize(){

        productsScrollPane.getStyleClass().add("main-scroll-pane");

        if(!initialized){
            sortByComboBox.setItems(FXCollections.observableArrayList("ID", "Name", "Stock", "Manufacturer"));
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
        pendingOrdersButton.setOnAction(e -> openPendingOrders());
        orderHistoryButton.setOnAction(e -> openOrderHistory());
        createOrderButton.setOnAction(e -> createOrder());
        cancelOrderButton.setOnAction(e -> cancelOrder());
        confirmOrderButton.setOnAction(e -> confirmOrder());

        reloadCheckBoxes();
        initialized = true;
    }

    /*
    void addNewProduct()
    @param uploadImageBytes, nameInput, stockInput, brandInput, shelfInput
    Adds a new product to the database.
    Passes proper Product parameters.
    - Calls initializeAddProductComponents().
    - Calls DatabaseManager addNewProduct(*Product parameters*).
    - Calls initialize().
    -- Called by FXML.
     */
    public void addNewProduct(){

        // Called the method which creates an Add Product panel.
        Optional<ButtonType> result = initializeAddProductComponents();

        if(!nameInput.getText().isBlank() && !stockInput.getText().isBlank() && !manufacturerInput.getText().isBlank()) {
            if (result.isPresent() && result.get() == ButtonType.OK) {

                dbManager.addNewProduct(new Product(nameInput.getText(), Integer.parseInt(stockInput.getText()), Double.parseDouble(srpInput.getText()), Double.parseDouble(buyingPriceInput.getText()),manufacturerInput.getText()));
                initialize();
            } else if(result.isPresent() && result.get() == ButtonType.CANCEL) {
                showAlert("Cancelled Request");
            }
        } else {
            showAlert("Incomplete Field");
        }
    }

    /*
    void deleteProduct()
    @param productsCheckBoxes
    Deletes selected item(s) from the database.
    - Calls DatabaseManager removeProduct(getSelectedProductIds()).
    - Calls getSelectedProductIds().
    - Calls initialize().
    -- Called by FXML.
     */
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

    /*
    List<Integer> getSelectedProductIds()
    @param productsCheckBoxes
    Gets all the selected products from ObsList productsCheckBoxes.
    -- Called by deleteProduct() , keepCheckBoxes().
     */
    public List<Integer> getSelectedProductIds() {
        return productsCheckBoxes.stream()
                .filter(CheckBox::isSelected)
                .map(checkBox -> (Integer) checkBox.getUserData())
                .collect(Collectors.toList());
    }

    /*
    void updateProduct()
    @param idToUpdate, nameInput, uploadImageBytes, stockInput, brandInput, shelfInput
    Updates a product, given a product ID.
    - Calls DatabaseManage getProduct(idToUpdate).
    - Calls initializeUpdateProductComponents(product).
    -- Called by FXML.
     */
    public void updateProduct(int idToUpdate){

        Product product = dbManager.getProduct(idToUpdate);
        Optional<ButtonType> result = initializeUpdateProductComponents(product);

        if(result.isPresent() && result.get().equals(ButtonType.OK)){

            if(!(nameInput.getText().isBlank())){
                product.setName(nameInput.getText());
            }
            if(!(stockInput.getText().isBlank())){
                product.setStock(Integer.parseInt(stockInput.getText()));
            }
            if(!(manufacturerInput.getText().isBlank())){
                product.setManufacturer(manufacturerInput.getText());
            }
            if(!(buyingPriceInput.getText().isBlank())){
                product.setBuyingPrice(Integer.parseInt(buyingPriceInput.getText()));
            }
            if(!(sellingPriceInput.getText().isBlank())){
                product.setSrp(Integer.parseInt(sellingPriceInput.getText()));
            }

            dbManager.updateProduct(product);
            initialize();

        } else {
            showAlert("Cancelled Request");
        }
    }

    /*
    Optional<ButtonType> initializeUpdateProductComponents(Product product)
    @param product, uploadImageBytes, nameInput, stockInput, brandInput, shelfInput.
    The panel which creates and holds components for updating products.
    - Calls Product getImg().
    -- Called by updateProduct(int idToUpdate).
     */
    public Optional<ButtonType> initializeUpdateProductComponents(Product product){

        Alert addNewProductAlert = new Alert(Alert.AlertType.CONFIRMATION);
        addNewProductAlert.setHeaderText("Update Product");
        addNewProductAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        //Button uploadImageBtn = new Button("Upload Image");
        //uploadImageBtn.setPrefSize(100,20);

        Label spaceLabel = new Label(" ");
        Label spaceLabel2 = new Label(" ");
        Label spaceLabel3 = new Label(" ");
        Label spaceLabel4 = new Label("       ");
        Label spaceLabel5 = new Label("       ");

        Label nameLabel = new Label("Name: ");
        this.nameInput = new TextField();
        nameInput.setPromptText(product.getName());

        Label stockLabel = new Label("Stock: ");
        this.stockInput = new TextField();
        stockInput.setPromptText("" + product.getStock());

        Label manufacturerLabel = new Label("Manufacturer: ");
        this.manufacturerInput = new TextField();
        manufacturerInput.setPromptText(product.getManufacturer());

        Label buyingPriceLabel = new Label("Buying Price: ");
        this.buyingPriceInput = new TextField();
        buyingPriceInput.setPromptText("" + product.getBuyingPrice());

        Label sellingPriceLabel = new Label("Selling Price: ");
        this.sellingPriceInput = new TextField();
        sellingPriceInput.setPromptText("" + product.getSrp());


        GridPane gridPane = new GridPane();
        //gridPane.add(uploadImageBtn, 0, 2);
        gridPane.add(nameLabel,0,0);
        gridPane.add(nameInput, 0 , 1);
        gridPane.add(spaceLabel, 0, 2);

        gridPane.add(stockLabel,0,3);
        gridPane.add(stockInput, 0 , 4);
        gridPane.add(spaceLabel2, 0, 5);

        gridPane.add(manufacturerLabel,0,6);
        gridPane.add(manufacturerInput, 0, 7);

        gridPane.add(spaceLabel4, 1, 0);
        gridPane.add(spaceLabel5, 1, 1);


        gridPane.add(buyingPriceLabel, 2, 0);
        gridPane.add(buyingPriceInput, 2, 1);
        gridPane.add(spaceLabel3, 2, 2);

        gridPane.add(sellingPriceLabel, 2, 3);
        gridPane.add(sellingPriceInput, 2, 4);

        addNewProductAlert.getDialogPane().setContent(gridPane);
        return addNewProductAlert.showAndWait();
    }

    /*
    void sortProducts()
    Sorts products, ascending or descending.
    - Calls initialize().
    -- Called by updateAscButton().
    -- Called by updateDescButton().
     */
    public void sortProducts(){

        initialize();
    }

    /*
    void printProducts()
    @param productsCheckBoxes, updateButtons, productsScrollPane
    Passes in a modifiable query, printing the products, initializing buttons and actions
    and adding it all into a panel.
    - Calls DatabaseManager getProducts(query).
    - Calls ProductsPanel getCheckBox().
    - Calls ProductsPanel getUpdateButton().
    -- Called by initialize().
     */
    public void printProducts(){

        VBox productsVBox = new VBox(0);

        String query = "SELECT * FROM products WHERE name LIKE ? AND stock BETWEEN ? AND ? ORDER BY " + sortByComboBox.getValue().toLowerCase() + " " + getActiveBtn();
        List<Product> products = dbManager.getProducts(query, getSearchBar(), getMinStockFilter(), getMaxStockFilter());

        for(Product product : products){

            ProductsPanel panel = new ProductsPanel(product);
            productsVBox.getChildren().add(panel);
            //PricePanel pricePanel = new PricePanel(product);
            //productsVBox.getChildren().add(pricePanel);
            this.productsCheckBoxes.add(panel.getCheckBox());
            this.updateButtons.add(panel.getUpdateButton());
            panel.getUpdateButton().setOnAction(e -> updateProduct((Integer) panel.getUpdateButton().getUserData()));
            Region spacer = new Region();
            spacer.setMinHeight(20);
            productsVBox.getChildren().add(spacer);
        }

        productsScrollPane.setContent(productsVBox);
    }

    /*
    Optional<ButtonType> initializeAddProductComponents()
    @param nameInput, stockInput, brandInput, shelfInput
    The panel which creates and holds components for adding products.
    -- Called by addNewProduct().
     */
    public Optional<ButtonType> initializeAddProductComponents(){

        ImageView imageView = new ImageView();
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);

        Alert addNewProductAlert = new Alert(Alert.AlertType.CONFIRMATION);
        addNewProductAlert.setHeaderText("Add New Product");
        addNewProductAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        //Button uploadImageBtn = new Button("Upload Image");
        //uploadImageBtn.setPrefSize(100,20);

        //uploadImageBtn.setOnAction(e -> imageView.setImage(uploadImage(e)));

        this.nameInput = new TextField();
        nameInput.setPromptText("Name");

        this.stockInput = new TextField();
        stockInput.setPromptText("Stock");

        this.srpInput = new TextField();
        srpInput.setPromptText("SRP");

        this.buyingPriceInput = new TextField();
        buyingPriceInput.setPromptText("Buying Price");

        this.manufacturerInput = new TextField();
        manufacturerInput.setPromptText("Manufacturer");

        GridPane gridPane = new GridPane();
        //gridPane.add(imageView, 0, 1);
        //gridPane.add(uploadImageBtn, 0, 2);
        gridPane.add(nameInput, 1 , 1);
        gridPane.add(stockInput, 1 , 2);
        gridPane.add(srpInput, 2, 1);
        gridPane.add(buyingPriceInput, 2 , 2);
        gridPane.add(manufacturerInput, 3, 1);

        addNewProductAlert.getDialogPane().setContent(gridPane);
        return addNewProductAlert.showAndWait();
    }

    /*
    Image uploadImage(ActionEvent event)
    @param uploadImageBytes
    Handles uploading images for product usage.
    -- Called by FXML.
     */
    public Image uploadImage(ActionEvent event){

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(

            new FileChooser.ExtensionFilter("Image Files (*.png, *.jpg, *.jpeg)", "*.png", "*.jpg", "*.jpeg")
        );

        File chosenFile = fileChooser.showOpenDialog(stage);
        Image image = new Image(chosenFile.toURI().toString());

        try {
            FileInputStream fis = new FileInputStream(chosenFile);
            //this.uploadImageBytes = fis.readAllBytes();
            fis.close();

        } catch (IOException e) {

            System.out.println(e.getMessage());
        }
        return image;
    }

    /*
    void updateAscButton()
    @param ascendingBtn, descendingBtn
    Handles toggling ascending button for sorting.
    - Calls sortProducts().
    -- Called by FXML.
     */
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
    /*
    void updateDescButton()
    @param ascendingBtn, descendingBtn
    Handles toggling descending button for sorting.
    - Calls sortProducts().
    -- Called by FXML.
     */
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

    /*
    String getActiveBtn()
    @param ascButtonActive
    Gets which button is active, determining how the printed products will be sorted.
    -- Called by printProducts().
     */
    public String getActiveBtn(){

        if(ascButtonActive){
            return "ASC";
        } else {
            return "DESC";
        }
    }

    /*
    void showAlert(String alertType)
    Handles what type of alert to show.
    - Called by addNewProduct().
     */
    public void showAlert(String alertType){

        Alert alert;

        switch (alertType){
            case "Incomplete Field":
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Incomplete Fields");
                alert.showAndWait();
                break;
            case "Cancelled Request":
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Cancelled Request");
                alert.showAndWait();
                break;
            case "Not enough stock":
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Order quantity higher than available stock");
                alert.showAndWait();
                break;
            default:
                System.out.println("Unknown error");
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

    /*
    void keepCheckBoxes()
    @param oldCheckBoxes
    Gets the currently selected products and adds them to a list.
    -- Called by initialize().
     */
    public void keepCheckBoxes(){

        this.oldCheckBoxes = getSelectedProductIds();
    }

    /*
    void reloadCheckBoxes()
    @param productsCheckBoxes, oldCheckBoxes
    Reloads all selected checkBoxes so that they are selected again
    when item filters are applied.
    -- Called by initialize().
     */
    public void reloadCheckBoxes(){

        for(CheckBox checkBoxes : productsCheckBoxes){
            for(Integer checkBoxesToKeep : oldCheckBoxes){

                if(checkBoxes.getUserData() == checkBoxesToKeep){
                    checkBoxes.setSelected(true);
                }
            }
        }
    }

    /*
    void setUsername()
    Sets the username for input verification.
    -- Called in LoginController setUsername().
     */
    public void setUsername(String username){
        dbManager.setUsername(username);
    }

    /*
    void setPassword()
    Sets the password for input verification.
    -- Called in LoginController setPassword().
     */
    public void setPassword(String password){
        dbManager.setPassword(password);
    }

    public void openPendingOrders(){

        try {

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Orders.fxml")));
            Stage stage = (Stage) pendingOrdersButton.getScene().getWindow();
            stage.setTitle("Pending Orders");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void openOrderHistory(){

        try {

            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("OrderHistory.fxml")));
            Stage stage = (Stage) orderHistoryButton.getScene().getWindow();
            stage.setTitle("Order History");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createOrder(){

        confirmOrderButton.setVisible(true);
        confirmOrderButton.setDisable(false);
        addNewProductBtn.setDisable(true);
        addNewProductBtn.setVisible(false);
        createOrderButton.setVisible(false);
        createOrderButton.setDisable(true);
        cancelOrderButton.setVisible(true);
        cancelOrderButton.setDisable(false);
        productsCheckBoxes.clear();
        initialize();

        this.create_order_labelsPane.setVisible(true);
        productsScrollPane.setContent(null);

        VBox productsVBox = new VBox(20);

        String query = "SELECT * FROM products WHERE name LIKE ? AND stock BETWEEN ? AND ? ORDER BY " + sortByComboBox.getValue().toLowerCase() + " " + getActiveBtn();
        List<Product> products = dbManager.getProducts(query, getSearchBar(), getMinStockFilter(), getMaxStockFilter());

        for(Product product : products){

            CreateOrderPanel createOrderPanel = new CreateOrderPanel(product);
            productsVBox.getChildren().add(createOrderPanel);
            this.createOrderStockTextField.add(createOrderPanel.getProductTextBox());
        }

        productsVBox.setSpacing(20);
        productsScrollPane.setContent(productsVBox);
    }

    public void cancelOrder(){

        confirmOrderButton.setVisible(false);
        confirmOrderButton.setDisable(true);
        addNewProductBtn.setDisable(false);
        addNewProductBtn.setVisible(true);
        cancelOrderButton.setVisible(false);
        cancelOrderButton.setDisable(true);
        createOrderButton.setVisible(true);
        createOrderButton.setDisable(false);
        this.create_order_labelsPane.setVisible(false);

        initialize();
    }

    public void confirmOrder(){

        Date date = new Date(System.currentTimeMillis());
        Optional<ButtonType> result = initializeCreateOrderCustomerName();
        boolean valid = true;

        if(result.isPresent() && result.get().equals(ButtonType.OK)){

            if(!(customerNameInput.getText().isBlank())) {

                List<TextField> inputs = getTextFieldsWithInput();

                for(TextField textField : inputs){

                    Product product = (Product) textField.getUserData();

                    if(Integer.parseInt(textField.getText()) <= product.getStock()){

                        System.out.println("Product name: " + product.getName());
                        System.out.println("Product stock: " + product.getStock());
                        System.out.println("Get text: " + textField.getText() + "\n");

                        createOrderProducts.add(new OrdersProducts(product.getName(), product.getId() ,Integer.parseInt(textField.getText()), product.getSrp()));

                    } else {

                        valid = false;
                        showAlert("Not enough stock");
                        inputs.clear();
                        createOrderProducts.clear();

                        for(TextField field : createOrderStockTextField) {
                            field.clear();
                        }

                        break;
                    }
                }

                if(valid){

                    dbManager.createOrder(new Orders(date, customerNameInput.getText(), createOrderProducts));
                    cancelOrder();
                }

                createOrderProducts.clear();
            }

        } else {
            showAlert("Cancelled Request");
        }
    }

    public List<TextField> getTextFieldsWithInput() {

        return createOrderStockTextField.stream()
                .filter(textField -> !textField.getText().isEmpty())
                .collect(Collectors.toList());
    }

    public Optional<ButtonType> initializeCreateOrderCustomerName(){

        Alert addNewProductAlert = new Alert(Alert.AlertType.CONFIRMATION);
        addNewProductAlert.setHeaderText("Enter customer name");
        addNewProductAlert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);

        this.customerNameInput = new TextField();
        customerNameInput.setPromptText("Enter customer name");

        GridPane gridPane = new GridPane();
        gridPane.add(customerNameInput, 1 , 1);

        addNewProductAlert.getDialogPane().setContent(gridPane);
        return addNewProductAlert.showAndWait();
    }


}

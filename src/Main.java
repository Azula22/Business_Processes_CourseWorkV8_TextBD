import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class Main extends Application {


    //Перший інтерфейс
    VBox mainVBox = new VBox();
    Label lb_greeteng = new Label("Вітаємо");
    //Зображення
    Image srartingImage = new Image("pcrs/biznes-ide.jpg");
    ImageView startImageView = new ImageView();


    //Табличка матеріалів
    private TableView tableViewTang = new TableView();
    TableColumn clmn_id = new TableColumn("#");
    TableColumn clmn_name = new TableColumn("Назва матеріалу");
    TableColumn clmn_priceOne = new TableColumn("Ціна за одиницю");
    TableColumn clmn_priceAll = new TableColumn("Загальна ціна");
    TableColumn clmn_dateOFreceiv = new TableColumn("Дата отримання");
    TableColumn clmn_amount = new TableColumn("Кількість");
    TableColumn clmn_measureValue = new TableColumn("Величина вимірювання");
    TableColumn clmn_resp_person = new TableColumn("Відповідальна особа");
    TableColumn clmn_debitDate = new TableColumn("Дата списання");

    //Табличка руху матеріальних цінностей
    private TableView tableViewMotion = new TableView();
    TableColumn clmn_NameMotion = new TableColumn("Назва матеріальної цінності");
    TableColumn clmn_amountMotion = new TableColumn("Зміна кількості");
    TableColumn clmn_priceMotion = new TableColumn("Обіг грошей");
    TableColumn clmn_dateMotion = new TableColumn("Дата зміни");

    //Інтерфейс для Оформлення приходу матеріалів
    TextField textFieldsName = new TextField();
    TextField textFieldsPrice = new TextField();
    DatePicker datePickersDebitting = new DatePicker();
    TextField textFieldsAmount = new TextField();
    TextField textFieldsMeasureValue = new TextField();
    TextField textFieldsPersons = new TextField();
    HBox hBoxTakeTangibles = new HBox();
    Button button_submit_adding = new Button("Підтвердити");


    //Інтерфейс для Оформлення відходу матеріалів
    TextField textFieldId = new TextField();
    TextField textFieldsAmountOut = new TextField();
    TextField textFieldsPriceForOneOut = new TextField();
    Button button_submit_out = new Button("Підтвердити");

    //Інтерфейс для списання матеріалів
    private TableView tableViewWriteOff = new TableView();
    TableColumn clmn_NameWriteOff = new TableColumn("Назва матеріальної цінності");
    TableColumn clmn_DateOfWriteOff = new TableColumn("Необхідна дата списання");
    TableColumn clmn_dateAmountWriteOff = new TableColumn("Кількість матеріалу");
    TableColumn clmn_datePriceWriteOff = new TableColumn("Оббіг грошей");
    TableColumn clmn_dateMeasValWriteOff = new TableColumn("Одиниця вимірювання");

    //Кнопки зліва
    Button button_motion_property = new Button("Рух матеріальних цінностей");
    Button button_handbook_of_property = new Button("Довідник матеріальних цінностей та працівників");
    Button button_property_coming = new Button("Оформлення приходу матеріалів");
    Button button_property_consumption = new Button("Оформлення витрат матеріалів");
    Button button_writeOff_property = new Button("Списані матеріальні цінності");
    Button button_attorney = new Button("Виписка довіреностей");
    Button button_form_report_come = new Button("Cформувати звіт");
    Button button_form_report_out = new Button("Cформувати звіт");
    VBox buttonsVBox = new VBox();

    HBox startingHBox = new HBox();

    //Для зображення БД матеріалів
    TangiblePersonDatabase tangiblesDataBase = new TangiblePersonDatabase();
    private ObservableList dataTangPers = FXCollections.observableArrayList();


    //Для зображення БД руху
    MotionDatabase motionDataBase = new MotionDatabase();
    private ObservableList dataMotion = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) throws Exception {

        DBWork();

        setCompleteInterface();
        setButtonsListeners();

        Scene startScene = new Scene(startingHBox);
        primaryStage.setTitle("Облік матеріальних цінностей");
        primaryStage.setMaximized(true);
        primaryStage.setIconified(true);

        primaryStage.setScene(startScene);
        primaryStage.show();
    }

    void DBWork() {
        motionDataBase.checkIfCreated();
        tangiblesDataBase.checkIfCreated();
        dataTangPers = tangiblesDataBase.listFromReaded();
        dataMotion = motionDataBase.listFromReaded();
        tableViewTang.setItems(dataTangPers);
    }

    void setCompleteInterface() {
        setStartInterface();
        setComingTangiblesInterface();
        makeTableForTangibles();
        makeTableForMotion();
        makeTableForWriteOff();
    }

    void setStartInterface() {
        //Додали зображення
        startImageView.setImage(srartingImage);
        mainVBox.getChildren().setAll(lb_greeteng, startImageView);

        //Додали кнопки
        setButtonsOnLeft();
        startingHBox.getChildren().setAll(mainVBox, buttonsVBox);

    }

    void setComingTangiblesInterface() {
        textFieldsName.setPromptText("Назва матеріалу");
        textFieldsAmount.setPromptText("Кількість (цифра)");
        textFieldsMeasureValue.setPromptText("Вимірювальна величина");
        textFieldsPrice.setPromptText("Ціна (формат #.##)");
        textFieldsPersons.setPromptText("Відповідальна особа");
        datePickersDebitting.setPromptText("Дата списання");
    }

    void makeTableForTangibles() {
        tableViewTang.setPrefWidth(1000);
        clmn_id.setCellValueFactory(new PropertyValueFactory<>("idd"));
        clmn_name.setCellValueFactory(new PropertyValueFactory<>("tangName"));
        clmn_amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        clmn_measureValue.setCellValueFactory(new PropertyValueFactory<>("measValue"));
        clmn_priceOne.setCellValueFactory(new PropertyValueFactory<>("priceForOne"));
        clmn_priceAll.setCellValueFactory(new PropertyValueFactory<>("priceForAll"));
        clmn_resp_person.setCellValueFactory(new PropertyValueFactory<>("responsible"));
        clmn_dateOFreceiv.setCellValueFactory(new PropertyValueFactory<>("receivingDate"));
        clmn_debitDate.setCellValueFactory(new PropertyValueFactory<>("debittingDate"));

        tableViewTang.getColumns().setAll(clmn_id,
                clmn_name,
                clmn_amount,
                clmn_measureValue,
                clmn_priceOne,
                clmn_priceAll,
                clmn_resp_person,
                clmn_dateOFreceiv,
                clmn_debitDate);
    }

    void makeTableForWriteOff() {
        //TODO table
    }

    void makeTableForMotion() {
        tableViewMotion.setPrefWidth(1000);
        clmn_NameMotion.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmn_amountMotion.setCellValueFactory(new PropertyValueFactory<>("change"));
        clmn_priceMotion.setCellValueFactory(new PropertyValueFactory<>("money"));
        clmn_dateMotion.setCellValueFactory(new PropertyValueFactory<>("date"));
        tableViewMotion.getColumns().setAll(clmn_NameMotion,
                clmn_amountMotion,
                clmn_priceMotion,
                clmn_dateMotion);
    }

    void setButtonsOnLeft() {
        buttonsVBox.getChildren().setAll(button_handbook_of_property,
                button_motion_property,
                button_property_coming,
                button_property_consumption,
                button_writeOff_property,
                button_attorney);
        buttonsVBox.setPadding(new Insets(5));
        buttonsVBox.setSpacing(5);
    }

    void setButtonsListeners() {

        //Оформлення приходу
        button_property_coming.setOnAction((ActionEvent e) -> {
            setInterfaceForPropertyComing();
            //Занесення матеріалу в БД
            button_submit_adding.setOnAction((ActionEven) -> addTangibleToDB());
        });

        button_handbook_of_property.setOnAction(ActionEvent -> {
            tableViewTang.setItems(dataTangPers);
            mainVBox.getChildren().setAll(tableViewTang);
        });

        button_motion_property.setOnAction(ActionEvent -> {
            tableViewMotion.setItems(dataMotion);
            mainVBox.getChildren().setAll(tableViewMotion);
        });

        button_property_consumption.setOnAction(ActionEvent -> {
            setInterfaceForPropertyLeaving();
            button_submit_out.setOnAction((ActionEvent e) -> {
                deleteAmountFromDB();
            });
        });
    }

    private void deleteAmountFromDB() {
        int textid = Integer.parseInt(textFieldId.getText());
        int amount = Integer.parseInt(textFieldsAmountOut.getText());
        tangiblesDataBase.selledAmount = Integer.parseInt(textFieldsAmountOut.getText());
        tangiblesDataBase.gettedMoney = Integer.parseInt(textFieldsPriceForOneOut.getText());

        textFieldId.clear();
        textFieldsAmountOut.clear();
        textFieldsPriceForOneOut.clear();
        //Переписати значення в БД мат.цінностей
        dataTangPers = tangiblesDataBase.deleteTangiblesWithSettedAmount(textid, amount);

        //Перевиведення БД матеріальних цінностей на екран
        DBWork();

        mainVBox.getChildren().setAll(hBoxTakeTangibles, button_submit_adding, tableViewTang, button_form_report_come);
        //Зробити звіт
        button_form_report_come.setOnAction((ActionEvent rep) -> {
            formReportForLeaving();
        });
    }

    private void formReportForLeaving() {
        File file = new File("/home/anna/Business_Processes_MyReport/reports/leave/" + "Report" + generateNumberForMatLeave() + ".txt");

        String content = "\t\t\t\t\t\t" + file.getName() + "\n\n\n" + "\t"
                + "1. Назва матеріалу: " + tangiblesDataBase.tangibleAndPersonForLeaving.getTangName() + " ;\n\t"
                + "2. Кількість: " + tangiblesDataBase.selledAmount + " " + tangiblesDataBase.tangibleAndPersonForLeaving.getMeasValue() + " ;\n\t"
                + "3. Собівартість за одиницю : " + tangiblesDataBase.tangibleAndPersonForLeaving.getPriceForOne()  + " ;\n\t"
                + "4. Собівартість за весь товар : " + tangiblesDataBase.tangibleAndPersonForLeaving.getPriceForOne()* tangiblesDataBase.selledAmount+ " ;\n\t"
                + "5. Ціна продажу за одиницю : " + tangiblesDataBase.gettedMoney+ " ;\n\t"
                + "6. Ціна продажу за весь товар : " + tangiblesDataBase.gettedMoney* tangiblesDataBase.selledAmount+ " ;\n\t"
                + "7. Відповідальна особа: " + tangiblesDataBase.tangibleAndPersonForLeaving.getResponsible() + " ;\n\t"
                + "8. Кінцевий термін експлуатації матеріалу: " + tangiblesDataBase.tangibleAndPersonForLeaving.getDebittingDate() + " ;\n\t"
                + "\n\n\n\t\t\t\t\t\t\t\t\t\tДата витрати матеріалу та складання звіту: " + LocalDate.now().toString();
        generateFile(file, content);
    }

    private void setInterfaceForPropertyLeaving() {
        hBoxTakeTangibles.getChildren().setAll(textFieldId,
                textFieldsAmountOut, textFieldsPriceForOneOut);
        mainVBox.getChildren().setAll(hBoxTakeTangibles, button_submit_out, tableViewTang);
    }

    private void formReportForComing(TangibleAndPerson tangible) {
        File file = new File("/home/anna/Business_Processes_MyReport/reports/come/" + "Report" + generateNumberForMatLeave() + ".txt");

        String content = "\t\t\t\t\t\t" + file.getName() + "\n\n\n" + "\t"
                + "1. Назва матеріалу: " + tangible.tangName + " ;\n\t"
                + "2. Кількість: " + tangible.amount + " " + tangible.measValue + " ;\n\t"
                + "3. Ціна за одиницю: " + tangible.priceForOne + " грн ;\n\t"
                + "4. Ціна в загальному: " + tangible.priceForAll + " грн ;\n\t"
                + "5. Відповідальна особа: " + tangible.getResponsible() + " ;\n\t"
                + "6. Кінцевий термін експлуатації матеріалу: " + tangible.debittingDate.toString() + " ;\n\t"
                + "\n\n\n\t\t\t\t\t\t\t\t\t\tДата отримання матеріалу та складання звіту: " + LocalDate.now();
        generateFile(file, content);
    }


    public void generateFile(File file, String content) {
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String generateNumberForMatLeave() {
        File file = new File("/home/anna/Business_Processes/src/reports/leave/");
        String[] cn = file.list();
        if (cn == null) {
            return "1";
        }
        return String.valueOf(cn.length + 1);
    }


    String generateNumberForMatCome() {
        File file = new File("/home/anna/Business_Processes_MyReport/reports/come/");
        String[] cn = file.list();
        if (cn == null) {
            return "1";
        }
        return String.valueOf(cn.length + 1);
    }

    private void addTangibleToDB() {
        TangibleAndPerson tangible = new TangibleAndPerson(++tangiblesDataBase.idTangible,
                textFieldsName.getText(),
                Integer.parseInt(textFieldsAmount.getText()),
                textFieldsMeasureValue.getText(),
                Double.parseDouble(textFieldsPrice.getText()),
                Double.parseDouble(textFieldsPrice.getText()) * Integer.parseInt(textFieldsAmount.getText()),
                textFieldsPersons.getText(),
                datePickersDebitting.getValue());
        Motion motion = new Motion(tangible.getTangName(), " + " + tangible.getAmount(), " + " + tangible.getPriceForAll(), LocalDate.now());

        tangiblesDataBase.writeData(tangible);
        motionDataBase.writeData(motion);

        //Очистити значення в комірках
        textFieldsName.clear();
        textFieldsAmount.clear();
        textFieldsMeasureValue.clear();
        textFieldsPersons.clear();
        textFieldsPrice.clear();

        //Перевиведення БД матеріальних цінностей на екран
        DBWork();

        mainVBox.getChildren().setAll(hBoxTakeTangibles, button_submit_adding, tableViewTang, button_form_report_come);
        //Зробити звіт
        button_form_report_come.setOnAction((ActionEvent rep) -> {
            formReportForComing(tangible);
        });

    }

    private void setInterfaceForPropertyComing() {
        hBoxTakeTangibles.getChildren().setAll(textFieldsName,
                textFieldsAmount,
                textFieldsMeasureValue,
                textFieldsPrice,
                textFieldsPersons,
                datePickersDebitting);
        mainVBox.getChildren().setAll(hBoxTakeTangibles, button_submit_adding, tableViewTang);
    }


    public static void main(String[] args) {
        launch(args);
    }
}

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
import java.util.LinkedList;

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
    TableColumn clmn_dateOFreceiv = new TableColumn("Дата отрим.");
    TableColumn clmn_amount = new TableColumn("Кільк.");
    TableColumn clmn_measureValue = new TableColumn("Од. вим.");
    TableColumn clmn_resp_person = new TableColumn("Відповідальна особа");
    TableColumn clmn_debitDate = new TableColumn("Дата спис.");

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
    //Button button_attorney = new Button("Виписка довіреностей");
    Button button_form_report_come = new Button("Cформувати звіт");
    VBox buttonsVBox = new VBox();

    HBox startingHBox = new HBox();

    //Для зображення БД матеріалів
    TangiblePersonDatabase tangiblesDataBase = new TangiblePersonDatabase();
    private ObservableList dataTangPers = FXCollections.observableArrayList();


    //Для зображення БД руху
    MotionDatabase motionDataBase = new MotionDatabase();
    private ObservableList dataMotion = FXCollections.observableArrayList();

    //Для зображення списаних матеріальних цінностей
    WriteOffDatabase writeOffDatabase = new WriteOffDatabase();
    private ObservableList dataWriteOff = FXCollections.observableArrayList();

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
        writeOffDatabase.checkIfCreated();
        dataTangPers = tangiblesDataBase.listFromReaded();
        dataMotion = motionDataBase.listFromReaded();
        dataWriteOff = writeOffDatabase.listFromReader();
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
        startImageView.setFitWidth(1000);
        mainVBox.getChildren().setAll(startImageView);
        mainVBox.setSpacing(5);
        //Додали кнопки
        setButtonsOnLeft();
        startingHBox.getChildren().setAll(mainVBox, buttonsVBox);

    }

    void setComingTangiblesInterface() {
        textFieldsName.setPromptText("Назва матеріалу");
        textFieldsAmount.setPromptText("Кількість (цифра)");
        textFieldsMeasureValue.setPromptText("Од. вим.");
        textFieldsPrice.setPromptText("Ціна (#.##)");
        textFieldsPersons.setPromptText("Відповідальна особа");
        datePickersDebitting.setPromptText("Дата спис.");

        textFieldsName.setPrefWidth(200);
        textFieldsAmount.setPrefWidth(200);
        textFieldsMeasureValue.setPrefWidth(100);
        textFieldsPrice.setPrefWidth(100);
        textFieldsPersons.setPrefWidth(200);
        datePickersDebitting.setPrefWidth(115);

    }

    void makeTableForTangibles() {
        tableViewTang.setPrefHeight(1000);
        clmn_id.setCellValueFactory(new PropertyValueFactory<>("idd"));
        clmn_name.setCellValueFactory(new PropertyValueFactory<>("tangName"));
        clmn_amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        clmn_measureValue.setCellValueFactory(new PropertyValueFactory<>("measValue"));
        clmn_priceOne.setCellValueFactory(new PropertyValueFactory<>("priceForOne"));
        clmn_priceAll.setCellValueFactory(new PropertyValueFactory<>("priceForAll"));
        clmn_resp_person.setCellValueFactory(new PropertyValueFactory<>("responsible"));
        clmn_dateOFreceiv.setCellValueFactory(new PropertyValueFactory<>("receivingDate"));
        clmn_debitDate.setCellValueFactory(new PropertyValueFactory<>("debittingDate"));

        clmn_id.setPrefWidth(50);
        clmn_name.setPrefWidth(200);
        clmn_amount.setPrefWidth(70);
        clmn_measureValue.setPrefWidth(70);
        clmn_priceOne.setPrefWidth(100);
        clmn_priceAll.setPrefWidth(100);
        clmn_resp_person.setPrefWidth(200);
        clmn_dateOFreceiv.setPrefWidth(130);
        clmn_debitDate.setPrefWidth(130);


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

        clmn_NameWriteOff.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmn_DateOfWriteOff.setCellValueFactory(new PropertyValueFactory<>("debittingDate"));
        clmn_dateAmountWriteOff.setCellValueFactory(new PropertyValueFactory<>("amount"));
        clmn_datePriceWriteOff.setCellValueFactory(new PropertyValueFactory<>("money"));
        clmn_dateMeasValWriteOff.setCellValueFactory(new PropertyValueFactory<>("measVal"));

        clmn_NameWriteOff.setPrefWidth(200);
        clmn_DateOfWriteOff.setPrefWidth(200);
        clmn_dateAmountWriteOff.setPrefWidth(200);
        clmn_datePriceWriteOff.setPrefWidth(200);
        clmn_dateMeasValWriteOff.setPrefWidth(200);

        tableViewWriteOff.setPrefHeight(1000);

        tableViewWriteOff.getColumns().setAll(clmn_NameWriteOff,
                clmn_DateOfWriteOff,
                clmn_dateAmountWriteOff,
                clmn_dateMeasValWriteOff,
                clmn_datePriceWriteOff);
        tableViewWriteOff.setPadding(new Insets(5));
    }

    void makeTableForMotion() {

        clmn_NameMotion.setCellValueFactory(new PropertyValueFactory<>("name"));
        clmn_amountMotion.setCellValueFactory(new PropertyValueFactory<>("change"));
        clmn_priceMotion.setCellValueFactory(new PropertyValueFactory<>("money"));
        clmn_dateMotion.setCellValueFactory(new PropertyValueFactory<>("date"));

        clmn_NameMotion.setPrefWidth(400);
        clmn_amountMotion.setPrefWidth(200);
        clmn_priceMotion.setPrefWidth(200);
        clmn_dateMotion.setPrefWidth(200);

        tableViewMotion.getColumns().setAll(clmn_NameMotion,
                clmn_amountMotion,
                clmn_priceMotion,
                clmn_dateMotion);

        tableViewMotion.setPrefHeight(1000);
        tableViewMotion.setPadding(new Insets(5));
    }

    void setButtonsOnLeft() {
        button_handbook_of_property.setPrefHeight(200);
        button_handbook_of_property.setPrefWidth(350);
        button_motion_property.setPrefSize(350, 200);
        button_property_coming.setPrefSize(350, 200);
        button_property_consumption.setPrefSize(350, 200);
        button_writeOff_property.setPrefSize(350, 200);
        buttonsVBox.getChildren().setAll(button_handbook_of_property,
                button_motion_property,
                button_property_coming,
                button_property_consumption,
                button_writeOff_property);
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

        button_writeOff_property.setOnAction((ActionEvent) -> {
            setInterfaceForWritingOffProperty();
            lookForWriteOff();
            tableViewWriteOff.setItems(dataWriteOff);
            mainVBox.getChildren().setAll(tableViewWriteOff);
        });
    }

    private void lookForWriteOff() {
        LinkedList list = tangiblesDataBase.readData();
        for (Object a : list) {
            String[] lists = a.toString().split("<>@#_");
            if (LocalDate.parse(lists[7]).isBefore(LocalDate.now())) {
                WriteOffTangible writeOffTangible = new WriteOffTangible(lists[1],
                        LocalDate.parse(lists[7]),
                        Integer.parseInt(lists[2]),
                        lists[3],
                        Double.parseDouble(lists[5]));
                writeOffDatabase.writeData(writeOffTangible);
                tangiblesDataBase.deleteTangiblesWithSettedAmount(Integer.parseInt(lists[0]), Integer.parseInt(lists[2]));
                Motion motion = new Motion(writeOffTangible.name, " - " + writeOffTangible.amount, " - ", LocalDate.now());
                motionDataBase.writeData(motion);

            }
        }
        DBWork();
        mainVBox.getChildren().setAll(tableViewWriteOff);
    }

    private void setInterfaceForWritingOffProperty() {
        mainVBox.getChildren().setAll(tableViewWriteOff);
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
        Motion motion = new Motion(tangiblesDataBase.tangibleAndPersonForLeaving.getTangName(), " - " + tangiblesDataBase.selledAmount, " + " + tangiblesDataBase.gettedMoney * tangiblesDataBase.selledAmount, LocalDate.now());

        motionDataBase.writeData(motion);

        //Перевиведення БД матеріальних цінностей на екран
        DBWork();

        mainVBox.getChildren().setAll(hBoxTakeTangibles, button_submit_adding, tableViewTang, button_form_report_come);
        //Зробити звіт
        button_form_report_come.setOnAction((ActionEvent rep) -> formReportForLeaving());
    }

    private void formReportForLeaving() {
        File file = new File("/home/anna/Business_Processes_MyReport/reports/leave/" + "Report" + generateNumberForMatLeave() + ".txt");

        String content = "\t\t\t\t\t\t" + file.getName() + "\n\n\n" + "\t"
                + "1. Назва матеріалу: " + tangiblesDataBase.tangibleAndPersonForLeaving.getTangName() + " ;\n\t"
                + "2. Кількість: " + tangiblesDataBase.selledAmount + " " + tangiblesDataBase.tangibleAndPersonForLeaving.getMeasValue() + " ;\n\t"
                + "3. Собівартість за одиницю : " + tangiblesDataBase.tangibleAndPersonForLeaving.getPriceForOne() + " ;\n\t"
                + "4. Собівартість за весь товар : " + tangiblesDataBase.tangibleAndPersonForLeaving.getPriceForOne() * tangiblesDataBase.selledAmount + " ;\n\t"
                + "5. Ціна продажу за одиницю : " + tangiblesDataBase.gettedMoney + " ;\n\t"
                + "6. Ціна продажу за весь товар : " + tangiblesDataBase.gettedMoney * tangiblesDataBase.selledAmount + " ;\n\t"
                + "7. Відповідальна особа: " + tangiblesDataBase.tangibleAndPersonForLeaving.getResponsible() + " ;\n\t"
                + "8. Кінцевий термін експлуатації матеріалу: " + tangiblesDataBase.tangibleAndPersonForLeaving.getDebittingDate() + " ;\n\t"
                + "\n\n\n\t\t\t\t\t\t\t\t\t\tДата витрати матеріалу та складання звіту: " + LocalDate.now().toString();
        generateFile(file, content);
    }

    private void setInterfaceForPropertyLeaving() {
        hBoxTakeTangibles.setSpacing(5);
        hBoxTakeTangibles.setPadding(new Insets(5));
        hBoxTakeTangibles.getChildren().setAll(textFieldId,
                textFieldsAmountOut, textFieldsPriceForOneOut);
        textFieldId.setPromptText("ID цінності");
        textFieldsAmountOut.setPromptText("Кількість мат. цінності");
        textFieldsPriceForOneOut.setPromptText("Ціна за одиницю");
        mainVBox.getChildren().setAll(hBoxTakeTangibles, button_submit_out, tableViewTang);
    }

    private void formReportForComing(TangibleAndPerson tangible) {
        File file = new File("/home/anna/Business_Processes_MyReport/reports/come/" + "Report" + generateNumberForMatCome() + ".txt");

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
        File file = new File("/home/anna/Business_Processes_MyReport/reports/leave/");
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
        Motion motion = new Motion(tangible.getTangName(), " + " + tangible.getAmount(), " - " + tangible.getPriceForAll(), LocalDate.now());

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
        hBoxTakeTangibles.setSpacing(5);
        hBoxTakeTangibles.setPadding(new Insets(5));
        hBoxTakeTangibles.getChildren().setAll(textFieldsName,
                textFieldsAmount,
                textFieldsMeasureValue,
                textFieldsPrice,
                textFieldsPersons,
                datePickersDebitting);
        mainVBox.setSpacing(5);
        mainVBox.setPadding(new Insets(5));
        mainVBox.getChildren().setAll(hBoxTakeTangibles, button_submit_adding, tableViewTang);
    }


    public static void main(String[] args) {
        launch(args);
    }
}

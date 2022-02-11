package com.example.javafxproject;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.io.*;
import java.net.URL;
import java.util.*;

public class HelloController implements Initializable {
    @FXML
    private TableView<Student> classView;
    @FXML
    private TableColumn<Student, String> name;
    @FXML
    private TableColumn<Student, String> surname;
    @FXML
    private TableColumn<Student, Double> points;
    @FXML
    private ComboBox<String> choseClass;
    @FXML
    private TextField namefidle;
    @FXML
    private TextField surnamefidle;
    @FXML
    private TextField markfidel;
    @FXML
    private Button add;
    @FXML
    private Button writeCsv;
    @FXML
    private Button readCsv;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
classView.setEditable(true);
        Random rand = new Random();
        for (Student student : studentObservableList) {
            for (Classroom classroom : classroomObservableList) {
                student.addClassroom(classroom);
                student.addMark(classroom, ((double) (rand.nextInt(40) + 10) / 10));
                student.addMark(classroom, ((double) (rand.nextInt(40) + 10) / 10));
                student.addMark(classroom, ((double) (rand.nextInt(40) + 10) / 10));
            }
        }
        for (Classroom classroom : classroomObservableList) {
            choseClass.getItems().add(classroom.nameOfGroup);
        }
        //final Button add = new Button("Add");
        add.setOnAction(this::handle);
        choseClass.setOnAction(this::getNewTable);
        writeCsv.setOnAction(this::saveTo);
        readCsv.setOnAction(this::loadDatabase);

    }
        public void handle(ActionEvent e) {
            studentObservableList.add(new Student(
                    namefidle.getText(),
                    surnamefidle.getText()));
            for (Classroom classroom : classroomObservableList)
            {
                studentObservableList.get(studentObservableList.size() - 1).addClassroom(classroom);
                studentObservableList.get(studentObservableList.size()- 1).addMark(classroom,Double.parseDouble(markfidel.getText()) );
            }

            namefidle.clear();
            surnamefidle.clear();
            markfidel.clear();
            classView.refresh();
        }
public void saveTo(ActionEvent e)
{
    try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("database.txt"))) {
        for (int i = 0; i < classroomObservableList.size(); i++) {
            if (Objects.equals(choseClass.getValue(), classroomObservableList.get(i).nameOfGroup)) {
                out.writeObject(classroomObservableList.get(i));
                classroomObservableList.remove(i);
                choseClass.getItems().remove(i);

            }
        }
    } catch (IOException ex) {
        ex.printStackTrace();
    }
    System.out.println("Saved objects to file ");
}
    public void loadDatabase(ActionEvent e) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("database.txt"))) {
            while (true) {
                try {
                    Classroom registryClass = (Classroom) in.readObject();
                    classroomObservableList.add(registryClass);
                    choseClass.getItems().add(registryClass.nameOfGroup);

                } catch (EOFException supsko) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException supsko) {
            supsko.printStackTrace();
        }
    }
public void getNewTable(ActionEvent event)
{
    for (int i = 0; i < classroomObservableList.size(); i++) {
        if (Objects.equals(choseClass.getValue(), classroomObservableList.get(i).nameOfGroup)) {
            int finalI = i;
            System.out.println(i);
            System.out.println(finalI);
            points.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().getPoints(finalI)));
            name.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().name));
            surname.setCellValueFactory(p -> new ReadOnlyObjectWrapper<>(p.getValue().surname));
            points.setCellFactory(param -> new TableCell<>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    if (!empty) {

                        int currentIndex = indexProperty()
                                .getValue() < 0 ? 0
                                : indexProperty().getValue();
                        int numberOfMarks = param.getTableView().getItems().get(currentIndex).marks.get(finalI).size();
                        double type = param
                                .getTableView().getItems()
                                .get(currentIndex).getPoints(finalI);
                        String marks = "";
for(int i = 0; i <numberOfMarks; i++)
                        {
              marks += i + 1 + " mark: " + param.getTableView().getItems().get(currentIndex).marks.get(finalI).get(i)+ "\n";
                        }
                        setTooltip(new Tooltip(marks));
                        if (type > 3.0) {
                            setTextFill(Color.GREEN);
                        } else {
                            setTextFill(Color.RED);
                        }
                        setText(" " + item);
                    }
                }
            });
            classView.setItems(studentObservableList);
            classView.refresh();
        }
    }
}


    public ObservableList<Classroom> classroomObservableList = FXCollections.observableArrayList(
        new Classroom ("Okienka"),
        new Classroom ("Metoda Elementow"),
        new Classroom ("Metale"),
        new Classroom ("Inzynieria"),
        new Classroom ("Rownolegle"),
        new Classroom("Optymalizacja")
        );
    public ObservableList<Student> studentObservableList = FXCollections.observableArrayList(
            new Student("Bartosz", "Lidwin"),
            new Student("Wojciech", "Lugowski"),
            new Student("Stanislaw", "Marek"),
            new Student("Jakub", "Litewka"),
            new Student("Tomasz", "Ligeza")
    );
}

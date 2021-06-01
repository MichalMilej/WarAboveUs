package fxml;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import scores.ScoresSavingSystem;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class ScoreboardController implements Initializable {
    @FXML
    private TableView<TableDataType> scoresTableView;

    @FXML
    private TableColumn<TableDataType, Integer> position;
    @FXML
    private TableColumn<TableDataType, String> nameTableColumn;
    @FXML
    private TableColumn<TableDataType, String> scoreTableColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        position.setCellValueFactory(new PropertyValueFactory<>("position"));
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("playerName"));
        scoreTableColumn.setCellValueFactory(new PropertyValueFactory<>("scoreWithUnits"));

        ScoresSavingSystem scoresSavingSystem = new ScoresSavingSystem();
        LinkedList<TableDataType> tableDataTypes = scoresSavingSystem.getLinkedListTableDataType();
        for (int i = 0; i < tableDataTypes.size(); i++){
            for (int j = 0; j < tableDataTypes.size() - 1; j++){
                if (tableDataTypes.get(j).getScore() < tableDataTypes.get(j+1).getScore()){
                    TableDataType temp = new TableDataType(tableDataTypes.get(j).getPlayerName(), tableDataTypes.get(j).getScore());
                    tableDataTypes.get(j).setPlayerName(tableDataTypes.get(j+1).getPlayerName());
                    tableDataTypes.get(j).setScore(tableDataTypes.get(j+1).getScore());
                    tableDataTypes.get(j+1).setPlayerName(temp.getPlayerName());
                    tableDataTypes.get(j+1).setScore(temp.getScore());
                }
            }
        }
        for (int i = 0; i < tableDataTypes.size(); i++){
            tableDataTypes.get(i).setScoreWithUnits("km");
            scoresTableView.getItems().add(new TableDataType(i+1, tableDataTypes.get(i).getPlayerName(), tableDataTypes.get(i).getScoreWithUnits()));
        }
    }

    public void backToTheMainScene(ActionEvent actionEvent) throws IOException {
        Parent mainMenuView = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        Scene mainMenuScene = new Scene(mainMenuView);

        Stage window = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();

        window.setScene(mainMenuScene);
        window.show();

    }

}

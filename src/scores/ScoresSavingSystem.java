package scores;

import fxml.TableDataType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Scanner;

public class ScoresSavingSystem {
    private final DecimalFormat decimalFormat = new DecimalFormat("0.00");
    private final String scoresFileName = "data/scores.txt";
    private final int maxScoresNumber = 10;

    public void saveDistance(String playerName, double score){
        score = Double.parseDouble(decimalFormat.format(score).replace(',', '.'));
        File file = new File(scoresFileName);
        LinkedList<String> lines = new LinkedList<>();
        int lowestValueId = 0;
        double lowestValue = score;
        try{
            if (!file.exists()){
                PrintWriter writer = new PrintWriter(file);
                writer.println(playerName + ":" + score);
                writer.close();
                return;
            }

            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()){
                lines.add(reader.nextLine());
                for (int i = 0; i < lines.getLast().length(); i++) { ;
                    if (lines.getLast().charAt(i) == ':') {
                        double value = Double.parseDouble(lines.getLast().substring(i+1));
                        if (value < lowestValue) {
                            lowestValue = value;
                            lowestValueId = i;
                        }
                    }
                }
            }
            reader.close();
            PrintWriter writer = new PrintWriter(scoresFileName);
            for (int i = 0; i < lines.size(); i++){
                if (lines.size() >= maxScoresNumber && i == lowestValueId){
                    writer.println(playerName + ":" + score);
                }else
                    writer.println(lines.get(i));
            }
            if (lines.size() < maxScoresNumber)
                writer.println(playerName + ":" + score);
            writer.close();
        }catch (FileNotFoundException e){
            System.out.println("There is a problem with saving or reading files.");
        }
    }

    public LinkedList<TableDataType> getLinkedListTableDataType(){
        LinkedList<TableDataType> list = new LinkedList<>();

        File file = new File(scoresFileName);
        try {
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == ':') {
                        list.add(new TableDataType(line.substring(0, i), Double.parseDouble(line.substring(i + 1))));
                        break;
                    }
                }
            }
            reader.close();
        }catch (FileNotFoundException e){

        }
        return list;
    }
}

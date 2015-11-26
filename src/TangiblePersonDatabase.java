import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.time.LocalDate;
import java.util.LinkedList;

public class TangiblePersonDatabase implements ManageData {
    final File file = new File("TangiblesData.txt");
    int idTangible = 0;

    @Override
    public void clear() {
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void checkIfCreated() {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public LinkedList readData() {
        LinkedList<String> list = new LinkedList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file.getAbsoluteFile()))) {
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                list.add(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public ObservableList<TangibleAndPerson> listFromReaded() {
        ObservableList<TangibleAndPerson> olist = FXCollections.observableArrayList();
        LinkedList linkedList = this.readData();
        if(linkedList!=null) {
            for (Object a : linkedList) {
                String[] list = divideStringOnElements(a);
                System.out.println(a.toString());
                olist.add(new TangibleAndPerson(Integer.parseInt(list[0]),
                        list[1],
                        Integer.parseInt(list[2]),
                        (list[3]),
                        Double.parseDouble(list[4]),
                        Double.parseDouble(list[5]),
                        list[6],
                        LocalDate.parse(list[7])));
                idTangible = Integer.parseInt(list[0]);
            }
        }
        return olist;
    }

    public void writeData(TangibleAndPerson tangibles) {
        try (FileWriter fileWriter = new FileWriter(file, true)) {
            fileWriter.write(tangibles.toString());
            fileWriter.append('\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String[] divideStringOnElements(Object a) {
        return a.toString().split("<>@#_");
    }

      /*  ObservableList<TangibleAndPerson> deleteTangiblesWithSettedAmount(int idd, int amount) {
        ObservableList<TangibleAndPerson> olist = FXCollections.observableArrayList();
        LinkedList<TangibleAndPerson> tangibles = new LinkedList<>();
        LinkedList linkedList = this.readData();
    }*/
}

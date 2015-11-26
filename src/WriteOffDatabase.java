import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.time.LocalDate;
import java.util.LinkedList;

/**
 * Created by anna on 11/26/15.
 */
public class WriteOffDatabase {
    final File file = new File("WriteOffDatabase.txt");

    public void checkIfCreated() {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeData(WriteOffTangible writeOffTangible) {
        try (FileWriter fileWriter = new FileWriter(file, true)) {
            fileWriter.write(writeOffTangible.toString());
            fileWriter.append('\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    String[] divideStringOnElements(Object a) {
        return a.toString().split("<>@#_");
    }


    public ObservableList listFromReader() {
        ObservableList<WriteOffTangible> olist = FXCollections.observableArrayList();
        LinkedList linkedList = this.readData();
        if (linkedList != null) {
            for (Object a : linkedList) {
                String[] list = divideStringOnElements(a);
                System.out.println(a.toString());
                olist.add(new WriteOffTangible(list[0],
                        LocalDate.parse(list[1]),
                        Integer.parseInt(list[2]),
                        list[3],
                        Double.parseDouble(list[4])));
            }
        }
        return olist;
    }
}

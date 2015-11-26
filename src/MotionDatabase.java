import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.time.LocalDate;
import java.util.LinkedList;


public class MotionDatabase implements ManageData {
    final File file = new File("MotionDatabase.txt");

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

    public void writeData(Motion motion) {
        try (FileWriter fileWriter = new FileWriter(file, true)) {
            fileWriter.write(motion.toString());
            fileWriter.append('\n');
        } catch (IOException e) {
            e.printStackTrace();
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

    String[] divideStringOnElements(Object a) {
        return a.toString().split("<>@#_");
    }

    @Override
    public ObservableList<Motion> listFromReaded() {
        ObservableList<Motion> olist = FXCollections.observableArrayList();
        LinkedList linkedList = this.readData();
        if (linkedList != null) {
            for (Object a : linkedList) {
                String[] list = divideStringOnElements(a);
                olist.add(new Motion(list[0],
                        list[1],
                        list[2],
                        LocalDate.parse(list[3])));
            }

        }
        return olist;
    }
}

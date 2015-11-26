import javafx.collections.ObservableList;

import java.util.LinkedList;

public interface ManageData<T> {
    void clear();
    void checkIfCreated();
    LinkedList readData();
    ObservableList<T> listFromReaded();
}

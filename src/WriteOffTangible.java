import java.time.LocalDate;

/**
 * Created by anna on 11/26/15.
 */
public class WriteOffTangible {
    String name;
    LocalDate debittingDate;
    int amount;
    String measVal;
    Double money;

    public WriteOffTangible(String name, LocalDate debittingDate, int amount, String measVal, Double money) {
        this.name = name;
        this.debittingDate = debittingDate;
        this.amount = amount;
        this.measVal = measVal;
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDebittingDate() {
        return debittingDate;
    }

    public int getAmount() {
        return amount;
    }

    public String getMeasVal() {
        return measVal;
    }

    public Double getMoney() {
        return money;
    }

    @Override
    public String toString() {
        return name + "<>@#_" + debittingDate + "<>@#_" + amount + "<>@#_" + measVal + "<>@#_" + money;
    }
}

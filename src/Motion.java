import java.time.LocalDate;

public class Motion {
    String name;
    String change;
    String money;
    LocalDate date;

    public Motion(String name, String change, String money, LocalDate date) {
        this.name = name;
        this.change = change;
        this.money = money;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public String getChange() {
        return change;
    }

    public String getMoney() {
        return money;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return name + "<>@#_" + change + "<>@#_" + money + "<>@#_" + date;
    }
}

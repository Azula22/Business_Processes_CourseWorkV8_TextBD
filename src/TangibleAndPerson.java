import java.time.LocalDate;

public class TangibleAndPerson {
    int idd;
    String tangName;
    int amount;
    String measValue;
    Double priceForOne;
    Double priceForAll;
    String responsible;
    LocalDate debittingDate;
    LocalDate receivingDate;

    public TangibleAndPerson(int idd, String tangName, int amount, String measValue, Double priceForOne, Double priceForAll, String responsible, LocalDate debittingDate) {
        this.idd = idd;
        this.tangName = tangName;
        this.amount = amount;
        this.measValue = measValue;
        this.priceForOne = priceForOne;
        this.priceForAll = priceForAll;
        this.responsible = responsible;
        this.debittingDate = debittingDate;
        this.receivingDate = LocalDate.now();
    }

    public int getIdd() {
        return idd;
    }

    public String getTangName() {
        return tangName;
    }

    public int getAmount() {
        return amount;
    }

    public String getMeasValue() {
        return measValue;
    }

    public Double getPriceForOne() {
        return priceForOne;
    }

    public Double getPriceForAll() {
        return priceForAll;
    }

    public String getResponsible() {
        return responsible;
    }

    public LocalDate getDebittingDate() {
        return debittingDate;
    }

    public LocalDate getReceivingDate() {
        return receivingDate;
    }

    @Override
    public String toString() {
        return idd + "<>@#_" +
                tangName + "<>@#_" +
                amount + "<>@#_" +
                measValue + "<>@#_" +
                priceForOne + "<>@#_" +
                priceForAll + "<>@#_" +
                responsible + "<>@#_" +
                debittingDate;
    }
}

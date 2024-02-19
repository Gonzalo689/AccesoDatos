import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Week {
    @SerializedName("NUMBER")
    private int number;
    @SerializedName("EXPENSE")
    private List<Expense> expenses;

    public Week() {
        expenses = new ArrayList<>();
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    public int sizeExpense() {
        return expenses.size();
    }
    public Expense getExpense(int i) {
        return expenses.get(i);
    }
    public void addExpense(Expense expense) {
        expenses.add(expense);
    }
    public double priceWeek(){

        double total = 0;
        for(int i = 0; i < expenses.size(); i++){
            total += expenses.get(i).getPrice();
        }
        return total;
    }

   
    
    @Override
    public String toString() {
        return "Week [number=" + number + ", expense=" + expenses + "]";
    }
}

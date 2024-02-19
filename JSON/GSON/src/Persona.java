

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Persona {
    @SerializedName("WHO")
    private String name;
    @SerializedName("WEEK")
    private List<Week> weeks;

    public Persona() {
        weeks = new ArrayList<>();
    }

    
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public int sizeWeek() {
        return weeks.size();
    }
    public Week getWeek(int i) {
        return weeks.get(i);
    }
    public void addWeek(Week week) {
        weeks.add(week);
    }


    @Override
    public String toString() {
        return "Persona [name=" + name + ", week=" + weeks + "]";
    }
    

    
}

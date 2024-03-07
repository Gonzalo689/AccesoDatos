import java.util.List;

public class Pokemon {
    private int id;
    private String num;
    private String name;
    private String img;
    private List<String> type;
    private String height;
    private String weight;
    private String candy;
    private int candy_count;
    private String egg;
    private double spawn_chance;
    private double avg_spawns;
    private String spawn_time;
    private List<Double> multipliers;
    private List<String> weaknesses;
    private List<Evolucion> prev_evolution;
    private List<Evolucion> next_evolution;
    @Override
    public String toString() {
        return "Pokemon [id=" + id + ", num=" + num + ", name=" + name + ", img=" + img + ", type=" + type + ", height="
                + height + ", weight=" + weight + ", candy=" + candy + ", candy_count=" + candy_count + ", egg=" + egg
                + ", spawn_chance=" + spawn_chance + ", avg_spawns=" + avg_spawns + ", spawn_time=" + spawn_time
                + ", multipliers=" + multipliers + ", weaknesses=" + weaknesses + ", prev_evolution=" + prev_evolution
                + ", next_evolution=" + next_evolution + "]";
    }


    
}

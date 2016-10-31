package app.lambdas.domain;

/**
 * Created by ajoshi on 31-Oct-16.
 */
public class Apple {
    private Integer weight;
    private String color;

    public Apple(Integer weight) {
        this.weight = weight;
        this.color = "green";
    }

    public Integer getWeight() {
        return weight;
    }

    public String getColor() {
        return color;
    }
}

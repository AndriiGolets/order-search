package site.golets.viber.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Order implements Comparable<Order>{

    private String name;
    private String artist;
    private String productTitle;
    private String date;
    private String productVariant;
    private String photoNotOk;
    private int heads;

    @Override
    public int compareTo(Order o) {
        return o.heads - heads;
    }

    @Override
    public String toString() {
        return name + " '" + productTitle + "' " + photoNotOk + " : heads " + heads;
    }
}

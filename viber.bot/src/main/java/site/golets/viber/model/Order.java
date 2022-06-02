package site.golets.viber.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Order {

    private String name;
    private String artist;
    private String productTitle;
    private String date;
    private String productVariant;
    private String photoNotOk;

    @Override
    public String toString() {
        return"id='" + name + '\'' +
                ", productTitle='" + productTitle;
    }
}

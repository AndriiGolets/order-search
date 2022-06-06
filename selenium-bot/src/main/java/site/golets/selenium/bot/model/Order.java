package site.golets.selenium.bot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;
import org.openqa.selenium.WebElement;

@Data
@Accessors(chain = true)
public class Order {

    private String name;
    private String artist;
    private String productTitle;
    private String date;
    private String productVariant;
    private String photoNotOk;
    private int heads;

}

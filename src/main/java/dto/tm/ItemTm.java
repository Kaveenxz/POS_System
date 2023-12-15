package dto.tm;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.scene.control.Button;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ItemTm extends RecursiveTreeObject<ItemTm> {
    private String code;
    private  String desc;
    private double unitPrice;
    private int qty;
    private Button btn;
}

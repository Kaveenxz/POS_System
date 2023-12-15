package dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class OrderDetailDto {
    private String orderId;
    private String itemCode;
    private int qty;
    private double unitPrice;
}

package com.samnart.ecommerce.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
    private Long productId;
    private Integer quantity;
    // private Long cartItemId;
    // private CartDTO cart;
    // private ProductDTO productDTO;
    // private Integer quantity;
    // private Double discount;
    // private Double productPrice;
}

package com.samnart.ecommerce.payload;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private List<ProductDTO> content;
    // Integer pageNumber;
    // Integer pageSize;
    // Long totalElements;
    // Integer totalPages;
    // boolean lastPage;
}

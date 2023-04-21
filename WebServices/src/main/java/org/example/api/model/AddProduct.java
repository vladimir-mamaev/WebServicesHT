package org.example.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddProduct {
    private Product product;
    private int quantity;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private class Product{
    private String code;
}
}




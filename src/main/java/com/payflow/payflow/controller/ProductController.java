package com.payflow.payflow.controller;

import com.payflow.payflow.dto.request.ProductCreate;
import com.payflow.payflow.dto.request.ProductEdit;
import com.payflow.payflow.dto.request.ProductPageRequest;
import com.payflow.payflow.dto.response.PagingResponse;
import com.payflow.payflow.dto.response.ProductResponse;
import com.payflow.payflow.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("")
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductCreate productCreate) {
        ProductResponse productResponse = productService.create(productCreate);
        return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable(name = "productId") Long productId) {
        ProductResponse productResponse = productService.get(productId);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<PagingResponse<ProductResponse>> getProductList(@ModelAttribute ProductPageRequest productPageRequest) {
        PagingResponse<ProductResponse> productList = productService.getList(productPageRequest);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponse> editProduct(@PathVariable(name = "productId") Long productId, @RequestBody @Valid ProductEdit productEdit) {
        ProductResponse productResponse = productService.edit(productId, productEdit);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable(name = "productId") Long productId) {
        productService.delete(productId);
        return ResponseEntity.noContent().build();
    }

}

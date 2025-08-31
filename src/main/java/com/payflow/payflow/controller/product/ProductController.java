package com.payflow.payflow.controller.product;

import com.payflow.payflow.dto.product.CreteaProductRequest;
import com.payflow.payflow.dto.product.EditProductRequest;
import com.payflow.payflow.dto.product.GetProductsRequest;
import com.payflow.payflow.dto.common.PagingResponse;
import com.payflow.payflow.dto.product.ProductResponse;
import com.payflow.payflow.service.auth.UserPrincipal;
import com.payflow.payflow.service.product.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("")
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody CreteaProductRequest creteaProductRequest, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getUser().getId();
        ProductResponse productResponse = productService.create(creteaProductRequest, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(productResponse);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable(name = "productId") Long productId) {
        ProductResponse productResponse = productService.get(productId);
        return ResponseEntity.ok(productResponse);
    }

    @GetMapping("")
    public ResponseEntity<PagingResponse<ProductResponse>> getProductList(@ModelAttribute GetProductsRequest getProductsRequest) {
        PagingResponse<ProductResponse> productList = productService.getList(getProductsRequest);
        return ResponseEntity.ok(productList);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponse> editProduct(@PathVariable(name = "productId") Long productId, @RequestBody @Valid EditProductRequest editProductRequest, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getUser().getId();
        ProductResponse productResponse = productService.edit(productId, editProductRequest, userId);
        return ResponseEntity.ok(productResponse);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable(name = "productId") Long productId, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getUser().getId();
        productService.delete(productId, userId);
        return ResponseEntity.noContent().build();
    }

}

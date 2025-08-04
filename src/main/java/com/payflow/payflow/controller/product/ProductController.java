package com.payflow.payflow.controller.product;

import com.payflow.payflow.domain.auth.User;
import com.payflow.payflow.dto.product.ProductCreate;
import com.payflow.payflow.dto.product.ProductEdit;
import com.payflow.payflow.dto.product.ProductPageRequest;
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
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductCreate productCreate, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getUser().getId();
        ProductResponse productResponse = productService.create(productCreate, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(productResponse);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable(name = "productId") Long productId) {
        ProductResponse productResponse = productService.get(productId);
        return ResponseEntity.ok(productResponse);
    }

    @GetMapping("")
    public ResponseEntity<PagingResponse<ProductResponse>> getProductList(@ModelAttribute ProductPageRequest productPageRequest) {
        PagingResponse<ProductResponse> productList = productService.getList(productPageRequest);
        return ResponseEntity.ok(productList);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponse> editProduct(@PathVariable(name = "productId") Long productId, @RequestBody @Valid ProductEdit productEdit, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getUser().getId();
        ProductResponse productResponse = productService.edit(productId, productEdit, userId);
        return ResponseEntity.ok(productResponse);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable(name = "productId") Long productId, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long userId = userPrincipal.getUser().getId();
        productService.delete(productId, userId);
        return ResponseEntity.noContent().build();
    }

}

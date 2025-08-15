package com.payflow.payflow.controller.admin;

import com.payflow.payflow.dto.admin.DashboardMetricResponse;
import com.payflow.payflow.service.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;


    @GetMapping("/metrics")
    public ResponseEntity<DashboardMetricResponse> getMetrics() {
        return ResponseEntity.ok(adminService.collectMetrics());
    }

}

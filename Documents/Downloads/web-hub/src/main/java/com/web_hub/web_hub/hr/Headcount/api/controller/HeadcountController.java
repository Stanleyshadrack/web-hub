package com.web_hub.web_hub.hr.Headcount.api.controller;

import com.web_hub.web_hub.hr.Headcount.api.dto.HeadcountResponse;
import com.web_hub.web_hub.hr.Headcount.service.HeadcountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/headcount")
@RequiredArgsConstructor
public class HeadcountController {

    private final HeadcountService service;

    /* ================= SUMMARY ================= */

    @GetMapping
    public ResponseEntity<HeadcountResponse> getSummary() {
        return ResponseEntity.ok(service.getSummary());
    }
}

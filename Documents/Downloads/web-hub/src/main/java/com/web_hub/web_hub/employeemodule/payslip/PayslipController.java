package com.web_hub.web_hub.employeemodule.payslip;
import com.web_hub.web_hub.employeemodule.dto.PayslipResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payslips")
@RequiredArgsConstructor
public class PayslipController {

    private final PayslipService payslipService;

    @GetMapping("/my")
    public ResponseEntity<List<PayslipResponse>> getMyPayslips() {
        return ResponseEntity.ok(payslipService.getMyPayslips());
    }
}

package com.web_hub.web_hub.employeemodule.payslip;

import com.web_hub.web_hub.employeemodule.dto.PayslipResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PayslipService {

    public List<PayslipResponse> getMyPayslips() {
        return List.of(); // later from DB
    }
}

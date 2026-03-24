package com.web_hub.web_hub.employeemodule.ticket;
import com.web_hub.web_hub.employeemodule.dto.TicketRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/support")
@RequiredArgsConstructor
public class SupportController {

    private final SupportService supportService;

    @PostMapping("/tickets")
    public ResponseEntity<?> createTicket(@RequestBody TicketRequest req) {
        supportService.createTicket(req);
        return ResponseEntity.ok("Ticket created");
    }
}

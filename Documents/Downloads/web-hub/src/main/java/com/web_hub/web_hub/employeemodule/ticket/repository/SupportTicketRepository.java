package com.web_hub.web_hub.employeemodule.ticket.repository;

import com.web_hub.web_hub.employeemodule.ticket.model.SupportTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupportTicketRepository extends JpaRepository<SupportTicket, Long> {

    List<SupportTicket> findByEmployeeId(Long employeeId);
}

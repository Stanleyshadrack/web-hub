package com.web_hub.web_hub.employeemodule.trainingandcertification.repository;

import com.web_hub.web_hub.employeemodule.trainingandcertification.model.Certification;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CertificationRepository extends JpaRepository<Certification, Long> {

    List<Certification> findByEmployeeId(Long employeeId);
}

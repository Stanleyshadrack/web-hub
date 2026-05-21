package com.web_hub.web_hub.employeemodule.trainingandcertification.service;

import com.web_hub.web_hub.employeemodule.trainingandcertification.model.Certification;
import com.web_hub.web_hub.employeemodule.trainingandcertification.repository.CertificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificationService {

    private final CertificationRepository certificationRepository;

    public Certification issueCertificate(Long employeeId, Long trainingId, String name) {

        Certification cert = Certification.builder()
                .employeeId(employeeId)
                .trainingId(trainingId)
                .certificateName(name)
                .issuedDate(LocalDate.now())
                .build();

        return certificationRepository.save(cert);
    }

    public List<Certification> getEmployeeCertificates(Long employeeId) {
        return certificationRepository.findByEmployeeId(employeeId);
    }
}

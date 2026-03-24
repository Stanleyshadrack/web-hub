package com.web_hub.web_hub.employeemodule.trainings;

import com.web_hub.web_hub.employeemodule.dto.TrainingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingService {

    public List<TrainingResponse> getMyTrainings() {
        return List.of();
    }
}

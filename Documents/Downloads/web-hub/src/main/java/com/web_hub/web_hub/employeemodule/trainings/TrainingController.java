package com.web_hub.web_hub.employeemodule.trainings;

import com.web_hub.web_hub.employeemodule.dto.TrainingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainings")
@RequiredArgsConstructor
public class TrainingController {

    @GetMapping("/my")
    public ResponseEntity<List<TrainingResponse>> getMyTrainings() {
        return ResponseEntity.ok(List.of());
    }
}

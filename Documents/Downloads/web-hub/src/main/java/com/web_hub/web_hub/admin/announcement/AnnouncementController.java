package com.web_hub.web_hub.admin.announcement;

import com.web_hub.web_hub.admin.dto.AnnouncementRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/announcements")
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @PostMapping
    public ResponseEntity<?> send(@RequestBody AnnouncementRequest req) {
        announcementService.broadcast(req);
        return ResponseEntity.ok("Announcement sent");
    }
}

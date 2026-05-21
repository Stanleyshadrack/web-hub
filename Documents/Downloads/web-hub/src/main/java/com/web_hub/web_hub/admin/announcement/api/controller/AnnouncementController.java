package com.web_hub.web_hub.admin.announcement.api.controller;

import com.web_hub.web_hub.admin.announcement.api.dto.AnnouncementRequest;
import com.web_hub.web_hub.admin.announcement.api.dto.AnnouncementResponse;
import com.web_hub.web_hub.admin.announcement.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/announcements")
@RequiredArgsConstructor
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @PostMapping
    public AnnouncementResponse createAnnouncement(@RequestBody AnnouncementRequest request) {
        return announcementService.createAnnouncement(request);
    }

    @GetMapping
    public List<AnnouncementResponse> getAllAnnouncements() {
        return announcementService.getAllAnnouncements();
    }
}

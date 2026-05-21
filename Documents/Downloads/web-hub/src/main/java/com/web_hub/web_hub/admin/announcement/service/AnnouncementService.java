package com.web_hub.web_hub.admin.announcement.service;

import com.web_hub.web_hub.admin.announcement.api.dto.AnnouncementRequest;
import com.web_hub.web_hub.admin.announcement.api.dto.AnnouncementResponse;
import com.web_hub.web_hub.admin.announcement.model.Announcement;
import com.web_hub.web_hub.admin.announcement.repository.AnnouncementRepository;
import com.web_hub.web_hub.admin.auditlog.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final AuditLogService auditLogService;

    /* ================= CREATE ================= */
    public AnnouncementResponse createAnnouncement(AnnouncementRequest request) {

        Announcement announcement = Announcement.builder()
                .title(request.getTitle())
                .message(request.getMessage())
                .createdBy("HR") // later replace with logged-in user
                .createdAt(LocalDateTime.now())
                .build();

        Announcement saved = announcementRepository.save(announcement);

        // 🔥 AUDIT LOG
        auditLogService.logAction(
                "CREATE",
                "Announcement",
                saved.getId(),
                "HR",
                "HR",
                "Created announcement: " + saved.getTitle()
        );

        return mapToResponse(saved);
    }

    /* ================= GET ALL ================= */
    public List<AnnouncementResponse> getAllAnnouncements() {
        return announcementRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    /* ================= MAPPER ================= */
    private AnnouncementResponse mapToResponse(Announcement announcement) {
        return new AnnouncementResponse(
                announcement.getId(),
                announcement.getTitle(),
                announcement.getMessage(),
                announcement.getCreatedBy(),
                announcement.getCreatedAt()
        );
    }
}

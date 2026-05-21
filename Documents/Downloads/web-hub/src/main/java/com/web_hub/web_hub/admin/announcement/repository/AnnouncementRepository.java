package com.web_hub.web_hub.admin.announcement.repository;

import com.web_hub.web_hub.admin.announcement.model.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
}

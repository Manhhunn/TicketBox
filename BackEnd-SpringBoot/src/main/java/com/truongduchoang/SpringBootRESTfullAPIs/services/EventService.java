package com.truongduchoang.SpringBootRESTfullAPIs.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.truongduchoang.SpringBootRESTfullAPIs.dto.request.EventCreateRequest;
import com.truongduchoang.SpringBootRESTfullAPIs.dto.request.EventUpdateRequest;
import com.truongduchoang.SpringBootRESTfullAPIs.dto.response.EventResponse;

public interface EventService {
    EventResponse createEvent(EventCreateRequest request, MultipartFile banner);

    List<EventResponse> getAllEvents();

    EventResponse getEventById(Long id);

    EventResponse updateEvent(Long id, EventUpdateRequest request, MultipartFile banner);

    void deleteEvent(Long id);
}

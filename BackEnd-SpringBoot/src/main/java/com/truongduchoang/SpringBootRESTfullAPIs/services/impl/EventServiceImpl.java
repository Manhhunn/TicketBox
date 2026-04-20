package com.truongduchoang.SpringBootRESTfullAPIs.services.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.truongduchoang.SpringBootRESTfullAPIs.dto.request.EventCreateRequest;
import com.truongduchoang.SpringBootRESTfullAPIs.dto.request.EventUpdateRequest;
import com.truongduchoang.SpringBootRESTfullAPIs.dto.response.EventResponse;
import com.truongduchoang.SpringBootRESTfullAPIs.dto.response.MediaUploadResponse;
import com.truongduchoang.SpringBootRESTfullAPIs.errors.BadRequestException;
import com.truongduchoang.SpringBootRESTfullAPIs.errors.DuplicateResourceException;
import com.truongduchoang.SpringBootRESTfullAPIs.errors.ResourceNotFoundException;
import com.truongduchoang.SpringBootRESTfullAPIs.mapper.EventMapper;
import com.truongduchoang.SpringBootRESTfullAPIs.models.Category;
import com.truongduchoang.SpringBootRESTfullAPIs.models.Event;
import com.truongduchoang.SpringBootRESTfullAPIs.models.OrganizerProfile;
import com.truongduchoang.SpringBootRESTfullAPIs.repository.CategoryRepository;
import com.truongduchoang.SpringBootRESTfullAPIs.repository.EventRepository;
import com.truongduchoang.SpringBootRESTfullAPIs.repository.OrderRepository;
import com.truongduchoang.SpringBootRESTfullAPIs.repository.OrganizerProfileRepository;
import com.truongduchoang.SpringBootRESTfullAPIs.repository.TicketRepository;
import com.truongduchoang.SpringBootRESTfullAPIs.services.CloudinaryService;
import com.truongduchoang.SpringBootRESTfullAPIs.services.EventService;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final CategoryRepository categoryRepository;
    private final OrganizerProfileRepository organizerProfileRepository;
    private final OrderRepository orderRepository;
    private final TicketRepository ticketRepository;
    private final EventMapper eventMapper;
    private final CloudinaryService cloudinaryService;

    public EventServiceImpl(
            EventRepository eventRepository,
            CategoryRepository categoryRepository,
            OrganizerProfileRepository organizerProfileRepository,
            OrderRepository orderRepository,
            TicketRepository ticketRepository,
            EventMapper eventMapper,
            CloudinaryService cloudinaryService) {
        this.eventRepository = eventRepository;
        this.categoryRepository = categoryRepository;
        this.organizerProfileRepository = organizerProfileRepository;
        this.orderRepository = orderRepository;
        this.ticketRepository = ticketRepository;
        this.eventMapper = eventMapper;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public EventResponse createEvent(EventCreateRequest request, MultipartFile banner) {
        validateTimeRange(request.getStartTime(), request.getEndTime());
        if (eventRepository.existsBySlug(request.getSlug())) {
            throw new DuplicateResourceException("Event slug already exists", "EVENT_SLUG_ALREADY_EXISTS");
        }

        Category category = findCategoryById(request.getCategoryId());
        OrganizerProfile organizer = findOrganizerById(request.getOrganizerId());
        Event event = eventMapper.toEntity(request, category, organizer);

        if (hasFile(banner)) {
            MediaUploadResponse uploadResponse = cloudinaryService.uploadImage(banner, "event-management/events");
            event.setBannerUrl(uploadResponse.getSecureUrl());
        }

        return eventMapper.toResponse(eventRepository.save(event));
    }

    @Override
    public List<EventResponse> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(eventMapper::toResponse)
                .toList();
    }

    @Override
    public EventResponse getEventById(Long id) {
        return eventMapper.toResponse(findEventById(id));
    }

    @Override
    public EventResponse updateEvent(Long id, EventUpdateRequest request, MultipartFile banner) {
        Event event = findEventById(id);

        if (request.getTitle() != null && !StringUtils.hasText(request.getTitle())) {
            throw new BadRequestException("Event title cannot be blank", "EVENT_TITLE_BLANK");
        }
        if (request.getSlug() != null && !StringUtils.hasText(request.getSlug())) {
            throw new BadRequestException("Event slug cannot be blank", "EVENT_SLUG_BLANK");
        }
        if (StringUtils.hasText(request.getSlug())
                && !request.getSlug().equalsIgnoreCase(event.getSlug())
                && eventRepository.existsBySlugAndEventIdNot(request.getSlug(), id)) {
            throw new DuplicateResourceException("Event slug already exists", "EVENT_SLUG_ALREADY_EXISTS");
        }

        LocalDateTime startTime = request.getStartTime() != null ? request.getStartTime() : event.getStartTime();
        LocalDateTime endTime = request.getEndTime() != null ? request.getEndTime() : event.getEndTime();
        validateTimeRange(startTime, endTime);

        Category category = request.getCategoryId() != null ? findCategoryById(request.getCategoryId()) : null;
        OrganizerProfile organizer = request.getOrganizerId() != null ? findOrganizerById(request.getOrganizerId()) : null;
        eventMapper.updateEntity(event, request, category, organizer);

        if (hasFile(banner)) {
            MediaUploadResponse uploadResponse = cloudinaryService.uploadImage(banner, "event-management/events");
            event.setBannerUrl(uploadResponse.getSecureUrl());
        }

        return eventMapper.toResponse(eventRepository.save(event));
    }

    @Override
    public void deleteEvent(Long id) {
        Event event = findEventById(id);
        if (orderRepository.existsByEventEventId(id) || ticketRepository.existsByEventEventId(id)) {
            throw new BadRequestException("Event already has orders or tickets", "EVENT_HAS_TRANSACTIONS");
        }
        eventRepository.delete(event);
    }

    private Event findEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event with id " + id + " not found", "EVENT_NOT_FOUND"));
    }

    private Category findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " not found", "CATEGORY_NOT_FOUND"));
    }

    private OrganizerProfile findOrganizerById(Long id) {
        return organizerProfileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organizer with id " + id + " not found", "ORGANIZER_NOT_FOUND"));
    }

    private void validateTimeRange(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null || endTime == null) {
            throw new BadRequestException("Start time and end time are required", "EVENT_TIME_REQUIRED");
        }
        if (!endTime.isAfter(startTime)) {
            throw new BadRequestException("End time must be after start time", "EVENT_TIME_INVALID");
        }
    }

    private boolean hasFile(MultipartFile file) {
        return file != null && !file.isEmpty();
    }
}

package com.truongduchoang.SpringBootRESTfullAPIs.dto.response;

public class OrganizerSummaryResponse {
    private Long organizerId;
    private String organizationName;
    private Long userId;
    private String userFullName;

    public OrganizerSummaryResponse() {
    }

    public OrganizerSummaryResponse(Long organizerId, String organizationName, Long userId, String userFullName) {
        this.organizerId = organizerId;
        this.organizationName = organizationName;
        this.userId = userId;
        this.userFullName = userFullName;
    }

    public Long getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(Long organizerId) {
        this.organizerId = organizerId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }
}

package hwan.project2.web.dto;

public record MeResponse(Long id, String email, String name, String tag, String role, String profileImageUrl, int chatUsed, int chatLimit) {}

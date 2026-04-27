package hwan.project2.web.dto.chat;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ChatFinishRequest(
        @NotNull List<ChatMessageDto> messages
) {}

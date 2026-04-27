package hwan.project2.web.dto.chat;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ChatRequest(
        @NotNull @Size(max = 21) List<ChatMessageDto> messages
) {}

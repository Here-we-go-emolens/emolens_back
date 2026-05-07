package hwan.project2.web.chat;

import hwan.project2.security.UserPrincipal;
import hwan.project2.service.ai.ChatService;
import hwan.project2.web.dto.chat.ChatFinishRequest;
import hwan.project2.web.dto.chat.ChatRequest;
import hwan.project2.web.dto.chat.ChatResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/message")
    public ChatResponse message(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody ChatRequest req) {
        String reply = chatService.reply(principal.getId(), req.messages());
        return new ChatResponse(reply);
    }

    @PostMapping("/finish")
    public ResponseEntity<Long> finish(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody ChatFinishRequest req) {
        Long diaryId = chatService.finish(principal.getId(), req.messages());
        return ResponseEntity.ok(diaryId);
    }
}

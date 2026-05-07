package hwan.project2.web.character;

import hwan.project2.security.UserPrincipal;
import hwan.project2.service.character.CharacterService;
import hwan.project2.web.dto.character.CharacterRequest;
import hwan.project2.web.dto.character.CharacterResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/characters")
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;

    @GetMapping
    public CharacterResponse getMyCharacter(@AuthenticationPrincipal UserPrincipal user) {
        return characterService.getMyCharacter(user.getId());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CharacterResponse createCharacter(@AuthenticationPrincipal UserPrincipal user,
                                             @Valid @RequestBody CharacterRequest req) {
        return characterService.createCharacter(user.getId(), req);
    }

    @PutMapping
    public CharacterResponse updateCharacter(@AuthenticationPrincipal UserPrincipal user,
                                             @Valid @RequestBody CharacterRequest req) {
        return characterService.updateCharacter(user.getId(), req);
    }
}

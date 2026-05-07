package hwan.project2.domain.character.repo;

import hwan.project2.domain.character.Character;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CharacterRepository extends JpaRepository<Character, Long> {
    Optional<Character> findByMemberId(Long memberId);
    boolean existsByMemberId(Long memberId);
}

package hongik.cartoonforblindbackend.domain.dialogue.repository;

import hongik.cartoonforblindbackend.domain.dialogue.entity.Dialogue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DialogueRepository extends JpaRepository<Dialogue,Long> {

}

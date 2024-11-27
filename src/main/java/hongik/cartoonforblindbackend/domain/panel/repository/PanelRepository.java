package hongik.cartoonforblindbackend.domain.panel.repository;

import hongik.cartoonforblindbackend.domain.panel.entity.Panel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PanelRepository extends JpaRepository<Panel,Long> {

  Optional<Panel> findByPanelId(Long panelId);
}

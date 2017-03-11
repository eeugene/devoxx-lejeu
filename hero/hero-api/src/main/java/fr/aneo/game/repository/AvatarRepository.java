package fr.aneo.game.repository;

import fr.aneo.game.model.Avatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by raouf on 05/03/17.
 */
@Transactional
public interface AvatarRepository extends JpaRepository<Avatar, Long> {
}

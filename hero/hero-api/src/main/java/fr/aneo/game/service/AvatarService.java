package fr.aneo.game.service;

import fr.aneo.game.model.Avatar;
import fr.aneo.game.repository.AvatarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by raouf on 04/03/17.
 */
@Service
@Slf4j
public class AvatarService {

    @Autowired
    private AvatarRepository avatarRepository;

    public List<Avatar> findAllAvatars() {
        return avatarRepository.findAll();
    }

    public Avatar find(long id) {
        return avatarRepository.findOne(id);
    }

    public Avatar createAvatar(Avatar avatar) {
        if (avatarRepository.exists(avatar.getId())) {
            throw new RuntimeException("Cet avatar existe déjà");
        }
        return avatarRepository.save(avatar);
    }
}

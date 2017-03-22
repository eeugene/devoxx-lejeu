package fr.aneo.game.resource;

import fr.aneo.game.model.Avatar;
import fr.aneo.game.repository.AvatarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by rcollard on 09/03/2017.
 */
@RestController
@RequestMapping("/api/avatar")
public class AvatarResource {

    @Autowired
    AvatarRepository avatarRepository;

    @GetMapping
    public List<Avatar> all() {
        return avatarRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> one(@PathVariable Long id) {
        byte[] depiction;
        String mimeType;

        Avatar avatar = avatarRepository.findOne(id);
        depiction = avatar.getDepiction();
        mimeType = avatar.getMimeType();

        byte[] decoded = Base64Utils.decode(depiction);

        return ResponseEntity
                .ok()
                .contentLength(decoded.length)
                .contentType(MediaType.parseMediaType(mimeType))
                .body(decoded);
    }
}

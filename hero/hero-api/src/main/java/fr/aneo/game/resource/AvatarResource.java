package fr.aneo.game.resource;

import fr.aneo.game.model.Avatar;
import fr.aneo.game.service.AvatarService;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

/**
 * Created by rcollard on 09/03/2017.
 */
@RestController
@RequestMapping("/api/avatar")
public class AvatarResource {

    @Autowired
    AvatarService avatarService;

    @GetMapping
    public List<Avatar> all() {
        return avatarService.findAllAvatars();
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> one(@PathVariable Long id) {
        byte[] depiction;
        String mimeType;

        Avatar avatar = avatarService.find(id);
        depiction = avatar.getDepiction();
        mimeType = avatar.getMimeType();

        byte[] decoded = Base64Utils.decode(depiction);

        return
                ok()
                .contentLength(decoded.length)
                .contentType(MediaType.parseMediaType(mimeType))
                .body(decoded);
    }

    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AvatarResponse> createAvatar(@RequestParam("image") MultipartFile image) {
        if (image != null && !image.isEmpty()) {
            try {
                byte[] bytes = image.getBytes();
                Avatar avatar = new Avatar().builder().mimeType(image.getContentType()).depiction(bytes).build();
                avatarService.createAvatar(avatar);
                return ok(AvatarResponse.builder().message(
                        format("Image %s successfully uploaded", image.getName())).build());
            } catch (IOException e) {
                return status(INTERNAL_SERVER_ERROR).body(AvatarResponse.builder().errors(e.getMessage()).build());
            }
        }

        return status(BAD_REQUEST).body(AvatarResponse.builder().errors("image is null").build());
    }

    @Data
    @Builder
    private static class AvatarResponse {
        private String message;
        private String errors;
    }
}

package fr.aneo.domain;

import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.codec.binary.StringUtils;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Avatar {

	public static final String DEFAULT_IMAGE_PATH = "/avatar/";
    private static final String DEFAULT_AVATAR_PNG = "default-avatar.png";
    public static final String DEFAULT_AVATAR_PATH = "/avatar/" + DEFAULT_AVATAR_PNG;
	public static final byte[] DEFAULT_AVATAR;
	static {
		try {
			DEFAULT_AVATAR = Files.readAllBytes(Paths.get(Avatar.class.getResource(DEFAULT_AVATAR_PATH).toURI()));
		} catch (Exception e) {
			throw new RuntimeException("Can't load " + DEFAULT_AVATAR_PNG);
		}
	}

    public static String getDefaultAvatar() throws Exception {
        return getImage(DEFAULT_AVATAR_PNG);
    }

    public static String getImage(String name) throws Exception {
        return toB64(Files.readAllBytes(getPath(DEFAULT_IMAGE_PATH + name)));
    }

    public static Path getPath(String path) throws URISyntaxException {
        return Paths.get(Avatar.class.getResource(path).toURI());
    }

    public static String toB64(byte[] imageByteArray) {
        StringBuilder sb = new StringBuilder();
        sb.append("data:image/png;base64,");
        sb.append(StringUtils.newStringUtf8(Base64.encodeBase64(imageByteArray, false)));
        return sb.toString();
    }
}
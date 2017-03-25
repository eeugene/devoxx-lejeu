package fr.aneo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class WebController {

	@Value("${hero-api.url}")
	private String heroApiUrl;

	@GetMapping(path = "/")
	String index(Map<String, Object> model) {
		model.put("heroApiUrl", heroApiUrl);
		return "index";
	}

}
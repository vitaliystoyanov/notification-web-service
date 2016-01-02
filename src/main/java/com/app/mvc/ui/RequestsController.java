package com.app.mvc.ui;

import com.app.mvc.domain.entity.Device;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/v1")
public class RequestsController {

	public static final String JSON_VIEW = "json";
	private static Logger logger = LogManager.getLogger(RequestsController.class);

	@RequestMapping(value= "/requests/{id}", method = RequestMethod.GET)
	public String retrieveRequest(@PathVariable long id, Model model) {
		logger.debug("URL: /requests/{id}, Accepted request with id: " + id);
		model.addAttribute("data", new Device("45y8fd8ga", "Android"));
		return JSON_VIEW;
	}

	@RequestMapping(value= "/requests", method = RequestMethod.POST)
	public String createRequest() {
		logger.debug("URL: /requests Create new request with next parameters: ");
		return null;
	}

	public String retrieveAllRequests() {
		return null;
	}
}
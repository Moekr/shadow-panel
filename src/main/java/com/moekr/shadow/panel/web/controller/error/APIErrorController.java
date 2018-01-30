package com.moekr.shadow.panel.web.controller.error;

import com.moekr.shadow.panel.util.ToolKit;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(GlobalErrorController.ERROR_PATH)
@ResponseStatus(HttpStatus.OK)
public class APIErrorController {
	@RequestMapping
	public Map<String, Object> error(HttpServletRequest request) {
		HttpStatus httpStatus = ToolKit.httpStatus(request);
		return ToolKit.assemblyResponseBody(httpStatus.value(), httpStatus.getReasonPhrase());
	}
}

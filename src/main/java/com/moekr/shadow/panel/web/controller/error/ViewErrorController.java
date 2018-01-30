package com.moekr.shadow.panel.web.controller.error;

import com.moekr.shadow.panel.util.ToolKit;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping(GlobalErrorController.ERROR_PATH)
public class ViewErrorController {
	@RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
	public String errorHtml(Map<String, Object> parameterMap, HttpServletRequest request) {
		HttpStatus status = ToolKit.httpStatus(request);
		parameterMap.put("error", status.value());
		parameterMap.put("message", status.getReasonPhrase());
		return "err";
	}
}

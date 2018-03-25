package com.moekr.shadow.panel.web.controller.subscribe;

import com.moekr.shadow.panel.logic.service.NodeService;
import com.moekr.shadow.panel.logic.service.PropertyService;
import com.moekr.shadow.panel.logic.service.UserService;
import com.moekr.shadow.panel.logic.vo.model.NodeModel;
import com.moekr.shadow.panel.logic.vo.model.UserModel;
import com.moekr.shadow.panel.util.enums.DefaultProperty;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;

@RestController
public class SubscribeController {
	private final UserService userService;
	private final NodeService nodeService;
	private final PropertyService propertyService;

	private final Base64 base64;
	private final Charset charset;

	@Autowired
	public SubscribeController(UserService userService, NodeService nodeService, PropertyService propertyService) {
		this.userService = userService;
		this.nodeService = nodeService;
		this.propertyService = propertyService;

		this.base64 = new Base64(0, null, true);
		this.charset = Charset.forName("UTF-8");
	}

	@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
	@GetMapping(value = "/subscribe", produces = MediaType.TEXT_PLAIN_VALUE)
	public String subscribe(@RequestParam(required = false) Optional<String> token) {
		if (!token.isPresent()) return "";
		UserModel user = userService.findByToken(token.get());
		if (user == null) return "";
		List<NodeModel> nodeList = nodeService.available(user.getId());
		if (nodeList == null || nodeList.isEmpty()) return "";
		StringBuilder stringBuilder = new StringBuilder();
		nodeList.forEach(node -> stringBuilder.append("ssr://").append(convertLink(user, node)).append('\n'));
		return base64Encode(stringBuilder.toString());
	}

	private String convertLink(UserModel user, NodeModel node) {
		StringBuilder builder = new StringBuilder();
		builder.append(node.getAddress()).append(':');
		builder.append(node.getPort()).append(':');
		builder.append(node.getProtocol()).append(':');
		builder.append(node.getMethod()).append(':');
		builder.append(node.getObfs()).append(':');
		builder.append(base64Encode(node.getPassword())).append("/?");
		builder.append("obfsparam=").append(base64Encode(node.getClientObfsParam())).append('&');
		builder.append("protoparam=").append(base64Encode(user.getId() + ":" + user.getToken())).append('&');
		builder.append("remarks=").append(base64Encode(node.getRegion() + " - " + node.getName())).append('&');
		builder.append("group=").append(base64Encode(propertyService.findByName(DefaultProperty.GROUP.getName()).getContent()));
		return base64Encode(builder.toString());
	}

	private String base64Encode(String str) {
		return base64.encodeToString(str.getBytes(charset));
	}
}

package com.moekr.shadow.panel.web.controller.subscribe;

import com.moekr.shadow.panel.logic.service.NodeService;
import com.moekr.shadow.panel.logic.service.UserService;
import com.moekr.shadow.panel.logic.vo.NodeVO;
import com.moekr.shadow.panel.logic.vo.PortVO;
import com.moekr.shadow.panel.logic.vo.UserVO;
import com.moekr.shadow.panel.util.ShadowProperties;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
import java.util.List;

@RestController
public class SubscribeController {
	private final UserService userService;
	private final NodeService nodeService;
	private final ShadowProperties properties;

	private final Base64 base64;
	private final Charset charset;

	@Autowired
	public SubscribeController(UserService userService, NodeService nodeService, ShadowProperties properties) {
		this.userService = userService;
		this.nodeService = nodeService;
		this.properties = properties;

		this.base64 = new Base64(0, null, true);
		this.charset = Charset.forName("UTF-8");
	}

	@GetMapping(value = "/subscribe", produces = MediaType.TEXT_PLAIN_VALUE)
	public String subscribe(@RequestParam(required = false) String token) {
		if (token == null) return "";
		UserVO user = userService.retrieveByToken(token);
		if (user == null) return "";
		List<NodeVO> nodeList = nodeService.available(user.getId());
		if (nodeList == null || nodeList.isEmpty()) return "";
		StringBuilder stringBuilder = new StringBuilder();
		nodeList.forEach(node -> stringBuilder.append(convertLink(user, node)));
		return base64Encode(stringBuilder.toString());
	}

	private String convertLink(UserVO user, NodeVO node) {
		StringBuilder sb1 = new StringBuilder();
		for (PortVO port : node.getPortSet()) {
			StringBuilder sb2 = new StringBuilder();
			sb2.append(node.getAddress()).append(':');
			sb2.append(port.getPort()).append(':');
			sb2.append(port.getProtocol()).append(':');
			sb2.append(port.getMethod()).append(':');
			sb2.append(port.getObfs()).append(':');
			sb2.append(base64Encode(port.getPassword())).append("/?");
			if (port.getObfsParam() != null && !port.getObfsParam().isEmpty()) {
				sb2.append("obfsparam=").append(base64Encode(port.getObfsParam())).append('&');
			}
			sb2.append("protoparam=").append(base64Encode(user.getPort() + ":" + user.getPort())).append('&');
			sb2.append("remarks=").append(base64Encode(node.getRegion() + " - " + node.getName() + " - " + port.getPort())).append('&');
			sb2.append("group=").append(base64Encode(properties.getSubscribe().getGroup()));
			sb1.append("ssr://").append(base64Encode(sb2.toString())).append('\n');
		}
		return sb1.toString();
	}

	private String base64Encode(String str) {
		return base64.encodeToString(str.getBytes(charset));
	}
}

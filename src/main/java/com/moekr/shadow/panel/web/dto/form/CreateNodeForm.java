package com.moekr.shadow.panel.web.dto.form;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@EqualsAndHashCode
@ToString
public class CreateNodeForm {
	@NotEmpty(message = "请填写地区！")
	private String region;
	@NotEmpty(message = "请填写名称！")
	private String name;
	@NotNull(message = "请填写权限等级！")
	private Integer level;
	@NotEmpty(message = "请填写地址！")
	private String address;
	@NotNull(message = "请填写备注！")
	private String note;
	@Range(min = 1, message = "请填写有效的总流量！")
	@NotNull(message = "请填写总流量！")
	private Integer totalData;
	@Range(message = "请填写有效的已用流量！")
	@NotNull(message = "请填写已用流量！")
	private Long usedData;
	@Range(min = 1, max = 65535, message = "请填写有效的端口！")
	@NotNull(message = "请填写端口！")
	private Integer port;
	@Pattern(regexp = "[a-zA-Z0-9_.-]{4,16}", message = "密码只能包含大小写字母数字与'_'、'.'、'-'且只能为4-16位！")
	@NotNull(message = "请填写密码！")
	private String password;
	@Pattern(regexp = "^(none|rc4-md5|(aes-(128|192|256)-(cfb(|8)|ctr))|chacha20-ietf|(|x)(salsa|chacha)20)$", message = "请填写有效的加密算法！")
	@NotEmpty(message = "请填写加密算法！")
	private String method;
	@Pattern(regexp = "^(origin|verify_deflate|auth_sha1_v4(|_compatible)|auth_aes128_(md5|sha1)|(auth_chain_[a-f]))$", message = "请填写有效的认证协议！")
	@NotEmpty(message = "请填写认证协议！")
	private String protocol;
	@Pattern(regexp = "^(plain|(http_simple|http_post|tls1\\.2_ticket_(|fast)auth)(|_compatible))$", message = "请填写有效的混淆插件！")
	@NotEmpty(message = "请填写混淆插件！")
	private String obfs;
	@NotNull(message = "请填写服务端混淆参数！")
	private String serverObfsParam;
	@NotNull(message = "请填写客户端混淆参数！")
	private String clientObfsParam;
}

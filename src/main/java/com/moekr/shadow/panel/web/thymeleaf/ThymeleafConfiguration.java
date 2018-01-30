package com.moekr.shadow.panel.web.thymeleaf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateModeHandler;
import org.thymeleaf.templateparser.html.LegacyHtml5TemplateParser;
import org.thymeleaf.templateparser.xmlsax.XhtmlAndHtml5NonValidatingSAXTemplateParser;
import org.thymeleaf.templateparser.xmlsax.XhtmlValidatingSAXTemplateParser;
import org.thymeleaf.templateparser.xmlsax.XmlNonValidatingSAXTemplateParser;
import org.thymeleaf.templateparser.xmlsax.XmlValidatingSAXTemplateParser;
import org.thymeleaf.templatewriter.XmlTemplateWriter;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashSet;

@Configuration
public class ThymeleafConfiguration {
	private static final int MAX_PARSERS_POOL_SIZE = 24;

	private final TemplateEngine templateEngine;

	@Autowired
	public ThymeleafConfiguration(TemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
	}

	@PostConstruct
	private void configure() {
		int availableProcessors = Runtime.getRuntime().availableProcessors();
		int poolSize = Math.min((availableProcessors <= 2 ? availableProcessors : availableProcessors - 1), MAX_PARSERS_POOL_SIZE);

		templateEngine.setDefaultTemplateModeHandlers(new HashSet<>(Arrays.asList(
				new TemplateModeHandler(
						"XML",
						new XmlNonValidatingSAXTemplateParser(poolSize),
						new XmlTemplateWriter()),
				new TemplateModeHandler(
						"VALIDXML",
						new XmlValidatingSAXTemplateParser(poolSize),
						new XmlTemplateWriter()),
				new TemplateModeHandler(
						"XHTML",
						new XhtmlAndHtml5NonValidatingSAXTemplateParser(poolSize),
						new CustomXhtmlHtml5TemplateWriter()),
				new TemplateModeHandler(
						"VALIDXHTML",
						new XhtmlValidatingSAXTemplateParser(poolSize),
						new CustomXhtmlHtml5TemplateWriter()),
				new TemplateModeHandler(
						"HTML5",
						new XhtmlAndHtml5NonValidatingSAXTemplateParser(poolSize),
						new CustomXhtmlHtml5TemplateWriter()),
				new TemplateModeHandler(
						"LEGACYHTML5",
						new LegacyHtml5TemplateParser("LEGACYHTML5", poolSize),
						new CustomXhtmlHtml5TemplateWriter())
		)));
	}
}

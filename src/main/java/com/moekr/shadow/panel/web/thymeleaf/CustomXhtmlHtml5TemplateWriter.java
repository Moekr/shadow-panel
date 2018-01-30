package com.moekr.shadow.panel.web.thymeleaf;

import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Attribute;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Text;
import org.thymeleaf.templatewriter.AbstractGeneralTemplateWriter;

import java.io.IOException;
import java.io.Writer;

public class CustomXhtmlHtml5TemplateWriter extends AbstractGeneralTemplateWriter {

	@Override
	protected void writeText(Arguments arguments, Writer writer, Text text) throws IOException {
		String content = text.getContent();
		if (StringUtils.isNotBlank(content)) {
			super.writeText(arguments, writer, text);
		}
	}

	@Override
	protected void writeElement(Arguments arguments, Writer writer, Element element) throws IOException {
		if (element.hasAttributes()) {
			Attribute[] attributes = element.unsafeGetAttributes();
			for (int index = 0; index < element.numAttributes(); index++) {
				Attribute attribute = attributes[index];
				String value = attribute.getEscapedValue();
				if (!(value == null && attribute.isOnlyName())) {
					if (StringUtils.isEmpty(value)) {
						attributes[index] = new Attribute(attribute.getOriginalName(), true, null);
					}
				}
			}
		}
		super.writeElement(arguments, writer, element);
	}

	@Override
	protected boolean shouldWriteXmlDeclaration() {
		return false;
	}

	@Override
	protected boolean useXhtmlTagMinimizationRules() {
		return true;
	}
}

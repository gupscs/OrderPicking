package br.silveira.orderpicking.common.util;

import br.silveira.orderpicking.common.templates.CommonTemplate;
import br.silveira.orderpicking.common.templates.PdfShippingLabelResultTemplateDto;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

public class TemplateUtil {

    public static String generateHtml(CommonTemplate templateDto){

        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariable("obj", templateDto);

        return templateEngine.process(templateDto.getTemplateHtmlFile(), context);

    }
}

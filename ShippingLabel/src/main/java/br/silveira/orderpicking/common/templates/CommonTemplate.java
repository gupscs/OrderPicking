package br.silveira.orderpicking.common.templates;

public abstract class CommonTemplate {

    public String templateHtmlFile;


    protected CommonTemplate(String templateHtmlFile) {
        this.templateHtmlFile = templateHtmlFile;
    }

    public String getTemplateHtmlFile() {
        return templateHtmlFile;
    }
}

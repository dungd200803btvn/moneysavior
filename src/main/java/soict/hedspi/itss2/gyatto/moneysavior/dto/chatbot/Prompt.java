package soict.hedspi.itss2.gyatto.moneysavior.dto.chatbot;

public class Prompt {
    protected String template;

    protected Prompt(String template) {
        this.template = template;
    }

    @Override
    public String toString() {
        return template;
    }
}

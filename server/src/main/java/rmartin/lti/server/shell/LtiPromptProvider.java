package rmartin.lti.server.shell;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component
public class LtiPromptProvider implements PromptProvider {
    @Override
    public AttributedString getPrompt() {
        return new AttributedString("lti: ", AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW));
    }
}

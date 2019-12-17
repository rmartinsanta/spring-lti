package rmartin.lti.server.shell;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.shell.table.*;
import rmartin.lti.server.model.Consumer;

import java.util.LinkedHashMap;

public class ShellUtils {
    static String colored(String message, ShellColor textColor){
        return new AttributedString(message, AttributedStyle.DEFAULT.foreground(textColor.getValue())).toAnsi();
    }

    static String error(String message, Object... objects){
        return colored(String.format(message, objects), ShellColor.RED);
    }

    static String info(String message){
        return colored(message, ShellColor.BLUE);
    }

    static String orange(String messae){
        return colored(messae, ShellColor.YELLOW);
    }

    static Table getConsumersAsTable(Iterable<Consumer> consumers){
        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("id", "Id");
        headers.put("username", "Username");
        headers.put("role", "Role");
        headers.put("secret", "Secret");
        TableModel model = new BeanListTableModel<>(consumers, headers);
        TableBuilder builder = new TableBuilder(model);
        return applyStyle(builder).build();
    }

    static TableBuilder applyStyle(TableBuilder builder){
        builder.addHeaderBorder(BorderStyle.fancy_heavy);
        builder.addInnerBorder(BorderStyle.fancy_light);
        return builder;
    }
}

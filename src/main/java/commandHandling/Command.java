package commandHandling;

import net.dv8tion.jda.core.entities.Message;

/**
 * Created by Adair on 04/22/17.
 */
public interface Command {

    public String[] getNames();

    public boolean safe(CommandContainer info);

    public void action(CommandContainer info);

    public Message help();
}
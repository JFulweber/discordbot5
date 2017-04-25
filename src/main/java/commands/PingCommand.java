package commands;

import commandHandling.Command;
import commandHandling.CommandContainer;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;

/**
 * Created by fulwejam000 on 4/20/2017.
 */
public class PingCommand implements Command{

    public String[] names = {"ping"};

    public String[] getNames(){
        return names;
    }

    public boolean safe(CommandContainer info) {
        return true;
    }

    public void action(CommandContainer info) {
        info.event.getMessage().getChannel().sendMessage(new MessageBuilder().append("Current ping is "+info.event.getJDA().getPing()+"ms").build()).queue();
    }

    public Message help() {
        return null;
    }
}
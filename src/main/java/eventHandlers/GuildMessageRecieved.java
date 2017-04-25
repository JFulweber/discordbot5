package eventHandlers;


import commandHandling.CommandContainer;
import commandHandling.CommandParser;
import commandHandling.Commands;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import config.Configuration;

/**
 * Created by fulwejam000 on 4/19/2017.
 */
public class GuildMessageRecieved extends ListenerAdapter{

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        super.onGuildMessageReceived(event);
        if(event.getMessage().getAuthor().equals(event.getJDA().getSelfUser())){
            System.out.println(event.getMessage().getAuthor().getName());
            return;
        }
        if(isCommand(event.getMessage().getContent())){
            CommandContainer container = CommandParser.getInstance().parse(event);
            Commands.handleCommand(container);
        }
    }

    private boolean isCommand(String msg){
        if(msg.length()==0) return false;
        if(msg.substring(0,
                Configuration.get("cmdprefix").length()).
                equals(Configuration.get("cmdprefix")))
            return true;
        return false;
    }
}
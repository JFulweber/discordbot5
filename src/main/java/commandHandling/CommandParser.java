package commandHandling;


import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import static config.Configuration.get;

public class CommandParser {

    private static CommandParser ourInstance = new CommandParser();

    public static CommandParser getInstance() {
        return ourInstance;
    }

    private CommandParser() {
        super();
    }

    public CommandContainer parse(GuildMessageReceivedEvent event){
        String raw = event.getMessage().getContent();
        String noPrefix = raw.replace(get("cmdprefix"),"");
        String invoke = noPrefix.split(" ")[0];
        String[] args = noPrefix.replace(invoke+" ","").split(" ");
        String allArgs = "";
        for(String arg: args){
            allArgs+=arg;
        }
        return new CommandContainer(event, raw, invoke, allArgs, args);
    }

}
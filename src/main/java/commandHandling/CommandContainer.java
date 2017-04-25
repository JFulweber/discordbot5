package commandHandling;

import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class CommandContainer {
    public GuildMessageReceivedEvent event;
    public String raw;
    public String invoke;
    public String allArgs;
    public String[] args;

    public CommandContainer(GuildMessageReceivedEvent event, String raw, String invoke, String allArgs, String[] args) {
        this.event = event;
        this.raw = raw;
        this.invoke = invoke;
        this.allArgs = allArgs;
        this.args = args;
    }
}
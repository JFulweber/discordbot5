package start; /**
 * Created by Adair on 04/22/17.
 */
import commandHandling.Commands;
import eventHandlers.GuildMessageRecieved;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import config.Configuration;
import youtubesearch.YoutubeSearcher;

/**
 * Created by fulwejam000 on 4/17/2017.
 */
public class Starup {
    public static JDA jda;
    public static void main(String[] args) {
        if(Configuration.ready()){
            try{
                jda = new JDABuilder(AccountType.BOT).setToken(Configuration.get("token")).buildAsync();
                jda.addEventListener(new GuildMessageRecieved());
                Commands.loadCommands();
                System.out.println(Configuration.get("cmdprefix"));
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        else{
            System.out.println("set token!");
        }
    }
}
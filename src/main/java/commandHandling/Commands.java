package commandHandling;


import commands.MusicCommand;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import sun.reflect.Reflection;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by fulwejam000 on 4/20/2017.
 */
public class Commands {

    private static HashMap<String, Command> commands = new HashMap<>();

    public static void loadCommands(){
        /*long first = System.nanoTime();*/
        List<ClassLoader> classLoaders = new LinkedList<ClassLoader>();
        classLoaders.add(ClasspathHelper.contextClassLoader());
        classLoaders.add(ClasspathHelper.staticClassLoader());
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setScanners(new SubTypesScanner(false), new ResourcesScanner())
                .setUrls(ClasspathHelper.forClassLoader(classLoaders.toArray(new ClassLoader[0])))
                .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix("commands"))));
        Set<Class<? extends Object>> classes = reflections.getSubTypesOf(Object.class);
        classes.forEach(c->{
            try{
                if(c.isAnonymousClass()) return;
                Command command = (Command)c.newInstance();
                String[] names = command.getNames();
                for(String name: names) {
                    commands.put(name, command);
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        });
        /*long second = System.nanoTime();
        System.out.println("Finding command classes took: "+(second-first)+" nanoseconds");*/
    }

    public static void handleCommand(CommandContainer container){
        if(commands.containsKey(container.invoke)){
            if(commands.get(container.invoke).safe(container)){
                commands.get(container.invoke).action(container);
            }
            else{
                container.event.getChannel().sendMessage(commands.get(container.invoke).help());
            }
        }
    }
}
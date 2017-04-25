package config;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by fulwejam000 on 4/17/2017.
 */
public class Configuration{

    private static Path CONFIG_FILEPATH = Paths.get("config.json");
    private static HashMap<String, String> configValues = new HashMap<>();

    public static boolean ready(){

        configValues.put("token","not set");
        configValues.put("game","my name jeff");
        configValues.put("nickname","lemon");
        configValues.put("cmdprefix","-");

        try{
            if(Files.exists(CONFIG_FILEPATH)){
                return read();
            }else{
                create();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    private static boolean read() throws FileNotFoundException {
        JSONParser parser = new JSONParser();
        FileReader reader = new FileReader(CONFIG_FILEPATH.toFile());

        try {
            Scanner scanner = new Scanner(CONFIG_FILEPATH);
            if(scanner.nextLine()==null){
                reader.close();
                scanner.close();
                Files.delete(CONFIG_FILEPATH);
                create();
                return false;
            }
            JSONObject object = (JSONObject) parser.parse(reader);
            if(!(object.keySet().equals(configValues.keySet()))){
                // reset file
                reader.close();
                Files.delete(CONFIG_FILEPATH);
                create();
                return false;
            }
            configValues.forEach((k,v)->{
                configValues.replace(k,object.get(k).toString());
            });
            return !configValues.get("token").equals("not set");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void create() throws IOException {
        Files.createFile(CONFIG_FILEPATH);
        JSONObject object = new JSONObject();
        configValues.forEach((k,v)->{
            System.out.println("put in "+k+" with value "+v);
            object.put(k,v);
        });
        FileWriter writer = new FileWriter(CONFIG_FILEPATH.toFile(),true);
        writer.write(object.toJSONString().replace(",",",\n"));
        System.out.println(object.toJSONString().replace(",",",\n"));
        writer.flush();
    }

    public static String get(String key){
        return (String) configValues.get(key);
    }

}
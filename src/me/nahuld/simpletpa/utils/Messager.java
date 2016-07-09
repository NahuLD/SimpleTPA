package me.nahuld.simpletpa.utils;

import java.io.File;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.nahuld.simpletpa.Main;

public class Messager {

    private final FileConfiguration storage;
    private String LENGUAGE = "en_US.";

    public Messager(Main main) {
        File storageFile = new File(main.getDataFolder(), "lang.yml");

        if (!storageFile.exists()) {
        	main.saveResource("lang.yml", false);
        }

        storage = YamlConfiguration.loadConfiguration(storageFile);
        LENGUAGE = storage.getString("lenguage") + ".";
    }

    public String stripColor(String input) {
    	return ChatColor.stripColor(input);
    }

    public String getPrefix() {
        return storage.getString(LENGUAGE + "prefix");
    }

    public String getMessage(String format) {
    	return ChatColor.translateAlternateColorCodes('&', getPrefix() + storage.getString(LENGUAGE + format));
    }
    
    public List<String> getList(String format) {
    	return storage.getStringList(LENGUAGE + format);
    }
    
    public String getText(String format) {
    	return ChatColor.translateAlternateColorCodes('&', storage.getString(LENGUAGE + format));
    }
}

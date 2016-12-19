package me.nahuld.simpletpa.utils;

import java.io.File;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import me.nahuld.simpletpa.Main;
import me.nahuld.simpletpa.data.Utf8YamlConfiguration;

public class Messager {

    private FileConfiguration storage;
    private String LENGUAGE = "en_US.";
    private File storageFile;
    
    private Main main;

    public Messager(Main main) {
    	this.main = main;
        storageFile = new File(main.getDataFolder(), "lang.yml");

        if (!storageFile.exists()) {
        	main.saveResource("lang.yml", false);
        }

        storage = Utf8YamlConfiguration.loadConfiguration(storageFile);
        LENGUAGE = storage.getString("language") + ".";
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
    
    public void save() {
		try {
	        storage = Utf8YamlConfiguration.loadConfiguration(storageFile);
			storage.save(storageFile);
		} catch (Exception ex) {
			main.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Error while saving: " + storageFile.getName());
		}
    }
}

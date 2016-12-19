package me.nahuld.simpletpa.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.nahuld.simpletpa.Main;
import me.nahuld.simpletpa.utils.Messager;

public class ReloadCommand implements CommandExecutor {
	
	private Messager messager;
	private Main main;
	
	public ReloadCommand(Main main) {
		this.main = main;
		messager = main.messager();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		if (!(sender instanceof Player)) sender.sendMessage(messager.getMessage("error.not-player"));
		else {
			Player player = (Player) sender;
			
			if (!player.hasPermission("simpletpa.reload")) player.sendMessage(messager.getMessage("error.no-permission"));
			else {
				main.config().save();
				main.messager().save();
				player.sendMessage(messager.getMessage("success.reload-config"));
			}
			
		}
		
		return true;
	}

}

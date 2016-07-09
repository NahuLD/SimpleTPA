package me.nahuld.simpletpa.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.nahuld.simpletpa.Main;
import me.nahuld.simpletpa.plugin.Request;
import me.nahuld.simpletpa.utils.Messager;

public class RequestAcceptCommand implements CommandExecutor {

	private Main main;
	
	private Messager messager;
	public RequestAcceptCommand(Main main) {
		this.main = main;
		messager = main.messager();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		

		if (!(sender instanceof Player)) sender.sendMessage(messager.getMessage("error.not-player"));
		else {
			Player requested = (Player) sender;
			
			if (requested.hasPermission("simpletpa.tpa")) {
				requested.sendMessage(messager.getMessage("error.no-permission"));
				return true;
			}
			
			Request request = main.manager().getRequested(requested);
			if (request != null) {
				request.accept();
			} else requested.sendMessage(messager.getMessage("error.no-petition"));
		}
		
		return true;
	}
}

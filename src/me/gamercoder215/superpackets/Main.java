package me.gamercoder215.superpackets;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	public static void sendPluginMessage(CommandSender sender, String message) {
		sender.sendMessage(ChatColor.GOLD + "[" + ChatColor.AQUA + "Super Packets" + ChatColor.GOLD + "] " + ChatColor.BLUE + message);
	}
	
	public void onEnable() {
		
	}
	
}

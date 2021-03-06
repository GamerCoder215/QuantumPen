package me.gamercoder215.quantumpen.edit;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import me.gamercoder215.quantumpen.QuantumPen;
import me.gamercoder215.quantumpen.utils.CommandTabCompleter;

public class Chunk implements CommandExecutor {
	
	protected QuantumPen plugin;
	
	public Chunk(QuantumPen plugin) {
		this.plugin = plugin;
		plugin.getCommand("chunk").setExecutor(this);
		plugin.getCommand("chunk").setTabCompleter(new CommandTabCompleter());
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length < 1) {
			QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid world.");
			return false;
		}
		
		if (Bukkit.getWorld(args[0]) == null) {
			QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid world.");
			return false;
		}
		
		org.bukkit.World w = Bukkit.getWorld(args[0]);
		
		if (args.length < 2) {
			QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid X.");
			return false;
		}
		
		if (args.length < 3) {
			QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid Z.");
			return false;
		}
		
		if (args.length < 4) {
			QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid action.");
			return false;
		}
		
		try {
			org.bukkit.Chunk chunk = w.getChunkAt(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
			
			switch (args[3].toLowerCase()) {
				case "gettickets": {
					List<String> plugins = new ArrayList<>();

					for (Plugin p : chunk.getPluginChunkTickets()) {
						plugins.add(ChatColor.BLUE + "Name: " + ChatColor.GOLD + p.getDescription().getName() + " | " +
						ChatColor.BLUE + "Version: " + ChatColor.GOLD + p.getDescription().getVersion());
					}

					sender.sendMessage(ChatColor.AQUA + "" + ChatColor.UNDERLINE + "Plugin Chunk Tickets\n" + String.join("\n", plugins));
					break;
				}
				case "load": {
					if (args.length < 5) {
						boolean loaded = chunk.load();
						
						if (loaded) {
							sender.sendMessage(ChatColor.GREEN + "Chunk load successful!");
						} else {
							sender.sendMessage(ChatColor.RED + "Chunk load unsuccessful.");
						}
					} else {
						boolean loaded = chunk.load(Boolean.parseBoolean(args[4]));
						
						if (loaded) {
							sender.sendMessage(ChatColor.GREEN + "Chunk load successful!");
						} else {
							sender.sendMessage(ChatColor.RED + "Chunk load unsuccessful.");
						}
					}
					break;
				}
				case "unload": {
					if (args.length < 5) {
						boolean unloaded = chunk.load();
						
						if (unloaded) {
							sender.sendMessage(ChatColor.GREEN + "Chunk unload successful!");
						} else {
							sender.sendMessage(ChatColor.RED + "Chunk unload unsuccessful.");
						}
					} else {
						boolean unloaded = chunk.load(Boolean.parseBoolean(args[4]));
						
						if (unloaded) {
							sender.sendMessage(ChatColor.GREEN + "Chunk unload successful!");
						} else {
							sender.sendMessage(ChatColor.RED + "Chunk unload unsuccessful.");
						}
					}
					break;
				}
				case "forceload": {
					if (!(chunk.isForceLoaded())) {
						chunk.setForceLoaded(true);
						
						sender.sendMessage(ChatColor.GREEN + "Force load successful!");
					} else sender.sendMessage(ChatColor.RED + "Force load unsuccessful; Chunk is already forceloaded.");
					break;
				}
				case "unforceload": {
					if (chunk.isForceLoaded()) {
						chunk.setForceLoaded(false);
						
						sender.sendMessage(ChatColor.GREEN + "Remove force load successful!");
					} else sender.sendMessage(ChatColor.RED + "Remove force load unsuccessful; Chunk is not forceloaded.");
					break;
				}
				case "setage": {
					if (args.length < 4) {
						QuantumPen.sendPluginMessage(sender, ChatColor.RED + "Please provide a valid age, in ticks.");
						return false;
					}
					
					long age = Long.parseLong(args[3]);
					DecimalFormat df = new DecimalFormat("###.##");
					chunk.setInhabitedTime(age);
					sender.sendMessage(ChatColor.GREEN + "Successfully set age to \"" + Long.toString(age) + "\" ticks. (" + df.format(age / 20) + " seconds).");
					break;
				}
				case "addticket": {
					chunk.addPluginChunkTicket(plugin);
					sender.sendMessage(ChatColor.GREEN + "Successfully added a plugin chunk ticket!");
					break;
				}
				case "removeticket": {
					chunk.removePluginChunkTicket(plugin);
					sender.sendMessage(ChatColor.GREEN + "Successfully removed a plugin chunk ticket!");
					break;
				}
				default: {
					QuantumPen.sendPluginMessage(sender, ChatColor.RED + "This action does not exist.");
					return false;
				}
			}
		} catch (IllegalArgumentException e) {
			QuantumPen.sendPluginMessage(sender, ChatColor.RED + "There was an error parsing arguments.");
			return false;
		} catch (Exception e) {
			QuantumPen.sendPluginMessage(sender, ChatColor.RED + "There was an error:\n" + e.getLocalizedMessage());
			return false;
		}
		return true;
	}

}

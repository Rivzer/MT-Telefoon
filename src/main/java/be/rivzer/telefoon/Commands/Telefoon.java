package be.rivzer.telefoon.Commands;

import be.rivzer.telefoon.Config.Config;
import be.rivzer.telefoon.Logger;
import be.rivzer.telefoon.Main;
import be.rivzer.telefoon.TabCompletor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Telefoon implements CommandExecutor {

    private Main plugin;
    List GPS;

    public Telefoon(Main plugin) {
        this.plugin = plugin;
        plugin.getCommand("telefoon").setExecutor(this);
        plugin.getCommand("telefoon").setTabCompleter(new TabCompletor());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String alias, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage(Logger.color("&f---------------------"));
            sender.sendMessage(Logger.color("&cDeze commands zijn niet bedoeld voor de console!"));
            sender.sendMessage(Logger.color("&f---------------------"));
            return true;
        }

        Player p = (Player) sender;

        if(cmd.getName().equalsIgnoreCase("telefoon")) {
            if(args.length == 0){
                if(p.hasPermission("telefoon.help")){
                    p.sendMessage(Logger.color("&f---------------------"));
                    p.sendMessage("");
                    p.sendMessage(Logger.color("&b/telefoon help &f- Bekijk het help menu"));
                    p.sendMessage("");
                    p.sendMessage(Logger.color("&b/telefoon locatie &f- Laat je aantal blokken ver weten aan je contacten"));
                    p.sendMessage("");
                    p.sendMessage(Logger.color("&b/telefoon addgps &f- Voeg een gps locatie toe"));
                    p.sendMessage("");
                    p.sendMessage(Logger.color("&b/telefoon info &f- Krijg informatie over deze plugin"));
                    p.sendMessage("");
                    p.sendMessage(Logger.color("&f---------------------"));
                }
                else{
                    p.sendMessage(Logger.color("&f---------------------"));
                    p.sendMessage("");
                    p.sendMessage(Logger.color("&b/telefoon help &f- Bekijk het help menu"));
                    p.sendMessage("");
                    p.sendMessage(Logger.color("&b/telefoon locatie &f- Laat je aantal blokken ver weten aan je contacten"));
                    p.sendMessage("");
                    p.sendMessage(Logger.color("&b/telefoon info &f- Krijg informatie over deze plugin"));
                    p.sendMessage("");
                    p.sendMessage(Logger.color("&f---------------------"));
                }
            }
            else if(args[0].equalsIgnoreCase("help")){
                if(p.hasPermission("telefoon.help")){
                    p.sendMessage(Logger.color("&f---------------------"));
                    p.sendMessage("");
                    p.sendMessage(Logger.color("&b/telefoon help &f- Bekijk het help menu"));
                    p.sendMessage("");
                    p.sendMessage(Logger.color("&b/telefoon locatie &f- Laat je aantal blokken ver weten aan je contacten"));
                    p.sendMessage("");
                    p.sendMessage(Logger.color("&b/telefoon addgps &f- Voeg een gps locatie toe"));
                    p.sendMessage("");
                    p.sendMessage(Logger.color("&b/telefoon info &f- Krijg informatie over deze plugin"));
                    p.sendMessage("");
                    p.sendMessage(Logger.color("&f---------------------"));
                }
                else{
                    p.sendMessage(Logger.color("&f---------------------"));
                    p.sendMessage("");
                    p.sendMessage(Logger.color("&b/telefoon help &f- Bekijk het help menu"));
                    p.sendMessage("");
                    p.sendMessage(Logger.color("&b/telefoon locatie &f- Laat je aantal blokken ver weten aan je contacten"));
                    p.sendMessage("");
                    p.sendMessage(Logger.color("&b/telefoon info &f- Krijg informatie over deze plugin"));
                    p.sendMessage("");
                    p.sendMessage(Logger.color("&f---------------------"));
                }
            }
            else if(args[0].equalsIgnoreCase("addgps")){
                if(p.hasPermission("telefoon.addgps")){
                    if(!Bukkit.getPluginManager().getPlugin("GPS").isEnabled()){
                        p.sendMessage(Main.Prefix() + "&cU heeft hier de plugin GPS voor nodig! u kan deze kopen op spigot.");
                        return true;
                    }
                    if(args.length == 1){
                        p.sendMessage(Logger.color("&cGeef een gps naam op!"));
                        return true;
                    }
                    else if(args.length == 2){
                        p.sendMessage(Logger.color("&cGeef een nbt tag op!"));
                        return true;
                    }

                    GPS = Config.getCustomConfig1().getStringList("GPSList");
                    GPS.add(args[1]);
                    Config.getCustomConfig1().set("GPSList", GPS);
                    Config.getCustomConfig1().set("GPS." + args[1] + ".NBT", args[2]);
                    Config.saveConfig1();

                    p.sendMessage(Logger.color(Main.Prefix() + "&aGPS &f" + args[1] + " &ais opgeslagen."));
                    return true;
                }
            }
            else if(args[0].equalsIgnoreCase("locatie")){
                if(Config.getCustomConfig2().getString("Players." + p.getUniqueId() + ".ShowLocation") == "true"){
                    Config.getCustomConfig2().set("Players." + p.getUniqueId() + ".ShowLocation", false);
                    p.sendMessage(Logger.color(Main.Prefix() + "&aUw locatie word nu &cniet &ameer weergeven bij contacten."));
                }
                else{
                    Config.getCustomConfig2().set("Players." + p.getUniqueId() + ".ShowLocation", true);
                    p.sendMessage(Logger.color(Main.Prefix() + "&aUw locatie word nu &3wel &aweergeven bij contacten."));
                }
            }
            else if(args[0].equalsIgnoreCase("info")){
                p.sendMessage(Logger.color("&f---------------------"));
                p.sendMessage(Logger.color("&bDeze plugin is gemaak door &f&lRivzer"));
                p.sendMessage(Logger.color("&bVersion: &f&l1.0"));
                p.sendMessage(Logger.color("&f---------------------"));
                return true;
            }
        }

        return false;
    }
}

package be.rivzer.telefoon;

import be.rivzer.telefoon.API.API_Manager;
import be.rivzer.telefoon.Commands.Telefoon;
import be.rivzer.telefoon.Config.Config;
import be.rivzer.telefoon.Inventorys.Contacten;
import be.rivzer.telefoon.Listeners.*;
import com.live.bemmamin.gps.api.GPSAPI;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Economy econ = null;
    public static API_Manager apimanager;
    public static GPSAPI gpsapi = null;

    @Override
    public void onEnable() {
        //Console
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

        //Config
        Config.createCustomConfig1();
        Config.createCustomConfig2();
        Config.createCustomConfig3();

        //Vault
        if (!setupEconomy()) {
            console.sendMessage(Logger.color("&4&lTelefoon plugin word uitgezet omdat de plugin Vault niet werd gevonden of deze staat niet aan!"));
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        //API_MANAGER
        apimanager = new API_Manager();

        //Util
        if (Bukkit.getPluginManager().getPlugin("GPS") != null) {
            if (Bukkit.getPluginManager().getPlugin("GPS").isEnabled()) {
                gpsapi = new GPSAPI(this);
            }
        }

        //Commands
        new Telefoon(this);

        //Bstats
        Metrics metrics = new Metrics(this, 12553);

        //Listeners
        Bukkit.getServer().getPluginManager().registerEvents(new OpenTelefoon(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new InventoryClick(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new OnJoin(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new AntiLose(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new Contacten(), this);

        Bukkit.getServer().getPluginManager().registerEvents(new FA(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new FA1(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new FA2(), this);

        //Console Logs
        console.sendMessage(Logger.color("&f----------------------------------------"));
        console.sendMessage(Logger.color(""));
        console.sendMessage(Logger.color("&b&lPlugin wordt aangezet..."));
        console.sendMessage(Logger.color(""));
        console.sendMessage(Logger.color("&b&lCoded by&f&l: Rivzer"));
        console.sendMessage(Logger.color(""));
        console.sendMessage(Logger.color("&f----------------------------------------"));
    }

    @Override
    public void onDisable() {
        //Console
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

        //Console Logs
        console.sendMessage(Logger.color("&f----------------------------------------"));
        console.sendMessage(Logger.color(""));
        console.sendMessage(Logger.color("&c&lPlugin wordt uitgezet..."));
        console.sendMessage(Logger.color(""));
        console.sendMessage(Logger.color("&c&lCoded by&f&l: Rivzer"));
        console.sendMessage(Logger.color(""));
        console.sendMessage(Logger.color("&f----------------------------------------"));
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEconomy() {
        return econ;
    }

    public static String Prefix(){
        return Config.getCustomConfig3().getString("Prefix");
    }
}

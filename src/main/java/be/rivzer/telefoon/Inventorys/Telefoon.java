package be.rivzer.telefoon.Inventorys;

import be.rivzer.telefoon.Config.Config;
import be.rivzer.telefoon.Logger;
import be.rivzer.telefoon.Main;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Telefoon {

    public static void OpenTelefoon(Player p){
        Inventory inv = Bukkit.createInventory(p, InventoryType.SHULKER_BOX, Logger.color("&0."));

        if(Config.getCustomConfig2().getString("Players." + p.getUniqueId() + ".Power") == "false"){
            setItem(inv, Material.IRON_INGOT, 10, "&a&lPower ON", "poweron");
        }
        else{
            setItem(inv, Material.IRON_INGOT, 10, "&9&lHome", "home");

            setItem(inv, Material.IRON_INGOT, 2, "&7&lContacten", "contacten");
            setItem(inv, Material.IRON_INGOT, 3, "&6&lING", "ing");
            if(Config.getCustomConfig2().getString("Players." + p.getUniqueId() + ".HeeftEenBericht") == "true"){
                setItem(inv, Material.IRON_INGOT, 4, "&a&lWhatsapp", "whatsapp1");
            }
            else{
                setItem(inv, Material.IRON_INGOT, 4, "&a&lWhatsapp", "whatsapp");
            }
            if (Bukkit.getPluginManager().getPlugin("GPS") != null) {
                if(Bukkit.getPluginManager().getPlugin("GPS").isEnabled()){
                    if(Main.gpsapi.gpsIsActive(p)){
                        setItem(inv, Material.IRON_INGOT, 5, "&e&lStop GPS", "stopgps");
                    }
                    else {
                        setItem(inv, Material.IRON_INGOT, 5, "&e&lGoogleMaps", "googlemaps");
                    }
                }
            }
            else {
                setItem(inv, Material.IRON_INGOT, 5, "&e&lGoogleMaps", "googlemaps");
            }
            if(Config.getCustomConfig2().getString("Players." + p.getUniqueId() + ".DarkWeb") == "true"){
                setItem(inv, Material.IRON_INGOT, 6, "&8&lDarkWeb", "darkweb");
            }
            else{
                setItemWithLore(inv, Material.IRON_INGOT, 6, "&8&lDarkWeb &c&l[LOCKED]", "&7(Unlock €15.000)", "darkweb-locked");
            }
        }

        p.closeInventory();
        p.openInventory(inv);
    }

    public static void setItemWithLore(Inventory inv, Material mat, Integer slotnum, String name, String Lore, String nbteNaam){
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Logger.color(name));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(Logger.color(Lore));
        meta.setLore(lore);
        item.setItemMeta(meta);

        NBTItem nbti = new NBTItem(item);
        nbti.setString("mtcustom", nbteNaam);

        ItemStack item1 = nbti.getItem();

        inv.setItem(slotnum, item1);
    }

    public static void setItem(Inventory inv, Material mat, Integer slotnum, String name, String nbteNaam){
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Logger.color(name));
        item.setItemMeta(meta);

        NBTItem nbti = new NBTItem(item);
        nbti.setString("mtcustom", nbteNaam);

        ItemStack item1 = nbti.getItem();

        inv.setItem(slotnum, item1);
    }
}
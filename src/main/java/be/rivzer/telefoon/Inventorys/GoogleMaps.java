package be.rivzer.telefoon.Inventorys;

import be.rivzer.telefoon.Config.Config;
import be.rivzer.telefoon.Logger;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class GoogleMaps implements Listener {

    public static void OpenTelefoonGoogleMaps(Player p){
        Inventory inv = Bukkit.createInventory(p, InventoryType.SHULKER_BOX, Logger.color("&0."));

        List<String> itemlijst = Config.getCustomConfig1().getStringList("GPSList");
        String[] items2 = (String[])itemlijst.toArray(new String[0]);
        String[] var12 = items2;
        int var15 = items2.length;

        for(int i = 0; i < var15; ++i) {
            String naam = var12[i];

            setItem(inv, Material.IRON_INGOT, (i+2), "&e&l" + naam, Config.getCustomConfig1().getString("GPS." + naam + ".NBT"));
        }

        setItem(inv, Material.IRON_INGOT, 10, "&9&lTerug", "back");

        p.closeInventory();
        p.openInventory(inv);
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
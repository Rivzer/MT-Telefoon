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

import java.util.ArrayList;
import java.util.List;

public class Whatsapp_BERICHTEN implements Listener {

    private static int positie = 1;

    public static void OpenTelefoonWhatsapp2(Player p){
        Inventory inv = Bukkit.createInventory(p, InventoryType.SHULKER_BOX, Logger.color("&0."));

        List<String> itemlijst2 = Config.getCustomConfig2().getStringList("Players." + p.getUniqueId() + ".Berichten");
        String[] items2 = (String[])itemlijst2.toArray(new String[0]);
        String[] var12 = items2;
        int var15 = items2.length;
        positie = 1;

        for(int i = 0; i < var15; ++i) {
            positie++;

            String list = var12[i];

            if(positie == 7){
                nextRij();
            }

            if(positie == 16){
                nextRij();
            }

            String brief = Config.getCustomConfig2().getString("Players." + p.getUniqueId() + ".Messages." + list + ".Brief");

            if(var15 > 15){
                for(int j = 0; j < 15; ++j) {
                    setItem(inv, Material.PAPER, positie, "&b&l" + list, "brief", brief);
                }
            }
            else {
                setItem(inv, Material.PAPER, positie, "&b&l" + list, "brief", brief);
            }
        }

        setItemWithNoLore(inv, Material.IRON_INGOT, 10, "&9&lTerug", "back");

        p.closeInventory();
        p.openInventory(inv);
    }

    public static void nextRij(){
        positie = (positie + 4);
    }

    public static void setItemWithNoLore(Inventory inv, Material mat, Integer slotnum, String name, String nbteNaam){
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Logger.color(name));
        item.setItemMeta(meta);

        NBTItem nbti = new NBTItem(item);
        nbti.setString("mtcustom", nbteNaam);

        ItemStack item1 = nbti.getItem();

        inv.setItem(slotnum, item1);
    }

    public static void setItem(Inventory inv, Material mat, Integer slotnum, String name, String nbteNaam, String briefDetails){
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Logger.color(name));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(Logger.color("&7" + briefDetails));
        lore.add(Logger.color("&7(Rechter Klik Om Te Markeren Als Gelezen)"));
        lore.add(Logger.color("&7(Linker Klik Om Te Lezen)"));
        meta.setLore(lore);
        item.setItemMeta(meta);

        NBTItem nbti = new NBTItem(item);
        nbti.setString("mtcustom", nbteNaam);

        ItemStack item1 = nbti.getItem();

        inv.setItem(slotnum, item1);
    }
}
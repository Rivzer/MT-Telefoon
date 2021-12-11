package be.rivzer.telefoon.Inventorys;

import be.rivzer.telefoon.Config.Config;
import be.rivzer.telefoon.Logger;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class ING_TRANSACTION_HISTORY {
    //2 11 20
    //6 15 24 - Gebruik +1 24 vanaf volgende pagina

    private static int positie = 1;

    public static void OpenTelefoonING3(Player p){
        Inventory inv = Bukkit.createInventory(p, InventoryType.SHULKER_BOX, Logger.color("&0."));

        positie = 1;

        List<String> itemlijst2 = Config.getCustomConfig2().getStringList("Players." + p.getUniqueId() + ".Transactions");
        String[] items2 = (String[])itemlijst2.toArray(new String[0]);
        int var15 = items2.length;

        makePage(p, 1, inv, var15, items2);
    }

    public static void makePage(Player p, int pageNum, Inventory inv, int var, String[] list){
        String[] var12 = list;

        if(var > 15){
            for(int i = 0; i < 15; ++i) {
                positie++;

                if(positie == 7){
                    nextRij();
                }

                if(positie == 16){
                    nextRij();
                }

                setItem(p, inv, Material.SKULL_ITEM, positie, "&6&l" + var12[i], "transaction", Config.getCustomConfig2().getInt("Players." + p.getUniqueId() + ".TransactionsWeight." + var12[i] + ".Bedrag"), Config.getCustomConfig2().getString("Players." + p.getUniqueId() + ".TransactionsWeight." + var12[i] + ".VanUUID"), Config.getCustomConfig2().getString("Players." + p.getUniqueId() + ".TransactionsWeight." + var12[i] + ".HeeftTransactieGemaakt"));
            }
        }
        else {
            for(int i = 0; i < var; ++i) {
                positie++;

                if(positie == 7){
                    nextRij();
                }

                if(positie == 16){
                    nextRij();
                }

                setItem(p, inv, Material.SKULL_ITEM, positie, "&6&l" + var12[i], "transaction", Config.getCustomConfig2().getInt("Players." + p.getUniqueId() + ".TransactionsWeight." + var12[i] + ".Bedrag"), Config.getCustomConfig2().getString("Players." + p.getUniqueId() + ".TransactionsWeight." + var12[i] + ".VanUUID"), Config.getCustomConfig2().getString("Players." + p.getUniqueId() + ".TransactionsWeight." + var12[i] + ".HeeftTransactieGemaakt"));
            }
        }

        setItemWithoutBedrag(inv, Material.IRON_INGOT, 10, "&9&lTerug", "back");

        p.closeInventory();
        p.openInventory(inv);
    }

    public static void nextRij(){
        positie = (positie + 4);
    }

    public static void setItemWithoutBedrag(Inventory inv, Material mat, Integer slotnum, String name, String nbteNaam){
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(Logger.color(name));
        item.setItemMeta(meta);

        NBTItem nbti = new NBTItem(item);
        nbti.setString("mtcustom", nbteNaam);

        ItemStack item1 = nbti.getItem();

        inv.setItem(slotnum, item1);
    }

    public static void setItem(Player p, Inventory inv, Material mat, Integer slotnum, String name, String nbteNaam, int bedrag, String senderUUID, String makerUUID){
        String budName = Config.getCustomConfig2().getString("Players." + senderUUID + ".UserName");

        ItemStack head = new ItemStack(mat, 1, (short) 3);
        ItemMeta meta = head.getItemMeta();
        meta.setDisplayName(Logger.color(name));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(Logger.color("&7Bedrag: &f€" + bedrag));
        if(makerUUID.equalsIgnoreCase(p.getUniqueId().toString())){
            lore.add(Logger.color("&7Naar: &f" + budName));
        }
        else {
            lore.add(Logger.color("&7Van: &f" + budName));
        }
        meta.setLore(lore);
        head.setItemMeta(meta);

        NBTItem nbti = new NBTItem(head);
        NBTCompound skull = nbti.addCompound("SkullOwner");
        skull.setString("Name", budName);
        nbti.setString("mtcustom", nbteNaam);

        head = nbti.getItem();

        inv.setItem(slotnum, head);
    }

}

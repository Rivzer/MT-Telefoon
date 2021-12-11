package be.rivzer.telefoon.Inventorys;

import be.rivzer.telefoon.Config.Config;
import be.rivzer.telefoon.Logger;
import de.tr7zw.nbtapi.NBTCompound;
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

public class Whatsapp_NIEUW_BERICHT implements Listener {

    private static int positie = 1;

    public static void OpenTelefoonWhatsapp3(Player p){
        Inventory inv = Bukkit.createInventory(p, InventoryType.SHULKER_BOX, Logger.color("&0."));

        List<String> itemlijst = Config.getCustomConfig2().getStringList("Players." + p.getUniqueId() + ".Contacts");
        List<String> itemlijst2 = Config.getCustomConfig2().getStringList("UUIDList");
        String[] items2 = (String[])itemlijst2.toArray(new String[0]);
        String[] var12 = items2;
        int var15 = items2.length;
        positie = 1;

        for(int i = 0; i < var15; ++i) {
            String uuid = var12[i];

            if(itemlijst.contains(Config.getCustomConfig2().getString("Players." + uuid + ".Nummer"))){
                positie++;

                String contactName = Config.getCustomConfig2().getString("Players." + uuid + ".UserName");
                String contactNumber = Config.getCustomConfig2().getString("Players." + uuid + ".Nummer");
                String contactUuid = uuid;

                if(positie == 7){
                    nextRij();
                }

                if(positie == 16){
                    nextRij();
                }

                if(var15 > 15){
                    for(int j = 0; j < 15; ++j) {
                        setItemSkull(inv, Material.SKULL_ITEM, contactNumber, positie, contactName, contactUuid, "contactskull");
                    }
                }
                else {
                    setItemSkull(inv, Material.SKULL_ITEM, contactNumber, positie, contactName, contactUuid, "contactskull");
                }
            }
        }

        setItem(inv, Material.IRON_INGOT, 10, "&9&lTerug", "back");

        p.closeInventory();
        p.openInventory(inv);
    }

    public static void nextRij(){
        positie = (positie + 4);
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

    public static void setItemSkull(Inventory inv, Material mat, String nummer, Integer slotnum, String contactName, String contactUuid, String nbteNaam){
        ItemStack head = new ItemStack(mat, 1, (short) 3);
        ItemMeta meta = head.getItemMeta();
        meta.setDisplayName(Logger.color("&f&l" + contactName));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(Logger.color("&6" + nummer));
        lore.add(Logger.color("&7(Klik Om Bericht Te Sturen)"));
        meta.setLore(lore);
        head.setItemMeta(meta);

        NBTItem nbti = new NBTItem(head);
        NBTCompound skull = nbti.addCompound("SkullOwner");
        skull.setString("Name", contactName);
        skull.setString("Id", contactUuid);
        nbti.setString("mtcustom", nbteNaam);

        head = nbti.getItem();

        inv.setItem(slotnum, head);
    }
}
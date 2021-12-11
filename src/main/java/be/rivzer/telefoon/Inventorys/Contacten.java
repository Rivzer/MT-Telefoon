package be.rivzer.telefoon.Inventorys;

import be.rivzer.telefoon.Config.Config;
import be.rivzer.telefoon.Logger;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Contacten implements Listener {

    private static int positie = 1;

    public static void OpenTelefoonContacten(Player p){
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
                        setItemSkull(inv, Material.SKULL_ITEM, contactNumber, positie, contactName, contactUuid, "contactskull", p);
                    }
                }
                else {
                    setItemSkull(inv, Material.SKULL_ITEM, contactNumber, positie, contactName, contactUuid, "contactskull", p);
                }
            }
        }

        setItem(inv, Material.IRON_INGOT, 24, "&3&lAdd", "addcontact");

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

    public static void setItemSkull(Inventory inv, Material mat, String nummer, Integer slotnum, String contactName, String contactUuid, String nbteNaam, Player player){
        Player buddy = Bukkit.getPlayer(contactName);
        Player p = player;

        ItemStack head = new ItemStack(mat, 1, (short) 3);
        ItemMeta meta = head.getItemMeta();
        meta.setDisplayName(Logger.color("&f&l" + contactName));
        ArrayList<String> lore = new ArrayList<>();
        lore.add(Logger.color("&6" + nummer));

        if(buddy == null){
            lore.add(Logger.color("&6∞ block's verwijderd van &f" + contactName));
        }
        else {
            Location locaB = buddy.getLocation();
            Location locaP = p.getLocation();
            int blocks = (int) locaB.distance(locaP);
            DecimalFormat format = new DecimalFormat("0");

            meta.setDisplayName(Logger.color("&f&l" + contactName));
            lore.add(Logger.color("&6" + nummer));

            if(Config.getCustomConfig2().getString("Players." + buddy.getUniqueId() + ".ShowLocation") == "true"){
                if(Integer.parseInt(format.format(blocks)) == 1){
                    lore.add(Logger.color("&6" + format.format(blocks) + " block verwijderd van &f" + contactName));
                }
                else {
                    lore.add(Logger.color("&6" + format.format(blocks) + " blocks verwijderd van &f" + contactName));
                }
            }
        }
        lore.add(Logger.color("&7(Shift-Klik Verwijderen)"));
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
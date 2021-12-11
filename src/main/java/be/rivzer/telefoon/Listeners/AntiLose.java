package be.rivzer.telefoon.Listeners;

import be.rivzer.telefoon.Config.Config;
import be.rivzer.telefoon.Main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class AntiLose implements Listener {

    private Main plugin = Main.getPlugin(Main.class);

    @EventHandler
    public void onSwitch(PlayerSwapHandItemsEvent e){
        Player p = e.getPlayer();

        ItemStack itemStack = p.getInventory().getItemInMainHand();

        if(itemStack.getType().equals(Material.BLAZE_POWDER)) e.setCancelled(true);
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e){
        Player p = (Player) e.getPlayer();

        if(e.getItemDrop() == null) return;
        if(e.getItemDrop().getItemStack() == null) return;
        if(e.getItemDrop().getItemStack().getItemMeta() == null) return;
        if(e.getItemDrop().getItemStack().getItemMeta().getDisplayName() == null) return;
        if(e.getItemDrop().getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase(Config.getCustomConfig2().getString("Players." + p.getUniqueId() + ".Nummer")) && e.getItemDrop().getItemStack().getType().equals(Material.BLAZE_POWDER)){
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void onPickUp(PlayerPickupItemEvent e){
        Player p = (Player) e.getPlayer();

        if(e.getItem() == null) return;
        if(e.getItem().getItemStack() == null) return;
        if(e.getItem().getItemStack().getItemMeta() == null) return;
        if(e.getItem().getItemStack().getItemMeta().getDisplayName() == null) return;
        if(e.getItem().getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase(Config.getCustomConfig2().getString("Players." + p.getUniqueId() + ".Nummer")) && e.getItem().getItemStack().getType().equals(Material.BLAZE_POWDER)){
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e){
        Player p = e.getPlayer();

        if(p.getGameMode() == GameMode.CREATIVE) return;

        String nummer = Config.getCustomConfig2().getString("Players." + p.getUniqueId() + ".Nummer");
        if(nummer == null) return;

        if(Bukkit.getPluginManager().getPlugin("2FA-Plus") != null){
            if(Bukkit.getPluginManager().getPlugin("2FA-Plus").isEnabled()){
                if(p.hasPermission("2fa.use")){
                    if(FA1.map2.containsKey(p.getUniqueId())){
                        if(FA1.map2.get(p.getUniqueId()) == true){
                            if(FA1.map.containsKey(p.getUniqueId())){
                                if(FA1.map.get(p.getUniqueId()) == true){
                                    if(p.getInventory().getItem(8) == null){
                                        ItemStack telefoon = new ItemStack(Material.BLAZE_POWDER);
                                        ItemMeta telefoonmeta = telefoon.getItemMeta();
                                        telefoonmeta.setDisplayName(nummer);
                                        telefoon.setItemMeta(telefoonmeta);

                                        p.getInventory().setItem(8, telefoon);
                                    }
                                    else if(!p.getInventory().getItem(8).equals(Material.BLAZE_POWDER)) {
                                        ItemStack telefoon = new ItemStack(Material.BLAZE_POWDER);
                                        ItemMeta telefoonmeta = telefoon.getItemMeta();
                                        telefoonmeta.setDisplayName(nummer);
                                        telefoon.setItemMeta(telefoonmeta);

                                        p.getInventory().setItem(8, telefoon);
                                    }
                                }
                            }
                        }
                    }
                }
                else {
                    if(p.getInventory().getItem(8) == null){
                        ItemStack telefoon = new ItemStack(Material.BLAZE_POWDER);
                        ItemMeta telefoonmeta = telefoon.getItemMeta();
                        telefoonmeta.setDisplayName(nummer);
                        telefoon.setItemMeta(telefoonmeta);

                        p.getInventory().setItem(8, telefoon);
                    }
                    else if(!p.getInventory().getItem(8).equals(Material.BLAZE_POWDER)) {
                        ItemStack telefoon = new ItemStack(Material.BLAZE_POWDER);
                        ItemMeta telefoonmeta = telefoon.getItemMeta();
                        telefoonmeta.setDisplayName(nummer);
                        telefoon.setItemMeta(telefoonmeta);

                        p.getInventory().setItem(8, telefoon);
                    }
                }
            }
            else {
                if(p.getInventory().getItem(8) == null){
                    ItemStack telefoon = new ItemStack(Material.BLAZE_POWDER);
                    ItemMeta telefoonmeta = telefoon.getItemMeta();
                    telefoonmeta.setDisplayName(nummer);
                    telefoon.setItemMeta(telefoonmeta);

                    p.getInventory().setItem(8, telefoon);
                }
                else if(!p.getInventory().getItem(8).equals(Material.BLAZE_POWDER)) {
                    ItemStack telefoon = new ItemStack(Material.BLAZE_POWDER);
                    ItemMeta telefoonmeta = telefoon.getItemMeta();
                    telefoonmeta.setDisplayName(nummer);
                    telefoon.setItemMeta(telefoonmeta);

                    p.getInventory().setItem(8, telefoon);
                }
            }
        }
        else {
            if(p.getInventory().getItem(8) == null){
                ItemStack telefoon = new ItemStack(Material.BLAZE_POWDER);
                ItemMeta telefoonmeta = telefoon.getItemMeta();
                telefoonmeta.setDisplayName(nummer);
                telefoon.setItemMeta(telefoonmeta);

                p.getInventory().setItem(8, telefoon);
            }
            else if(!p.getInventory().getItem(8).equals(Material.BLAZE_POWDER)) {
                ItemStack telefoon = new ItemStack(Material.BLAZE_POWDER);
                ItemMeta telefoonmeta = telefoon.getItemMeta();
                telefoonmeta.setDisplayName(nummer);
                telefoon.setItemMeta(telefoonmeta);

                p.getInventory().setItem(8, telefoon);
            }
        }
    }

    @EventHandler
    public void onInvMove(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();

        if(p.getGameMode() == GameMode.CREATIVE) return;

        if(e.getCurrentItem() == null) return;
        if(e.getCurrentItem().getItemMeta() == null) return;
        if(e.getCurrentItem().getItemMeta().getDisplayName() == null) return;
        if(!e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase(Config.getCustomConfig2().getString("Players." + p.getUniqueId() + ".Nummer"))) return;
        if(!e.getCurrentItem().getType().equals(Material.BLAZE_POWDER)) return;

        e.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInventoryInteract(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();

        if(p.getGameMode() == GameMode.CREATIVE) return;

        String nummer = Config.getCustomConfig2().getString("Players." + p.getUniqueId() + ".Nummer");
        if(nummer == null) return;

        if(Bukkit.getPluginManager().getPlugin("2FA-Plus") != null) {
            if (Bukkit.getPluginManager().getPlugin("2FA-Plus").isEnabled()) {
                if (p.hasPermission("2fa.use")) {
                    if (FA1.map2.containsKey(p.getUniqueId())) {
                        if (FA1.map2.get(p.getUniqueId()) == true) {
                            if (FA1.map.containsKey(p.getUniqueId())) {
                                if (FA1.map.get(p.getUniqueId()) == true) {
                                    if (p.getInventory().getItem(8) == null) {
                                        ItemStack telefoon = new ItemStack(Material.BLAZE_POWDER);
                                        ItemMeta telefoonmeta = telefoon.getItemMeta();
                                        telefoonmeta.setDisplayName(nummer);
                                        telefoon.setItemMeta(telefoonmeta);

                                        p.getInventory().setItem(8, telefoon);
                                    } else if (!p.getInventory().getItem(8).equals(Material.BLAZE_POWDER)) {
                                        ItemStack telefoon = new ItemStack(Material.BLAZE_POWDER);
                                        ItemMeta telefoonmeta = telefoon.getItemMeta();
                                        telefoonmeta.setDisplayName(nummer);
                                        telefoon.setItemMeta(telefoonmeta);

                                        p.getInventory().setItem(8, telefoon);
                                    }

                                    return;
                                }
                            }
                        }
                    }

                    if (p.getInventory().getItem(8) == null) {
                        ItemStack telefoon = new ItemStack(Material.BLAZE_POWDER);
                        ItemMeta telefoonmeta = telefoon.getItemMeta();
                        telefoonmeta.setDisplayName(nummer);
                        telefoon.setItemMeta(telefoonmeta);

                        p.getInventory().setItem(8, telefoon);
                    } else if (!p.getInventory().getItem(8).equals(Material.BLAZE_POWDER)) {
                        ItemStack telefoon = new ItemStack(Material.BLAZE_POWDER);
                        ItemMeta telefoonmeta = telefoon.getItemMeta();
                        telefoonmeta.setDisplayName(nummer);
                        telefoon.setItemMeta(telefoonmeta);

                        p.getInventory().setItem(8, telefoon);
                    }
                } else {
                    if (p.getInventory().getItem(8) == null) {
                        ItemStack telefoon = new ItemStack(Material.BLAZE_POWDER);
                        ItemMeta telefoonmeta = telefoon.getItemMeta();
                        telefoonmeta.setDisplayName(nummer);
                        telefoon.setItemMeta(telefoonmeta);

                        p.getInventory().setItem(8, telefoon);
                    } else if (!p.getInventory().getItem(8).equals(Material.BLAZE_POWDER)) {
                        ItemStack telefoon = new ItemStack(Material.BLAZE_POWDER);
                        ItemMeta telefoonmeta = telefoon.getItemMeta();
                        telefoonmeta.setDisplayName(nummer);
                        telefoon.setItemMeta(telefoonmeta);

                        p.getInventory().setItem(8, telefoon);
                    }
                }
            }
            else {
                if (p.getInventory().getItem(8) == null) {
                    ItemStack telefoon = new ItemStack(Material.BLAZE_POWDER);
                    ItemMeta telefoonmeta = telefoon.getItemMeta();
                    telefoonmeta.setDisplayName(nummer);
                    telefoon.setItemMeta(telefoonmeta);

                    p.getInventory().setItem(8, telefoon);
                } else if (!p.getInventory().getItem(8).equals(Material.BLAZE_POWDER)) {
                    ItemStack telefoon = new ItemStack(Material.BLAZE_POWDER);
                    ItemMeta telefoonmeta = telefoon.getItemMeta();
                    telefoonmeta.setDisplayName(nummer);
                    telefoon.setItemMeta(telefoonmeta);

                    p.getInventory().setItem(8, telefoon);
                }
            }
        }
        else {
            if (p.getInventory().getItem(8) == null) {
                ItemStack telefoon = new ItemStack(Material.BLAZE_POWDER);
                ItemMeta telefoonmeta = telefoon.getItemMeta();
                telefoonmeta.setDisplayName(nummer);
                telefoon.setItemMeta(telefoonmeta);

                p.getInventory().setItem(8, telefoon);
            } else if (!p.getInventory().getItem(8).equals(Material.BLAZE_POWDER)) {
                ItemStack telefoon = new ItemStack(Material.BLAZE_POWDER);
                ItemMeta telefoonmeta = telefoon.getItemMeta();
                telefoonmeta.setDisplayName(nummer);
                telefoon.setItemMeta(telefoonmeta);

                p.getInventory().setItem(8, telefoon);
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){

        if(e.getDrops() == null) return;
        List<ItemStack> list = e.getDrops();
        Iterator<ItemStack> i = list.iterator();
        Player p = (Player) e.getEntity();

        while(i.hasNext()){
            ItemStack item = i.next();
            if(item.getItemMeta().getDisplayName().equalsIgnoreCase(Config.getCustomConfig2().getString("Players." + p.getUniqueId() + ".Nummer")) && item.getType().equals(Material.BLAZE_POWDER)){
                i.remove();
            }
        }
    }

}

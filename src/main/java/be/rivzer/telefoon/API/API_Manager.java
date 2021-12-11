package be.rivzer.telefoon.API;

import be.rivzer.telefoon.Config.Config;
import org.bukkit.entity.Player;

public class API_Manager {

    public static String krijgTelefoonNummer(Player p){
        if(p == null) return "Er is iets fout gegaan probeer het opnieuw!";
        if(Config.getCustomConfig2().getString("Players." + p.getUniqueId() + ".Nummer") != null){
            return Config.getCustomConfig2().getString("Players." + p.getUniqueId() + ".Nummer");
        }
        else{
            return "Er was geen telefoon nummer gevonden voor de speler " + p.getName();
        }
    }

}

package be.rivzer.telefoon.API;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TransactionsEvents extends Event {

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    Player p;
    int bedrag;

    public TransactionsEvents(Player p, int bedrag){
        this.p = p;
        this.bedrag = bedrag;
    }

    public Player getPlayer(){
        return p;
    }

    public int getBedrag(){
        return bedrag;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

}

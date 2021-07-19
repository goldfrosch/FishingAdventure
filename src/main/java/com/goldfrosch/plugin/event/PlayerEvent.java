package com.goldfrosch.plugin.event;

import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

public class PlayerEvent implements Listener {
  @EventHandler
  public void onPlayerFishingEvent(PlayerFishEvent e){
    if(e.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)){

      final Item fish = (Item) e.getCaught();
      fish.remove();

      int percent = (int)(Math.random()*100 + 1);
      if(percent <= 60) {
        e.getPlayer().sendMessage("test");
      }
    }
  }
}
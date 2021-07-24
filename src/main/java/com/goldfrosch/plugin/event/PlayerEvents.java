package com.goldfrosch.plugin.event;

import com.goldfrosch.plugin.MainPlugin;
import com.goldfrosch.plugin.items.ItemsList;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;

import java.util.ArrayList;

public class PlayerEvents implements Listener {
  private MainPlugin plugin;

  public PlayerEvents(MainPlugin plugin){
    this.plugin = plugin;
  }

  @EventHandler
  public void onPlayerFishingEvent(PlayerFishEvent e){
    if(e.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)){
      final Item fish = (Item) e.getCaught();
      fish.remove();

      int enchantLevel;
      try {
        enchantLevel = e.getPlayer().getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LUCK);
      } catch (Exception error) {
        enchantLevel = 0;
      }

      String rank[] = {"C","B","A","S"};
      ArrayList<Double> rankPercent = new ArrayList<Double>();
      for(int i = 0;i < 4; i++) {
        if(i == 0){
          rankPercent.add(plugin.getConfig().getDouble("FishingAdventure.RankPercent." + enchantLevel + "." + rank[i]));
        }
        else {
          rankPercent.add(rankPercent.get(i - 1) + plugin.getConfig().getDouble("FishingAdventure.RankPercent." + enchantLevel + "." + rank[i]));
        }
      }
      ItemsList item = new ItemsList(plugin);
      int percent = (int)(Math.random() * 100 + 1);

      String rankPrefix = "FishingAdventure.RankPrefix.";
      String currentRank = "";
      if(percent <= rankPercent.get(0)) {
        currentRank = "C";
      }
      else if(percent <= rankPercent.get(1)) {
        currentRank = "B";
      }
      else if(percent <= rankPercent.get(2)) {
        currentRank = "A";
      }
      else {
        currentRank = "S";
      }

      e.getPlayer().sendMessage(item.getItemsName(currentRank,0));
    }
  }
}

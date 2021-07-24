package com.goldfrosch.plugin.items;

import com.goldfrosch.plugin.MainPlugin;

public class ItemsList {
  private MainPlugin plugin;

  public ItemsList(MainPlugin plugin){
    this.plugin = plugin;
  }

  String rankPrefix = "FishingAdventure.RankPrefix.";

  public String getItemsName(String rank,int number) {
    return plugin.replaceText(plugin.getConfig().getString(rankPrefix + rank) + plugin.getItemsConfig().getString("Items." + rank + "." + number + ".name"));
  }
}

package com.goldfrosch.plugin.items;

import com.goldfrosch.plugin.MainPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ItemsList {
  private MainPlugin plugin;
  public ItemsList(MainPlugin plugin){
    this.plugin = plugin;
  }

  String rankPrefix = "FishingAdventure.RankPrefix.";

  public int getRandomItemNumber(String Rank) {
    Set<String> itemArray = plugin.getItemsConfig().getConfigurationSection("Items." + Rank).getKeys(false);
    int answer = (int) Math.floor(Math.random() * itemArray.size());
    return answer;
  }

  public String getRandomItemsMaterial(String rank,int number) {
    return plugin.getStringItemsList("Items." + rank + "." + number + ".material");
  }

  public String getRandomItemsName(String rank,int number) {
    return plugin.replaceText(plugin.getStringConfig(rankPrefix + rank) + " " + plugin. getStringItemsList("Items." + rank + "." + number + ".name"));
  }

}

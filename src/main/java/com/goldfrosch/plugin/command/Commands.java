package com.goldfrosch.plugin.command;

import com.goldfrosch.plugin.MainPlugin;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Set;

public class Commands extends AbstractCommand{
  public Commands(MainPlugin plugin, String Command) {
    super(plugin,Command);
  }

  @Override
  public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String alias, String[] args) {
    return null;
  }

  @Override
  public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
    String prefix = plugin.getConfig().getString("FishingAdventure.Message.Prefix").replace("&", "§");
    if (sender instanceof Player) {
      Player player = (Player) sender;
      if(label.equalsIgnoreCase("fishing")){
        if(args.length == 0){
          player.sendMessage(prefix + "TEST");
        }
        else {
          if(args[0].equalsIgnoreCase("help")){
            player.sendMessage(prefix + "/fishing help => 도움말을 확인합니다");
            player.sendMessage(prefix + "/fishing save 등급(C,B,A,S) => 손에 들고 있는 아이템을 저장합니다");
          }
          else if(args[0].equalsIgnoreCase("save")){
            String[] RankList = {"C","B","A","S"};
            if(args[1] == null){
              player.sendMessage(prefix + "등급을 입력해주세요");
              player.sendMessage(prefix + "사용법: /fishing save 등급(C,B,A,S)");
            }
            else {
              String currentRank = args[1].toUpperCase();
              
              boolean hasRank = false;
              for(int i = 0;i < RankList.length;i++){
                if(currentRank.equals(RankList[i])){
                  hasRank = true;
                  break;
                }
              }

              Set<String> itemListSize = plugin.getItemsConfig().getConfigurationSection("Items." + currentRank).getKeys(false);
              ItemStack playerItem = player.getInventory().getItemInMainHand();
              ItemMeta playerItemMeta = playerItem.getItemMeta();

              String saveName = playerItemMeta.getDisplayName();
              for(int i = 0;i < RankList.length;i++){
                saveName.replace(plugin.getStringConfig("FishingAdventure.RankPrefix." + RankList[i]) + " ","");
              }
              saveName.replace("§", "&");

              if(hasRank) {
//                plugin.getItemsConfig().set("Items." + currentRank + "." + itemListSize.size() + ".material",String.valueOf(playerItem.getType()));
//                player.sendMessage("Items." + currentRank + "." + itemListSize.size() + ".material");
//                plugin.getItemsConfig().set("Items." + currentRank + "." + itemListSize.size() + ".name",saveName);
//                player.sendMessage("Items." + currentRank + "." + itemListSize.size() + ".name");
//
//                plugin.saveResource("items.yml", true);
                player.sendMessage("현재 개발중인 기능입니다!");
              }
              else {
                player.sendMessage(prefix + "잘못된 등급입니다");
                player.sendMessage(prefix + "사용법: /fishing save 등급(C,B,A,S)");
              }
            }
          }
        }
      }
    }
    return false;
  }
}

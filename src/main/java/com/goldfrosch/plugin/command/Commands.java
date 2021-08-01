package com.goldfrosch.plugin.command;

import com.goldfrosch.plugin.MainPlugin;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
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

  public void checkPerm(String prefix, boolean check, String[] res, Player player){
    if(check){
      for(int i = 0;i < res.length;i++) {
        player.sendMessage(prefix + res[i]);
      }
    }
    else {
      player.sendMessage(prefix + "권한이 없습니다");
    }
  }

  @Override
  public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
    String prefix = plugin.getConfig().getString("FishingAdventure.Message.Prefix").replace("&", "§");
    if (sender instanceof Player) {
      Player player = (Player) sender;
      boolean hasPerm = player.hasPermission("fishingAdventure.op");
      String[] responseCmd;
      if(label.equalsIgnoreCase("fishing")){
        if(args.length == 0){
          player.sendMessage(prefix + "TEST");
        }
        else {
          if(args[0].equalsIgnoreCase("help")){
            responseCmd = new String[]{ "/fishing help => 도움말을 확인합니다", "/fishing save 등급(C,B,A,S) => 손에 들고 있는 아이템을 저장합니다" };
            checkPerm(prefix,hasPerm,responseCmd,player);
          }

          else if(args[0].equalsIgnoreCase("reload")) {
            responseCmd = new String[]{"성공적으로 리로드했습니다"};
            checkPerm(prefix,hasPerm,responseCmd,player);
            if(hasPerm) {
              super.plugin.reloadConfig();
              plugin.saveDefaultConfig();
              plugin.getConfig().options().copyDefaults(true);
              plugin.saveConfig();

              plugin.reloadItemsConfig();
            }
          }

          else if(args[0].equalsIgnoreCase("save")){
            if(hasPerm){
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

                if(hasRank) {
                  plugin.getItemsConfig().set("Items." + currentRank + "." + itemListSize.size() + ".material",String.valueOf(playerItem.getType()));
                  plugin.getItemsConfig().set("Items." + currentRank + "." + itemListSize.size() + ".name",saveName.replace("§", "&"));

                  plugin.getItemsConfig().set("Items." + currentRank + "." + itemListSize.size() + ".broadcast","&a테스트 브로드캐스팅");
                  plugin.getItemsConfig().set("Items." + currentRank + "." + itemListSize.size() + ".message","&a테스트 메세지");

                  plugin.saveItemsConfig();
                  plugin.reloadItemsConfig();
                  player.sendMessage(prefix + "성공적으로 저장되었습니다!");
                }
                else {
                  player.sendMessage(prefix + "잘못된 등급입니다");
                  player.sendMessage(prefix + "사용법: /fishing save 등급(C,B,A,S)");
                }
              }
            }
            else {
              player.sendMessage(prefix + "권한이 없습니다");
            }
          }
        }
      }
    }
    return false;
  }
}

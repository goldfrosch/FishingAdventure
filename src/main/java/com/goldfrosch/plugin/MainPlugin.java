package com.goldfrosch.plugin;

import com.goldfrosch.plugin.fishRank.Rank;
import org.bukkit.Bukkit;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class MainPlugin extends JavaPlugin implements Listener {
  PluginDescriptionFile pdfFile = this.getDescription();
  PluginManager pm = Bukkit.getPluginManager();
  String pfName = pdfFile.getName() + " v" + pdfFile.getVersion();

  private final File f = new File(getDataFolder(), "/items.yml");
  public void makeFile(File file){
    if(!file.exists() || !file.isFile()){
      try{
        file.createNewFile();
      }catch(IOException e){
        e.printStackTrace();
      }
    }
  }

  @Override
  public void onEnable(){
    //이벤트 사용 선언
    pm.registerEvents(this, this);

    //config파일 있는지 파악 후 생성
    if (!getDataFolder().exists()) {
      getConfig().options().copyDefaults(true);
      saveConfig();
    } else {
      saveConfig();
    }

    //data.yml 생성
    makeFile(f);
    consoleLog(pfName+"이 활성화 되었습니다");
    super.onEnable();
  }

  @Override
  public void onDisable(){
    consoleLog(pfName+"이 비활성화 되었습니다");
    super.onDisable();
  }

  @EventHandler
  public void onPlayerFishingEvent(PlayerFishEvent e){
    if(e.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)){

      final Item fish = (Item) e.getCaught();
      fish.remove();

      int percent = (int)(Math.random()*100 + 1);
      if(percent <= Rank.C.getRankPercent()){
        e.getPlayer().sendMessage(fishingResult(Rank.C,percent));
      }
      else if(percent <= Rank.B.getRankPercent()){
        e.getPlayer().sendMessage(fishingResult(Rank.B,percent));
      }
      else if(percent <= Rank.A.getRankPercent()){
        e.getPlayer().sendMessage(fishingResult(Rank.A,percent));
      }
      else{
        e.getPlayer().sendMessage(fishingResult(Rank.S,percent));
      }
    }
  }
  String fishingResult(Rank rank,int percent){
    return "낚은 품목: "+ rank + " / " + percent;
  }
  public void consoleLog(String msg){
    getLogger().info(msg);
  }
}

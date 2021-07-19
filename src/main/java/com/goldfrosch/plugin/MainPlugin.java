package com.goldfrosch.plugin;

import com.goldfrosch.plugin.event.PlayerEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
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
    getServer().getPluginManager().registerEvents(new PlayerEvent(),this);

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

  public void consoleLog(String msg){
    getLogger().info(msg);
  }
}

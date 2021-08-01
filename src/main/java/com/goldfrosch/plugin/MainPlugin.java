package com.goldfrosch.plugin;

import com.goldfrosch.plugin.command.Commands;
import com.goldfrosch.plugin.event.PlayerEvents;
import com.goldfrosch.plugin.items.ItemsList;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class MainPlugin extends JavaPlugin implements Listener {
  PluginDescriptionFile pdfFile = this.getDescription();
  String pfName = pdfFile.getName() + " v" + pdfFile.getVersion();

  private File itemsConfigFile;
  private FileConfiguration itemsConfig;

  private void createItemsConfig() {
    itemsConfigFile = new File(getDataFolder(), "items.yml");
    if (!itemsConfigFile.exists()) {
      itemsConfigFile.getParentFile().mkdirs();
      saveResource("items.yml", false);
    }

    itemsConfig= new YamlConfiguration();
    try {
      itemsConfig.load(itemsConfigFile);
    } catch (IOException | InvalidConfigurationException e) {
      e.printStackTrace();
    }
  }

  public FileConfiguration getItemsConfig() {
    return this.itemsConfig;
  }

  public void reloadItemsConfig() {
    if(itemsConfigFile == null) {
      itemsConfigFile = new File(getDataFolder(), "items.yml");
    }
    itemsConfig = YamlConfiguration.loadConfiguration(itemsConfigFile);
  }

  public void saveItemsConfig() {
    try {
      getItemsConfig().save(itemsConfigFile);
    }
    catch (IOException e) {
      consoleLog("서버 저장 에러 발생");
    }
  }

  @Override
  public void onEnable(){
    new ItemsList(this);

    Bukkit.getPluginManager().registerEvents(new PlayerEvents(this),this);
    //config파일 있는지 파악 후 생성
    if (!getDataFolder().exists()) {
      getConfig().options().copyDefaults(true);
      saveConfig();
    } else {
      saveConfig();
    }

    //items.yml파일 생성
    createItemsConfig();

    //command
    Commands cmd = new Commands(this,"fishing");
    getCommand(cmd.getCommand()).setExecutor(cmd);
    getCommand(cmd.getCommand()).setTabCompleter(cmd);

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

  public String getStringConfig(String path) {
    return getConfig().getString(path);
  }

  public String getStringItemsList(String path){
    return getItemsConfig().getString(path);
  }

  public String replaceText(String text){
    return text.replace("&", "§");
  }

  public String configReturnPlaceholder(String msg, Player p) {
    return msg.contains("%player%") ? msg.replace("%player",p.getName()) : msg;
  }

  public String Prefix = getConfig().getString("FishingAdventure.Message.Prefix").replace("&", "§");
}

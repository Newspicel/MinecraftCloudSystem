package net.kyrin.air.api.airkyrinapi.command;

import net.kyrin.air.api.airkyrinapi.AirKyrinAPI;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.zombiehausen.api.zombieapi.ZombieAPI;
import net.zombiehausen.api.zombieapi.lib.command.ZombieCommand;
import net.zombiehausen.api.zombieapi.lib.player.ZombiePlayer;
import net.zombiehausen.api.zombieapi.lib.player.permissions.ranks.Rank;
import net.zombiehausen.api.zombieapi.utils.messages.TextComponentBuilder;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class GetUUIDCommand extends ZombieCommand {

    private AirKyrinAPI airKyrinAPI;


    public GetUUIDCommand(AirKyrinAPI airKyrinAPI) {
        super(airKyrinAPI, "getUUID", 0, 0);
        this.airKyrinAPI = airKyrinAPI;
    }

    @Override
    public void execute(ZombiePlayer zombiePlayer, ArrayList<String> arrayList) {
        if (zombiePlayer.hasMinimumrank(Rank.HEAD_BUILDER)){
            String serverUUID = airKyrinAPI.getFileManager().getGlobalConfigManager().get().getServerUUID();
            TextComponent textComponent = new TextComponentBuilder("Die Server UUID ist: " + serverUUID).addPrefixToFront("Technik").addHover("Klick zum einfügen in den CHat").addClickEvent(ClickEvent.Action.SUGGEST_COMMAND, serverUUID).build();
            zombiePlayer.sendMessage(textComponent);
        }else {
            zombiePlayer.sendMessage(ZombieAPI.getInstance().getMessages().getNoPerm());
        }
    }

    @Override
    public void executeConsole(ConsoleCommandSender consoleCommandSender, ArrayList<String> arrayList) {
        System.out.println(airKyrinAPI.getFileManager().getGlobalConfigManager().get().getServerUUID());
    }

    @Override
    public void sendUsageMessage(ZombiePlayer zombiePlayer, ArrayList<String> arrayList) {}

    @Override
    public void sendUsageMessageConsole(ConsoleCommandSender consoleCommandSender, ArrayList<String> arrayList) {}
}

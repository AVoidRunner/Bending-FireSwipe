package me.MasterVrunner.FireSwipeHelp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAnimationEvent;

import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.ability.CoreAbility;

public class FireSwipeListener implements Listener{
	
	@EventHandler
    public void onPlayerAnimationEvent(PlayerAnimationEvent event) {

        Player player = event.getPlayer();
        BendingPlayer bPlayer = BendingPlayer.getBendingPlayer(player);

        if (event.isCancelled() || bPlayer == null) {
            return;
        } else if (bPlayer.canBend(CoreAbility.getAbility(FireSwipe.class)) && !CoreAbility.hasAbility(event.getPlayer(), FireSwipe.class)) {
            new FireSwipe (player);
        }
    }
	
	ArrayList<String> mylist = new ArrayList<String>(); 	
	Map<String,ArrayList<String>> multiMap = new HashMap<>();

	@EventHandler
	public void onLeavesDecayEvent(Block block) {

	}

}

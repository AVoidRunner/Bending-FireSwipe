package me.MasterVrunner.FireSwipeHelp;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector; 

import com.projectkorra.projectkorra.GeneralMethods;
import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.ability.AddonAbility;
import com.projectkorra.projectkorra.ability.FireAbility;
import com.projectkorra.projectkorra.util.ParticleEffect;

public class FireSwipe extends FireAbility implements AddonAbility {
	
	private Random rand = new Random();
	private ArrayList<Integer> angles = new ArrayList<Integer>();
	private double damage;
	private double range;
	private double range1;
	private double range2;
	private double speed;
	private Location startlocation;
	private Location location;
	private Vector direction;
	private Map<Integer, Vector> directions = new ConcurrentHashMap<>();
	private ArrayList<Location> locationarray = new ArrayList<Location>();
	private Map<Integer, ArrayList<Location>> locations1 = new ConcurrentHashMap<>();
	private Map<Integer, ArrayList<Location>> locations2 = new ConcurrentHashMap<>();
	private int arc;
	private int particles;
	private double stepSize;
	private long cooldown;

	public FireSwipe(Player player) {
		super(player);
		// TODO Auto-generated constructor stub
		
		startlocation = player.getEyeLocation();
		particles = 1;
		arc = 16;
		stepSize = 3;
		cooldown = 3000;
		damage = 3;
		speed = 25 * (ProjectKorra.time_step / 1000.0);
		range = 8;
		range1 = range/2;
		if(bPlayer.isOnCooldown(this)) {
			remove();
			return;
		}
		
		if(!bPlayer.canBend(this)) {
			remove();
			return;
		}
		
		player.sendMessage("Calling setFields()");
		
		setFields();
		
	}
	
	public void setFields() {
		bPlayer.addCooldown(this);
		
		startlocation = this.player.getEyeLocation();
		direction = player.getEyeLocation().getDirection();
		
		for(int i = -arc; i <= arc; i += stepSize) {
			directions.put(i, GeneralMethods.rotateXZ(direction.clone(), i));
			angles.add(i);
		}
		
		for(int angle : directions.keySet()) {
			Vector direction = directions.get(angle);
			while (location.distanceSquared(startlocation) <= range1 * range1) {
				location = location.clone().add(direction.multiply(speed));
				locationarray.add(location);
			}
			locations1.put(angle, locationarray);
			locationarray.clear();
		}
		
		start();
	}

	@Override
	public long getCooldown() {
		// TODO Auto-generated method stub
		return cooldown;
	}
	
	@Override
	public Location getLocation() {
		// TODO Auto-generated method stub
		return player.getLocation();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "FireSwipe";
	}

	@Override
	public boolean isHarmlessAbility() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSneakAbility() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void progress() {
		// TODO Auto-generated method stub
		if(!bPlayer.canBendIgnoreBindsCooldowns(this)) {
			remove();
			return;
		}
		
		if(!player.isOnline() || player.isDead()) {
			remove();
			return;
		}
		
		int random = rand.nextInt(angles.size());
		int angle = angles.get(random);
		
		ArrayList<Location> loc1 = locations1.get(angle);
		for(Location location : loc1) {
			ParticleEffect.CLOUD.display(location, 1, 1, 1, 1, particles);
			player.sendMessage("Displaying! ");
		}
	}
	
	@Override
	public void remove() {
		bPlayer.addCooldown(this);
		super.remove();
		return;
	}

	@Override
	public String getAuthor() {
		// TODO Auto-generated method stub
		return "ddicco";
	}

	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return "0.0.1";
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		ProjectKorra.plugin.getServer().getPluginManager().registerEvents(new FireSwipeListener(), ProjectKorra.plugin);
		ProjectKorra.log.info("Successfully enabled" + getName() + " by " + getAuthor() + " Version " + getVersion());
		
	}
	
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}
}
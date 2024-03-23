package snw.slowitem;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class SlowItem extends JavaPlugin {
    private static final int MAX_LEVEL = 127;
    private static final PotionEffect OVERFLOW_JUMP = new PotionEffect(PotionEffectType.JUMP,
            20, 128, false, false);

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getScheduler().runTaskTimer(this, () -> {
            for (Player player : getServer().getOnlinePlayers()) {
                int level = slowLevel(player);
                if (level > 0) {
                    PotionEffect effect = new PotionEffect(
                            PotionEffectType.SLOW, 20, level,
                            false, false);
                    player.addPotionEffect(effect);
                    player.addPotionEffect(OVERFLOW_JUMP);
                }
            }
        }, 0L, 20L);
    }

    // return: element in ([0, 4] and {255})
    private int slowLevel(Player player) {
        int hay = 0;
        for (ItemStack stack : player.getInventory()) {
            if (stack == null) {
                continue;
            }
            if (stack.getType() == Material.HAY_BLOCK) {
                hay += stack.getAmount();
            }
        }
        if (hay >= 5) {
            return MAX_LEVEL;
        } else {
            return hay;
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

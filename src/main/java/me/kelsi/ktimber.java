package me.kelsi;

import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.LinkedList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class ktimber extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        if (!e.getPlayer().isSneaking())
            if(e.getPlayer().hasPermission("timber.use"))
                if (isAxe(e.getPlayer().getInventory().getItemInMainHand().getType()))
                    if (e.getBlock().getType() == Material.OAK_LOG)
                        dropTree(e.getBlock().getLocation());
    }

    @EventHandler
    private void onBlockDamage(BlockDamageEvent e) {
        Player player = e.getPlayer();
        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 100, 1));
    }

    private void dropTree(final Location location) {
        List<Block> blocks = new LinkedList<>();

        for (int i = location.getBlockY(); i < location.getWorld().getHighestBlockYAt(location.getBlockX(), location.getBlockZ()); i++) {
            Location l = location.add(0, 1, 0);
            if (l.getBlock().getType() == Material.OAK_LOG)
                blocks.add(l.getBlock());
            else
                break;

            l = null;
        }
        for (Block block : blocks) {
            block.breakNaturally(new ItemStack(Material.DIAMOND_AXE));
        }
        blocks = null;
    }

    private boolean isAxe(Material material) {
        boolean isAxe;
        switch (material) {
            case WOODEN_AXE:
            case STONE_AXE:
            case IRON_AXE:
            case GOLDEN_AXE:
            case DIAMOND_AXE:
            case NETHERITE_AXE:
                isAxe = true;
                break;
            default:
                isAxe = false;
                break;
        }
        return isAxe;
    }
}

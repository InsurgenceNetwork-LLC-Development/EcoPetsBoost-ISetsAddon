package org.insurgencedev.ecopetsboost;

import com.willfp.ecopets.api.event.PlayerPetExpGainEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.insurgencedev.insurgencesets.api.ISetsAPI;
import org.insurgencedev.insurgencesets.libs.fo.remain.nbt.NBTItem;
import org.insurgencedev.insurgencesets.models.armorset.ArmorSet;
import org.insurgencedev.insurgencesets.models.upgrade.Boost;
import org.insurgencedev.insurgencesets.models.upgrade.Upgrade;
import org.insurgencedev.insurgencesets.settings.ArmorSetData;
import org.insurgencedev.insurgencesets.settings.ISetsPlayerCache;

public class PetExperienceGainListener implements Listener {

    @EventHandler
    public void onGain(PlayerPetExpGainEvent event) {
        Player player = event.getPlayer();
        ISetsPlayerCache cache = ISetsPlayerCache.from(player);

        ItemStack[] armorContents = player.getInventory().getArmorContents();
        for (ItemStack item : armorContents) {
            if (item != null) {
                NBTItem nbtItem = new NBTItem(item);
                if (!nbtItem.hasTag("armorSet")) {
                    continue;
                }

                ArmorSet armorSet = ISetsAPI.getArmorSetManager().findArmorSet(nbtItem.getString("armorSet"));
                if (armorSet == null) {
                    continue;
                }

                String armorSetName = armorSet.getName();
                String itemType = item.getType().name().split("_")[1];
                ArmorSetData armorSetData = cache.getArmorSetData(armorSetName);
                if (armorSetData == null) {
                    continue;
                }

                Object levels = getLevelsFromType(itemType, armorSetData);
                if (levels instanceof Integer) {
                    Upgrade upgrade = armorSet.findPieceLevels(itemType, (Integer) levels);
                    if (upgrade == null) {
                        continue;
                    }

                    for (Boost boost : upgrade.getBoosts()) {
                        if ("BOOSTER".equals(boost.getNamespace()) && boost.getType().equals("Pet XP")) {
                            double boostAmount = boost.getBOOST_SETTINGS().getDouble("Boost_Amount");
                            event.setAmount(calcAmountToGive(event.getAmount(), boost, boostAmount));
                        }
                    }
                }
            }
        }
    }

    private double calcAmountToGive(double amountFromEvent, Boost boost, double boostAmount) {
        if (boost.isPercent()) {
            return (amountFromEvent * (1 + boostAmount / 100));
        } else {
            return (amountFromEvent * (boostAmount < 1 ? 1 + boostAmount : boostAmount));
        }
    }

    private Object getLevelsFromType(String type, ArmorSetData armorSetData) {
        return switch (type) {
            case "HEAD", "HELMET" -> armorSetData.getHelmetLevels();
            case "CHESTPLATE" -> armorSetData.getChestplateLevels();
            case "LEGGINGS" -> armorSetData.getLeggingsLevels();
            case "BOOTS" -> armorSetData.getBootsLevels();
            default -> false;
        };
    }
}

package org.insurgencedev.ecopetsboost;

import org.bukkit.Bukkit;
import org.insurgencedev.insurgencesets.api.addon.ISetsAddon;
import org.insurgencedev.insurgencesets.api.addon.InsurgenceSetsAddon;

@ISetsAddon(name = "EcoPetsBoost", version = "1.0.0", author = "Insurgence Dev Team", description = "Boost the pet experience earned from EcoPets")
public class EcoPetsBoostAddon extends InsurgenceSetsAddon {

    @Override
    public void onAddonReloadablesStart() {
        if (isDependentEnabled()) {
            registerEvent(new PetExperienceGainListener());
        }
    }

    private boolean isDependentEnabled() {
        return Bukkit.getPluginManager().isPluginEnabled("EcoPets");
    }
}

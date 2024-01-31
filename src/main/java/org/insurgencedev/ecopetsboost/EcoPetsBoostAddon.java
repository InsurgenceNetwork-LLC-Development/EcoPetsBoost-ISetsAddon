package org.insurgencedev.ecopetsboost;

import org.insurgencedev.ecopetsboost.listeners.PetExperienceGainListener;
import org.insurgencedev.insurgencesets.api.addon.ISetsAddon;
import org.insurgencedev.insurgencesets.api.addon.InsurgenceSetsAddon;
import org.insurgencedev.insurgencesets.libs.fo.Common;

@ISetsAddon(name = "EcoPetsBoost", version = "1.0.2", author = "Insurgence Dev Team", description = "Boost the pet experience earned from EcoPets")
public class EcoPetsBoostAddon extends InsurgenceSetsAddon {

    @Override
    public void onAddonReloadablesStart() {
        if (Common.doesPluginExist("EcoPets")) {
            registerEvent(new PetExperienceGainListener());
        }
    }
}

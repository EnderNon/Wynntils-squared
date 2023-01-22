/*
 * Copyright © Wynntils 2022.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynntils.wynn.model.actionbar;

import com.wynntils.core.components.Handlers;
import com.wynntils.core.components.Model;
import com.wynntils.models.character.actionbar.HealthSegment;
import com.wynntils.models.character.actionbar.ManaSegment;
import com.wynntils.models.character.actionbar.PowderSpecialSegment;
import com.wynntils.models.character.actionbar.SprintSegment;
import com.wynntils.models.concepts.Powder;
import com.wynntils.models.spells.actionbar.SpellSegment;
import com.wynntils.wynn.model.actionbar.event.CenterSegmentClearedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public final class ActionBarModel extends Model {
    private final CoordinatesSegment coordinatesSegment = new CoordinatesSegment();
    private final HealthSegment healthSegment = new HealthSegment();
    private final ManaSegment manaSegment = new ManaSegment();
    private final PowderSpecialSegment powderSpecialSegment = new PowderSpecialSegment();
    private final SpellSegment spellSegment = new SpellSegment();
    private final SprintSegment sprintSegment = new SprintSegment();

    public ActionBarModel() {
        Handlers.ActionBar.registerSegment(coordinatesSegment);
        Handlers.ActionBar.registerSegment(healthSegment);
        Handlers.ActionBar.registerSegment(manaSegment);
        Handlers.ActionBar.registerSegment(powderSpecialSegment);
        Handlers.ActionBar.registerSegment(spellSegment);
        Handlers.ActionBar.registerSegment(sprintSegment);
    }

    public int getCurrentHealth() {
        return healthSegment.getCurrentHealth();
    }

    public int getMaxHealth() {
        return healthSegment.getMaxHealth();
    }

    public int getCurrentMana() {
        return manaSegment.getCurrentMana();
    }

    public int getMaxMana() {
        return manaSegment.getMaxMana();
    }

    public float getPowderSpecialCharge() {
        return powderSpecialSegment.getPowderSpecialCharge();
    }

    public Powder getPowderSpecialType() {
        return powderSpecialSegment.getPowderSpecialType();
    }

    public void hideHealth(boolean shouldHide) {
        healthSegment.setHidden(shouldHide);
    }

    public void hideMana(boolean shouldHide) {
        manaSegment.setHidden(shouldHide);
    }

    @SubscribeEvent
    public void onCenterSegmentCleared(CenterSegmentClearedEvent event) {
        powderSpecialSegment.replaced();
    }
}

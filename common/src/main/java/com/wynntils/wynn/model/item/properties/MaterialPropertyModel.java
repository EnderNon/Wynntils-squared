/*
 * Copyright © Wynntils 2022.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynntils.wynn.model.item.properties;

import com.wynntils.core.managers.Model;
import com.wynntils.wynn.item.parsers.WynnItemMatchers;
import com.wynntils.wynn.item.properties.MaterialProperty;
import com.wynntils.wynn.model.item.ItemStackTransformManager;
import com.wynntils.wynn.model.item.ItemStackTransformManager.ItemPropertyWriter;

public class MaterialPropertyModel extends Model {
    private static final ItemPropertyWriter MATERIAL_WRITER =
            new ItemPropertyWriter(WynnItemMatchers::isMaterial, MaterialProperty::new);

    public static void init() {
        ItemStackTransformManager.registerProperty(MATERIAL_WRITER);
    }

    public static void disable() {
        ItemStackTransformManager.unregisterProperty(MATERIAL_WRITER);
    }
}
/*
 * Copyright © Wynntils 2022.
 * This file is released under AGPLv3. See LICENSE for full license details.
 */
package com.wynntils.models.objectives;

import com.wynntils.core.WynntilsMod;
import com.wynntils.core.components.Handlers;
import com.wynntils.core.components.Model;
import com.wynntils.handlers.scoreboard.ScoreboardPart;
import com.wynntils.handlers.scoreboard.ScoreboardSegment;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ObjectivesModel extends Model {
    private static final ScoreboardPart OBJECTIVES_SCOREBOARD_PART = new ObjectivesScoreboardPart();

    private List<WynnObjective> personalObjectives = new ArrayList<>();
    private WynnObjective guildObjective = null;

    public ObjectivesModel() {
        Handlers.Scoreboard.addPart(OBJECTIVES_SCOREBOARD_PART);
    }

    public WynnObjective getGuildObjective() {
        return guildObjective;
    }

    public List<WynnObjective> getPersonalObjectives() {
        // Make copy, so we don't have to worry about concurrent modification
        return new ArrayList<>(personalObjectives);
    }

    public boolean isObjectiveSegment(ScoreboardSegment segment) {
        return segment.getMatcher() == ObjectivesScoreboardPart.OBJECTIVES_MATCHER;
    }

    public boolean isGuildObjectiveSegment(ScoreboardSegment segment) {
        return segment.getMatcher() == ObjectivesScoreboardPart.GUILD_OBJECTIVES_MATCHER;
    }

    void resetObjectives() {
        guildObjective = null;
        personalObjectives = new ArrayList<>();
    }

    void updatePersonalObjective(WynnObjective parsed) {
        Optional<WynnObjective> objective = personalObjectives.stream()
                .filter(wynnObjective -> wynnObjective.isSameObjective(parsed))
                .findFirst();

        if (objective.isEmpty()) {
            // New objective
            personalObjectives.add(parsed);
        } else {
            // Objective progress updated
            objective.get().setScore(parsed.getScore());
        }

        if (personalObjectives.size() > 3) {
            WynntilsMod.error("ObjectiveManager: Stored more than 3 objectives. Reset objective list.");
            personalObjectives.clear();
            personalObjectives.add(parsed);
        }
    }

    void purgePersonalObjectives(List<WynnObjective> objectives) {
        personalObjectives.removeIf(
                wynnObjective -> objectives.stream().noneMatch(other -> other.isSameObjective(wynnObjective)));
    }

    void updateGuildObjective(WynnObjective parsed) {
        if (guildObjective != null && guildObjective.isSameObjective(parsed)) {
            // Objective progress updated
            guildObjective.setScore(parsed.getScore());
            return;
        }

        // New objective
        guildObjective = parsed;
    }

    void removeObjective(WynnObjective parsed) {
        if (guildObjective != null && guildObjective.isSameObjective(parsed)) {
            guildObjective = null;
            return;
        }

        personalObjectives.removeIf(wynnObjective -> wynnObjective.isSameObjective(parsed));
    }
}
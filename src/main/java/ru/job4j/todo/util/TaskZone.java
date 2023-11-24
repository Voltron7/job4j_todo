package ru.job4j.todo.util;

import lombok.experimental.UtilityClass;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@UtilityClass
public final class TaskZone {

    public static LocalDateTime setUsersTimeZone(Task task, User user) {
        ZoneId defaultTimeZoneId = TimeZone.getDefault().toZoneId();
        ZoneId usersTimeZoneId = ZoneId.of(user.getTimezone());
        return task.getCreated()
                .atZone(defaultTimeZoneId)
                .withZoneSameInstant(usersTimeZoneId)
                .toLocalDateTime();
    }

    public static List<TimeZone> getAllTimeZones() {
        var zones = new ArrayList<TimeZone>();
        for (String timeId : TimeZone.getAvailableIDs()) {
            zones.add(TimeZone.getTimeZone(timeId));
        }
        return zones;
    }
}

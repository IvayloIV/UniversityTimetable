package bg.tuvarna.universitytimetable.dto.model;

import lombok.Data;

@Data
public class GroupScheduleModel implements Comparable<GroupScheduleModel> {

    private Long id;

    private String name;

    @Override
    public int compareTo(GroupScheduleModel other) {
        return name.compareTo(other.name);
    }
}

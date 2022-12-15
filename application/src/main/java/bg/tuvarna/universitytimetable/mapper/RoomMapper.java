package bg.tuvarna.universitytimetable.mapper;

import bg.tuvarna.universitytimetable.dto.model.RoomListModel;
import bg.tuvarna.universitytimetable.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoomMapper {

    List<RoomListModel> entityToModel(List<Room> rooms);
}

package bg.tuvarna.universitytimetable.service;

import bg.tuvarna.universitytimetable.dto.model.RoomListModel;

import java.util.List;

public interface RoomService {

    List<RoomListModel> getList();
}

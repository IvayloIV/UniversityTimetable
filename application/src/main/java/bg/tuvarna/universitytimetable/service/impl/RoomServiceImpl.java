package bg.tuvarna.universitytimetable.service.impl;

import bg.tuvarna.universitytimetable.dto.model.RoomListModel;
import bg.tuvarna.universitytimetable.entity.Room;
import bg.tuvarna.universitytimetable.mapper.RoomMapper;
import bg.tuvarna.universitytimetable.repository.RoomRepository;
import bg.tuvarna.universitytimetable.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository,
                           RoomMapper roomMapper) {
        this.roomRepository = roomRepository;
        this.roomMapper = roomMapper;
    }

    @Override
    public List<RoomListModel> getList() {
        List<Room> rooms = roomRepository.findAllByOrderByNumberBg();
        return roomMapper.entityToModel(rooms);
    }
}

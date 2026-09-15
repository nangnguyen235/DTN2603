package backend.controller;

import backend.service.IQLPositionService;
import backend.service.impl.QLPositionServiceImpl;
import entity.Position;

import java.util.List;

public class QLPositionController {
    private IQLPositionService service;

    public QLPositionController() {
        service = new QLPositionServiceImpl();
    }

    public List<Position> getAll() {
        List<Position> positions = service.getAll();
        return positions;
    }

    public boolean themPosition(String positionName) {
        return service.themPosition(positionName);
    }

    public boolean xoaPositionTheoId(int id) {
        return service.xoaPositionTheoId(id);
    }

    public boolean suaPositionNameTheoId(int id, String newPositionName) {
        return service.suaPositionNameTheoId(id, newPositionName);
    }
}

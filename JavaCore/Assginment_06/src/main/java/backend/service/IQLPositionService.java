package backend.service;

import entity.Position;

import java.util.List;

public interface IQLPositionService {
    List<Position> getAll();

    boolean themPosition(String positionName);

    boolean xoaPositionTheoId(int id);

    boolean suaPositionNameTheoId(int id, String newPositionName);
}

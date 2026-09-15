package backend.repository;

import entity.Position;

import java.util.List;

public interface IQLPositionRepository {
    List<Position> getAll();

    boolean themPosition(String positionName);

    boolean xoaPositionTheoId(int id);

    boolean suaPositionNameTheoId(int id, String newPositionName);
}

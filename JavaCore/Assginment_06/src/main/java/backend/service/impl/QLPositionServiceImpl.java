package backend.service.impl;

import backend.repository.IQLPositionRepository;
import backend.repository.impl.QLPositionRepositoryImpl;
import backend.service.IQLPositionService;
import entity.Position;

import java.util.List;

public class QLPositionServiceImpl implements IQLPositionService {
    private IQLPositionRepository repository;

    public QLPositionServiceImpl() {
        repository = new QLPositionRepositoryImpl();
    }

    @Override
    public List<Position> getAll() {
        List<Position> positions = repository.getAll();
        return positions;
    }

    @Override
    public boolean themPosition(String positionName) {
        return repository.themPosition(positionName);
    }

    @Override
    public boolean xoaPositionTheoId(int id) {
        return repository.xoaPositionTheoId(id);
    }

    @Override
    public boolean suaPositionNameTheoId(int id, String newPositionName) {
        return repository.suaPositionNameTheoId(id, newPositionName);
    }
}

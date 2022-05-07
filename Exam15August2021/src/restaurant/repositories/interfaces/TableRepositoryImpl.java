package restaurant.repositories.interfaces;

import restaurant.entities.tables.interfaces.Table;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class TableRepositoryImpl implements TableRepository<Table>{
    private Map<Integer,Table> table;

    public TableRepositoryImpl() {
        this.table = new LinkedHashMap<>();
    }

    @Override
    public Collection<Table> getAllEntities() {
        return Collections.unmodifiableCollection(table.values());
    }

    @Override
    public void add(Table entity) {
        this.table.put(entity.getTableNumber(), entity);
    }

    @Override
    public Table byNumber(int number) {
        return table.get(number);
    }
}

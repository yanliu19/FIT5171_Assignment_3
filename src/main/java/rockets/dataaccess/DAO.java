package rockets.dataaccess;

import rockets.model.Entity;
import rockets.model.User;

import java.util.Collection;

public interface DAO {
    <T extends Entity> T load(Class<T> clazz, Long id);

    <T extends Entity> T createOrUpdate(T entity);

    <T extends Entity> Collection<T> loadAll(Class<T> clazz);

    <T extends Entity> void delete(T entity);

    User getUserByEmail(String email);

    void close();
}

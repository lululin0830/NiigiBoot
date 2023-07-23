package tw.idv.tibame.core.dao;

import java.util.List;

public interface CoreDAO<E, I> {

	Boolean insert(E entity)throws Exception;

	E selectById(I id)throws Exception;

	List<E> getAll()throws Exception;

}

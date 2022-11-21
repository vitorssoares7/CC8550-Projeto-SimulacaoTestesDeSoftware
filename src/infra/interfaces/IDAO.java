package infra.interfaces;

import java.util.List;

public interface IDAO<E> {

	public abstract boolean save(E object)
	throws IllegalArgumentException;

	public abstract void delete(E object)
	throws IllegalArgumentException;

	public abstract List<E> list()
	throws IllegalArgumentException;

	public abstract E get(E object)
	throws IllegalArgumentException;

	public abstract List<E> search(E object)
	throws IllegalArgumentException;

}
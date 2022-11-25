package infra.dao;

import java.util.ArrayList;
import java.util.List;

import core.interfaces.IValidator;
import infra.interfaces.IDAO;

import com.db4o.ObjectSet;
import com.db4o.ext.ExtObjectContainer;

import app.utils.DatabaseManager;

public class DAO<E> implements IDAO<E> {

	private static ExtObjectContainer db = DatabaseManager.getConnection();
	private Class<E> clazz;
	private IValidator<E> validator;
	private boolean useEquals;
	
	public DAO(Class<E> clazz, IValidator<E> val, boolean comp){
		this.validator = val;
		this.useEquals = comp;
		this.clazz = clazz;
	}
	
	public DAO(Class<E> clazz, boolean useEquals){
		this.validator = new DefaultValidator<E>();
		this.useEquals = useEquals;
		this.clazz = clazz;
	}
	
	public DAO(Class<E> clazz){
		this.validator = new DefaultValidator<E>();
		this.clazz = clazz;
	}
	
	@Override
	public synchronized boolean save(E object) {
		if(validator.validate(object)){
			db.store(object);
			db.commit();
			return true;
		}
		else{
			return false;
		}
	}
	
	@Override
	public synchronized void delete(E object) {
		db.delete(object);
		db.commit();
	}
	
	@Override
	public List<E> list() {
		List<E> objects = new ArrayList<E>();
		ObjectSet<E> result = db.queryByExample(clazz);
		while(result.hasNext()){
			objects.add((E)result.next());
		}
		return objects;
	}
	
	@Override
	public E get(E object) throws IllegalArgumentException{
		List<E> objectList = db.queryByExample(clazz);
		if(useEquals){
			for(E each: objectList){
				if(each.equals(object)){
					return each;
				}
			}
		}
		else{
			int index = objectList.indexOf(object);
			if(index >= 0){
				return objectList.get(index);
			}
		}
		throw new IllegalArgumentException("Nenhum objeto encontrado!");
	}

	@Override
	public List<E> search(E object) {
		List<E> objects = new ArrayList<E>();
		ObjectSet<E> result = db.queryByExample(object);
		while(result.hasNext()){
			objects.add((E)result.next());
		}
		return objects;		
	}

	/**
	 * Classe utilizada caso o nenhuma classe Validador seja fornecida na instanciação.
	 */
	public class DefaultValidator<T> implements IValidator<T> {
		@Override
		public boolean validate(T obj) {
			return true;
		}
	}
}
package core.model.validator;


public interface Validator<E> {
	public boolean validate(E obj);
}

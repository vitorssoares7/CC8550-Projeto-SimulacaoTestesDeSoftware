package core.validators;

import core.entities.Aluno;
import core.interfaces.IValidator;

public class AlunoValidator implements IValidator<Aluno>{
	public boolean validate(Aluno obj) {
		return true;
	}
}
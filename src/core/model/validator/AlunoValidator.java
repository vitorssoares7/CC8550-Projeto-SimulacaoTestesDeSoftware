package core.model.validator;

import core.model.beans.Aluno;

public class AlunoValidator implements Validator<Aluno>{
	public boolean validate(Aluno obj) {
		return true;
	}
}
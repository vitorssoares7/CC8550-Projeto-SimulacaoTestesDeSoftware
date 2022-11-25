package core.validators;

import core.entities.Entidade;
import core.interfaces.IValidator;

public class EntidadeValidator implements IValidator<Entidade>{
	public boolean validate(Entidade obj) {
		EnderecoValidator enderecoValidator = new EnderecoValidator();
		if (obj.getNome() == null || obj.getNome().equals(""))
			return false;

		if (obj.getTelefone1() == null || obj.getTelefone1().equals(""))
			return false;

		if (obj.getCnpj() == null || obj.getCnpj().equals(""))
			return false;

		if (!enderecoValidator.validate(obj.getEndereco()))
			return false;

		return true;
	}
}
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
		else if(obj.getCnpj().length() > 12){
			return false;
		}

		if (obj.getCnpj() == null || obj.getCnpj().equals(""))
			return false;
		else if(obj.getCnpj().length() > 14){
			return false;
		}

		if (!enderecoValidator.validate(obj.getEndereco()))
			return false;

		return true;
	}
}
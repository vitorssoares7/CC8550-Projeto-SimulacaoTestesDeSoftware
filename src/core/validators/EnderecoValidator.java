package core.validators;

import core.entities.Endereco;
import core.interfaces.IValidator;

public class EnderecoValidator implements IValidator<Endereco>{
	public boolean validate(Endereco obj) {
		if (obj.getRua() == null || obj.getRua().equals(""))
			return false;

		if (obj.getNumero() == null || obj.getNumero().equals(""))
			return false;

		if (obj.getCep() == null || obj.getCep().equals(""))
			return false;
		
		if (obj.getBairro() == null || obj.getBairro().equals(""))
			return false;

		if (obj.getCidade() == null || obj.getCidade().equals("")) 
			return false;

		if (obj.getEstado() == null|| obj.getEstado().equals(""))
			return false;

		return true;
	}
}
package core.validators;

import app.utils.CPFValidator;
import core.entities.Aluno;
import core.interfaces.IValidator;

public class AlunoValidator implements IValidator<Aluno>{
	public boolean validate(Aluno obj) {
		if (obj.getFiliado().getCpf() == null || obj.getFiliado().getCpf().equals(""))
			return false;

		if (obj.getFiliado().getNome() == null || obj.getFiliado().getNome().equals(""))
			return false;

		if (obj.getFiliado().getEmail() == null || obj.getFiliado().getEmail().equals(""))
			return false;

		if (obj.getFiliado().getTelefone1() == null || obj.getFiliado().getTelefone1().equals(""))
			return false;

		if (obj.getFiliado().getRg() == null)
			return false;

		if (obj.getFiliado().getRg().getNumero() == null || obj.getFiliado().getRg().getNumero().equals(""))
			return false;

		if (obj.getFiliado().getRg().getOrgaoExpedidor() == null || obj.getFiliado().getRg().getOrgaoExpedidor().equals(""))
			return false;

		if (!CPFValidator.isCPF(obj.getFiliado().getCpf()))
			return false;

		return true;
	}
}
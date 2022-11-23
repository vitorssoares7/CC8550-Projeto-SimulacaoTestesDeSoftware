package core.validators;

import app.utils.CPFValidator;
import core.entities.Aluno;
import core.interfaces.IValidator;

public class AlunoValidator implements IValidator<Aluno>{
	public boolean validate(Aluno obj) {
		if (obj.getFiliado().getCpf() == null || obj.getFiliado().getCpf().equals(""))
			return false;

		if (obj.getFiliado().getNome() == null)
			return false;

		if (obj.getFiliado().getEmail() == null)
			return false;

		if (obj.getFiliado().getTelefone1() == null)
			return false;

		if (obj.getFiliado().getRg() == null)
			return false;

		if (obj.getFiliado().getRg().getNumero() == null)
			return false;

		if (obj.getFiliado().getRg().getOrgaoExpedidor() == null)
			return false;

		if (!CPFValidator.isCPF(obj.getFiliado().getCpf()))
			return false;

		if (obj.getFiliado().getTelefone1().contains("[a-zA-Z]+") && obj.getFiliado().getTelefone1().length() < 8) {
			return false;
		}

		return true;
	}
}
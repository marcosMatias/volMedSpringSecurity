package med.voll.web_application.domain.medico;

import java.math.BigDecimal;

public record DadosListagemMedico(BigDecimal id, String nome, String email, String crm, Especialidade especialidade) {

    public DadosListagemMedico(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getEspecialidade());
    }

}

package med.voll.web_application.domain.consulta;

import med.voll.web_application.domain.medico.Especialidade;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DadosListagemConsulta(BigDecimal id, String medico, String paciente, LocalDateTime data, Especialidade especialidade) {

    public DadosListagemConsulta(Consulta consulta) {
        this(consulta.getId(), consulta.getMedico().getNome(), consulta.getPaciente().getNome(), consulta.getData(), consulta.getMedico().getEspecialidade());
    }

}

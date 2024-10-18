package med.voll.web_application.domain.consulta;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.web_application.domain.medico.Especialidade;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DadosAgendamentoConsulta(

        BigDecimal id,
        BigDecimal idMedico,

        @NotNull
        String paciente,

        @NotNull
        @Future
        LocalDateTime data,

        Especialidade especialidade) {
}

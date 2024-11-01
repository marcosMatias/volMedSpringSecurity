package med.voll.web_application.domain.consulta;

import jakarta.persistence.*;
import med.voll.web_application.domain.medico.Medico;
import med.voll.web_application.domain.paciente.Paciente;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "consultas")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONSULTAS_S")
    @SequenceGenerator(sequenceName = "CONSULTAS_S", allocationSize = 1, name = "CONSULTAS_S")
    private BigDecimal id;
    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    private Medico medico;

    private LocalDateTime data;

    @Deprecated
    public Consulta(){}

    public Consulta(Medico medico, Paciente paciente, DadosAgendamentoConsulta dados) {
        modificarDados(medico, paciente, dados);
    }
    
    public void modificarDados(Medico medico, Paciente paciente, DadosAgendamentoConsulta dados) {
        this.medico = medico;
        this.paciente = paciente;
        this.data = dados.data();
    }

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public Paciente getPaciente() {
        return paciente;
    }

	public Medico getMedico() {
		return medico;
	}

	public void setMedico(Medico medico) {
		this.medico = medico;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

   
}

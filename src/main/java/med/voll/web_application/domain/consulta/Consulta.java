package med.voll.web_application.domain.consulta;

import jakarta.persistence.*;
import med.voll.web_application.domain.medico.Medico;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "consultas")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONSULTAS_S")
    @SequenceGenerator(sequenceName = "CONSULTAS_S", allocationSize = 1, name = "CONSULTAS_S")
    private BigDecimal id;
    private String paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    private Medico medico;

    private LocalDateTime data;

    @Deprecated
    public Consulta(){}

    public Consulta(Medico medico, DadosAgendamentoConsulta dados) {
        modificarDados(medico, dados);
    }

    public void modificarDados(Medico medico, DadosAgendamentoConsulta dados) {
        this.medico = medico;
        this.paciente = dados.paciente();
        this.data = dados.data();
    }

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getPaciente() {
		return paciente;
	}

	public void setPaciente(String paciente) {
		this.paciente = paciente;
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

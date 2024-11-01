package med.voll.web_application.domain.paciente;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pacientes")
public class Paciente {

	
	@Id
	/*@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PACIENTES_S")
    @SequenceGenerator(sequenceName = "PACIENTES_S", allocationSize = 1, name = "PACIENTES_S")*/ // comentar para incluir o id do usuario no momento da criação do paciente
    private BigDecimal id;
    private String nome;
    private String email;
    private String telefone;
    private String cpf;

    @Deprecated
    public Paciente(){}

    public Paciente(BigDecimal id, DadosCadastroPaciente dados) {
        this.id = id;
        modificarDados(dados);
    }

    public void modificarDados(DadosCadastroPaciente dados) {
        this.nome = dados.nome();
        this.email = dados.email();
        this.telefone = dados.telefone();
        this.cpf = dados.cpf();
    }
    public BigDecimal getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getCpf() {
        return cpf;
    }
}

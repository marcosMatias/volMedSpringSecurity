package med.voll.web_application.domain.consulta;

import jakarta.transaction.Transactional;
import med.voll.web_application.domain.medico.MedicoRepository;
import med.voll.web_application.domain.paciente.PacienteRepository;
import med.voll.web_application.domain.usuario.Perfil;
import med.voll.web_application.domain.usuario.Usuario;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ConsultaService {

    private final ConsultaRepository repository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;
    
    public ConsultaService(ConsultaRepository repository, MedicoRepository medicoRepository, PacienteRepository pacienteRepository) {
        this.repository = repository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
    }

    public Page<DadosListagemConsulta> listar(Pageable paginacao, Usuario logado) {
    	if(logado.getPerfil()==Perfil.ATENDENTE)
        return repository.findAllByOrderByData(paginacao).map(DadosListagemConsulta::new);
    	
    	return repository.buscarPersonalizadaConsultas(logado.getId(), paginacao).map(DadosListagemConsulta::new);
    	
    }

    @Transactional
    public void cadastrar(DadosAgendamentoConsulta dados) {
    	 var medicoConsulta = medicoRepository.findById(dados.idMedico()).orElseThrow();
         var pacienteConsulta = pacienteRepository.findByCpf(dados.paciente()).orElseThrow();
         if (dados.id() == null) {
             repository.save(new Consulta(medicoConsulta, pacienteConsulta, dados));
         } else {
             var consulta = repository.findById(dados.id()).orElseThrow();
             consulta.modificarDados(medicoConsulta, pacienteConsulta, dados);
         }
    }

    public DadosAgendamentoConsulta carregarPorId(BigDecimal id) {
        var consulta = repository.findById(id).orElseThrow();
        var medicoConsulta = medicoRepository.getReferenceById(consulta.getMedico().getId());
        return new DadosAgendamentoConsulta(consulta.getId(), consulta.getMedico().getId(), consulta.getPaciente().getNome(), consulta.getData(), medicoConsulta.getEspecialidade());
    }

    @Transactional
    public void excluir(BigDecimal id) {
        repository.deleteById(id);
    }

}

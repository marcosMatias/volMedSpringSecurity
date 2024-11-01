package med.voll.web_application.domain.usuario;

import jakarta.validation.constraints.NotBlank;

public record DadosRecuperacaoConta(@NotBlank String senhaAtual,
        							@NotBlank String novaSenha, 
                                    @NotBlank String novaSenhaConfirmacao) {

}

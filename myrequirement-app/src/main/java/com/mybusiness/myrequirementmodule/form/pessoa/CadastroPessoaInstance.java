package com.mybusiness.myrequirementmodule.form.pessoa;

import com.mybusiness.myrequirementmodule.model.dto.CEPDto;
import org.opensingular.form.SIComposite;
import org.opensingular.form.type.core.SIString;

public class CadastroPessoaInstance extends SIComposite {


    public CadastroPessoaInstance() {
        //Empty constructor
    }

    @Override
    public CadastroPessoaForm getType() {
        return (CadastroPessoaForm) super.getType();
    }

    public SIString getLogradouro() {
        return this.getDescendant(this.getType().endereco.logradouro);
    }

    public SIString getComplemento() {
        return this.getDescendant(this.getType().endereco.complemento);
    }

    public SIString getCidade() {
        return this.getDescendant(this.getType().endereco.cidade);
    }

    public SIString getEstado() {
        return this.getDescendant(this.getType().endereco.estado.sigla);
    }

    public SIString getBairro() {
        return this.getDescendant(this.getType().endereco.bairro);
    }

    public void fillInstance(CEPDto dto) {

        getLogradouro().setValue(dto.getLogradouro());
        getComplemento().setValue(dto.getComplemento());
        getCidade().setValue(dto.getLocalidade());
        getEstado().setValue(dto.getUf());
        getBairro().setValue(dto.getBairro());
    }

}

package com.mybusiness.myrequirementmodule.form.pessoa;

import com.mybusiness.myrequirementmodule.form.MyRequirementPackage;
import com.mybusiness.myrequirementmodule.model.dto.CEPDto;
import com.mybusiness.myrequirementmodule.model.stypes.STypePessoa;
import com.mybusiness.myrequirementmodule.spring.service.EnderecoCustomService;
import org.opensingular.form.SIComposite;
import org.opensingular.form.SInfoType;
import org.opensingular.form.STypeComposite;
import org.opensingular.form.TypeBuilder;
import org.opensingular.form.type.core.SIString;
import org.opensingular.form.type.country.brazil.STypeAddress;
import org.opensingular.form.view.SViewByBlock;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.Optional;

@SInfoType(spackage = MyRequirementPackage.class, name = "CadastroPessoa")
public class CadastroPessoaForm extends STypeComposite<CadastroPessoaInstance> {

    public STypePessoa dadosPessoa;
    public STypeAddress endereco;

    @Inject
    private EnderecoCustomService enderecoCustomService;

    public CadastroPessoaForm() {
        super(CadastroPessoaInstance.class);
    }

    @Override
    protected void onLoadType(@Nonnull TypeBuilder tb) {
        asAtr().displayString("Nova Solicitação");

        dadosPessoa = addField("dadosPessoa", STypePessoa.class);
        endereco = addField("endereco", STypeAddress.class);

        endereco.withUpdateListener(this::pesquisarCEP)
                .asAtr()
                .dependsOn(endereco.cep);

        this.withView(new SViewByBlock(), block ->
                block.newBlock("Dados Pessoais")
                        .add(dadosPessoa)
                        .newBlock("Endereço")
                        .add(endereco)
        );
    }

    private void pesquisarCEP(SIComposite instance) {
        final Optional<SIString> cepField = instance.findNearest(endereco.cep);
        cepField.ifPresent(c -> {

            CEPDto ender = enderecoCustomService.getByCep(c.getValue());
            instance.findNearest(CadastroPessoaForm.class).ifPresent(s -> s.fillInstance(ender));

        });
    }
}


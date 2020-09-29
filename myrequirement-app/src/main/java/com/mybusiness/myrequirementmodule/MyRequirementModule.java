package com.mybusiness.myrequirementmodule;

import com.mybusiness.myrequirementmodule.box.CadastroPessoaCaixa;
import com.mybusiness.myrequirementmodule.box.MyRequirementCaixaPendencia;
import com.mybusiness.myrequirementmodule.flow.CadastroPessoaFlow;
import com.mybusiness.myrequirementmodule.flow.MyRequirementFlow;
import com.mybusiness.myrequirementmodule.form.myrequirement.MyRequirementForm;
import com.mybusiness.myrequirementmodule.form.pessoa.CadastroPessoaForm;
import org.opensingular.requirement.commons.SingularRequirement;
import org.opensingular.requirement.module.FormFlowSingularRequirement;
import org.opensingular.requirement.module.RequirementConfiguration;
import org.opensingular.requirement.module.SingularModule;
import org.opensingular.requirement.module.WorkspaceConfiguration;
import org.opensingular.requirement.module.workspace.DefaultDonebox;
import org.opensingular.requirement.module.workspace.DefaultDraftbox;
import org.opensingular.requirement.module.workspace.DefaultInbox;
import org.opensingular.requirement.module.workspace.DefaultOngoingbox;

public class MyRequirementModule implements SingularModule {

    public static final String MYREQUIREMENT_CONST = "MYREQUIREMENT";
    private final SingularRequirement myrequirement = new FormFlowSingularRequirement("MyRequirement", MyRequirementForm.class, MyRequirementFlow.class);
    private final SingularRequirement cadastroPessoaRequirement = new FormFlowSingularRequirement("CadastroPessoa", CadastroPessoaForm.class, CadastroPessoaFlow.class);

    @Override
    public String abbreviation() {
        return MYREQUIREMENT_CONST;
    }

    @Override
    public String name() {
        return "Módulo MyRequirement";
    }

    @Override
    public void requirements(RequirementConfiguration config) {
        config
                .addRequirement(myrequirement)
                .addRequirement(cadastroPessoaRequirement);
    }

    @Override
    public void workspace(WorkspaceConfiguration config) {
        config
                .addBox(new DefaultDraftbox()).newFor(myrequirement, cadastroPessoaRequirement)
                .addBox(new MyRequirementCaixaPendencia())
                .addBox(new CadastroPessoaCaixa())
                .addBox(new DefaultInbox())
                .addBox(new DefaultOngoingbox())
                .addBox(new DefaultDonebox());
    }
}

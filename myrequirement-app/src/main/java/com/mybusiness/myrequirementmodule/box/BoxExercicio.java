package com.mybusiness.myrequirementmodule.box;

import org.opensingular.lib.wicket.util.resource.DefaultIcons;
import org.opensingular.requirement.commons.box.BoxItemData;
import org.opensingular.requirement.commons.box.action.AbstractURLPopupBoxItemAction;
import org.opensingular.requirement.commons.config.IServerContext;
import org.opensingular.requirement.commons.config.PServerContext;
import org.opensingular.requirement.commons.form.FormAction;
import org.opensingular.requirement.commons.service.dto.DatatableField;
import org.opensingular.requirement.commons.service.dto.ItemBox;
import org.opensingular.requirement.module.ActionProviderBuilder;
import org.opensingular.requirement.module.BoxItemDataProvider;
import org.opensingular.requirement.module.provider.RequirementBoxItemDataProvider;
import org.opensingular.requirement.module.workspace.BoxDefinition;

import java.util.ArrayList;
import java.util.List;

import static com.mybusiness.myrequirementmodule.flow.MyRequirementFlow.MyRequirementTasks.SOLICITACAO_ADMIN;

public class BoxExercicio implements BoxDefinition {
    @Override
    public boolean appliesTo(IServerContext iServerContext) {
        return PServerContext.REQUIREMENT.isSameContext(iServerContext);
    }

    @Override
    public ItemBox build(IServerContext iServerContext) {
        final ItemBox adminBox = new ItemBox();
        adminBox.setName("Admin");
        adminBox.setDescription("Caixa de Administração");
        adminBox.setIcone(DefaultIcons.DASHBOARD);
        return adminBox;
    }

    @Override
    public BoxItemDataProvider getDataProvider() {

                 ActionProviderBuilder actionProvider = new ActionProviderBuilder()
                .addCustomActions((boxInfo, line, filter, list) -> list.addAction(new AcionarAdmin(line)))
                .addViewAction()
                .addHistoryAction();


        return new RequirementBoxItemDataProvider(Boolean.TRUE, actionProvider)
                .addTask(SOLICITACAO_ADMIN.getName());
    }

    @Override
    public List<DatatableField> getDatatableFields() {
        List<DatatableField> fields = new ArrayList<>();
        fields.add(DatatableField.of("NÚMERO", "codRequirement"));
        fields.add(DatatableField.of("Descrição", "description"));
        fields.add(DatatableField.of("Dt. Entrada", "processBeginDate"));
        fields.add(DatatableField.of("Situação", "situation"));
        fields.add(DatatableField.of("Dt. Situação", "situationBeginDate"));
        return fields;
    }

    public static class AcionarAdmin extends AbstractURLPopupBoxItemAction {
        public AcionarAdmin(BoxItemData line) {
            super("admin", "Acionar admin", DefaultIcons.HELP,
                    FormAction.FORM_FILL_WITH_ANALYSIS, line);
        }
    }
}

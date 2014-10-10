package br.com.cgc.vaadin.ui;

import org.apache.log4j.Logger;
import org.vaadin.dialogs.ConfirmDialog;

import br.com.cgc.vaadin.ds.OcorrenciaJPAContainer;
import br.com.cgc.vaadin.ds.TipoOcorrenciaJPAContainer;
import br.com.cgc.vaadin.model.Ocorrencia;
import br.com.cgc.vaadin.model.TipoOcorrencia;
import br.com.cgc.vaadin.util.DateToSqlTimestampConverter;
import com.vaadin.addon.jpacontainer.EntityItem;
import com.vaadin.addon.jpacontainer.fieldfactory.SingleSelectConverter;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Notification.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import org.vaadin.thomas.timefield.TimeField;

/**
 * Tela de Edição / Cadastro / Delete da
 * <code>Ocorrencia</code>.
 *
 * @author CGC Automação
 */
public class OcorrenciaWindow extends Window implements Button.ClickListener {

    private static Logger log = Logger.getLogger(OcorrenciaWindow.class);
    private static final long serialVersionUID = -2996627404786697495L;
    private FormLayout layout;
    private BeanFieldGroup<Ocorrencia> binder;
    private HorizontalLayout buttons;
    private Button bSalvar;
    private Button bCancelar;
    private Button bExcluir;
    private OcorrenciaJPAContainer datasource;

    public OcorrenciaWindow(OcorrenciaJPAContainer datasource) {
        this.datasource = datasource;
        init();
        setModal(true);
    }

    private void init() {
        layout = new FormLayout();
        layout.setSizeFull();
        layout.setSpacing(true);

        bSalvar = new Button("Salvar");
        bSalvar.addClickListener(this);
        bSalvar.setClickShortcut(KeyCode.ENTER);

        bCancelar = new Button("Cancelar");
        bCancelar.addClickListener(this);
        bCancelar.setClickShortcut(KeyCode.ESCAPE);

        bExcluir = new Button("Excluir");
        bExcluir.addClickListener(this);
        bExcluir.setVisible(false);

        buttons = new HorizontalLayout();
        buttons.addComponent(bSalvar);
        buttons.addComponent(bCancelar);
        buttons.addComponent(bExcluir);

        setContent(layout);

        setHeight("370");
        setWidth("400");
    }

    /**
     * Apresenta a
     * <code>Window</code> em forma de edição.
     *
     * @param id
     */
    public void edit(Integer id) {
        try {
            setCaption("Editar Ocorrência");
            Ocorrencia m = datasource.getItem(id).getEntity();
            bindingFields(m);
            bExcluir.setVisible(true);
            UI.getCurrent().addWindow(this);
        } catch (Exception ex) {
            log.error("Não conseguiu carregar a tela de edição: ", ex);
            Notification.show("Não consegui abrir a ocorrência para edição!\n" + ex.getMessage(), Type.ERROR_MESSAGE);
        }
    }

    /**
     * Apresenta a
     * <code>Window</code> em forma de edição.
     */
    public void create() {
        setCaption("Nova Ocorrência");
        bindingFields(new Ocorrencia());
        UI.getCurrent().addWindow(this);
    }

    /**
     * Cria o formulário com os campos para preenchimento.
     *
     * @param m
     */
    private void bindingFields(Ocorrencia m) {
        binder = new BeanFieldGroup<Ocorrencia>(Ocorrencia.class);
        binder.setItemDataSource(m);
        Field<?> field = null;
        field = binder.buildAndBind("Nome", "nome");
        field.setWidth("140");
        layout.addComponent(field);

        field = binder.buildAndBind("Descrição", "descricao");
        field.setWidth("200");
        layout.addComponent(field);

        DateField dataHoraField = new DateField("Data Hora");
        dataHoraField.setWidth("150");
        dataHoraField.setValue(new Date());
        dataHoraField.setDateFormat("dd/MM/yyyy  HH:mm:ss");
        dataHoraField.setResolution(Resolution.SECOND);
        dataHoraField.setConverter(new DateToSqlTimestampConverter());
        binder.bind(dataHoraField, "dataHora");
        layout.addComponent(dataHoraField);

        //=======================================================//
        final TipoOcorrenciaJPAContainer tipoOcorrenciaJPAContainer = new TipoOcorrenciaJPAContainer();
        final ComboBox comboBox = new ComboBox();
        comboBox.setContainerDataSource(tipoOcorrenciaJPAContainer);
        comboBox.setCaption("TipoOcorrencia");
        comboBox.setConverter(new SingleSelectConverter<TipoOcorrencia>(comboBox));
        comboBox.setItemCaptionPropertyId("caption");
        binder.bind(comboBox, "tipoOcorrencia");
        layout.addComponent(comboBox);        
        //=======================================================//
        layout.addComponent(field);
        layout.addComponent(binder.buildAndBind("Id", "id"));

        layout.addComponent(buttons);
    }

    /**
     * Implementa tratadores para os eventos dos
     * <code>Button</code> dessa tela.
     */
    @Override
    public void buttonClick(ClickEvent event) {
        if (event.getButton() == bSalvar) {
            try {

                binder.commit();
            } catch (CommitException e) {
                log.error("Preencha o formulário corretamente: ", e);
                Notification.show("Preencha o formulário corretamente\n" + e.getCause());
                return;
            }

            try {
                datasource.addEntity(binder.getItemDataSource().getBean());
                //log.debug("Ocorrencia persistida!");
                Notification.show("Ocorrência persistida!", Type.HUMANIZED_MESSAGE);
            } catch (Exception e) {
                log.debug("Não consegui salvar a ocorrência!", e);
                Notification.show("Não consegui salvar a ocorrência!\n" + e.getMessage(), Type.ERROR_MESSAGE);
                return;
            }
        } else if (event.getButton() == bExcluir) {
            ConfirmDialog.show(this.getUI(), "Confirma a exclusão?",
                    new ConfirmDialog.Listener() {

                        public void onClose(ConfirmDialog dialog) {
                            if (dialog.isConfirmed()) {
                                try {
                                    datasource.removeItem(binder.getItemDataSource().getBean().getId());
                                    //log.debug("Excluiu a Ocorrencia!");
                                } catch (Exception e) {
                                    log.debug("Não consegui remover a ocorrência!", e);
                                    Notification.show("Não consegui remover a ocorrência!\n" + e.getMessage(), Type.ERROR_MESSAGE);
                                }
                                close();
                            }
                        }
                    });
            return;
        } else if (event.getButton() == bCancelar) {
            binder.discard();
        }
        close();
    }
}

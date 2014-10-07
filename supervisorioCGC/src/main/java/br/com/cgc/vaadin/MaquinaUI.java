package br.com.cgc.vaadin;

import br.com.cgc.vaadin.ui.MaquinaTable;
import br.com.cgc.vaadin.ui.TurnoWindow;
import br.com.cgc.vaadin.ui.MaquinaWindow;
import br.com.cgc.vaadin.ui.SobreWindow;
import br.com.cgc.vaadin.ui.TurnoTable;
import java.util.Locale;

import org.apache.log4j.Logger;

import br.com.cgc.vaadin.ds.MaquinaJPAContainer;
import br.com.cgc.vaadin.ds.TurnoJPAContainer;

import com.vaadin.annotations.Theme;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Runo;
import grafico.BSGraphcsCore;
import java.io.File;

/**
 * Classe principal da aplicação, o ponto de entrada.
 *
 * <p>Com essa classe o Vaadin cria os componentes para apresentar na página
 * principal (Browser).</p>
 *
 * <p>O método
 * <code>init</code> é executado quando uma nova sessão do navegador é
 * aberta.</p>
 *
 * @see
 * <code>web.xml</code>
 */
@Theme("runo")
@SuppressWarnings("serial")
public class MaquinaUI extends UI {

    private Logger log = Logger.getLogger(MaquinaUI.class);
    private Table tableMaquina;
    private Table tableTurno;
    private final MaquinaJPAContainer dataSourceMaquina = new MaquinaJPAContainer();
    private final TurnoJPAContainer datasourceturno = new TurnoJPAContainer();

    @Override
    protected void init(VaadinRequest request) {
        setLocale(new Locale("pt", "BR"));
        tableMaquina = new MaquinaTable(dataSourceMaquina);
        tableMaquina.addItemClickListener(new ItemClickEvent.ItemClickListener() {

            @Override
            public void itemClick(ItemClickEvent event) {
                MaquinaWindow window = new MaquinaWindow(dataSourceMaquina);
                window.edit(Integer.valueOf(event.getItemId().toString()));
            }
        });

        tableTurno = new TurnoTable(datasourceturno);
        tableTurno.addItemClickListener(new ItemClickEvent.ItemClickListener() {

            @Override
            public void itemClick(ItemClickEvent event) {
                TurnoWindow window = new TurnoWindow(datasourceturno);
                window.edit(Integer.valueOf(event.getItemId().toString()));
            }
        });

        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        setContent(content);

        VerticalLayout vertical = putAllOnVertical(
                buildTop(),
                buildTabelas(),
                buildGrafico(),
                buildBottom());
        content.addComponent(vertical);
        content.setComponentAlignment(vertical, Alignment.MIDDLE_CENTER);

        //
        addErrorHandle(content);

        //log.debug("Pagina principal inicializada!");
    }

    private VerticalLayout putAllOnVertical(Component... components) {
        VerticalLayout vertical = new VerticalLayout();
        for (Component c : components) {
            if (c != null) {
                vertical.addComponent(c);
                vertical.setComponentAlignment(c, Alignment.MIDDLE_CENTER);
            }
        }
        return vertical;
    }

    private HorizontalLayout buildLabelTabela(String label) {
        Label lProjeto = new Label(label);

        lProjeto.addStyleName(Runo.LABEL_H2);
        HorizontalLayout layoutTop = new HorizontalLayout();
        layoutTop.addComponent(lProjeto);
        layoutTop.setExpandRatio(lProjeto, 1.0f);

        layoutTop.setComponentAlignment(lProjeto, Alignment.MIDDLE_CENTER);

        return layoutTop;
    }

    private HorizontalLayout buildTop() {
        Label lProjeto = new Label("Supervisório");
        lProjeto.addStyleName(Runo.LABEL_H1);

        String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

        FileResource resource = new FileResource(new File(basepath
                + "/WEB-INF/images/cgc_logo.png"));

        Image logo = new Image("", resource);

        HorizontalLayout layoutTop = new HorizontalLayout();
        layoutTop.setHeight("180");
        layoutTop.setWidth("650");
        layoutTop.addComponent(logo);
        layoutTop.addComponent(lProjeto);
        layoutTop.setExpandRatio(lProjeto, 1.0f);

        layoutTop.setComponentAlignment(lProjeto, Alignment.MIDDLE_CENTER);

        return layoutTop;
    }

    private VerticalLayout buildTabelas() {
        HorizontalLayout h1 = new HorizontalLayout();
        h1.setHeight("300");
        h1.setWidth("1024");
        VerticalLayout verticalFinal = new VerticalLayout();

        VerticalLayout verticalTabela = new VerticalLayout();
        verticalTabela.addComponent(buildLabelTabela("Máquinas"));
        verticalTabela.addComponent(tableMaquina);
        verticalTabela.addComponent(buildBarButtonsMaquina(dataSourceMaquina));
        h1.addComponent(verticalTabela);

        verticalTabela = new VerticalLayout();
        verticalTabela.addComponent(buildLabelTabela("Turnos"));
        verticalTabela.addComponent(tableTurno);
        verticalTabela.addComponent(buildBarButtonsTurno(datasourceturno));
        h1.addComponent(verticalTabela);

        verticalFinal.addComponent(h1);
        verticalFinal.setComponentAlignment(h1, Alignment.MIDDLE_CENTER);

        return verticalFinal;
    }

    private VerticalLayout buildGrafico() {
        HorizontalLayout h1 = new HorizontalLayout();
        h1.setHeight("450");
        h1.setWidth("1024");
        VerticalLayout v1 = new VerticalLayout();
        h1.addComponent(BSGraphcsCore.getInstance().getGraphcsBarra("Grafico Teste 1",
                new String[]{"T1", "T2", "T3", "T4"},
                new String[]{"12", "30", "43", "54"},
                "Mes",
                "Marcela",
                400,
                400));



        h1.addComponent(BSGraphcsCore.getInstance().getGraphcsBarra("Grafico Teste 2",
                new String[]{"T1", "T2", "T3", "T4"},
                new String[]{"12:45:18:89", "30:45:81:89", "43:12:45:32", "54:45:65:85"},
                "Mes",
                "Marcela:Andre:Maria:Jose",
                400,
                400));
        v1.addComponent(h1);
        v1.setComponentAlignment(h1, Alignment.MIDDLE_CENTER);

        //----------------------------------------------------------------------

        h1 = new HorizontalLayout();
        h1.setHeight("450");
        h1.setWidth("1024");
        h1.addComponent(BSGraphcsCore.getInstance().getGraphcsPizza("Grafico Teste 3",
                new String[]{"T1", "T2", "T3", "T4"},
                new String[]{"12", "30", "43", "54"},
                400,
                400));



        h1.addComponent(BSGraphcsCore.getInstance().getGraphcsGauge("Grafico Teste4",
                new String[]{"T1", "T2", "T3", "T4"},
                new String[]{"12", "89", "32", "85"},
                400,
                400));

        v1.addComponent(h1);
        v1.setComponentAlignment(h1, Alignment.MIDDLE_CENTER);

        return v1;
    }

    private HorizontalLayout buildBarButtonsMaquina(final MaquinaJPAContainer datasource) {
        Button bIncluir = new Button("Incluir");
        bIncluir.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                MaquinaWindow window = new MaquinaWindow(datasource);
                window.create();
            }
        });

        Button bAtualizar = new Button("Atualizar");
        bAtualizar.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                datasource.refresh();
            }
        });

        Button bSobre = new Button("Sobre");
        bSobre.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                new SobreWindow().show();
            }
        });
        bSobre.setClickShortcut(KeyCode.F1);

        Button[] buttons = {bIncluir, bAtualizar, bSobre};
        HorizontalLayout barButton = new HorizontalLayout();
        barButton.setHeight("50");

        for (Button b : buttons) {
            b.setStyleName(Runo.BUTTON_BIG);
            barButton.addComponent(b);
            barButton.setComponentAlignment(b, Alignment.MIDDLE_CENTER);
        }

        return barButton;
    }

    private HorizontalLayout buildBarButtonsTurno(final TurnoJPAContainer datasource) {
        Button bIncluir = new Button("Incluir");
        bIncluir.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                TurnoWindow window = new TurnoWindow(datasource);
                window.create();
            }
        });

        Button bAtualizar = new Button("Atualizar");
        bAtualizar.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                datasource.refresh();
            }
        });

        Button bSobre = new Button("Sobre");
        bSobre.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                new SobreWindow().show();
            }
        });
        bSobre.setClickShortcut(KeyCode.F1);

        Button[] buttons = {bIncluir, bAtualizar, bSobre};
        HorizontalLayout barButton = new HorizontalLayout();
        barButton.setHeight("50");

        for (Button b : buttons) {
            b.setStyleName(Runo.BUTTON_BIG);
            barButton.addComponent(b);
            barButton.setComponentAlignment(b, Alignment.MIDDLE_CENTER);
        }

        return barButton;
    }

    private HorizontalLayout buildBottom() {
        String content = "<a href='http://www.cgcautomacao.com.br' "
                + "title='CGC Automação' target='open'>CGC Automação</a> "
                + "<small><strong>Projeto Supervisório</strong>,  "
                + "(<a href='http://www.cgcautomacao.com.br' title='saiba mais' target='open'>saiba mais</a>) </small>";
        Label text = new Label(content);
        text.setContentMode(ContentMode.HTML);

        HorizontalLayout layoutbottom = new HorizontalLayout();
        layoutbottom.setHeight("100");
        layoutbottom.setWidth("650");
        layoutbottom.addComponent(text);

        return layoutbottom;
    }

    /**
     * Adiciona tratador geral para exceção não capturadas.
     *
     * @param content
     */
    private void addErrorHandle(final VerticalLayout content) {
        UI.getCurrent().setErrorHandler(new DefaultErrorHandler() {

            @Override
            public void error(com.vaadin.server.ErrorEvent event) {
                String cause = "Erro durante execução:\n";
                for (Throwable t = event.getThrowable(); t != null;
                        t = t.getCause()) {
                    if (t.getCause() == null) {
                        cause += t.getClass().getName() + "\n";
                    }
                }

                log.error("Exceção capturada", event.getThrowable());
                Notification.show(cause, Type.ERROR_MESSAGE);

                doDefault(event);
            }
        });
    }
}

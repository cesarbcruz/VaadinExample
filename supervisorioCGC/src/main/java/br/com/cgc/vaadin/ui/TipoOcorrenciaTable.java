package br.com.cgc.vaadin.ui;


import br.com.cgc.vaadin.ds.TipoOcorrenciaJPAContainer;

import com.vaadin.ui.Table;
import java.text.SimpleDateFormat;

/**
 * <code>Table</code> para apresentar os registros da
 * <code>TipoOcorrencia</code>.
 *
 * @author CGC Automação
 */
public class TipoOcorrenciaTable extends Table {

    private static final long serialVersionUID = 7224804420418157408L;
    private SimpleDateFormat fmtDataHora;

    public TipoOcorrenciaTable(TipoOcorrenciaJPAContainer tipoocorrenciaContainer) {
        fmtDataHora = new SimpleDateFormat("dd/ MM/yyyy HH:mm:ss");
        setContainerDataSource(tipoocorrenciaContainer);
        setWidth("500px");
        setHeight("200px");
        configColumns();
    }

    private void configColumns() {
        setVisibleColumns(new Object[]{"id", "nome", "descricao"});
        setColumnHeaders(new String[]{"#", "Nome", "Descrição"});
    }

}

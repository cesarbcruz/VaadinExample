package br.com.cgc.vaadin.ui;


import br.com.cgc.vaadin.ds.TurnoJPAContainer;

import com.vaadin.data.Property;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import java.text.SimpleDateFormat;

/**
 * <code>Table</code> para apresentar os registros da
 * <code>Turno</code>.
 *
 * @author CGC Automação
 */
public class TurnoTable extends Table {

    private static final long serialVersionUID = 7224804420418157408L;
    private SimpleDateFormat fmtDataHora;

    public TurnoTable(TurnoJPAContainer turnoContainer) {
        fmtDataHora = new SimpleDateFormat("HH:mm:ss");
        setContainerDataSource(turnoContainer);
        setWidth("500px");
        setHeight("200px");
        configColumns();
    }

    private void configColumns() {
        setVisibleColumns(new Object[]{"id", "nome", "descricao", "entrada", "saida"});
        setColumnHeaders(new String[]{"#", "Nome", "Descrição", "Entrada", "Saida"});
        addGeneratedColumn("entrada", new DateColumnGenerator());
        addGeneratedColumn("saida", new DateColumnGenerator());
    }

    /**
     * Implementa um formatador de moeda durante a renderização da coluna.
     */
    private class DateColumnGenerator implements Table.ColumnGenerator {

        @Override
        public Object generateCell(Table source, Object itemId, Object columnId) {
            Property<?> prop = source.getItem(itemId).getItemProperty(columnId);
            if (prop.getType().equals(java.sql.Timestamp.class)) {
                Label label = new Label(fmtDataHora.format((java.sql.Timestamp) prop.getValue()));
                label.addStyleName("column-type-value");
                label.addStyleName("column-" + (String) columnId);
                return label;
            }
            return null;
        }
    }
}

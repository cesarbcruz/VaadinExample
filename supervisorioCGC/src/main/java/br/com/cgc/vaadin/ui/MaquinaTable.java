package br.com.cgc.vaadin.ui;

import java.text.NumberFormat;

import br.com.cgc.vaadin.ds.MaquinaJPAContainer;

import com.vaadin.data.Property;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;

/**
 * <code>Table</code> para apresentar os registros da <code>Maquina</code>.
 * 
 * @author CGC Automação
 */
public class MaquinaTable extends Table {

	private static final long serialVersionUID = 7224804420418157408L;

	private NumberFormat fmtCurrency;
	
	public MaquinaTable(MaquinaJPAContainer maquinaContainer) {
		fmtCurrency = NumberFormat.getNumberInstance(UI.getCurrent().getLocale());
		setContainerDataSource(maquinaContainer);
		setWidth("500px");
                                    setHeight("200px");
		configColumns();
	}
	
	private void configColumns() {
		setVisibleColumns(new Object[]{"id", "nome", "descricao", "producaominima", "producaomaxima"});
		setColumnHeaders(new String[] {"#", "Nome", "Descrição", "Produção Mínima", "Produção Máxima"});
		addGeneratedColumn("producaominima", new CurrencyColumnGenerator());
		addGeneratedColumn("producaomaxima", new CurrencyColumnGenerator());
	}
	
	/**
	 * Implementa um formatador de moeda durante a renderização da coluna.
	 */
	private class CurrencyColumnGenerator implements Table.ColumnGenerator {
		@Override
		public Object generateCell(Table source, Object itemId, Object columnId) {
			Property<?> prop = source.getItem(itemId).getItemProperty(columnId);
		        if (prop.getType().equals(Double.class)) {
		        	Label label = new Label(fmtCurrency.format((Double) prop.getValue()));
		        	label.addStyleName("column-type-value");
		            label.addStyleName("column-" + (String) columnId);
		            return label;
		        }
			return null;
		}
	}
}

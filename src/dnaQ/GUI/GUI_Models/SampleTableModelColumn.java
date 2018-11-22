package dnaQ.GUI.GUI_Models;

import dnaQ.Models.Sample;

public class SampleTableModelColumn extends CommonTableColumn {
	
	/**
	 * The Lambda interface object
	 */
	private final SampleGetValueAtOperation operation;
	
	public SampleTableModelColumn(String description, String title, Class<?> columnClass, SampleGetValueAtOperation operation) {
		super(description, title, columnClass);
		this.operation = operation;
	}
	
	/**
	 * Lambda expression function
	 */
	public Object getValue(Sample sample){
		return operation.getValue(sample);
	}
	
	/**
	 * Lambda expression interface
	 *
	 */
	public interface SampleGetValueAtOperation{
		Object getValue(Sample sample);
}

}

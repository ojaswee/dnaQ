package dnaQ.GUI.GUI_Models;

import dnaQ.Models.Sample;

public class SampleTableModelColumn  {
	
	/**
	 * The Lambda interface object
	 */
	private final SampleGetValueAtOperation operation;

	public final String description;
	public final String title;
	public final Class<?> columnClass;
	
	public SampleTableModelColumn(String description, String title, Class<?> columnClass, SampleGetValueAtOperation operation) {
		this.description = description;
		this.title = title;
		this.columnClass= columnClass;
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

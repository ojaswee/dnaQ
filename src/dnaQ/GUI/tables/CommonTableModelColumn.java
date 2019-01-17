package dnaQ.GUI.tables;

import dnaQ.Models.Sample;

public class CommonTableModelColumn {
	
	/**
	 * The Lambda interface object
	 */
	private final SampleGetValueAtOperation operation;

	public final String description;
	public final String title;
	public final Class<?> columnClass;
	
	public CommonTableModelColumn(String description, String title,
								  Class<?> columnClass, SampleGetValueAtOperation operation) {
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

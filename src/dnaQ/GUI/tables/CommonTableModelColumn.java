package dnaQ.GUI.tables;


import dnaQ.Models.Sample;

public class CommonTableModelColumn {

    private final CommonTableModelColumn.GetValueAtOperation operation;

    public final String description;
    public final String title;
    public final Class<?> columnClass;

    public CommonTableModelColumn(String description, String title, Class<?> columnClass,
                                  CommonTableModelColumn.GetValueAtOperation operation) {
        this.description = description;
        this.title = title;
        this.columnClass= columnClass;
        this.operation = operation;

    }

    public Object getValue(Sample sample){
        return operation.getValue(sample);
    }

    public interface GetValueAtOperation{
        Object getValue(Sample sample);
    }
}

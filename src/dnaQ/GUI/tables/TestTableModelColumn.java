package dnaQ.GUI.tables;

import dnaQ.Models.Test;

public class TestTableModelColumn {

        /**
         * The Lambda interface object
         */
        private final TestTableModelColumn.UserTestGetValueAtOperation operation;

        public final String description;
        public final String title;
        public final Class<?> columnClass;

        public TestTableModelColumn(String description, String title,
                                    Class<?> columnClass, TestTableModelColumn.UserTestGetValueAtOperation operation) {
            this.description = description;
            this.title = title;
            this.columnClass= columnClass;
            this.operation = operation;
        }

        /**
         * Lambda expression function
         */
        public Object getValue(Test test){
            return operation.getValue(test);
        }

        /**
         * Lambda expression interface
         *
         */

        public interface UserTestGetValueAtOperation{
            Object getValue(Test test);
        }
}

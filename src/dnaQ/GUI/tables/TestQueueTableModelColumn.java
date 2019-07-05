package dnaQ.GUI.tables;

import dnaQ.Models.TestQueue;

public class TestQueueTableModelColumn {

        /**
         * The Lambda interface object
         */
        private final TestQueueTableModelColumn.UserTestGetValueAtOperation operation;

        public final String description;
        public final String title;
        public final Class<?> columnClass;

        public TestQueueTableModelColumn(String description, String title,
                                         Class<?> columnClass, TestQueueTableModelColumn.UserTestGetValueAtOperation operation) {
            this.description = description;
            this.title = title;
            this.columnClass= columnClass;
            this.operation = operation;
        }

        /**
         * Lambda expression function
         */
        public Object getValue(TestQueue test){
            return operation.getValue(test);
        }

        /**
         * Lambda expression interface
         *
         */

        public interface UserTestGetValueAtOperation{
            Object getValue(TestQueue test);
        }
}

package sample.Access;

public class AccessTables {




    public enum ADMINISTRATION {


        TIME_OF_RECEIPT("SELECT * FROM ВРЕМЯ_ПРИЁМА","DELETE FROM ВРЕМЯ_ПРИЁМА WHERE ID_ВРЕМЯ_ПРИЁМА=?","UPDATE ВРЕМЯ_ПРИЁМА SET ID_СПЕЦИАЛИСТА=?,ДАТА=?,ВРЕМЯ=?,СТАТУС=?" +
                                "WHERE ID_ВРЕМЯ_ПРИЁМА=?","INSERT INTO ВРЕМЯ_ПРИЁМА (ID_ВРЕМЯ_ПРИЁМА,ID_СПЕЦИАЛИСТА,ДАТА,ВРЕМЯ,СТАТУС) " +
                                "VALUES (?,?,?,?,?)",1),
        CONSULTATION("SELECT * FROM КОНСУЛЬТАЦИЯ","DELETE FROM КОНСУЛЬТАЦИЯ WHERE ID_ВРЕМЯ_ПРИЁМА=?" +
                "AND ID_ПАЦИЕНТА=? AND ID_УСЛУГИ=?","UPDATE КОНСУЛЬТАЦИЯ SET СТАТУС=?,ССЫЛКА_НА_ZOOM=?" +
                "WHERE ID_ВРЕМЯ_ПРИЁМА=? AND ID_ПАЦИЕНТА=? AND ID_УСЛУГИ=?","INSERT INTO КОНСУЛЬТАЦИЯ (ID_ВРЕМЯ_ПРИЁМА,ID_ПАЦИЕНТА,ID_УСЛУГИ,СТАТУС,ССЫЛКА_НА_ZOOM) " +
                "VALUES (?,?,?,?,?)",3),
        REVIEWS("SELECT * FROM ОТЗЫВЫ","","","",4),
        A_PATIENT("SELECT * FROM ПАЦИЕНТ","DELETE FROM ПАЦИЕНТ WHERE ID_ПАЦИЕНТА=?","UPDATE ПАЦИЕНТ SET ФИО=?," +
                "ТЕЛЕФОН=?,ПОЧТА=?,ВОЗРАСТ=?" +
                "WHERE ID_ПАЦИЕНТА=?","INSERT INTO ПАЦИЕНТ (ID_ПАЦИЕНТА,ФИО,ТЕЛЕФОН,ПОЧТА,ВОЗРАСТ) " +
                "VALUES (?,?,?,?,?)",5);

        private String querySQLSelectNow;
        private String querySQLDeleteNow;
        private String querySQLUpdateNow;
        private String querySQLAddNow;
        private int column;

        ADMINISTRATION(String querySQLSelectNow, String querySQLDeleteNow, String querySQLUpdateNow, String querySQLAddNow, int column) {
            this.querySQLSelectNow = querySQLSelectNow;
            this.querySQLDeleteNow = querySQLDeleteNow;
            this.querySQLUpdateNow = querySQLUpdateNow;
            this.querySQLAddNow = querySQLAddNow;
            this.column = column;
        }

        public int getColumn() {
            return column;
        }

        public String getQuerySQLSelectNow() {
            return querySQLSelectNow;
        }

        public String getQuerySQLDeleteNow() {
            return querySQLDeleteNow;
        }

        public String getQuerySQLUpdateNow() {
            return querySQLUpdateNow;
        }


        public String getQuerySQLAddNow() {
            return querySQLAddNow;
        }

    };

    public enum DOCTOR{


        ANALYSIS("SELECT * FROM АНАЛИЗ","DELETE FROM АНАЛИЗ WHERE ID_АНАЛИЗА=?","UPDATE АНАЛИЗ SET ID_ПАЦИЕНТА=?, ДАТА=?, НАИМЕНОВАНИЕ=?, ССЫЛКА_НА_ФАЙЛ=?" +
                         "WHERE ID_АНАЛИЗА=?","INSERT INTO АНАЛИЗ (ID_АНАЛИЗА,ID_ПАЦИЕНТА,ДАТА,НАИМЕНОВАНИЕ,ССЫЛКА_НА_ФАЙЛ) " +
                         "VALUES (?,?,?,?,?)",0),
        TIME_OF_RECEIPT("SELECT * FROM ВРЕМЯ_ПРИЁМА","","","",1),
        A_PATIENT("SELECT * FROM ПАЦИЕНТ","","","",5),
        RECOMMENDATIONS("SELECT * FROM РЕКОМЕНДАЦИИ","DELETE FROM РЕКОМЕНДАЦИИ WHERE ID_РЕКОМЕНДАЦИИ=?","UPDATE РЕКОМЕНДАЦИИ SET ID_СПЕЦИАЛИСТА=?," +
                "ID_ПАЦИЕНТА=?,ДАТА=?,ТЕКСТ=?" +
                "WHERE ID_РЕКОМЕНДАЦИИ=?","INSERT INTO РЕКОМЕНДАЦИИ (ID_РЕКОМЕНДАЦИИ,ID_СПЕЦИАЛИСТА,ID_ПАЦИЕНТА,ДАТА,ТЕКСТ) " +
                "VALUES (?,?,?,?,?)",6);

        private String querySQLSelectNow;
        private String querySQLDeleteNow;
        private String querySQLUpdateNow;
        private String querySQLAddNow;
        private int column;

        public int getColumn() {
            return column;
        }

        DOCTOR(String querySQLSelectNow, String querySQLDeleteNow, String querySQLUpdateNow, String querySQLAddNow, int column) {
            this.querySQLSelectNow = querySQLSelectNow;
            this.querySQLDeleteNow = querySQLDeleteNow;
            this.querySQLUpdateNow = querySQLUpdateNow;
            this.querySQLAddNow = querySQLAddNow;
            this.column = column;
        }


        public String getQuerySQLSelectNow() {
            return querySQLSelectNow;
        }


        public String getQuerySQLDeleteNow() {
            return querySQLDeleteNow;
        }


        public String getQuerySQLUpdateNow() {
            return querySQLUpdateNow;
        }


        public String getQuerySQLAddNow() {
            return querySQLAddNow;
        }

    };

    public enum ACCOUNTANT{

        JOB_TITLE("SELECT * FROM ДОЛЖНОСТЬ","","","",2),
        SPECIALIST("SELECT * FROM СПЕЦИАЛИСТ","","","",7),
        SPECIALITY("SELECT * FROM СПЕЦИАЛЬНОСТЬ","","","",8),
        SERVICES("SELECT * FROM УСЛУГИ","DELETE FROM УСЛУГИ WHERE ID_УСЛУГИ=?","UPDATE УСЛУГИ SET ID_СПЕЦИАЛЬНОСТИ=?," +
                "ID_ДОЛЖНОСТИ=?,ТИП_ПРИЁМА=?,СТОИМОСТЬ=? WHERE ID_УСЛУГИ=?","INSERT INTO УСЛУГИ (ID_УСЛУГИ,ID_СПЕЦИАЛЬНОСТИ,ID_ДОЛЖНОСТИ,ТИП_ПРИЁМА,СТОИМОСТЬ) " +
                "VALUES (?,?,?,?,?)",9);

        private String querySQLSelectNow;
        private String querySQLDeleteNow;
        private String querySQLUpdateNow;
        private String querySQLAddNow;
        private int column;

        public int getColumn() {
            return column;
        }

        ACCOUNTANT(String querySQLSelectNow, String querySQLDeleteNow, String querySQLUpdateNow, String querySQLAddNow, int column) {
            this.querySQLSelectNow = querySQLSelectNow;
            this.querySQLDeleteNow = querySQLDeleteNow;
            this.querySQLUpdateNow = querySQLUpdateNow;
            this.querySQLAddNow = querySQLAddNow;
            this.column = column;
        }


        public String getQuerySQLSelectNow() {
            return querySQLSelectNow;
        }

        public String getQuerySQLDeleteNow() {
            return querySQLDeleteNow;
        }

        public String getQuerySQLUpdateNow() {
            return querySQLUpdateNow;
        }


        public String getQuerySQLAddNow() {
            return querySQLAddNow;
        }

    };

}

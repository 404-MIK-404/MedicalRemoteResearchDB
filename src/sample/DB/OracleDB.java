package sample.DB;


import sample.Table.TableViewForDB;


import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class OracleDB implements ConnectionsDB {


    private static OracleDB oracleDB;
    private static Connection OracleConn;

    private Statement cursorDataBaseIra;
    private QuerySQL querySQL;


    private final String PASSWORD_DB = "KOLOBOK";
    private final String LOGIN_DB = "IRA_KR";
    private final String URL_DB = "jdbc:oracle:thin:@localhost:1521:xe";

    public Statement getCursorDataBaseIra() {
        return cursorDataBaseIra;
    }

    private OracleDB() throws Exception{
        setConnectionDB();
        querySQL = new QuerySQL();
    }

    public void setConnectionDB() throws Exception {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        OracleConn = DriverManager.getConnection(this.URL_DB,this.LOGIN_DB,this.PASSWORD_DB);
        this.cursorDataBaseIra = OracleConn.createStatement();
    }


    public static OracleDB getDataBaseIra() throws Exception {
        if (oracleDB == null){
            oracleDB = new OracleDB();
        }
        return oracleDB;
    }


    public String getResultCheckPasswordLogin(String EnterUserName, String EnterPassword)  throws Exception {
        String resultAnswer = querySQL.QueryAccess(EnterUserName,EnterPassword);
        if (!"empty".equals(resultAnswer)){
            return resultAnswer;
        }
        return "not";
    }

    public boolean getResultUpdateDate(String sqlUpdate, ArrayList<String> data, TableViewForDB tableViewForDB) throws Exception{
        return querySQL.QueryChangeSQL(sqlUpdate, data, tableViewForDB);
    }

    public boolean getResultDeleteDate(String sqlDelete, ArrayList<String> data, TableViewForDB tableViewForDB) throws Exception{
        return querySQL.QueryDeleteSQL(sqlDelete,data,tableViewForDB);
    }

    public boolean getResultAddDate(String sqlAdd, ArrayList<String> data, TableViewForDB tableViewForDB) throws Exception{
        return querySQL.QueryAddSQL(sqlAdd, data, tableViewForDB);
    }




    public class QuerySQL{

        public String QueryAccess(String EnterUserName, String EnterPassword) throws Exception
        {
            ResultSet rs = cursorDataBaseIra.executeQuery("SELECT * FROM ДОСТУП_К_БД WHERE ИМЯ='"+ EnterUserName + "' AND ПАРОЛЬ='" + EnterPassword + "'");
            String result = "";
            while (rs.next()){
                result = rs.getString("ДОСТУП");
            }
            if (!result.isEmpty()){
                return result;
            }
            return "empty";
        }



        public boolean QueryDeleteSQL(String sqlDelete,ArrayList<String> data,TableViewForDB tableViewForDB) throws Exception
        {
            PreparedStatement stDelete = OracleConn.prepareStatement(sqlDelete);
            addData(data,tableViewForDB,stDelete);
            stDelete.executeUpdate();
            return true;
        }

        public boolean QueryChangeSQL(String sqlUpdate, ArrayList<String> data, TableViewForDB tableViewForDB) throws Exception
        {
            PreparedStatement stUpdate = OracleConn.prepareStatement(sqlUpdate);
            data.add(data.get(0));
            data.remove(0);
            addData(data,tableViewForDB,stUpdate);
            stUpdate.executeUpdate();
            return true;
        }


        public boolean QueryAddSQL(String sqlAdd, ArrayList<String> data, TableViewForDB tableViewForDB) throws Exception
        {
            PreparedStatement stAdd = OracleConn.prepareStatement(sqlAdd);
            addData(data,tableViewForDB,stAdd);
            stAdd.executeUpdate();
            return true;
        }

        private void addData(ArrayList<String> data,TableViewForDB tableViewForDB,PreparedStatement stAny) throws Exception{
            for (int i = 0; i < data.size();i++){
                if (tableViewForDB.isInt(data.get(i))){
                    stAny.setInt(i+1,Integer.parseInt(data.get(i)));
                } else if (tableViewForDB.isDateValid(data.get(i))){
                    SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date date = dataFormat.parse(data.get(i));
                    java.sql.Date sqlFromLong = new java.sql.Date(date.getTime());
                    stAny.setDate(i+1,sqlFromLong);
                }
                else if ("NULL".equals(data.get(i))) stAny.setNull(i+1,Types.NULL);
                else stAny.setString(i+1,data.get(i));

            }
        }

    }



}

/*
 * This class is responsible for all data access for the application and contains
 * the very general purpose methods that are used in the bean managers class.
 * Each bean managers contain specific methods to deal with the corresponding
 * tables.
 */
package model;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nina Fu
 */
public class DataAccessLayer {
//........................ D A T A   F I E L D S ............................//
//............. G L O B A L   P R I V A T E   C O N S T A N T S .............//

    /** hold default database name. */
    private static final String DATABASE_NAME = "virtual_lab";
    /** hold default field size. */
    private static final int DEFAULT_FIELD_SIZE = 20;
    /** hold default null value. */
    private static final String DEFAULT_NULL_VALUE = " ";
// ................. G L O B A L   P R I V A T E   V A R S ...................//
    /** hold a pointer to the database connection. */
    protected Connection con;
    /** hold a pointer to the MySQLConnection object. */
    private MySQLConnection sqlCon;

// ........................ C O N S T R U C T O R S ..........................//
    DataAccessLayer() {
        sqlCon = new MySQLConnection(DATABASE_NAME);
        this.con = sqlCon.getDBConnection();

    } // end of the constructor


//...................... P R I V A T E   M E T H O D S ......................//
//...................... P U B L I C   M E T H O D S ........................//

    public
    Connection getCon()
    {  return con;  }
    

    /**
     * close the database connection.
     */
    public void releaseConnection() {
        sqlCon.disconnectFromDB();

    } // end of the method

    /**
     * create a query from the table name and columns names and the desired
     * columns positions
     * @param t1 - the table name.
     * @param t1Cols - the table columns names.
     * @param t1ColsPos - the desired columns positions
     * @return the query statement.
     */
    private static String createQueryStatement(String t1Name,
            String[] t1Cols, int[] t1ColsPos) {
        StringBuilder sb =
                new StringBuilder("SELECT " + t1Cols[t1ColsPos[0]].toUpperCase());

        for (byte i = 1; i < t1ColsPos.length; i++) {
            sb.append(", ").append(t1Cols[t1ColsPos[i]].toUpperCase());
        }

        sb.append(" FROM ").append(t1Name);

        return sb.toString();

    } // end of the method

    /**
     * execute a SQL statement (query)
     * @param statement - the SQL statement
     * @return the result set.
     */
    public ResultSet executeQuery(String stmnt) {
        try {
            PreparedStatement pstmt = con.prepareStatement(stmnt);
            ResultSet rs = pstmt.executeQuery();
            return rs;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }

    } // end of the method

    /**
     * execute a SQL statement (query)
     * @param statement - the SQL statement
     * @return the result set.
     */
    private void testexecuteQuery(String keyword)
            throws ClassNotFoundException, SQLException {
        String stmnt =
                "SELECT * FROM recipe WHERE Name LIKE \'%" + keyword + "%\'";

//        String tabName = "RECIPE";
//
//		ResultSet rs = executeQuery(stmnt);
//
//        Table t = new Table(con, tabName);
//
//        t.printResultSet(rs, "   ");

    } // end of the method

    /**
     * execute a SQL statement (update)
     * @param statement - the SQL statement
     * @return the number of records affected.
     */
    public int executeUpdate(String stmnt) {
        try {
            PreparedStatement pstmt = con.prepareStatement(stmnt);
            int result = pstmt.executeUpdate();

            return result;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }

    } // end of the method

    /**
     * execute a SQL statement (query)
     * @param statement - the SQL statement
     * @return the result set.
     */
    private void testexecuteUpdate(String keyword)
            throws ClassNotFoundException, SQLException {
        String stmnt =
                "SELECT * FROM recipe WHERE Name LIKE \'%" + keyword + "%\'";

//        String tabName = "RECIPE";
//
//		ResultSet rs = executeQuery(stmnt);
//
//        Table t = new Table(con, tabName);
//
//        t.printResultSet(rs, "   ");

    } // end of the method

    /** find table columns names
     * @precondition the table exists and the user has select privilege on it.
     * @param rsmd the table meta data pointer
     * @return columns names array
     */
    private String[] getTableColName(ResultSetMetaData rsmd) {
        try {
            int noOfCols = rsmd.getColumnCount();

            String[] colNameArray = new String[noOfCols];

            for (int i = 0; i < noOfCols; i++) {
                colNameArray[i] = rsmd.getColumnName(i + 1);
            }

            return colNameArray;

        } // end of the try
        catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }

    } //end of the method

    /** find table columns size
     * @precondition the table exists and the user has select privilege on it.
     * @param rsmd the table meta data pointer
     * @return columns size array
     */
    private int[] getTableColSize(ResultSetMetaData rsmd) {
        try {
            int noOfCols = rsmd.getColumnCount();

            int[] colSizeArr = new int[noOfCols];

            for (int i = 0; i < noOfCols; i++) {
                colSizeArr[i] = rsmd.getColumnDisplaySize(i + 1);
            }

            return colSizeArr;

        } // end of the try
        catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }

    } //end of the method

    /**
     * find the optimum size of the columns as the following alg.:
     * 								CSize > 20
     *       | 20     hSize <= CSize
     * len = |
     * 		 | 20     hSize >  CSize
     *
     * 								CSize <= 20
     *       | cSize  hSize <= CSize
     * len = |
     * 		 | hSize  hSize >  CSize
     *
     * @param headerArr the table columns array
     * @param colSizeArr the table columns size array
     * @return the array of int containing the optimum size of the columns for
     * displaying as a report.
     */
    private static int[] findColsOptSize(String[] colNameArr, int[] colSizeArr) {
        int count = colNameArr.length;
        int[] colOptSizeArr = new int[count];

        for (int i = 0; i < count; i++) {
            if (colSizeArr[i] > DEFAULT_FIELD_SIZE) {
                colOptSizeArr[i] = DEFAULT_FIELD_SIZE;
            } else if (colSizeArr[i] <= DEFAULT_FIELD_SIZE
                    && colNameArr[i].length() <= colSizeArr[i]) {
                colOptSizeArr[i] = colSizeArr[i];
            } else {
                colOptSizeArr[i] = colNameArr[i].length();
            }
        }

        return colOptSizeArr;

    } //end of the method

    /** get optimum size of each column and the columns themselves, then
     * truncate or pad the values, combine them and return it in one string.
     * @param optSizeArr - the columns optimum size array
     * @param fieldsArr - the table columns that should be optimum in size.
     * @return the string of all columns combined.
     * displaying as a report.
     */
    private static String rowMaker(int[] colOptSizeArr, String[] fieldsArr) {
        int count = colOptSizeArr.length;
        String rowStr = "";

        for (int i = 0; i < count; i++) {
            if (fieldsArr[i].length() > colOptSizeArr[i]) {
                rowStr += " " + fieldsArr[i].substring(0, colOptSizeArr[i]);
            } else if (fieldsArr[i].length() == colOptSizeArr[i]) {
                rowStr += " " + fieldsArr[i];
            } else {
                int padSize = colOptSizeArr[i];
                String modFieldStr = String.format("%1$-" + padSize + "s",
                        fieldsArr[i]);
                rowStr += " " + modFieldStr;
            }
        } // end of the for-loop

        return rowStr.substring(1);

    } //end of the method

    /** make the report title under lines
     * @param optSizeArr - the columns optimum size array
     * @return the underline string
     */
    private static String titleUnderlineMaker(int[] colOptSizeArr) {
        int count = colOptSizeArr.length;

        String ch = "-";
        String rowStr = "";

        for (int i = 0; i < count; i++) {
            String fieldUnderline = "";

            for (int j = 0; j < colOptSizeArr[i]; j++) {
                fieldUnderline += ch;
            }

            rowStr += " " + fieldUnderline;
        } // end of the for-loop

        return rowStr.substring(1);

    } //end of the method

    /** get one row of the table and make a string array.
     * @param rs the table result set
     * @param colCount - number of columns
     */
    private String[] getOneRow(ResultSet rs, int colCount)
            throws SQLException, ClassNotFoundException {
        String[] colValue = new String[colCount];

        for (int i = 0; i < colCount; i++) {
            colValue[i] = rs.getString(i + 1);
            //System.out.println(colValue[i]);

            if (colValue[i] == null) {
                colValue[i] = DEFAULT_NULL_VALUE;
            }

        } // end of for-loop

        return colValue;

    } //end of the method

    /** print a ResultSet to system console.
     * @throws SQLException which is handled in caller method.
     * @param rs the result set
     */
    public void printResultSet(ResultSet rs)
            throws SQLException, ClassNotFoundException {
        ResultSetMetaData rsmd = rs.getMetaData();
        ;
        int colCount = rsmd.getColumnCount();

        String[] headerArr = getTableColName(rsmd);
        int[] colSizeArr = getTableColSize(rsmd);
        int[] colOptSizeArr = findColsOptSize(headerArr, colSizeArr);

        String rowStr = rowMaker(colOptSizeArr, headerArr);
        System.out.println(rowStr);
        String underlineStr = titleUnderlineMaker(colOptSizeArr);
        System.out.println(underlineStr);

        while (rs.next()) {
            String[] fieldsArr = getOneRow(rs, colCount);
            rowStr = rowMaker(colOptSizeArr, fieldsArr);
            System.out.println(rowStr);

        } // end of while-loop

        System.out.println("\n\n");

    } //end of method

    private void testprintResultSet()
            throws ClassNotFoundException, SQLException,
            InstantiationException, IllegalAccessException {
//        ResultSet rs = queryRecipeByID(101);
//        printResultSet(rs);
    } // end of the method.

    /**
     * make a new bean and put the result set contents into it and return it.
     * @param rs - the result set
     * @param e - the bean object that will return the contents of the result set.
     */
    private <E> void beanMakerEngine(ResultSet rs, E e) {
        Field[] f = e.getClass().getDeclaredFields();
        int index = 1;

        for (Field field : f) {
            field.setAccessible(true);
            try {
                String type = field.getType().toString();

                // private fields has modifer = 2
                if (field.getModifiers() != 2) {
                    continue;
                }

                if (type.equals("int")) {
                    field.set(e, new Integer(rs.getInt(index++)));
                } else if (type.equals("class java.lang.String")) {
                    field.set(e, rs.getString(index++));
                } else if (type.equals("class java.sql.Timestamp")) {
                    field.set(e, rs.getTimestamp(index++));
                }

            } catch (IllegalAccessException ex) {
                System.out.println(ex.getMessage());
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }

        } // end of the for-loop

    } // end of the method



    /**
     * make an arrayList and put the result set contents into it and return it.
     * @param rs - the result set
     * @param c - the class of the been
     * @return - the arrayList
     */
    public <E> ArrayList<E> resultSetPacker(
            ResultSet rs,
            Class<? extends E> c) {
        try {
            ArrayList<E> arr = new ArrayList<E>();

            while (rs.next()) {
                E e = c.newInstance();
                beanMakerEngine(rs, e);
                arr.add(e);

            } // end of the for-loop

            return arr;

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return null;
        } catch (InstantiationException ex) {
            System.err.println(ex.getMessage());
            return null;
        } catch (IllegalAccessException ex) {
            System.err.println(ex.getMessage());
            return null;
        }

    } // end of the method

 

    /**
     * get the bean fields types.
     * @param e - the bean object.
     */
    private <E> ArrayList<String> getBeanFieldType(E e) {
        ArrayList<String> arr = new ArrayList<String>();

        Field[] f = e.getClass().getDeclaredFields();
        for (Field field : f) {
            field.setAccessible(true);
            String type = field.getType().toString();
            arr.add(type);

        } // end of the for-loop 

        return arr;

    } // end of the method

 
    /**
     * get the bean fields Names.
     * @param e - the bean object.
     */
    private <E> ArrayList<String> getBeanFieldName(E e) {
        ArrayList<String> arr = new ArrayList<String>();

        Field[] f = e.getClass().getDeclaredFields();
        for (Field field : f) {
            field.setAccessible(true);
            String name = field.getName();
            arr.add(name);

        } // end of the for-loop

        return arr;

    } // end of the method

 
    /**
     * get the bean fields Names.
     * @param e - the bean object.
     */
    private <E> ArrayList<String> getBeanFieldValue(E e) {
        ArrayList<String> arr = new ArrayList<String>();

        Field[] f = e.getClass().getDeclaredFields();
        for (Field field : f) {
            field.setAccessible(true);
            try {
                Object value = field.get(e);

                if (value == null) {
                    arr.add("null");
                } else {
                    arr.add(value.toString());
                }
            } catch (IllegalAccessException ex) {
                System.out.println(ex.getMessage());
            }

        } // end of the for-loop

        return arr;

    } // end of the method

 
    /**
     * make a string of all values of an array separated by comma.
     * @param valueArr - the input array.
     * @return - the string of the array values.
     */
    private static String createCommaSeparatedStr(
            ArrayList<String> NameArr,
            ArrayList<String> valueArr, ArrayList<String> typeArr) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < NameArr.size(); i++) {
            if (valueArr.get(i).toUpperCase().equals("NULL")) {
                continue;
            } else if (typeArr.get(i).equals("int")
                    && Integer.valueOf(valueArr.get(i)) < 0) {
                continue;
            } else {
                result.append(", ").append(NameArr.get(i));
            }
        }

        return result.toString().substring(2);

    } // end of the method

    /**
     * make a string of all values of an array separated by comma. It looks at
     * the corresponding numOrStr and if it is 0 means that the value is a
     * String. So, it wrapped it in single quotes.
     * @param valueArr - the input array.
     * @return - the string of the array values.
     */
    private static String createCommaSeparatedStrWrapped(
            ArrayList<String> valueArr, ArrayList<String> typeArr) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < valueArr.size(); i++) {
            // remove \' from the char types
            valueArr.set(i, valueArr.get(i).replaceAll("[']", "\'"));


            if (valueArr.get(i).toUpperCase().equals("NULL")) {
                continue;
            } else if (typeArr.get(i).equals("int")
                    && Integer.valueOf(valueArr.get(i)) < 0) {
                continue;
            } else if (!typeArr.get(i).equals("int")) {
                result.append(", \'").append(valueArr.get(i)).append("\'");
            } else {
                result.append(", ").append(valueArr.get(i));
            }
        }

        return result.toString().substring(2);

    } // end of the method

    /** create INSERT statement
     * @param tabName - the table name.
     * @param conName - the array of the columns names.
     * @param values - the values array
     * @return the INSERT statement.
     */
    private static String createINSERTStatement(
            String tabName, ArrayList<String> nameArr,
            ArrayList<String> valueArr, ArrayList<String> typeArr) {
        String colStr = createCommaSeparatedStr(nameArr, valueArr, typeArr);
        String valStr = createCommaSeparatedStrWrapped(valueArr, typeArr);

        String insertStr = "INSERT INTO %s (%s) \nVALUES (%s);";

        return String.format(insertStr, tabName, colStr, valStr);

    } //end of the method

    private static void testcreateINSERTStatement() {
        String tabName = "test";
        ArrayList<String> nameArr = new ArrayList();
        nameArr.add("a");
        nameArr.add("b");
        nameArr.add("c");

        ArrayList<String> valueArr = new ArrayList();
        valueArr.add("25");
        valueArr.add("ahmad");
        valueArr.add("-34");

        ArrayList<String> typeArr = new ArrayList();
        typeArr.add("int");
        typeArr.add("String");
        typeArr.add("int");

        System.out.println(
                createINSERTStatement(tabName, nameArr, valueArr, typeArr));

    } //end of the method

    /**
     * make the where clause for delete.
     * @param nameArr - the array of the columns names.
     * @param valueArr - the values array
     * @param typeArr - the type array
     * @return the where clause.
     */
    private static String createWHEREClause(ArrayList<String> nameArr,
            ArrayList<String> valueArr, ArrayList<String> typeArr) {
        StringBuilder result = new StringBuilder();
        String tempStr = "";

        for (int i = 0; i < valueArr.size(); i++) {
            // remove \' from the char types
            valueArr.set(i, valueArr.get(i).replaceAll("[']", "\'"));

            if (valueArr.get(i).toUpperCase().equals("NULL")) {
                continue;
            } else if (typeArr.get(i).equals("int")
                    && Integer.valueOf(valueArr.get(i)) < 0) {
                continue;
            } else if (!typeArr.get(i).equals("int")) {
                tempStr = String.format("AND   %s = \'%s\'\n",
                        nameArr.get(i), valueArr.get(i));
            } else {
                tempStr = String.format("AND   %s = %s\n",
                        nameArr.get(i), valueArr.get(i));
            }

            result.append(tempStr);

        } // end of the for-loop

        if (result.toString().equals("")) {
            return null;
        } else {
            return result.toString().substring(6);
        }

    } // end of the method

    /** create DELET statement
     * @param tabName - the table name.
     * @param nameArr - the array of the columns names.
     * @param valueArr - the values array
     * @param typeArr - the type array
     * @return the delete statement.
     */
    private static String createDELETEStatement(
            String tabName, ArrayList<String> nameArr,
            ArrayList<String> valueArr, ArrayList<String> typeArr) {
        String deleteStr = "";
        String whereClauseStr = createWHEREClause(nameArr, valueArr, typeArr);

        if (whereClauseStr != null) {
            deleteStr = String.format("DELETE FROM %s \nWHERE %s",
                    tabName, whereClauseStr);
        } else {
            deleteStr = String.format("DELETE FROM %s", tabName);
        }

        return deleteStr;

    } //end of method

    private static void testcreateDELETEStatement() {
        String tabName = "test";
        ArrayList<String> nameArr = new ArrayList();
        nameArr.add("a");
        nameArr.add("b");
        nameArr.add("c");

        ArrayList<String> valueArr = new ArrayList();
        valueArr.add("-25");
        valueArr.add("null");
        valueArr.add("-34");

        ArrayList<String> typeArr = new ArrayList();
        typeArr.add("int");
        typeArr.add("String");
        typeArr.add("int");

        System.out.println(
                createDELETEStatement(tabName, nameArr, valueArr, typeArr));

    } //end of the method

    /**
     * get a bean and delete the corresponding record from the table.
     * @param e - the bean object.
     * @return  true if the delete operation is successful and false otherwise.
     */
    public <E> boolean beanDeleteEngine(E e) {
        String tabName = e.getClass().getSimpleName();

        ArrayList<String> nameArr = getBeanFieldName(e);
        ArrayList<String> typeArr = getBeanFieldType(e);
        ArrayList<String> valueArr = getBeanFieldValue(e);

        String stmnt = createDELETEStatement(
                tabName, nameArr, valueArr, typeArr);
        //Test!
        //System.out.println(stmnt);

        int result = executeUpdate(stmnt);

        if (result == -1) {
            return false;
        } else {
            return true;
        }

    } // end of the method

 

    /**
     * return most recently inserted recipe id by user_id
     * @param user_id - the user id
     * @return the recipe_id.
     */
    private int getRecipeIDAfterInsertion(int user_id)
    {
        try
        {
            String stmnt = String.format(
                    "SELECT   id " +
                    "FROM     recipe " +
                    "WHERE    user_id = %d " +
                    "ORDER BY postedDate DESC " +
                    "LIMIT    1", user_id);
            ResultSet rs = executeQuery(stmnt);
            rs.next();
            if (rs == null)
            {
                return -1;
            }
            int recipe_id = rs.getInt(1);
            //Test!
            //System.out.println(arr.toString());
            return recipe_id;
        }

        catch (SQLException ex)
        {
            System.err.println(ex.getMessage());
            return -1;
        }

    } // end of the method.

    private void testgetRecipeIDAfterInsertion()
    {
        int recipe_id = getRecipeIDAfterInsertion(1);

        System.out.println(recipe_id);

    } // end of the method.

    /**
     * get a bean and insert it into the appropriate table.
     * @param e - the bean object.
     * @return  true if the insert is successful and false otherwise.
     */
    public <E> boolean beanInsertEngine(E e) {
        String tabName = e.getClass().getSimpleName();

        ArrayList<String> nameArr = getBeanFieldName(e);
        ArrayList<String> typeArr = getBeanFieldType(e);
        ArrayList<String> valueArr = getBeanFieldValue(e);

        String stmnt = createINSERTStatement(
                tabName, nameArr, valueArr, typeArr);

        //Test!
        //System.out.println(stmnt);

        int result = executeUpdate(stmnt);

        if (result == -1) {
            return false;
        } else {
            return true;
        }

    } // end of the method

 
    /**
     * return user ID by userName
     * @param userName - the user name
     * @return the user id.
     */
    public int getUserIDByUserName(String userName)
            throws ClassNotFoundException, SQLException,
            InstantiationException, IllegalAccessException {
        String stmnt = String.format(
                "SELECT   id "
                + "FROM     user "
                + "WHERE    userName = \'%s\' "
                + "LIMIT    1",
                userName);

        ResultSet rs = executeQuery(stmnt);
        rs.next();
        if (rs == null) {
            return -1;
        }

        return rs.getInt(1);

    } // end of the method.

    private void testgetUserIDByUserName()
            throws ClassNotFoundException, SQLException,
            InstantiationException, IllegalAccessException {
        String userName = "Duc";
        System.out.println(getUserIDByUserName(userName));

    } // end of the method.

    /**
     * get a User bean and insert it into the table.
     * @param user - the bean object.
     * @return the user id
     */
    public int insertUser(User user)
            throws ClassNotFoundException, SQLException,
            InstantiationException, IllegalAccessException {
        String userName = user.getUserName();
        boolean isOK = beanInsertEngine(user);

        if (isOK) {
            return getUserIDByUserName(userName);
        } else {
            return -1;
        }

    } // end of the method.

 

    /**
     * return username by ID
     * @param userName - the user name
     * @return the user id.
     */
    public String getUserNameByID(int id)
    {
        try
        {
            String stmnt = String.format(
                    "SELECT UserName " +
                    "FROM   user " +
                    "WHERE  ID = %d " +
                    "LIMIT  1", id);

            ResultSet rs = executeQuery(stmnt);
            rs.next();

            if (rs == null) return null;
            else            return rs.getString(1);
        }

        catch (SQLException ex)
        {
            System.err.println(ex.getMessage());
            return null;
        }

    } // end of the method.

    /**
     * return username by ID
     * @param id - User id
     * @return The User with that id or null.
     */
    public User getUserByID(int id) {
        User u = null;

        try {
            u = getUserByUserName(getUserNameByID(id));
        } catch (Exception e) {
        }
        return u;
    }

    /**
     * return all Users beans for  a specific user name
     * @param userName - the user's name
     * @return the array list of the users.
     */
    public User getUserByUserName(String userName) {
        User u = null;
        try {
            String stmnt = String.format(
                "SELECT   * "
                + "FROM     user_login "
                + "WHERE    UserName = \'%s\' "
                + "LIMIT    1", userName);

        ResultSet rs = executeQuery(stmnt);
     
        if(rs.next()){
        	int id = rs.getInt("ID");
        	String UserName = rs.getString("UserName");
        	String UserPass = rs.getString("UserPass");
        	String type = rs.getString("type");
        	String AbleToManage = rs.getString("AbleToManage");
        	u = new User(UserName, UserPass, type, AbleToManage);
        	u.setUserId(id);
        }


        }
        catch (Exception e) {
            System.out.println("DAL: Error in getUserByUserName:"+e.toString());
            u = null;
        }
        return u;
    } // end of the method.
    
    public Admin getAdminByUserName(String userName) {
        Admin u = null;
        try {
            String stmnt = String.format(
                "SELECT   * "
                + "FROM     admin_login "
                + "WHERE    UserName = \'%s\' "
                + "LIMIT    1", userName);

        ResultSet rs = executeQuery(stmnt);
     
        if(rs.next()){
        	int id = rs.getInt("ID");
        	String UserName = rs.getString("UserName");
        	String UserPass = rs.getString("UserPass");
           	u = new Admin(UserName, UserPass);
        	u.setUserId(id);
        }


        }
        catch (Exception e) {
            System.out.println("DAL: Error in getAdminByUserName:"+e.toString());
            u = null;
        }
        return u;
    } // end of the method.

    private void testgetUserByUserName() throws
            ClassNotFoundException, SQLException,
            InstantiationException, IllegalAccessException {
        String userName = "Duc";
        User user = getUserByUserName(userName);

        System.out.println(user.toString());
    }

    /**
     * make a set clause for UPDATE.
     * @param nameArr - the array of the columns names.
     * @param valueArr - the values array
     * @param typeArr - the type array
     * @return the set clause.
     */
    private static String createSETClause(ArrayList<String> nameArr,
            ArrayList<String> valueArr, ArrayList<String> typeArr) {
        StringBuilder result = new StringBuilder();
        String tempStr = "";

        for (int i = 0; i < valueArr.size(); i++) {
            // remove \' from the char types
            valueArr.set(i, valueArr.get(i).replaceAll("[']", "\'"));

//			if (valueArr.get(i).toUpperCase().equals("NULL"))
//				continue;

            if ((typeArr.get(i).equals("int")
                    && Integer.valueOf(valueArr.get(i)) == -1)
                    || (!typeArr.get(i).equals("int")
                    && valueArr.get(i).equals("-1"))) {
                continue;
            } else if (!typeArr.get(i).equals("int")
                    && valueArr.get(i).toUpperCase().equals("NULL")) {
                tempStr = String.format(",   %s = null\n", nameArr.get(i));
            } else if (!typeArr.get(i).equals("int")
                    && !valueArr.get(i).toUpperCase().equals("NULL")) {
                tempStr = String.format(",   %s = \'%s\'\n",
                        nameArr.get(i), valueArr.get(i));
            } else {
                tempStr = String.format(",   %s = %s\n",
                        nameArr.get(i), valueArr.get(i));
            }

            result.append(tempStr);

        } // end of the for-loop

        if (result.toString().equals("")) {
            return null;
        } else {
            return result.toString().substring(4);
        }

    } // end of the method


    /** create DELET statement
     * @param tabName - the table name.
     * @param nameArr - the array of the columns names.
     * @param valueArr - the values array
     * @param typeArr - the type array
     * @param whereClause - the where clause string
     * @return the delete statement.
     */
    private static String createUPDATEStatement(
            String tabName,
            ArrayList<String> nameArr, ArrayList<String> valueArr,
            ArrayList<String> typeArr, String whereClause) {

        String setClauseStr = createSETClause(nameArr, valueArr, typeArr);

        return String.format("UPDATE %s \nSET %s%s",
                tabName, setClauseStr, whereClause);

    } //end of method


    private void testcreateUPDATEStatement() {
        String tabName = "test";
        ArrayList<String> nameArr = new ArrayList();
        nameArr.add("a");
        nameArr.add("b");
        nameArr.add("c");

        ArrayList<String> valueArr = new ArrayList();
        valueArr.add("25");
        valueArr.add("null");
        valueArr.add("-1");

        ArrayList<String> typeArr = new ArrayList();
        typeArr.add("int");
        typeArr.add("String");
        typeArr.add("int");

        String whereClause = "WHERE recipe_id = 101";

        System.out.println(createUPDATEStatement(
                tabName, nameArr, valueArr, typeArr, whereClause));

    } //end of the method


    /**
     * get two beans and update the corresponding record from the table.
     * @param value - the bean object that contain values.
     * @param where - the bean object that contain where clause values.
     * @return  true if the update operation is successful and false otherwise.
     */
    public <E> boolean beanUpdateEngine(E value, String whereClause) {
        String tabName = value.getClass().getSimpleName();

        ArrayList<String> nameArr = getBeanFieldName(value);
        ArrayList<String> typeArr = getBeanFieldType(value);
        ArrayList<String> valueArr = getBeanFieldValue(value);

        String stmnt =
                createUPDATEStatement(tabName, nameArr, valueArr, typeArr, whereClause);

        //Test!
        //System.out.println("Statement: \n" + stmnt);

        int result = executeUpdate(stmnt);

        if (result == -1) return false;
        else              return true;

    } // end of the method


 


    /**
     * update User table by user id.
     * @param u - the user bean. It contain the user id and other stuffs to be
     * changed.
     * @return  true if the update operation is successful and false otherwise.
     */
    public boolean updateUserByID(User u) {
        String whereClause = String.format("WHERE id = %d", u.getUserId());
        return beanUpdateEngine(u, whereClause);

    } // end of the method



    /**
     * encrypt password.
     * @param pass - the password that should be encrypted.
     * @return the encrypted password.
     */
    public static String encryptPass(String pass) {
        try {
            byte[] bytesOfMessage = pass.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] md5hash = md.digest(bytesOfMessage);

            BigInteger i = new BigInteger(1, md5hash);

            return String.format("%1$032X", i);

        } catch (UnsupportedEncodingException e) {
            System.err.println(e.getMessage());
            return null;
        } catch (NoSuchAlgorithmException e) {
            System.err.println(e.getMessage());
            return null;
        }

    } // end of the method


    private static void testencryptPass() {
        String pass = ""
                + "Josh4";

        System.out.println("[" + encryptPass(pass) + "]");

    } // end of the method.




    /**
     * convert temp user object to user object
     * @param tu - the temp user bean.
     * @return  user object.
     */
 
    /**
	 * show an array of the objects.
	 * @param arr - the array
	 */
	public static <E> void showObject(E[] arr)
	{
		System.out.println("...........................................");

        if (arr == null) return;

		for (int i = 0; i < arr.length; i++)
		{
			System.out.println("Element # " + i + " = [" + arr[i].toString() + "]");

		}// end of the for-loop

	} // end of the method


    /**
     */
//........................ M A I N   M E T H O D ............................//
    /**
     * This main method is just for testing this class.
     * @param args the arguments
     */
    public static void main(String[] args) throws
            ClassNotFoundException, SQLException,
            InstantiationException, IllegalAccessException,
            NoSuchAlgorithmException, UnsupportedEncodingException {
        /* ....... REQUIREMENT TO RUN THIS CLASS ........*/
        DataAccessLayer dal = new DataAccessLayer();
        /* .............................................*/

        dal.testencryptPass();

//        User u = new User("Ron", "1", "ron@gmail.com", "121.jpg");
//        System.out.println(dal.insertUser(u));

        /* .............................................*/
        dal.releaseConnection();

    } // end of the main method.
} // end of the class


/*
 * this class contains all operations needed to deal with users. It extends
 * DataAccessLayer class which contains more general methods to handle all
 * kind of beans and tables.
 *
 */
package model;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Nina
 */
public class AdminManager extends DataAccessLayer
{

    Connection connection;

    public Admin getUserInfo(String name) {

        Admin user = new Admin();
        try {
            user = getAdminByUserName(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
    
    public boolean validatePass(String name, String pass){
        Admin u = getAdminByUserName(name);
        /* disable encrypted password
         String encryptedPass = DataAccessLayer.encryptPass(pass);
        if(u.getUserPass().equals(encryptedPass))
            return true;    
        */
        
        if(u.getUserPass().equals(pass))
        	return true;
        return false;
    }

        /*
     * updateUserPassword
     * @param u - Take the user password in the object u and update the db.
     * The User id is used to determine which user, but all other fields are ignored.
     * @return - true if successful, false otherwise
     */
//    public boolean updateUserPassword(User u) {
//
//      //  String encryptedPword = DataAccessLayer.encryptPass(u.getUserPass());
//      //  u.setUserPass(encryptedPword);
//    	  u.setUserPass(u.getUserPass());
//
//        boolean result = false;
//        try {
//            result = beanUpdateEngine(u, "WHERE id=" + u.getUserId());
//            System.out.println("UserManager: attempted update of password: "+result);
//        } catch (Exception e) {
//            System.out.println("UserManager: Error updating User image.");
//            result = false;
//        }
//        return result;
//    }

//    public void updateUserInfo(int userId, String userPass, String userEmail) {
//        try {
//
//            Statement statement = connection.createStatement();
//
//            String querySQL_pass = "UPDATE User SET UserPass=\'" + userPass + "\' WHERE user.ID=" + userId;
//            ;
//            String querySQL_email = "UPDATE User SET email=\'" + userEmail + "\' WHERE user.ID=" + userId;
//
//            if (userPass != null && !userPass.equals("")) {
//                statement.executeQuery(querySQL_pass);
//            }
//            if (userEmail != null && !userEmail.equals("")) {
//                statement.executeQuery(querySQL_email);
//            }
//
//            statement.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

  

//    /**
//     * return username by ID
//     * @param id - User id
//     * @return The User with that id or null.
//     */
//    public User getUserByEmail(String email) {
//        User u = null;
//        try {
//            String stmnt = String.format(
//                    "SELECT   * "
//                    + "FROM     user "
//                    + "WHERE    email = \'%s\' "
//                    + "LIMIT    1",
//                    email);
//            ResultSet rs = executeQuery(stmnt);
//            rs.next();
//            if (rs == null) {
//                System.out.println("no result");
//                u = null;
//            } else {
//                u = new User();
//                u.setUserId(rs.getInt(1));
//                u.setUserName(rs.getString(2));
//                u.setUserPass(rs.getString(3));
//                u.setEmail(rs.getString(4));
//                u.setImage_url(rs.getString(5));
//                //TODO: Ahmad get this working. Does not work
//                //ArrayList<User> arr = resultSetPacker(rs, User.class);
//            }
//        } catch (Exception e) {
//            System.out.println("DAL: Error in getUserByEmail: "+e.toString());
//            u = null;
//        }
//        return u;
//    }

 



//    /*
//     * addUser
//     * Adds a User to the DB using the DAL
//     * @param u - User to be added. ID of User object will be updated during insertion.
//     * @returns the User with its modified ID based on insertion in the DB.
//     */
//    public User addUser(User u) {
//
//       // String encryptedPword = DataAccessLayer.encryptPass(u.getUserPass());
//        //u.setUserPass(encryptedPword);
//    	u.setUserPass(u.getUserPass());
//
//        int result = -1;
//        try {
//            result = insertUser(u);
//        } catch (Exception e) {
//            //should have logging code to report this error
//        }
//        u.setUserId(result);
//        return u;
//    }
//
//    public User addUser(String name, String pword, String email) {        
//        return (addUser(new User(name, pword, email)));
//    }
//
//
//    public User addUser(String name, String pword, String email, String image) {
//        return (addUser(new User(name, pword, email, image)));
//    }

  


   /**
     */
//........................ M A I N   M E T H O D ............................//
	/**
	 * This main method is just for testing this class.
	 * @param args the arguments
	 */
	public static void main(String[] args)
	{
        AdminManager um = new AdminManager();

	//	um.testactivateUser();

		/* .............................................*/
        um.releaseConnection();

	} // end of the main method.


} //end of the class


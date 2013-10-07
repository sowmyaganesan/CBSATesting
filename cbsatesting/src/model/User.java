/*
 * this class is the object version of the table User.
 */

package model;

/**
 *
 * @author Nina Fu
 */
public class User
{
//........................ D A T A   F I E L D S ............................//
    private int    id;
    private String userName;
    private String userPass;
    private String type;
    private String ableToManage;
    
//    private String email;
//    private String image_url;

    private static final int NO_ID = -1;

//........................ C O N S T R U C T O R S ..........................//

     public User()
    {
        id        = NO_ID;
        userName  = "";
        userPass  = "";
        type = "";
        ableToManage = "";
    } // end of the constructor


    public User(String userName, String userPass, String type, String ableToManage)
    {
		this.userName = userName;
		this.userPass = userPass;
		this.type    = type;
		this.ableToManage = ableToManage;
	} // end of the constructor



    
//...................... P U B L I C   M E T H O D S ........................//

	@Override
    public String toString()
    {
        return                 "[id        = " +
            this.id        + "] [userName  = " +
            this.userName  + "] [userPass  = " +
            this.userPass  + "] [type     = " +
            this.type     + "] [ableToManage = " +
            this.ableToManage + "]";
    } // end of the method


    /**
	 * @return the userId
	 */
	public int getUserId() {
		return this.id;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.id = userId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return this.userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the userPass
	 */
	public String getUserPass() {
		return this.userPass;
	}

	/**
	 * @param userPass the userPass to set
	 */
	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

	/**
	 * @return the email
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * @param email the email to set
	 */
	public void setType(String type) {
		this.type = type;
	}

    /**
	 * @return the email
	 */
	public String getAbleToManage() {
		return this.ableToManage;
	}

	/**
	 * @param email the email to set
	 */
	public void setAbleToManage(String ableToManage) {
		this.ableToManage = ableToManage;
	}

} // end of the class
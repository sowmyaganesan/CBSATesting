package cbsa.test.simulation;

import java.io.*;
import java.net.URLEncoder;
import java.sql.DriverManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class SeleniumTest {
	private String testURL;
	private String testSuite;
	private String suiteName;
	private long timeStamp;
	private String resultPath;
	private String testProject;
	private final String SERVICE_ROOTPATH = "/Users/arleen/Documents/workspace/cbsatesting/WebContent/";
	
	//FIXME need to update the selenium location
	public SeleniumTest(String url, String tp, String suite) {
		testURL = url;
		testProject=tp;
		suiteName = suite;
		testSuite = SERVICE_ROOTPATH+"seleniumTS/"+tp+"/"+suite;
		timeStamp = System.currentTimeMillis();
		resultPath = SERVICE_ROOTPATH+"seleniumResults/"+tp+"/"
			+ timeStamp + ".html";

	}

	public void setUp() {
		try {
			Runtime rt = Runtime.getRuntime();

	String cmd = "java -jar /Users/arleen/Documents/workspace/cbsatesting/WebContent/WEB-INF/lib/selenium-server-standalone-2.32.0.jar -htmlSuite *firefox "
					+ testURL
					+ " "
					+ testSuite
					+ " "
					+ resultPath
					+ " ";
					
		/*	String cmd = "java -jar -classpath \"/Users/arleen/Downloads/*\" \"selenium-server-standalone-2.31.0.jar\" -htmlSuite \"*firefox\"  \""
					+ testURL
					+ "\" \""
					+ testSuite
					+ "\" \""
					+ resultPath
					+ "\" ";
*/
			System.out.println("the cmd test is:"+cmd);
			Process pr = rt.exec(cmd);

		
			BufferedReader input = new BufferedReader(new InputStreamReader(
					pr.getInputStream()));

			String line = null;

			while ((line = input.readLine()) != null) {
				System.out.println(line);
			}
			BufferedReader input2 = new BufferedReader(new InputStreamReader(
					pr.getErrorStream()));

			String line2 = null;

			while ((line2 = input2.readLine()) != null) {
				System.out.println(line2);
			}
			

			int exitVal = pr.waitFor();
			System.out.println(exitVal);
			System.out.println("Saving results to database... ");
			saveToDB();	

		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}

	private void saveToDB() throws IOException {
		java.sql.Connection connection = null;
		String resultFile=SERVICE_ROOTPATH+"seleniumResults/"+testProject+"/"+timeStamp+".html";
	
		try {
			File input = new File(resultFile);
			Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
			java.sql.Statement statement = null;
			//Document doc = Jsoup.connect(resultFile).get();
			//System.out.println(doc);
			Elements lines = doc.select("td");
			//System.out.println(lines);
	        int pass=Integer.parseInt(lines.get(7).text());
	        System.out.println(pass);
	        int fail=Integer.parseInt(lines.get(9).text());	        
	        System.out.println(fail);
	        
			String connectionURL = "jdbc:mysql://localhost:3306/CBSA";

			Class.forName("com.mysql.jdbc.Driver").newInstance();

			connection = DriverManager.getConnection(connectionURL, "root", "");
			statement = connection.createStatement();
			String testSuiteName = suiteName;
			String cont= "(NULL,"+"'"+testProject+"',2, NULL,'"+ URLEncoder.encode(testURL)+ "', '"+ URLEncoder.encode(testSuiteName)+ "', '"+ timeStamp+ "', "+pass+", "+fail+", NULL)";
            System.out.println(cont);
            statement.executeUpdate("INSERT INTO `cbsa`.`seleniumresult` VALUES " +cont);
		} catch (Exception ex) {
			System.out.println("Unable to connect to database. "
					+ ex.getMessage());
			ex.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
					System.out.println("Database connection terminated");
				} catch (Exception e) { /* ignore close errors */
				}
			}
		}

	}
}
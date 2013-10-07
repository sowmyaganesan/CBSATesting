import java.io.BufferedReader;
import java.io.InputStreamReader;


public class TestClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			
		Runtime rt = Runtime.getRuntime();

		String cmd = "ls /Users/arleen/Documents/workspace/cbsatesting/WebContent/WEB-INF/lib/selenium-server-standalone-2.32.0.jar";
						
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
				System.out.println("done with the printing");
			} catch (Exception e) {
				System.out.println(e.toString());
				e.printStackTrace();
			}
	}

}

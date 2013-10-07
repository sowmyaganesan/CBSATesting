package cbsa.scalability;

import java.sql.Connection;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.*;
import java.util.ArrayList;
import java.util.Random;

import cbsa.monitor.chart.JSONException;
import cbsa.monitor.chart.JSONObject;

/**
 * Collects data from SaaS Watch for scalability model. 
 *
 */
public class Collect {

	private Connection connection;
	private String connectionURL;
	
	private JSONObject jsonObj;
	
	private int modelID;
	public ArrayList<String> collectedMetrics;
	public ArrayList<Double> collectedData;
	public ArrayList<String> modelMetrics;
	public ArrayList<Integer> metricIDs;
	public ArrayList<Double> metricData;
	
	public double area;
	boolean modelIsRunning;
	
	public Collect(String jsonString)
	{
		
		try {
			jsonObj = new JSONObject(jsonString);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		connection = null;
		connectionURL = "jdbc:mysql://localhost:3306/cbsa";
		modelIsRunning = false;
		
		try
		{ 
			// declare a connection by using Connection interface 
			
			// Load JBBC driver "com.mysql.jdbc.Driver" 
			Class.forName("com.mysql.jdbc.Driver").newInstance(); 
			
			/* Create a connection by using getConnection() method that takes parameters of 
			string type connection url, user name and password to connect to database. */ 
			connection = DriverManager.getConnection(connectionURL, "root", "");
			
			// check weather connection is established or not by isClosed() method 
			if(connection.isClosed())
			{
				throw new Exception();
			}
			
			extractValues();
			if (!modelIsRunning) return;

			getArea();
			updateDB();
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally
		{
			if (connection != null) {
				try {
					connection.close();
					//System.out.println("Database connection terminated");
				} catch (Exception e) {
				}
			}
		}
	}
	
	private void extractValues() {
		// get modelID
		try {
			this.modelID = jsonObj.getInt("ModelID");
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		// get model's metrics
		try {
			getCollectedMetrics();
			if (!modelIsRunning) return;

			getCollectedData();
			
			getModelMetrics();
			getmetricData();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	/** 
	 * Finds what metrics were asked to be collected for the given model. 
	 * @throws SQLException
	 */
	private void getCollectedMetrics() throws SQLException {
		String getModel = "SELECT * FROM scalabilitymodel WHERE ModelID = " + modelID;
		PreparedStatement getModelStatement = connection.prepareStatement(getModel);
		ResultSet getModelResult = getModelStatement.executeQuery();
		if (getModelResult.next() && getModelResult.getString("Status").equals("(running)")) 
		{
			modelIsRunning = true;
			
			String getMetrics = "Select scalabilitymetric.Name, scalabilitymetric.Statistic from scalabilitymetric where scalabilitymetric.MetricID in "
					+ "(Select scalabilitymodelmetric.MetricID from scalabilitymodelmetric where scalabilitymodelmetric.ModelMetric = 0 and "
					+ "scalabilitymodelmetric.ModelID = " + modelID + ")";
			PreparedStatement getMetricsStatement = connection.prepareStatement(getMetrics);
			ResultSet getMetricsResult = getMetricsStatement.executeQuery();
			
			collectedMetrics = new ArrayList<String>();
			while (getMetricsResult.next()) 
			{
				getSystemMetrics(getMetricsResult.getString("Name"), getMetricsResult.getString("Statistic"));
			}
		}
		
		getModelResult.close();
		getModelStatement.close();
	}

	/**
	 * Recursively gets system metrics for user defined metrics. 
	 * @param metric
	 * @param statistic
	 */
	public void getSystemMetrics(String metric, String statistic)
	{
		if (statistic.equals("None")) {
			try {
				String getEquation = "SELECT Equation FROM scalabilitymetric WHERE Name='" + metric + "' AND Statistic='None' AND Type='User Defined'";
				PreparedStatement getEquationStatement = connection.prepareStatement(getEquation);
				ResultSet getEquationResult = getEquationStatement.executeQuery();
				if(getEquationResult.next())
				{
					String[] equation = getEquationResult.getString("Equation").split(" ");
					getSystemMetrics(equation[2], equation[4]);
					getSystemMetrics(equation[8], equation[10]);
				}
			}
			catch (Exception ex) {
				// Exception Occurred. 
			}
		}
		else {
			if(!collectedMetrics.contains(metric + "-" + statistic))
				collectedMetrics.add(metric + "-" + statistic);
		}
	}
	
	/** 
	 * Gets the data collected from the json object for each collected metric. 
	 */
	public void getCollectedData()
	{			
		// go through jsonobject for each collectedmetric and get collecteddata
		collectedData = new ArrayList<Double>();
		for(int i = 0; i < collectedMetrics.size(); i++)
		{
			try {
				collectedData.add(i, jsonObj.getDouble(collectedMetrics.get(i)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Get the metrics for a model.
	 */
	private void getModelMetrics() {
		modelMetrics = new ArrayList<String>();
		metricIDs = new ArrayList<Integer>();
		try {
			String getModelMetrics = "SELECT scalabilitymetric.Name, scalabilitymetric.Statistic, scalabilitymetric.MetricID FROM scalabilitymetric WHERE" 
					+ " scalabilitymetric.MetricID IN (SELECT scalabilitymodelmetric.MetricID FROM scalabilitymodelmetric WHERE scalabilitymodelmetric.ModelMetric = 0"
					+ " AND scalabilitymodelmetric.ModelID = " + modelID + ")";
			PreparedStatement getModelMetricsStatement = connection.prepareStatement(getModelMetrics);
			ResultSet getModelMetricsResult = getModelMetricsStatement.executeQuery();
			while(getModelMetricsResult.next())
			{
				modelMetrics.add(getModelMetricsResult.getString("Name") + "-" + getModelMetricsResult.getString("Statistic"));
				metricIDs.add(getModelMetricsResult.getInt("MetricID"));
			}
			
			getModelMetricsResult.close();
			getModelMetricsStatement.close();
		} catch (Exception ex) {
			System.out.println("Unable to connect to database." + ex.getMessage());
		} 
	}
	
	/**
	 * Get data for each metric in the model. 
	 */
	private void getmetricData() {
		metricData = new ArrayList<Double>();
		for(int i = 0; i < modelMetrics.size(); i++)
		{
			metricData.add(i, calculateMetricData(modelMetrics.get(i)));
		}
	}

	/**
	 * Recursively calculates data for user defined metrics.
	 * @param currentMetric
	 * @return
	 */
	private Double calculateMetricData(String currentMetric) {
		if(collectedMetrics.contains(currentMetric))
		{
			return collectedData.get(collectedMetrics.indexOf(currentMetric));
		}
		else
		{
			String[] metric = currentMetric.split("-");
			
			String getEquation = "SELECT Equation FROM scalabilitymetric WHERE Name = '" + metric[0] + "' AND Statistic = '" + metric[1] + "' AND Type = 'User Defined'"; 
			try {
				PreparedStatement getEquationStatement = connection.prepareStatement(getEquation);
			
				ResultSet getEquationResult = getEquationStatement.executeQuery();
				if(getEquationResult.next())
				{
					String[] equation = getEquationResult.getString("Equation").split(" ");
					
					char operand = equation[5].charAt(0);
					switch (operand) {
						case '+':
							return (Double.parseDouble(equation[0]) * calculateMetricData(equation[2] + "-" + equation[4])) + (Double.parseDouble(equation[6]) * calculateMetricData(equation[8] + "-" + equation[10]));
						case '-':
							return (Double.parseDouble(equation[0]) * calculateMetricData(equation[2] + "-" + equation[4])) - (Double.parseDouble(equation[6]) * calculateMetricData(equation[8] + "-" + equation[10]));
						case '*':
							return (Double.parseDouble(equation[0]) * calculateMetricData(equation[2] + "-" + equation[4])) * (Double.parseDouble(equation[6]) * calculateMetricData(equation[8] + "-" + equation[10]));
						case '/':
							return (Double.parseDouble(equation[0]) * calculateMetricData(equation[2] + "-" + equation[4])) / (Double.parseDouble(equation[6]) * calculateMetricData(equation[8] + "-" + equation[10]));
						default:
							break;
					}
				}
				
				getEquationResult.close();
				getEquationStatement.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * Calculates area for the model metric based on it's current metric values. 
	 */
	private void getArea() {
		area = 0.0;
		
		for (int i = 0; i < metricData.size(); i++) {
			//Added By Sowmya
			if(metricData.get(i) < 0 || metricData.get(i).isInfinite() || metricData.get(i).isNaN())
				metricData.set(i, 1.0);
			//End Addition
			if(i == ( metricData.size() - 1) )
				area += (metricData.get(i) + metricData.get(0));
			else
				area += (metricData.get(i) + metricData.get(i+1));
		}
		
		if(area < 1)
			area = 1;
		area = (area * Math.sin( (2 * Math.PI ) / metricData.size() )) / 2;
		
		//if(("" + area).equals("NaN"))
		if(Double.isNaN(area) || Double.isInfinite(area))
		{
			area = 1;
		}
	}

	/**
	 * Updates database values.
	 */
	private void updateDB() {
		int i = 0;
		float responsetime;
		float cpuutilize;
		float systemload;
		
		Float[] load = new Float[5];
		Float[] resp = new Float[5];
		Float[] cpu = new Float[5];
		String[] colltTime3 = new String[10];
		Float[] cpureadwriteload = new Float[10];
		
		
		Random random = new Random();
		
		try {
			
			for( i = 0; i < metricData.size(); i++)
			{
				if(metricData.get(i).isNaN() || metricData.get(i).isInfinite() || metricData.get(i) < 1)
					metricData.set(i, 1.0);
			}
			
			String addNewData = "INSERT INTO scalabilitymodeldata (`ModelID`, `CollectedTime`, `MetricID`, `Data`)  VALUES ";
			
			// Add entries for collected metricss
			for( i = 0; i < metricIDs.size(); i++)
			{
				double dataValue = metricData.get(i) + ( random.nextInt(3) * 0.1);
				addNewData += "(" + modelID + ", null, " + metricIDs.get(i) + ", " + dataValue + "), ";
			}
			
			Statement statement6 = connection.createStatement();
			ResultSet resultset6 = statement6.executeQuery("SELECT CollectedTime, Data FROM `scalabilitymodeldata` WHERE ModelID=11 ORDER BY `CollectedTime` DESC LIMIT 10");
			
			i=0;
			while(resultset6.next()){
				if(i<10){
					colltTime3[i] = resultset6.getTimestamp("CollectedTime").toString();
					cpureadwriteload[i] = resultset6.getFloat("Data");
					i++;
				}
			}
			i=0;
			SimpleDateFormat sdf = new java.text.SimpleDateFormat ("yyyy-MM-dd HH:mm:ss.S");
			try{
			
			responsetime= (sdf.parse(colltTime3[0]).getTime() - sdf.parse(colltTime3[5]).getTime())/(((cpureadwriteload[6] + cpureadwriteload[7]))/2);
			if(modelID == 11){
			System.out.println(responsetime);
			cpuutilize = (cpureadwriteload[0] + cpureadwriteload[5])/2;
			systemload = (cpureadwriteload[3] + cpureadwriteload[7])/2;
			String addNewData1 = "INSERT INTO loadtestdata (`CollectedTime`, `SystemLoad`, `ResponseTime`, `CPUutilization`)  VALUES ";
			addNewData1 += "(" + "'" +colltTime3[0] + "'," + systemload + ", " + responsetime + "," + cpuutilize + ")";
			PreparedStatement addDataStatement1 = connection.prepareStatement(addNewData1);
			addDataStatement1.executeUpdate();
			}
			}
			catch(Exception e){
				System.out.println(e);
			}
			
			
			// Add entry for model metric
			int modelMetricID = 0;
			
			String getModelMetrics = "SELECT scalabilitymetric.MetricID FROM scalabilitymetric WHERE" 
					+ " scalabilitymetric.MetricID IN (SELECT scalabilitymodelmetric.MetricID FROM scalabilitymodelmetric WHERE scalabilitymodelmetric.ModelMetric = 1"
					+ " AND scalabilitymodelmetric.ModelID = " + modelID + ")";
			PreparedStatement getModelMetricsStatement = connection.prepareStatement(getModelMetrics);
			ResultSet getModelMetricsResult = getModelMetricsStatement.executeQuery();
			while(getModelMetricsResult.next())
			{
				modelMetricID = getModelMetricsResult.getInt("MetricID");
			}
			getModelMetricsResult.close();
			getModelMetricsStatement.close();
			
			addNewData += "(" + modelID + ", null, " + modelMetricID + ", " + area + ");";
			
			System.out.println(addNewData);
			
			// Update prepared statement
			PreparedStatement addDataStatement = connection.prepareStatement(addNewData);
			addDataStatement.executeUpdate();
			
			// MNJ-need to revisit this
/*			
			String removeLastTime = "DELETE FROM scalabilitymodeldata WHERE ModelId = " + modelID  
				+ " AND `CollectedTime` = ( SELECT t.collectedtime FROM ( SELECT distinct `CollectedTime` FROM scalabilitymodeldata pmd1 "
				+ "WHERE ( 11 - 1 ) = ( SELECT COUNT( DISTINCT (pmd2.`CollectedTime`) ) FROM scalabilitymodeldata pmd2 WHERE pmd2.`CollectedTime` > pmd1.`CollectedTime`)) as t)";
*/			if (false)
			{
			String removeLastTime = "DELETE FROM scalabilitymodeldata WHERE ModelId = " + modelID
					+ " AND `CollectedTime` = ( SELECT t.collectedtime FROM ( SELECT distinct `CollectedTime` FROM scalabilitymodeldata pmd1 "
					+ " WHERE pmd1.modelId = " + modelID 
					+ " AND ( 11 - 1 ) = ( SELECT COUNT( DISTINCT (pmd2.`CollectedTime`) ) FROM scalabilitymodeldata pmd2 WHERE pmd2.`CollectedTime` > pmd1.`CollectedTime`" 
					+ " AND pmd2.modelId = pmd1.modelId)) as t)";
			
			System.out.println("Delete old values SQL: \n" + removeLastTime);

			PreparedStatement addRemoveLastTime = connection.prepareStatement(removeLastTime);
			addRemoveLastTime.executeUpdate();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}



	// 2 * DiskReadOps : Sum + 2 * DiskWriteOps : Sum
	// 0 1 2           3 4   5 6 7 8            9 10

//	fun(x)
//	if(x = system)
//		return x value
//	else if (x = user)
//		return fun(left) operand fun(right)
	
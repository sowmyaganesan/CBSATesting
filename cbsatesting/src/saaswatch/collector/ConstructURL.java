package saaswatch.collector;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;

import cbsa.runner.run.SendMessage;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.AmazonServiceException.ErrorType;

import org.apache.commons.codec.binary.Base64; 


public class ConstructURL {
	private static final String UTF_8 = "utf-8";

	public static URLConnection getConstructedURLByMsgV2(SendMessage message, int cnt) {
		URL		 url;
		URLConnection    urlConn = null;
		DataOutputStream cgiInput;
		//String   monHost = "monitoring.us-east-1.amazonaws.com";
		String   monHost = "monitoring.us-west-1.amazonaws.com";
		String   monUrl  = "https://" + monHost;

		// URL of target page script.
		try {
			url = new URL(monUrl);
			urlConn = url.openConnection();
			urlConn.setDoInput(true);
			urlConn.setDoOutput(true);
			urlConn.setUseCaches(false);
			urlConn.setRequestProperty("Content-Type", 
					"application/x-www-form-urlencoded");

			message.NameSpace = "AWS/EC2";
			
			String instVol = "InstanceId";
			String instVolId = message.InstanceId;
					
			String metricName = message.MetricNames.get(cnt);
			if (metricName.startsWith("Volume"))
			{
				instVol = "VolumeId";
				//instVolId = "vol-6cb90605"; //ami-6cb90605 0a34b165
				instVolId = "vol-05f3ad40"; //ami-05f3ad40 my magento instance
				message.NameSpace = "AWS/EBS";
			}
			
			// Getting times
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
			//message.EndTime = now;
			// MNJ AWS cannot return data for sample interval that ends at 'now' - thus request for data 2 mins in the past
			Date pastNow = new Date(now.getTime() - 2*60*1000);

			if (metricName.startsWith("Volume"))
			{	// for volume metric, only data 15min in the past is available
				pastNow = new Date(now.getTime() - 15*60*1000);
				message.Period = 300;
				message.CollectPeriod = 300;
			}
			
			message.EndTime = pastNow;
			message.ConvetEndTime = sdf.format( message.EndTime ) ;
			message.StartTime = new Date(pastNow.getTime() - (message.CollectPeriod * 1000));
			message.ConvetStartTime = sdf.format( message.StartTime );
			
//			System.out.println(message.ConvetEndTime);
//			System.out.println(message.ConvetStartTime);
			
			//message.TimeStamp = sdf.format( new Date( now.getTime() + 3600000*8 ) ); // "+ 3600000*8" : 8 hours for west coast, remove for east
			message.TimeStamp = sdf.format( now );
			
			// Send POST output.
			cgiInput = new DataOutputStream(urlConn.getOutputStream());
			
			// MNJ step1: string to sign
			String CanonicalizedQueryString = "AWSAccessKeyId=" + message.AWS_ID 
					+ "&Action=" 
					+ URLEncoder.encode("GetMetricStatistics",UTF_8)
					+ "&Dimensions.member.1.Name=" 
					+ URLEncoder.encode(instVol,UTF_8)
					+ "&Dimensions.member.1.Value=" 
					+ URLEncoder.encode(instVolId,UTF_8)
					+ "&EndTime="
					+ URLEncoder.encode(message.ConvetEndTime,UTF_8)
					+ "&MetricName="
					+ URLEncoder.encode(metricName,UTF_8)
					+ "&Namespace="
					+ URLEncoder.encode(message.NameSpace,UTF_8)
					+ "&Period="
					+ URLEncoder.encode(message.Period.toString(),UTF_8)
					+ "&SignatureMethod="
					+ URLEncoder.encode("HmacSHA1",UTF_8)
					+ "&SignatureVersion="
					+ URLEncoder.encode("2",UTF_8)
					+ "&StartTime="
					+ URLEncoder.encode(message.ConvetStartTime.toString(),UTF_8)
					+ "&Statistics.member.1="
					+ URLEncoder.encode(message.Statistics.get(cnt),UTF_8)
					+ "&Timestamp="
					+ URLEncoder.encode(message.TimeStamp,UTF_8)
					+ "&Version="
					+ URLEncoder.encode("2010-08-01",UTF_8);
					//+ "&Unit="
					//+ URLEncoder.encode("",UTF_8); // depends on the metric
/*			
			String stringToSign1 = "GET\n"
					+ monHost + "\n"
					+ "/\n" 
					+ CanonicalizedQueryString;
			System.out.println(stringToSign1);
			String content1 = CanonicalizedQueryString  
					+ "&Signature=" 
					+ URLEncoder.encode(signString(message.AWS_ID, message.AWS_Key, stringToSign1),UTF_8);
			System.out.println(content1);
*/
			String stringToSign = "POST\n"
					+ monHost + "\n"
					+ "/\n" 
					+ CanonicalizedQueryString;
			System.out.println(stringToSign);
			
			String content = CanonicalizedQueryString  
					+ "&Signature=" 
					+ URLEncoder.encode(signString(message.AWS_ID, message.AWS_Key, stringToSign),UTF_8);
			//System.out.println(content);
			//PostRequest(stringToSign);
			cgiInput.writeBytes(content);
			//cgiInput.writeBytes("&SignatureVersion=1&Action=GetMetricStatistics&Version=2009-05-15&Statistics.member.1=Sum&Period=60&MeasureName=CPUUtilization&StartTime=2011-11-28T00%3A00%3A00.000Z"
					// + "&EndTime=2011-11-29T00%3A00%3A00.000Z&Namespace=AWS%2FEC2&Timestamp=2011-11-29T20%3A10%3A20.000Z&AWSAccessKeyId=AKIAJHRBPUIJYODBNKBA&Signature=2ZTyaGr2cMvfVrxjXsnNNY2NBiM%3D");
			cgiInput.flush();
			cgiInput.close();

		} catch (MalformedURLException e) {
			System.out.println("MalformedURLException :" + e.getMessage());
		} catch (IOException e) {
			System.out.println("IOException :" + e.getMessage());
		}		
		return urlConn;
	}
	
	//mnj not used

	public static URLConnection getConstructedURL(HttpServletRequest request,String metricName){
		URL		 url;
		URLConnection    urlConn = null;
		DataOutputStream cgiInput;

		// URL of target page script.
		try {
			url = new URL("https://monitoring.amazonaws.com");
			urlConn = url.openConnection();
			urlConn.setDoInput(true);
			urlConn.setDoOutput(true);
			urlConn.setUseCaches(false);
			urlConn.setRequestProperty("Content-Type", 
					"application/x-www-form-urlencoded");

			// Send POST output.
			cgiInput = new DataOutputStream(urlConn.getOutputStream());

			String content = "AWSAccessKeyId=" + URLEncoder.encode(request.getParameter(metricName+":AWSAccessKeyId"),UTF_8) 
					+ "&Action=" 
					+ URLEncoder.encode(request.getParameter(metricName+":Action"),UTF_8)
					+ "&EndTime="
					+ URLEncoder.encode(request.getParameter(metricName+":EndTime"),UTF_8)
					+ "&MeasureName="
					+ URLEncoder.encode(request.getParameter(metricName+":MeasureName"),UTF_8)
					+ "&Namespace="
					+ URLEncoder.encode(request.getParameter(metricName+":Namespace"),UTF_8)
					+ "&Period="
					+ URLEncoder.encode(request.getParameter(metricName+":Period"),UTF_8)
					+ "&Signature="
					+ URLEncoder.encode(request.getParameter(metricName+":Signature"),UTF_8)
					+ "&SignatureVersion="
					+ URLEncoder.encode(request.getParameter(metricName+":SignatureVersion"),UTF_8)
					+ "&StartTime="
					+ URLEncoder.encode(request.getParameter(metricName+":StartTime"),UTF_8)
					+ "&Statistics.member.1="
					+ URLEncoder.encode(request.getParameter(metricName+":Statistics.member.1"),UTF_8)
					+ "&Timestamp="
					+ URLEncoder.encode(request.getParameter(metricName+":Timestamp"),UTF_8)
					+ "&Version="
					+ URLEncoder.encode(request.getParameter(metricName+":Version"),UTF_8);
			System.out.println(request.toString());

			cgiInput.writeBytes(content);
			cgiInput.flush();
			cgiInput.close();
		} catch (MalformedURLException e) {
			System.out.println("MalformedURLException :" + e.getMessage());
		} catch (IOException e) {
			System.out.println("IOException :" + e.getMessage());
		}		
		return urlConn;
	}

	
	//mnj v-1 - old code obsolete
	public static URLConnection getConstructedURLByMsg(SendMessage message,int cnt, String metricName){
		URL		 url;
		URLConnection    urlConn = null;
		DataOutputStream cgiInput;

		// URL of target page script.
		try {
			url = new URL("https://monitoring.us-east-1.amazonaws.com");
			urlConn = url.openConnection();
			urlConn.setDoInput(true);
			urlConn.setDoOutput(true);
			urlConn.setUseCaches(false);
			urlConn.setRequestProperty("Content-Type", 
					"application/x-www-form-urlencoded");
			
			// Getting times
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			message.EndTime = new Date(now.getTime() );
			message.ConvetEndTime = sdf.format( message.EndTime ) ;
			message.StartTime = new Date(now.getTime() - (message.CollectPeriod * 1000));
			message.ConvetStartTime = sdf.format( message.StartTime );
			
//			System.out.println(message.ConvetEndTime);
//			System.out.println(message.ConvetStartTime);
			
			message.NameSpace = "AWS/EC2";
			message.TimeStamp = sdf.format( new Date( now.getTime() + 3600000*8)  ); // "+ 3600000*8" : 8 hours for west coast, remove for east

			// Send POST output.
			cgiInput = new DataOutputStream(urlConn.getOutputStream());
			
			// MNJ: old signature method by amazon
			String stringToSign = "Action" + "GetMetricStatistics"
					+ "AWSAccessKeyId" + message.AWS_ID
					+ "EndTime" + message.ConvetEndTime
					+ "MeasureName" + message.MetricNames.get(cnt)
					+ "Namespace" + message.NameSpace
					+ "Period" + message.Period.toString()
					+ "SignatureVersion" + "1"
					+ "StartTime" + message.ConvetStartTime
					+ "Statistics.member.1" + message.Statistics.get(cnt)
					+ "Timestamp" + message.TimeStamp
					+ "Version" + "2009-05-15";
			System.out.println(stringToSign);		

			String content = "AWSAccessKeyId=" + message.AWS_ID 
					+ "&Action=" 
					+ URLEncoder.encode("GetMetricStatistics",UTF_8)
					+ "&EndTime="
					+ URLEncoder.encode(message.ConvetEndTime,UTF_8)
					+ "&MeasureName="
					+ URLEncoder.encode(message.MetricNames.get(cnt),UTF_8)
					+ "&Namespace="
					+ URLEncoder.encode(message.NameSpace,UTF_8)
					+ "&Period="
					+ URLEncoder.encode(message.Period.toString(),UTF_8)
					+ "&Signature="
					+ URLEncoder.encode(signString(message.AWS_ID, message.AWS_Key, stringToSign),UTF_8)
					+ "&SignatureVersion="
					+ URLEncoder.encode("1",UTF_8)
					+ "&StartTime="
					+ URLEncoder.encode(message.ConvetStartTime.toString(),UTF_8)
					+ "&Statistics.member.1="
					+ URLEncoder.encode(message.Statistics.get(cnt),UTF_8)
					+ "&Timestamp="
					+ URLEncoder.encode(message.TimeStamp,UTF_8)
					+ "&Version="
					+ URLEncoder.encode("2009-05-15",UTF_8);
			//System.out.println(content);
			//PostRequest(stringToSign);
			cgiInput.writeBytes(content);
			//cgiInput.writeBytes("&SignatureVersion=1&Action=GetMetricStatistics&Version=2009-05-15&Statistics.member.1=Sum&Period=60&MeasureName=CPUUtilization&StartTime=2011-11-28T00%3A00%3A00.000Z"
					// + "&EndTime=2011-11-29T00%3A00%3A00.000Z&Namespace=AWS%2FEC2&Timestamp=2011-11-29T20%3A10%3A20.000Z&AWSAccessKeyId=AKIAJHRBPUIJYODBNKBA&Signature=2ZTyaGr2cMvfVrxjXsnNNY2NBiM%3D");
			cgiInput.flush();
			cgiInput.close();

		} catch (MalformedURLException e) {
			System.out.println("MalformedURLException :" + e.getMessage());
		} catch (IOException e) {
			System.out.println("IOException :" + e.getMessage());
		}		
		return urlConn;
	}

	public static URLConnection loadImage(String urlStr){
		URL		 url;
		URLConnection    urlConn = null;
		DataOutputStream cgiInput;

		// URL of target page script.
		try {
			url = new URL(urlStr);
			urlConn = url.openConnection();
			urlConn.setDoInput(true);
			urlConn.setDoOutput(true);
			urlConn.setUseCaches(false);
			urlConn.setRequestProperty("Content-Type", 
					"application/x-www-form-urlencoded");

			// Send POST output.
			cgiInput = new DataOutputStream(urlConn.getOutputStream());

			String content = "";
			//System.out.println(content);

			cgiInput.writeBytes(content);
			cgiInput.flush();
			cgiInput.close();
		} catch (MalformedURLException e) {
			System.out.println("MalformedURLException :" + e.getMessage());
		} catch (IOException e) {
			System.out.println("IOException :" + e.getMessage());
			System.exit(-1);
		}		
		return urlConn;
	}

	protected static String signString (String AWSId, String key, String string) {
		//System.out.println(key + " AND " + string);
		final String UTF8_CHARSET = "UTF-8";
		String signString = null;

		try {
			// get an hmac_sha1 key from the raw key bytes
			SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA1");
			
			// get an hmac_sha1 Mac instance and initialize with the signing key
			Mac mac = Mac.getInstance("HmacSHA1");
			mac.init(signingKey);

			// compute the hmac on input data bytes
			String signature = new String(Base64.encodeBase64(mac.doFinal(string.getBytes(UTF8_CHARSET))));

			// base64-encode the hmac
			//signature = new String(rawHmac);
			//signature = Signature.calculateRFC2104HMAC(string, key);
			//Signature signature  = new Signature(AWSAccessKey, key);
			//signString = signature.signCanonicalString(string);
			
			//System.out.println(signString);
			return signature;

		}
		catch (Exception e) {
			try {
				throw new SignatureException("Failed to generate HMAC");
			} catch (SignatureException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return null;
	}
	
	/*public static void PostRequest(String string) {
        try {
        // Construct data
        String data = URLEncoder.encode("CPUUtilization:AWSAccessKeyId", "UTF-8") + "=" + URLEncoder.encode("AKIAJHRBPUIJYODBNKBA", "UTF-8");
        data += "&" + URLEncoder.encode("CPUUtilization:Action", "UTF-8") + "=" + URLEncoder.encode("GetMetricStatistics", "UTF-8");
        data += "&" + URLEncoder.encode("CPUUtilization:EndTime", "UTF-8") + "=" + URLEncoder.encode("2011-11-29T00:00:00.000Z", "UTF-8");
        data += "&" + URLEncoder.encode("CPUUtilization:StartTime", "UTF-8") + "=" + URLEncoder.encode("2011-11-28T00:00:00.000Z", "UTF-8");
        data += "&" + URLEncoder.encode("CPUUtilization:NameSpace", "UTF-8") + "=" + URLEncoder.encode("AWS/EC2", "UTF-8");
        data += "&" + URLEncoder.encode("CPUUtilization:Action", "UTF-8") + "=" + URLEncoder.encode("GetMetricStatistics", "UTF-8");
        data += "&" + URLEncoder.encode("CPUUtilization:Action", "UTF-8") + "=" + URLEncoder.encode("GetMetricStatistics", "UTF-8");
        data += "&" + URLEncoder.encode("CPUUtilization:Action", "UTF-8") + "=" + URLEncoder.encode("GetMetricStatistics", "UTF-8");
        data += "&" + URLEncoder.encode("CPUUtilization:Action", "UTF-8") + "=" + URLEncoder.encode("GetMetricStatistics", "UTF-8");
        data += "&" + URLEncoder.encode("CPUUtilization:Action", "UTF-8") + "=" + URLEncoder.encode("GetMetricStatistics", "UTF-8");
        data += "&" + URLEncoder.encode("CPUUtilization:Action", "UTF-8") + "=" + URLEncoder.encode("GetMetricStatistics", "UTF-8");

        
        System.out.println("request:" + data);
        // Send data
        URL url = new URL("http://localhost:8080/SaasWatch/MetricCollector");
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(data);
        wr.flush();
 
        // Get the response
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            System.out.println(line);
        }
        wr.close();
        rd.close();
    } catch (Exception e) {
    }
    }*/
}

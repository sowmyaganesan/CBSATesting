package cbsa.runner.run;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class SendMessage implements Serializable, Cloneable
{ 
	private static final long serialVersionUID = -8106426323396137805L;
	// activeMQ
	public int ModelID; 
	public String Type;
	
	public String AWS_ID=""; // amazon id
	public String AWS_Key=""; // amazon secret key
	public String InstanceId; // instance to get it from
	
	public String EndPoint; // region, locational, ex: us-east-1

	public Integer Period; // min 60 seconds, intervals of 60 
	
	public List<String> MetricNames; 
	public List<String> Statistics; 
	
	// default
	public String Unit;
	public String NameSpace; 
	public String TimeStamp; // default ex: 2009-05-15
	
	public Date StartTime; // Collect data starting from 
	public Date EndTime; // Collect data ending at
	
	public String ConvetStartTime; // Default to current time - collect period
	public String ConvetEndTime; // Default to current time
	
	public int CollectPeriod; // Start time and End time difference
	
	public SendMessage clone() {
		SendMessage s = new SendMessage();
		s.ModelID = this.ModelID;
		if(this.Type != null) {
			s.Type = new String(this.Type);	
		}
		if(this.AWS_ID != null) {
			s.AWS_ID = new String(this.AWS_ID);	
		}
		if(this.AWS_Key != null) {
			s.AWS_Key = new String(this.AWS_Key);	
		}
		if(this.InstanceId != null) {
			s.InstanceId = new String(this.InstanceId);	
		}
		if(this.EndPoint != null) {
			s.EndPoint = new String(this.EndPoint);
		}
		if(this.Period != null) {
			s.Period = new Integer(this.Period);	
		}
		if(this.MetricNames != null && !this.MetricNames.isEmpty()) {
			s.MetricNames = new ArrayList<String>(this.MetricNames);
		} else {
			s.MetricNames = Collections.emptyList();
		}
		if(this.Statistics != null && !this.Statistics.isEmpty()) {
			s.Statistics = new ArrayList<String>(this.Statistics);
		} else {
			s.Statistics = Collections.emptyList();
		}
		if(this.Unit != null) {
			s.Unit = new String(this.Unit);
		}
		if(this.NameSpace != null) {
			s.NameSpace = new String(this.NameSpace);
		}
		if(this.TimeStamp != null) {
			s.TimeStamp = new String(this.TimeStamp);
		}
		if(this.StartTime != null) {
			s.StartTime = new Date(this.StartTime.getTime());
		}
		if(this.EndTime != null) {
			s.EndTime = new Date(this.EndTime.getTime());
		}
		if(this.ConvetStartTime != null) {
			s.ConvetStartTime = new String(this.ConvetStartTime);
		}
		if(this.ConvetEndTime != null) {
			s.ConvetEndTime = new String(this.ConvetEndTime);
		}
		s.CollectPeriod = this.CollectPeriod;
		return s;
	}
	
	public String toString()
	{
		String temp = "ModelId=" + ModelID + " type=" + Type + " InstanceId=" + InstanceId;
		return temp;
	}
}
package excel.utils;

import java.io.IOException;
import java.util.Date;

import report.logs.Logs;
import xpath.hub.XpathHub;

public class StringClsUtil 
{
	String [] spitData;
	String replace;
	static String xpathConversion;

	static Logs log = new Logs();

	public String[] SplitData(String data)
	{
		log._INFO("Spiltting the Data: "+data);
		spitData = data.split(";");
		log._INFO("Data Spitted: [");
		log._INFO("DataSet |--->");

		String[] spitData1 = new String[spitData.length];

		for(int i=0;i<spitData.length;i++)
		{
			log._INFO(" "+spitData[i]);
			spitData1[i] = spitData[i].trim();

		}
		log._INFO("DataSet --->|");
		log._INFO("Data Spitted: ]");
		return spitData1;
	}
	public String[] SplitData1(String data)
	{
		log._INFO("Spiltting the Data: "+data);
		spitData = data.split(":");
		log._INFO("Data Spitted: [");
		log._INFO("DataSet |--->");
		String[] spitData1 = new String[spitData.length];
		for(int i=0;i<spitData.length;i++)
		{
			log._INFO(" "+spitData[i]);
			spitData1[i] = spitData[i].trim();
		}
		log._INFO("DataSet --->|");
		log._INFO("Data Spitted: ]");
		return spitData1;
	}

	public String[] SplitAnyData(String data,String delimator)
	{
		log._INFO("Spiltting the Data: "+data);
		spitData = data.split(delimator);
		log._INFO("Data Spitted: [");
		log._INFO("DataSet |--->");
		String[] spitData1 = new String[spitData.length];
		for(int i=0;i<spitData.length;i++)
		{
			log._INFO(" "+spitData[i]);
			spitData1[i] = spitData[i].trim();
		}
		log._INFO("DataSet --->|");
		log._INFO("Data Spitted: ]");
		return spitData1;
	}

	public String[] SplitData2(String data)
	{
		log._INFO("Spiltting the Data: "+data);
		spitData = data.split("::");
		log._INFO("Data Spitted: [");
		log._INFO("DataSet |--->");
		String[] spitData1 = new String[spitData.length];
		for(int i=0;i<spitData.length;i++)
		{
			log._INFO(" "+spitData[i]);
			spitData1[i] = spitData[i].trim();
		}
		log._INFO("DataSet --->|");
		log._INFO("Data Spitted: ]");
		return spitData1;
	}

	public String ReplaceString(String Xpath,String replaceWith)
	{
		log._INFO("Expression : [$] is in String:"+Xpath+"is replacing with "+replaceWith);
		replace = Xpath.replace("[$]",replaceWith);
		log._INFO("After Conversion: "+replace);
		return replace;
	}
	public String ReplaceString1(String Xpath,String replaceWith)
	{
		log._INFO("Expression : [*] is in String:"+Xpath+"is replacing with "+replaceWith);
		replace = Xpath.replace("[*]",replaceWith);
		log._INFO("After Conversion: "+replace);
		return replace;
	}
	public String ReplaceString2(String Xpath,String replaceWith)
	{
		log._INFO("Expression : [%] is in String:"+Xpath+"is replacing with "+replaceWith);
		replace = Xpath.replace("[%]",replaceWith);
		log._INFO("After Conversion: "+replace);
		return replace;
	}
	public boolean XpathConversion(String Xpath)
	{
		replace = Xpath.replace("[$]","xyz");
		return Xpath.equals(replace);
	}
	public String Convert_Customized_Format(Date Date)
	{
		;
		log._INFO("Symbols : or Space is in String:"+Date.toString()+"is replacing with "+"_");
		replace = Date.toString().replaceAll(":","_");
		String newReplace = replace.replaceAll(" ","_");
		log._INFO("After Conversion: "+new StringBuffer().append("_").append(newReplace));
		return new StringBuffer().append("_").append(newReplace).toString();
	}

	public String XpathconversionUpToThreeParameeters(String xpath,String one,String two, String three) throws IOException
	{
		int j=0;

		xpathConversion = new XpathHub().xpathGetter(xpath);

		if(xpathConversion!=null)
		{
			if(one!="")
			{
				j = 1;

				if(two!="")
				{	
					j = 2;
					if(three!="") j = 3;
				}

			}
			else
			{
				if(two!="")
				{
					j = 1;

					if(three!="") j = 2;
				}

			}

			for(int i=0;i<j;i++)
			{
				if(i==0)
				{
					if(one!="") xpathConversion = ReplaceString(xpathConversion,one);
				}
				else if(i==1)
				{
					if(two!="") xpathConversion = ReplaceString1(xpathConversion,two);
				}
				else if(i==2)
				{
					if(three!="") xpathConversion = ReplaceString2(xpathConversion,three);
				}
			}

		}

		return xpathConversion;


	}

	public String XpathconversionOne(String xpath,String one) throws IOException
	{
		xpathConversion = new XpathHub().xpathGetter(xpath);

		if(xpathConversion!=null)
		{

			if(one!=null) xpathConversion = ReplaceString(xpathConversion,one);

		}

		return xpathConversion;

	}

	public String XpathconversionTwo(String xpath,String two) throws IOException
	{	
		if(xpath!=null)
		{

			if(two!=null) xpathConversion = ReplaceString1(xpath,two);

		}

		return xpathConversion;

	}
	public String XpathconversionThree(String xpath,String three) throws IOException
	{

		if(xpath!=null)
		{

			if(three!=null) xpathConversion = ReplaceString2(xpath,three);

		}

		return xpathConversion;

	}

}

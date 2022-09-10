package listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer
{
	int limit =0;
	int runLimit =1;

	@Override
	public boolean retry(ITestResult result) 
	{
		if(limit<runLimit)
		{
			limit++;

			return true;
		}

		return false;
	}

}

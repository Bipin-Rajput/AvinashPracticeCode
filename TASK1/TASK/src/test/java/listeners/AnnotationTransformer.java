package listeners;

import java.lang.reflect.*;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

public class AnnotationTransformer implements IAnnotationTransformer
{
	public void transform(ITestAnnotation annotation, Class testCalss, Constructor testConstructor,Method testMethod)
	{
		annotation.setRetryAnalyzer(RetryAnalyzer.class);
	}
}
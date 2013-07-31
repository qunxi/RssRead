package github.com.qunxi.rssreader.test.utils;

import junit.framework.Assert;
import github.com.qunxi.rssreader.utils.DateNormalize;
import android.test.AndroidTestCase;

public class DateNormalizeTest extends AndroidTestCase{
	public void test_AtomDateConvert_True(){
		String date = DateNormalize.AtomDateConvert("2005-08-14T16:13:03+0000");
		Assert.assertEquals("2005-08-14 16:13:03", date);
	}
	
	public void test_RssDateConvert_True(){
			String date = DateNormalize.RssDateConvert("Sun, 14 Aug 2005 16:13:03 +0000");
			Assert.assertEquals("2005-08-14 16:13:03", date);
	}
}

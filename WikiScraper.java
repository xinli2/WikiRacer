import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * TODO: You will have to implement memoization somewhere in this class.
 */
public class WikiScraper {
			
	/*
	 * get the links in the page
	 */
	public static Set<String> findWikiLinks(String link) {
		String html = fetchHTML(link);
		Set<String> links = scrapeHTML(html);
		return links;
	}
	
	/*
	 * get the HTML file of the link
	 */
	private static String fetchHTML(String link) {
		StringBuffer buffer = null;
		try {
			URL url = new URL(getURL(link));
			InputStream is = url.openStream();
			int ptr = 0;
			buffer = new StringBuffer();
			while ((ptr = is.read()) != -1) {
			    buffer.append((char)ptr);
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return buffer.toString();
	}
	
	/*
	 * get che completed URL
	 */
	private static String getURL(String link) {
		return "https://en.wikipedia.org/wiki/" + link;
	}
	
	/*
	 * get a set of words in the html
	 */
	private static Set<String> scrapeHTML(String html) {
		Pattern pattern = Pattern.compile("<a href=\\\\?\"/wiki/([^\\s:#]+)\".*?>");
		Set<String> ret = new HashSet<String>();
		for(String eachLine : html.split("\n")) {
			Matcher tmp = pattern.matcher(eachLine);
			while(tmp.find()) {
				ret.add(tmp.group(1));
			}
		}
		return ret;
	}
}

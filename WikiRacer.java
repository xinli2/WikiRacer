import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WikiRacer {
	private static Set<String> visited = new HashSet<>();
	/*
	 * Do not edit this main function
	 */
	public static void main(String[] args) {
//		System.setProperty("java.net.useSystemProxies", "true");
		List<String> ladder = findWikiLadder(args[0], args[1]);
		System.out.println(ladder);
	}

	/*
	 * Do not edit the method signature/header of this function
	 * TODO: Fill this function in.
	 */
	private static List<String> findWikiLadder(String start, String end) {
		MaxPQ ladders = new MaxPQ();
		List<String> ret = new ArrayList<String>();
		ret.add(start);
		ladders.enqueue(ret, 0);

		while(!ladders.isEmpty()) {
			ret = ladders.dequeue();
			Set<String> links = WikiScraper.findWikiLinks(ret.get(ret.size() - 1));
			if(links.contains(end)) {
				ret.add(end);
				return ret;
			}
			links.parallelStream().forEach(link -> {
				WikiScraper.findWikiLinks(link);
			});

			for(String link : links) {
				if (visited.contains(link)) continue;
				List<String> tmp = new ArrayList<>(ret);
				tmp.add(link);
				links = WikiScraper.findWikiLinks(end);
				links.retainAll(WikiScraper.findWikiLinks(link));
				ladders.enqueue(tmp, links.size());
				visited.add(link);
			}
		}
		return null;
	}

}

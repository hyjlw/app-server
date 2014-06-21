/**
 * 
 */
package org.mob.app.queue;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.mob.app.pojo.WebPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

/**
 * @author LL
 *
 */
public class CrawlerManager {
	private static final Logger log = LoggerFactory.getLogger(CrawlerManager.class);
	
	private static final int MAX_QUEUE_CAPACITY = 300000;
	
	private static int poolSize = 10;
	private static int maxPoolSize = 100;
	private static long keepAlive = 300L;
	
	private static BlockingQueue<Consumer> queue;
	private static ThreadPoolExecutor threadPool;
	
	private static CrawlerManager manager;
	
	private CrawlerManager(){}
	
	public static CrawlerManager getInstance() {
		if(manager == null) {
			manager = new CrawlerManager();
			
			queue = new ArrayBlockingQueue<Consumer>(MAX_QUEUE_CAPACITY);
			threadPool = new ThreadPoolExecutor(poolSize, maxPoolSize, keepAlive, TimeUnit.SECONDS, (ArrayBlockingQueue)queue);
			threadPool.prestartAllCoreThreads();
		}
		
		return manager;
	}
	
	public void addQueue(List<WebPage> items) {
		log.info("Adding to consumer: " + items.size());
		
		for(WebPage item : items) {
			Consumer c = new Consumer(item);
			threadPool.execute(c);
		}
	}
	
	
	private static class Consumer implements Runnable {
		
		private WebPage item = null;
		
		public Consumer(WebPage item) {
			this.item = item;
		}

		@Override
		public void run() {
			if(item != null && !StringUtils.isBlank(item.getWebUrl())) {
				String crawlStorageFolder = "/tmp/data/crawl" + item.getStorageFolder();
				log.info("Set storage folder to: " + crawlStorageFolder);
				
		        int numberOfCrawlers = 7;

		        CrawlConfig config = new CrawlConfig();
		        config.setCrawlStorageFolder(crawlStorageFolder);

		        /*
		         * Instantiate the controller for this crawl.
		         */
		        PageFetcher pageFetcher = new PageFetcher(config);
		        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		        
		        try {
					CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

					/*
					 * For each crawl, you need to add some seed urls. These are the first
					 * URLs that are fetched and then the crawler starts following links
					 * which are found in these pages
					 */
					log.info("Adding seed: " + item.getWebUrl());
					controller.addSeed(item.getWebUrl());

					/*
					 * Start the crawl. This is a blocking operation, meaning that your code
					 * will reach the line after this only when crawling is finished.
					 */
					String crawler = item.getCrawlerClass();
					log.info("Setting crawler: " + crawler);
					controller.start((Class<WebCrawler>)Class.forName(crawler), numberOfCrawlers);
				} catch (Exception e) {
					log.error("Error: " + e.getMessage());
				}
			}
		}
		
	}
}

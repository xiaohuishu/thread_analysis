package cn.march.analysis.juc.future.pool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class FileSizeCalc {

	public static void main(String[] args) throws Exception {
		
		long startMills = System.currentTimeMillis();
		
		FileSizeCalc fileCalc = new FileSizeCalc();
		
		long total = fileCalc.getFileSize(new File("/home/antsmarth"));
		
		long endMills = System.currentTimeMillis();
		
		System.out.printf("total dirs : %dMB%n", total/(1024*1024));
		System.out.printf("consume time : %.3fs%n ", (endMills - startMills)/1.0e3);
		
	}
	
	
	static class SubDirAndSize {
		
		private final long size;
		private final List<File> subDirs;
		
		public SubDirAndSize(long size, List<File> subDirs) {
			
			this.size = size;
			this.subDirs = subDirs;
			
		}

		public long getSize() {
			return size;
		}

		public List<File> getSubDirs() {
			return subDirs;
		}
		
	}
	
	
	private SubDirAndSize getSubDirsAndSize(File path) {
		
		long total = 0;
		List<File> subDirs = new ArrayList<File>();
		
		if(path.isDirectory()) {
			
			File[] subFiles = path.listFiles();
			
			if(subFiles != null) {
				
				for(File file : subFiles) {
					
					if(file.isFile())
						total += file.length();
					else
						subDirs.add(file);
				}
				
			}
			
		}
		
		return new SubDirAndSize(total, subDirs);
		
	}
	
	
	private long getFileSize(File path) throws Exception {
		
		long total = 0;
		
		final int cpuCore = Runtime.getRuntime().availableProcessors();
		final int threadSize = cpuCore + 1;
		
		ExecutorService executors = Executors.newFixedThreadPool(threadSize);
		
		List<File> dirs = new ArrayList<File>();
		SubDirAndSize subDir = null;
		
		if(!dirs.isEmpty()) {
			
			List<Future<SubDirAndSize>> parResults = new ArrayList<Future<SubDirAndSize>>();
			
			for(File file : dirs) {
				
				parResults.add(executors.submit(() -> {
					
					return getSubDirsAndSize(file);
					
				}));
				
			}
			
			dirs.clear();
			
			for(Future<SubDirAndSize> future : parResults) {
				
				subDir = future.get(100, TimeUnit.SECONDS);
				
				total += subDir.getSize();
				
				dirs.addAll(subDir.getSubDirs());
			}
			
		}
		
		return total;
	}
	
	
	
	
}

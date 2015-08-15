	


	package cn.march.analysis.juc.future.pool;
	
	
	
	import java.io.File;
	import java.util.ArrayList;
	import java.util.List;
	import java.util.concurrent.ExecutorService;
	import java.util.concurrent.Executors;
	import java.util.concurrent.Future;
	import java.util.concurrent.TimeUnit;
	
	
	/**
	 * 测试CPU密集型加大线程数并不能提高效率;
	 * 通常建立线程数：
	 * 		thread_size = CPU核数 * 2;
	 * 
	 * @author antsmarth
	 *
	 */
	public class FileSizeCalc {
	
		//测试方法
		public static void main(String[] args) throws Exception {
	
			long startMills = System.currentTimeMillis();
	
			FileSizeCalc fileCalc = new FileSizeCalc();
	
			long total = fileCalc.getFileSize(new File("/home/antsmarth/视频"));
	
			long endMills = System.currentTimeMillis();
	
			System.out.printf("total dirs : %dMB%n", total / (1024 * 1024));
			System.out.printf("consume time : %.3fs%n ",
					(endMills - startMills) / 1.0e3);
	
		}
	
		/**
		 * 一个Bean,存储当前目录的子目录和当前文件大小;
		 * @author antsmarth
		 *
		 */
		static class SubDirAndSize {
	
			//文件大小
			private final long size;
			//子目录
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
	
		/**
		 * 通过传入的文件path路径：
		 * 		判断path是否是目录：
		 * 			若是：
		 * 				遍历此path下的文件和目录：
		 * 				如果有文件则计算文件大小
		 * 				如果有子目录则直接加入集合
		 * 				返回一个之前创建的Bean对象
		 * 			否则：
		 * 				返回null的Bean集合;
		 * 
		 * @param path
		 * @return
		 */
		private SubDirAndSize getSubDirsAndSize(File path) {
	
			long total = 0;
			List<File> subDirs = null;
	
			if (path.isDirectory()) {
	
				subDirs = new ArrayList<File>();
				
				//遍历目录
				File[] subFiles = path.listFiles();
	
				if (subFiles != null) {
	
					for (File file : subFiles) {
	
						if (file.isFile())
							total += file.length();
						else
							subDirs.add(file);
					}
	
				}
	
			}
	
			return new SubDirAndSize(total, subDirs);
	
		}
	
		/**
		 * 通过线程池计算path目录下实际的容量
		 * 
		 * @param path
		 * @return
		 * @throws Exception
		 */
		private long getFileSize(File path) throws Exception {
	
			long total = 0;
	
			//获取CPU核数
			final int cpuCore = Runtime.getRuntime().availableProcessors();
			//创建cpuCore * 2线程数
			final int threadSize = cpuCore * 2;
	
			//创建线程池
			ExecutorService executors = Executors.newFixedThreadPool(threadSize);
	
			List<File> dirs = new ArrayList<File>() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
	
				{
	
					add(path);
	
				}
			
			};
	
			SubDirAndSize subDir = null;
	
			try {
				
				while (!dirs.isEmpty()) {
	
					List<Future<SubDirAndSize>> parResults = new ArrayList<Future<SubDirAndSize>>();
	
					for (File file : dirs) {
	
						parResults.add(executors.submit(() -> {
	
							return getSubDirsAndSize(file);
	
						}));
	
					}
	
					dirs.clear();
	
					for (Future<SubDirAndSize> future : parResults) {
	
						subDir = future.get(100, TimeUnit.SECONDS);
	
						total += subDir.getSize();
	
						dirs.addAll(subDir.getSubDirs());
					}
	
				}
	
				return total;
	
			} finally {
	
				executors.shutdown();
	
			}
		}
	
	}

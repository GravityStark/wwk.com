package com.wwk.utils;

import java.io.File;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.UUID;

public class Utils {

	/** 根路径 */
	private static String rootPath;

	/** 随机概率是否小于该值 （万分比） */
	public static boolean isInRate(int rate) {
		return Math.random() * 10000 + 1 <= rate;
	}

	/**
	 * 以数组值为权重(万分比概率)，返回数组索引
	 * 
	 * @param randomArray
	 * @return
	 * @throws Exception
	 */
	public static int belonged2Rate(Integer[] randomArray) throws Exception {
		int sum = 0;
		for (int num : randomArray) {
			if (num < 0) {
				throw new Exception("数组中不能有负数");
			}
			sum += num;
		}
		int rand = (int) (Math.random() * sum);
		final int R = rand;
		for (int i = 0, len = randomArray.length; i < len; i++) {
			int value = randomArray[i];
			if (rand <= value) {
				return i;
			}
			rand -= value;
		}
		throw new RuntimeException("没有找到下标，随机值=" + R);
	}

	/** 取范围内随机数,包括小的，不包括大的 */
	public static int nextInt(int min, int max) {
		if (min > max) {
			final int _temp = min;
			min = max;
			max = _temp;
		}
		return (int) (Math.random() * (max - min) + min);
	}

	/** 取范围内随机数,包括小的，不包括大的 */
	public static double nextDouble(double min, double max) {
		if (min > max) {
			final double _temp = min;
			min = max;
			max = _temp;
		}
		return (Math.random() * (max - min) + min);
	}

	/** 四舍五入(保留两位小数) */
	public static double numberFormat(double number) {
		BigDecimal bd = new BigDecimal(number);
		return bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/** 四舍五入(根据传入参数保留小数位) */
	public static double numberFormat(double number, int digits) {
		BigDecimal bd = new BigDecimal(number);
		return bd.setScale(digits, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 字符串转int数组
	 * 
	 * @param str
	 * @param split
	 * @return
	 */
	public static int[] stringToIntArr(String str, String split) {
		if (str.length() == 0) {
			return new int[] {};
		}
		String[] ss = stringToStringArr(str, split);
		int[] ii = new int[ss.length];
		for (int i = 0; i < ss.length; i++) {
			ii[i] = Integer.parseInt(ss[i]);
		}
		return ii;
	}

	public static float[] stringToFloatArr(String str, String split) {
		if (str.length() == 0) {
			return new float[] {};
		}
		String[] ss = stringToStringArr(str, split);
		float[] ii = new float[ss.length];
		for (int i = 0; i < ss.length; i++) {
			ii[i] = Float.parseFloat(ss[i]);
		}
		return ii;
	}

	/**
	 * 字符串转int数组
	 * 
	 * @param str
	 * @param split
	 * @return
	 */
	public static int[][] stringToIntArr(String str, String split1, String split2) {
		if (str.length() == 0) {
			return new int[][] {};
		}
		String[] ii = stringToStringArr(str, split1);
		int[][] iii = new int[ii.length][];
		for (int i = 0; i < ii.length; i++) {
			iii[i] = stringToIntArr(ii[i], split2);
		}
		return iii;
	}

	/**
	 * 字符串转字符串数组（用split分隔，并且去掉两边的空格trim）
	 * 
	 * @param str
	 * @param split
	 * @return
	 */
	public static String[] stringToStringArr(String str, String split) {
		if (str.length() == 0) {
			return new String[] {};
		}
		String[] ss = str.split(split);
		String[] strs = new String[ss.length];
		for (int i = 0; i < ss.length; i++) {
			strs[i] = ss[i].trim();
		}
		return strs;
	}

	/**
	 * 得到系统路径 1.如果系统发布方式为class，则返回包根目录，例如部署目录
	 * /FiveHero/bin/com/noah，则返回/FiveHero/ 2.如果系统发布方式为jar，则返回jar所在目录，例如部署目录
	 * /FiveHero/FiveHero.jar，则返回/FiveHero/
	 */
	public static String getRootPath() {
		if (rootPath == null || rootPath.length() == 0) {
			try {
				rootPath = Utils.class.getResource("/").getPath();
				try {
					rootPath = rootPath.substring(0, rootPath.lastIndexOf('!'));
				} catch (Exception e) {
				}
				rootPath = rootPath.substring(0, rootPath.lastIndexOf('/') + 1);
				if (rootPath.startsWith("/") && !System.getProperty("file.separator").equals("/")) {
					rootPath = rootPath.substring(1, rootPath.length());
				}
				if (rootPath.startsWith("file:/")) {
					rootPath = rootPath.substring(6, rootPath.length());
				}
				if (rootPath.endsWith("bin/")) {
					File file = new File(rootPath);
					rootPath = file.getParent() + "/";
				}
			} catch (Exception e) {
				rootPath = "";
			}
		}
		return rootPath;
	}

	/**
	 * 却掉时间分之后的精确度
	 * 
	 * @param time
	 * @return
	 */
	public static long cutOffAfterMinute(long time) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTimeInMillis();
	}

	public static int getWeekDay() {
		Calendar now = Calendar.getInstance();
		// 一周第一天是否为星期天
		boolean isFirstSunday = (now.getFirstDayOfWeek() == Calendar.SUNDAY);
		// 获取周几
		int weekDay = now.get(Calendar.DAY_OF_WEEK);
		// 若一周第一天为星期天，则-1
		if (isFirstSunday) {
			weekDay = weekDay - 1;
			if (weekDay == 0) {
				weekDay = 7;
			}
		}
		return weekDay;
	}

	public static int[] seconds2Time(int second) {
		int h = 0;
		int d = 0;
		int s = 0;
		int temp = second % 3600;
		if (second > 3600) {
			h = second / 3600;
			if (temp != 0) {
				if (temp > 60) {
					d = temp / 60;
					if (temp % 60 != 0) {
						s = temp % 60;
					}
				} else {
					s = temp;
				}
			}
		} else {
			d = second / 60;
			if (second % 60 != 0) {
				s = second % 60;
			}
		}
		int[] time = { h, d, s };
		return time;
	}

	public static String generateUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	public static void main(String[] args) throws Exception {
		// long time = Long.parseLong("1467579600000") -
		// 2*(long)7*24*60*60*1000;
		// System.out.println(time);
		System.out.println(new Date(Long.parseLong("1495386000000")));

		// System.out.println(new Date(time));
		// System.out.println(time);1465765200000 1466974800000 1467579600000
		// time = time +(long)7*24*60*60*1000;
		// System.out.println(new Date(time));
		// System.out.println(time);

		// int[] results = new int[9];
		// double[] weightArrays =
		// {1,1,1,0.907636698,1.192876307,1.225475119,1.20102601,0.255660449,1.217325416};
		// for (int i = 0; i < weightArrays.length; i++) {
		// int index = WeightRandom.getWeightRandom(weightArrays);
		// weightArrays[index] = 0;
		// results[i] = index;
		// System.out.println(index);
		// }
		// for (int i = 0; i < results.length; i++) {
		// for (int j = 0; j < results.length; j++) {
		// if(i != j && results[i] == results[j]){
		// System.out.println(results[i]+"==="+ results[j]);
		// }
		// }
		// }
//		int x = 764, y = 109;
//		System.out.println((x - 1) + "," + (y));
//		System.out.println((x - 1) + "," + (y - 1));
//		System.out.println((x) + "," + (y - 2));
//		System.out.println((x) + "," + (y - 1));
//		System.out.println((x + 1) + "," + (y));
//		System.out.println((x) + "," + (y + 1));
//		System.out.println((x) + "," + (y + 2));
//		System.out.println((x - 1) + "," + (y + 1));
//
//		System.out.println("===================");
//		System.out.println((x - 2) + "," + (y));
//		System.out.println((x - 2) + "," + (y - 1));
//		System.out.println((x - 1) + "," + (y - 2));
//		System.out.println((x - 1) + "," + (y - 3));
//		System.out.println((x) + "," + (y - 4));
//		System.out.println((x) + "," + (y - 3));
//		System.out.println((x + 1) + "," + (y - 2));
//		System.out.println((x + 1) + "," + (y - 1));
//		System.out.println((x + 2) + "," + (y));
//		System.out.println((x + 1) + "," + (y + 1));
//		System.out.println((x + 1) + "," + (y + 2));
//		System.out.println((x) + "," + (y + 3));
//		System.out.println((x) + "," + (y + 4));
//		System.out.println((x - 1) + "," + (y + 3));
//		System.out.println((x - 1) + "," + (y + 2));
//		System.out.println((x - 2) + "," + (y + 1));

//		int x1 = 764, y1 = 109;
//		System.out.println((x1 - 1) + "," + (y1));
//		System.out.println((x1) + "," + (y1 - 1));
//		System.out.println((x1) + "," + (y1 - 2));
//		System.out.println((x1 + 1) + "," + (y1 - 1));
//		System.out.println((x1 + 1) + "," + (y1));
//		System.out.println((x1 + 1) + "," + (y1 + 1));
//		System.out.println((x1) + "," + (y1 + 2));
//		System.out.println((x1) + "," + (y1 + 1));
//
//		System.out.println("===================");
//		System.out.println((x1 - 2) + "," + (y1));
//		System.out.println((x1 - 1) + "," + (y1 - 1));
//		System.out.println((x1 - 1) + "," + (y1 - 2));
//		System.out.println((x1) + "," + (y1 - 3));
//		System.out.println((x1) + "," + (y1 - 4));
//		System.out.println((x1) + "," + (y1 - 3));
//		System.out.println((x1 + 1) + "," + (y1 - 2));
//		System.out.println((x1 + 2) + "," + (y1 - 1));
//		System.out.println((x1 + 2) + "," + (y1));
//		System.out.println((x1 + 2) + "," + (y1 + 1));
//		System.out.println((x1 + 1) + "," + (y1 + 2));
//		System.out.println((x1 + 1) + "," + (y1 + 3));
//		System.out.println((x1) + "," + (y1 + 4));
//		System.out.println((x1) + "," + (y1 + 3));
//		System.out.println((x1 - 1) + "," + (y1 + 2));
//		System.out.println((x1 - 1) + "," + (y1 + 1));
	}

	static class KeyComperator implements Comparator<Integer> {
		@Override
		public int compare(Integer paramT1, Integer paramT2) {
			return -paramT1.compareTo(paramT2);
		}
	}

	/**
	 * 获取当前主线程上开启的所有线程
	 * 
	 * @return
	 */
	public static Thread[] findAllThreads() {
		ThreadGroup group = Thread.currentThread().getThreadGroup();
		ThreadGroup topGroup = group;

		// 遍历线程组树，获取根线程组
		while (group != null) {
			topGroup = group;
			group = group.getParent();
		}
		// 激活的线程数加倍
		int estimatedSize = topGroup.activeCount() * 2;
		Thread[] slackList = new Thread[estimatedSize];
		// 获取根线程组的所有线程
		int actualSize = topGroup.enumerate(slackList);
		// copy into a list that is the exact size
		Thread[] list = new Thread[actualSize];
		System.arraycopy(slackList, 0, list, 0, actualSize);
		return list;
	}
}

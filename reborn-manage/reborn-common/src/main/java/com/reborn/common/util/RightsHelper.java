package com.reborn.common.util;

import java.math.BigInteger;
import java.util.BitSet;

/**
 * 
* @Title 

* @Description: 权限计算帮助类

* @author reborngao  Email 460600117@qq.com

* @CreateTime：2016年12月19日 下午8:18:23
 
* @version V1.0
 */
public class RightsHelper {
	/**
	 * 测试是否具有指定编码的权限  
	 * @Title: testRights   
	 * @Description: TODO(这里用一句话描述这个方法的作用)   
	 * @param: @param sum
	 * @param: @param targetRights
	 * @param: @return      
	 * @return: boolean
	 */
	public static boolean testRights(String sum, String targetRights) {
		if(Tools.isEmpty(sum)) return false;
		return testRights(new BigInteger(sum), targetRights);
	}
	/***
	 * 测试是否具有指定编码的权限
	 * @Title: testRights   
	 * @Description:   
	 * @param: @param bigInteger
	 * @param: @param targetRights
	 * @param: @return      
	 * @return: boolean
	 */
	public static boolean testRights(BigInteger sum, String targetRights) {
		// TODO Auto-generated method stub
		return testRights(sum,Integer.parseInt(targetRights));
	}
	/**
	 * 测试是否具有指定编码的权限
	 * @Title: testRights   
	 * @Description:  
	 * @param: @param sum
	 * @param: @param targetRights
	 * @param: @return      
	 * @return: boolean
	 */
	public static boolean testRights(BigInteger sum,int targetRights){
		return sum.testBit(targetRights);
	}
	/**
	 * 测试是否具有指定编码的权限
	 * @Title: testRights   
	 * @Description:
	 * @param: @param sum
	 * @param: @param targetRights
	 * @param: @return      
	 * @return: boolean
	 */
	public static boolean testRights(String sum,int targetRights){
		if(Tools.isEmpty(sum))
			return false;
		return testRights(new BigInteger(sum),targetRights);
	}
	/**
	 * 利用BigInteger对权限进行2的权的和计算
	 * @param rights int型权限编码数组
	 * @return 2的权的和
	 */
	public static BigInteger sumRights(int[] rights){
		BigInteger num = new BigInteger("0");
		for(int i=0; i<rights.length; i++){
			num = num.setBit(rights[i]);
		}
		return num;
	}
	/**
	 * 利用BigInteger对权限进行2的权的和计算
	 * @param rights String型权限编码数组
	 * @return 2的权的和
	 */
	public static BigInteger sumRights(String[] rights){
		BigInteger num = new BigInteger("0");
		for(int i=0; i<rights.length; i++){
			num = num.setBit(Integer.parseInt(rights[i]));
		}
		return num;
	}
	
	public static void main(String[] args) {
		System.out.println(testRights("73786413343844589510", "7"));
		BigInteger  bigInteger=new BigInteger("111");
		bigInteger.setBit(1);
		System.out.println(bigInteger);
	}
	
}

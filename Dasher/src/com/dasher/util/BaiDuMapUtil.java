package com.dasher.util;

public class BaiDuMapUtil {
	static double DEF_PI = 3.14159265359; // PI
    static double DEF_2PI= 6.28318530712; // 2*PI
    static double DEF_PI180= 0.01745329252; // PI/180.0
    static double DEF_R =6370693.5; // radius of earth
    static double DEF_RJ = 6356725;  // 极半径
    
    public static String GetDirection(double lon1, double lat1, double lon2, double lat2){
    	String[] dirs={"正东","东北","正北","西北","正西","西南","正南","东南"};
    	
    	double Ec=DEF_RJ + (DEF_R - DEF_RJ) * (90.0-lon1) / 90.0;
    	double Ed = Ec * Math.cos(lat1* Math.PI/180.0);
    	double dx = (lon2 * Math.PI/180.0 - lon1 * Math.PI/180.0) * Ed;
        double dy = (lat2* Math.PI/180.0 - lat1* Math.PI/180.0) * Ec;

        double angle = 0.0;
        angle = Math.atan(Math.abs(dx/dy))*180./Math.PI;
        // 判断象限
        double dLo = lon2 - lon1;
        double dLa = lat2 - lat1;
     
	    if(dLo > 0 && dLa <= 0) {
	       angle = (90.0 - angle) + 90.0;
	    }
	    else if(dLo <= 0 && dLa < 0) {
	       angle = angle + 180.0;
	    }
	    else if(dLo < 0 && dLa >= 0) {
	       angle = (90.0 - angle) + 270;
	    }
	    
	    if((angle>=0&&angle<=22.5)||(angle>=337.5)){
	    	return dirs[0];
	    }else if(angle>22.5&&angle<=67.5){
	    	return dirs[1];
	    }else if(angle>67.5&&angle<=112.5){
	    	return dirs[2];
	    }else if(angle>112.5&&angle<=157.5){
	    	return dirs[3];
	    }else if(angle>157.5&&angle<=202.5){
	    	return dirs[4];
	    }else if(angle>202.5&&angle<=247.5){
	    	return dirs[5];
	    }else if(angle>247.5&&angle<=292.5){
	    	return dirs[6];
	    }else if(angle>292.5&&angle<=337.5){
	    	return dirs[6];
	    }
	    
	    return "未知";
    }
    
    //适用于近距离
    public static double GetShortDistance(double lon1, double lat1, double lon2, double lat2)
    {
        double ew1, ns1, ew2, ns2;
        double dx, dy, dew;
        double distance;
        // 角度转换为弧度
        ew1 = lon1 * DEF_PI180;
        ns1 = lat1 * DEF_PI180;
        ew2 = lon2 * DEF_PI180;
        ns2 = lat2 * DEF_PI180;
        // 经度差
        dew = ew1 - ew2;
        // 若跨东经和西经180 度，进行调整
        if (dew > DEF_PI)
        dew = DEF_2PI - dew;
        else if (dew < -DEF_PI)
        dew = DEF_2PI + dew;
        dx = DEF_R * Math.cos(ns1) * dew; // 东西方向长度(在纬度圈上的投影长度)
        dy = DEF_R * (ns1 - ns2); // 南北方向长度(在经度圈上的投影长度)
        // 勾股定理求斜边长
        distance = Math.sqrt(dx * dx + dy * dy);
        return distance;
    }
    //适用于远距离
    public static double GetLongDistance(double lon1, double lat1, double lon2, double lat2)
    {
        double ew1, ns1, ew2, ns2;
        double distance;
        // 角度转换为弧度
        ew1 = lon1 * DEF_PI180;
        ns1 = lat1 * DEF_PI180;
        ew2 = lon2 * DEF_PI180;
        ns2 = lat2 * DEF_PI180;
        // 求大圆劣弧与球心所夹的角(弧度)
        distance = Math.sin(ns1) * Math.sin(ns2) + Math.cos(ns1) * Math.cos(ns2) * Math.cos(ew1 - ew2);
        // 调整到[-1..1]范围内，避免溢出
        if (distance > 1.0)
             distance = 1.0;
        else if (distance < -1.0)
              distance = -1.0;
        // 求大圆劣弧长度
        distance = DEF_R * Math.acos(distance);
        return distance;
    }
}

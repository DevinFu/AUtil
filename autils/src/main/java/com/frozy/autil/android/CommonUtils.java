package com.frozy.autil.android;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.view.View;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class CommonUtils {

	private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    /**
     * if the network is available
     * @param pContext 上下文
     * @return true if network is available, false otherwise
     */
    public static boolean isNetworkAvailable(Context pContext) {
        ConnectivityManager connectivity = (ConnectivityManager) pContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity != null) {
            // 获取网络连接管理的对象
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 判断当前网络是否已经连接
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String MD5Generator(String pText) {
        if (pText != null) {
            try {
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                char[] charArray = pText.toCharArray();
                byte[] byteArray = new byte[charArray.length];

                for (int i = 0; i < charArray.length; i++) {
                    byteArray[i] = (byte) charArray[i];
                }

                byte[] md5Bytes = md5.digest(byteArray);
                StringBuilder hexValue = new StringBuilder();
                for (byte md5Byte : md5Bytes) {
                    int val = ((int) md5Byte) & 0xff;
                    if (val < 16)
                        hexValue.append("0");
                    hexValue.append(Integer.toHexString(val));
                }
                return hexValue.toString();
            } catch (NoSuchAlgorithmException e) {
                // No execption
            }
        }
        return null;
    }

    public static int getVersion(Context pContext) {
        try {
            PackageInfo tInfo = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), 0);
            return tInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 手机号码
     * 移动：134[0-8],135,136,137,138,139,150,151,157,158,159,182,187,188
     * 联通：130,131,132,152,155,156,185,186
     * 电信：133,1349,153,180,189
     * NSString * MOBILE = @"^1(3[0-9]|4[57]|5[0-35-9]|7[06-8]|8[0-9])\\d{8}$";<br>
     *
     * 中国移动：China Mobile
     * 134[0-8],135,136,137,138,139,150,151,152,157,158,159,178,182,183,184,187,188
     * NSString * CM = @"^1(34[0-8]|(3[5-9]|47|5[0-27-9]|78|8[2-478])\\d)\\d{7}$";<br>
     *
     * 中国联通：China Unicom
     * 130,131,132,155,156,185,186,176,145
     * NSString * CU = @"^1(3[0-2]|5[56]|76|8[56])\\d{8}$";<br>
     *
     * 中国电信：China Telecom
     * 133,1349,153,177,180,181,189
     * NSString * CT = @"^1((33|53|77|8[09])[0-9]|349)\\d{7}$";<br>
     *
     * 大陆地区固话及小灵通
     * 区号：010,020,021,022,023,024,025,027,028,029
     * NSString * PHS = @"^0(10|2[0-5789]|\\d{3})\\d{7,8}$";
     *
     * @param mobile 待判断的手机号
     * @return {@code true}，是手机号；{@code false}，不是手机号
     */
    public static boolean isMobileNumber(String mobile) {

        String mobileRe = "^1(3[0-9]|4[57]|5[0-35-9]|7[06-8]|8[0-9])\\d{8}$";
        Pattern p1 = Pattern.compile(mobileRe);
        Matcher m1 = p1.matcher(mobile);

        String cmRe = "^1(34[0-8]|(3[5-9]|47|5[0-27-9]|78|8[2-478])\\\\d)\\\\d{7}$";
        Pattern p2 = Pattern.compile(cmRe);
        Matcher m2 = p2.matcher(mobile);

        String ucRe = "^1(3[0-2]|5[56]|76|8[56])\\\\d{8}$";
        Pattern p3 = Pattern.compile(ucRe);
        Matcher m3 = p3.matcher(mobile);

        String ctRe = "^1((33|53|77|8[09])[0-9]|349)\\\\d{7}$";
        Pattern p4 = Pattern.compile(ctRe);
        Matcher m4 = p4.matcher(mobile);
        return (m1.matches() || m2.matches() || m3.matches() || m4.matches());

    }

    /**
     * url 编码
     *
     * @param url 原始url字符串
     * @return 经过url编码的字符串
     */
    public static String urlEncode(String url) {
        try {
            url = URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * 输入密码校验
     *
     * @param password 密码(6-12 只能由数字与字母组成)
     * @return 是否是符合要求的密码
     */
    public static boolean matchesPassword(String password) {
        String reg = "^[0-9A-Za-z]{6,12}$";
        return password != null && password.matches(reg);
    }

    /**
     * 用户名验证(1-20,并且不能带有空格)
     *
     * @param userName 待检测的用户名
     * @return 是否是符合要求的用户名
     */
    public static boolean matchesUserName(String userName) {
        String reg = "[^\\s]{1,20}";
        return userName != null && userName.matches(reg);
    }

    public static boolean isWIFI(Context context) {
        ConnectivityManager connectMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = connectMgr.getActiveNetworkInfo();

        if (info != null) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            }
        }

        return false;
    }

    public static boolean isMobileNet(Context context) {
        ConnectivityManager connectMgr = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = connectMgr.getActiveNetworkInfo();

        if (info != null) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
        }

        return false;
    }

    /**
     * 返回用户手机运营商名称
     * @param context 上下文
     * @return 运营商中文名称
     */
    public static String getProvidersName(Context context) {
        String ProvidersName = null;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        // 返回唯一的用户ID;就是这张卡的编号神马的
        String imsi = telephonyManager.getSubscriberId();
        if (imsi == null) return null;

        // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。其中
        if (imsi.startsWith("46000") || imsi.startsWith("46002")) ProvidersName = "中国移动";
        else if (imsi.startsWith("46001")) ProvidersName = "中国联通";
        else if (imsi.startsWith("46003")) ProvidersName = "中国电信";
        return ProvidersName;
    }

	public static int generateViewId() {
		if (Build.VERSION.SDK_INT < 17) {
			for (; ; ) {
				final int result = sNextGeneratedId.get();
				// aapt-generated IDs have the high byte nonzero; clamp to the range under that.
				int newValue = result + 1;
				if (newValue > 0x00FFFFFF)
					newValue = 1; // Roll over to 1, not 0.
				if (sNextGeneratedId.compareAndSet(result, newValue)) {
					return result;
				}
			}
		}
		else {
			return View.generateViewId();
		}
	}
}

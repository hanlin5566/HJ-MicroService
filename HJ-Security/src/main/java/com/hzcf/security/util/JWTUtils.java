package com.hzcf.security.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
/**
 * Create by hanlin on 2018年5月24日
 **/
@Component
public class JWTUtils {
	@Value("${hj.secrity.jwt.secret}")
	public String secret;
	//jwt的生命周期  单位分钟
	@Value("${hj.secrity.jwt.ttl}")
	public long ttl;
	/**
	 * 创建jwt
	 * 
	 * @param id
	 * @param subject
	 *            过期的时间长度
	 * @return
	 * @throws Exception
	 */
	public String createJWT(String id, String subject){
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256; // 指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
		long nowMillis = System.currentTimeMillis();// 生成JWT的时间
		Date now = new Date(nowMillis);
		Map<String, Object> claims = new HashMap<String, Object>();// 创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
//		claims.put("uid", "DSSFAWDWADAS...");
//		claims.put("user_name", "admin");
//		claims.put("nick_name", "DASDA121");
		/**
		 * 生成签名的时候使用的秘钥secret,这个方法本地封装了的，一般可以从本地配置文件中读取，切记这个秘钥不能外露哦。它就是你服务端的私钥，
		 * 在任何场景都不应该流露出去。一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
		 * 下面就是在为payload添加各种标准声明和私有声明了
		 */
		SecretKey key = generalKey();
		JwtBuilder builder = Jwts.builder() // 这里其实就是new一个JwtBuilder，设置jwt的body
				.setClaims(claims) // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
				.setId(id) // 设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
				.setIssuedAt(now) // iat: jwt的签发时间
				.setSubject(subject) // sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
				.signWith(signatureAlgorithm, key);// 设置签名使用的签名算法和签名使用的秘钥
		if (ttl >= 0) {
			//TODO:将ttl单位置换为分钟
			long expMillis = nowMillis + ttl*60*1000;
			Date exp = new Date(expMillis);
			builder.setExpiration(exp); // 设置过期时间
		}
		return builder.compact(); // 就开始压缩为xxxxxxxxxxxxxx.xxxxxxxxxxxxxxx.xxxxxxxxxxxxx这样的jwt
		// 打印了一哈哈确实是下面的这个样子
		// eyJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiJEU1NGQVdEV0FEQVMuLi4iLCJzdWIiOiIiLCJ1c2VyX25hbWUiOiJhZG1pbiIsIm5pY2tfbmFtZSI6IkRBU0RBMTIxIiwiZXhwIjoxNTE3ODI4MDE4LCJpYXQiOjE1MTc4Mjc5NTgsImp0aSI6Imp3dCJ9.xjIvBbdPbEMBMurmwW6IzBkS3MPwicbqQa2Y5hjHSyo
	}

	/**
	 * 解密jwt
	 * 
	 * @param jwt
	 * @return
	 * @throws Exception
	 */
	public Claims parseJWT(String jwt){
		SecretKey key = generalKey(); // 签名秘钥，和生成的签名的秘钥一模一样
		Claims claims = Jwts.parser() // 得到DefaultJwtParser
				.setSigningKey(key) // 设置签名的秘钥
				.parseClaimsJws(jwt).getBody();// 设置需要解析的jwt
		return claims;
	}

	/**
	 * 由字符串生成加密key
	 * 
	 * @return
	 */
	public SecretKey generalKey() {
		String stringKey = secret;// 本地配置文件中加密的密文
		byte[] encodedKey = Base64.decodeBase64(stringKey);// 本地的密码解码[B@152f6e2
//		System.out.println(Base64.encodeBase64URLSafeString(encodedKey));// 7786df7fc3a34e26a61c034d5ec8245d
		/**
		 * 根据给定的字节数组使用AES加密算法构造一个密钥，使用 encodedKey中的始于且包含0到前leng个字节。
		 */
		SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
		return key;
	}
}

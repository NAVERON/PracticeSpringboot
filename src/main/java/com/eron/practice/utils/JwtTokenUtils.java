package com.eron.practice.utils;


import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.interfaces.RSAKeyProvider;
import com.eron.practice.config.JWTConfigProperties;


@Component 
@ConfigurationProperties(prefix = "spring.auth0.jwt")  // 变量少, 不单独出来  直接用@Value 
// @PropertySource(value = "")  // 设置自定义属性的来源文件 
public class JwtTokenUtils {
	
	// 请求头 Authorization: Bearer <token> 
	private Long TOKEN_VALIDITY = 5 * 60 * 60L; //unit = s  
	private String TOKEN_HEADER = "Authorization";
	private String TOKEN_PREFIX = "Bearer ";
	private String TOKEN_SECRET = "wangyulong";
    
    private RSAPublicKey publicKey = null;//Get the key instance
    private RSAPrivateKey privateKey = null;//Get the key instance
    private Algorithm algorithmRS = null;  // RSA 
    private Algorithm algorithmHS = null;  //HMAC 
    
    // 保存获取 publicKey privateKey 封装对象 
    // 所有的一切目的是生成 公钥 私钥
    private RSAKeyProvider keyProvider = new RSAKeyProvider() {
        @Override
        public RSAPublicKey getPublicKeyById(String kid) {
            //Received 'kid' value might be null if it wasn't defined in the Token's header
            RSAPublicKey publicKey = null;
            return (RSAPublicKey) publicKey;
        }

        @Override
        public RSAPrivateKey getPrivateKey() {
            return privateKey;
        }

        @Override
        public String getPrivateKeyId() {
            return null;  // privateKeyId 
        }
    };
    
    @PostConstruct 
    public void initialAlgorithmsRS() throws NoSuchAlgorithmException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(1024);
        KeyPair kp = kpg.generateKeyPair();
        this.publicKey = (RSAPublicKey) kp.getPublic();
        this.privateKey = (RSAPrivateKey) kp.getPrivate();
        
        algorithmRS = Algorithm.RSA256(publicKey, privateKey);
    }
    @PostConstruct 
    public void initialAlgorithmsHS() throws IllegalArgumentException {
    	algorithmHS = Algorithm.HMAC256(this.TOKEN_SECRET);
    }
	
    public Algorithm getHMAC256Algorithm() {
    	return this.algorithmHS;
    }
    public Algorithm getRSA256Algorithm() {
    	return this.algorithmRS;
    }
    
    /**
     * 创建 jwt token
     * @param algorithm 加密算法
     * @param args 自定义载荷参数 
     * @return
     */
    public String generateToken(Algorithm algorithm, String... args) {
    	String token = "";
    	try {
    	    //Use the Algorithm to create and verify JWTs.
			token = JWT.create()
					.withIssuer("unknow")
					.withArrayClaim("custom", args)  // 自定义载荷参数 
					.sign(algorithm);
		} catch (IllegalArgumentException | JWTCreationException e) {
			e.printStackTrace();
		}
    	
    	return token;
    }
    
    public Boolean verifyToken(String token, Algorithm algorithm) {
    	Boolean status = true;
    	
    	//String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXUyJ9.eyJpc3MiOiJhdXRoMCJ9.AbIJTDMFc7yUa5MhvcP03nJPyCPzZtQcGEp-zWfOkEE";
    	try {
    	    // only the public key is used during verification
    	    JWTVerifier verifier = JWT.require(algorithm)
    	        .withIssuer("unknow")
    	        .build(); //Reusable verifier instance
    	    DecodedJWT jwt = verifier.verify(token);
    	    
    	    // just test below  包含时间的验证，是否超时
    	    JWTVerifier verifier22 = JWT.require(algorithm)
    	    	    .acceptLeeway(1)   //1 sec for nbf and iat
    	    	    .acceptExpiresAt(5)   //5 secs for exp
    	    	    .build();
    	    DecodedJWT jwt22 = verifier22.verify(token);
    	    
    	} catch (JWTVerificationException exception){
    	    //Invalid signature/claims
    		status = false;
    	}
    	
    	return status;
    }
    
    // 测试多种参数 和含义 
    public void decodeToken(String token) {
    	//String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXUyJ9.eyJpc3MiOiJhdXRoMCJ9.AbIJTDMFc7yUa5MhvcP03nJPyCPzZtQcGEp-zWfOkEE";
    	DecodedJWT jwt = null;
    	try {
    	    jwt = JWT.decode(token);
    	} catch (JWTDecodeException exception){
    	    //Invalid token
    	}
    	
    	// 获取 算法  Returns the Algorithm value or null if it's not defined in the Header.
    	String algorithm = jwt.getAlgorithm();
    	//  Returns the Content Type value or null if it's not defined in the Header. 
    	String contentType = jwt.getContentType();
    	// Returns the Key Id value or null if it's not defined in the Header. 
    	String keyId = jwt.getKeyId();
    	
    	// 自己定义的claim  参数 
    	// 检查参数是否存在  claim.isNull()
    	Claim claim = jwt.getHeaderClaim("owner");
    	/**
    	 *  这里设置header  claim , 生成token的时候 
    	 * Map<String, Object> headerClaims = new HashMap();
		 * headerClaims.put("owner", "auth0");
		 * String token = JWT.create()
         * .withHeader(headerClaims)
         * .sign(algorithm);
    	 */
    	String issuer = jwt.getIssuer();  // 签发人
    	String subject = jwt.getSubject();  // 主题 
    	List<String> audience = jwt.getAudience(); // 
    	Date expiresAt = jwt.getExpiresAt();  // 过期时间
    	Date notBefore = jwt.getNotBefore();
    	Date issuedAt = jwt.getIssuedAt();
    	String id = jwt.getId();
    	
    }
    
    
}













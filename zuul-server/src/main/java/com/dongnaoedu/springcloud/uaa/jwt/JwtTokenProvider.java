package com.dongnaoedu.springcloud.uaa.jwt;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {
	private JwtConfiguration configuration;

	public JwtTokenProvider(JwtConfiguration configuration) {
		this.setConfiguration(configuration);
	}

	/**
	 * 生成token
	 * 
	 * @return
	 */
	public String createToken(Claims claims) {
		String compactJws = Jwts.builder().setPayload(JSONObject.toJSONString(claims))
				.compressWith(CompressionCodecs.DEFLATE)
				.signWith(SignatureAlgorithm.HS512, configuration.getSecretKeySpec()).compact();
		return compactJws;
	}

	/** token转换 */
	public Claims parseToken(String token) {
		try {
			return Jwts.parser().setSigningKey(configuration.getSecretKeySpec()).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public JwtConfiguration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(JwtConfiguration configuration) {
		this.configuration = configuration;
	}
}

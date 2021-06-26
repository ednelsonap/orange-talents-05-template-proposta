package br.com.zup.academy.ednelson.proposta.security;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.keygen.KeyGenerators;

@Convert
public class Criptografador implements AttributeConverter<String, String> {

	@Value("${proposta.criptografia.password}")
	private String password;
	private final String salt = KeyGenerators.string().generateKey();

	@SuppressWarnings("deprecation")
	@Override
	public String convertToDatabaseColumn(String attribute) {
		return Encryptors.queryableText(password, salt).encrypt(attribute);
	}

	@SuppressWarnings("deprecation")
	@Override
	public String convertToEntityAttribute(String dbData) {
		return Encryptors.queryableText(password, salt).decrypt(dbData);
	}

}
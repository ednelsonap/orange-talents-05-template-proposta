package br.com.zup.academy.ednelson.proposta.validation;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.com.zup.academy.ednelson.proposta.paypal.CarteiraRequest;
import br.com.zup.academy.ednelson.proposta.paypal.Emissor;

@Component
public class EmissorValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {	
		return CarteiraRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (errors.hasErrors()) {
			return;
		}
		CarteiraRequest request = (CarteiraRequest) target;
		
		try {
			Emissor.valueOf(request.getCarteira());
		} catch (IllegalArgumentException e) {
			errors.rejectValue("carteira", null, request.getCarteira() + " não está entre os valores aceitos: "
					+ Arrays.stream(Emissor.values()).map(Emissor::name).collect(Collectors.toList()));
		}
	}

}

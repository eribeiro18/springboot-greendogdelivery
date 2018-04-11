package com.boaglio.casadocodigo;

import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.stereotype.Component;

import com.boaglio.casadocodigo.model.Item;
import com.boaglio.casadocodigo.repositories.ClienteRepository;

@Component
public class SpringDataRestCustomization extends RepositoryRestConfigurerAdapter {

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
		config.exposeIdsFor(Item.class,ClienteRepository.class);
	}
}

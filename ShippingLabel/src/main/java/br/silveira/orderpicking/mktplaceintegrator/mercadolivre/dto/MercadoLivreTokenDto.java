package br.silveira.orderpicking.mktplaceintegrator.mercadolivre.dto;

import lombok.Data;

@Data
public class MercadoLivreTokenDto {
	public String access_token;
	public String token_type;
	public Integer expires_in;
	public String scope;
	public Integer user_id;
	public String refresh_token;

}

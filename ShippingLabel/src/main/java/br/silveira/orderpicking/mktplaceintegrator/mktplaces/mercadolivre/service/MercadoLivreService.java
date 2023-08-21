package br.silveira.orderpicking.mktplaceintegrator.mktplaces.mercadolivre.service;

public interface MercadoLivreService {
    public String getToken(String sellerId);

    public void saveMercadoLivreSetup(String authorizationCode);
}

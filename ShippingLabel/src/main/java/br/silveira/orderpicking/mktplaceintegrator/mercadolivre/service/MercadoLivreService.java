package br.silveira.orderpicking.mktplaceintegrator.mercadolivre.service;

public interface MercadoLivreService {
    public String getToken(String sellerId);

    public void saveMercadoLivreSetup(String authorizationCode);
}

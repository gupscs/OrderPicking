package br.silveira.orderpicking.mktplaceintegrator.mercadolivre.service;

public interface MercadoLivreService {
    public String getToken(String companyId, String sellerId);

    public void saveMercadoLivreSetup(Long companyId, String authorizationCode);
}

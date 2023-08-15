package br.silveira.orderpicking.auth.service;

import br.silveira.orderpicking.auth.payload.Register;
import br.silveira.orderpicking.auth.payload.RegisterCheck;

public interface RegisterService {

    public void register(Register register);

    public RegisterCheck registerCheck(String identificationNo, String username);
}

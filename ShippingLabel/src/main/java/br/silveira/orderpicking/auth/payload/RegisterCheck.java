package br.silveira.orderpicking.auth.payload;

import lombok.Data;

@Data
public class RegisterCheck {

    private boolean existIdentificationNo;

    private boolean existUsername;

}


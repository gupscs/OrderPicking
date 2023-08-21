package br.silveira.orderpicking.mktplaceintegrator.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SearchOrderDto {

    private LocalDateTime createdFrom;
    private LocalDateTime createdTo;



}

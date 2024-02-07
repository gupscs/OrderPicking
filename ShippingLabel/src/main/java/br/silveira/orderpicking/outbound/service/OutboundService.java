package br.silveira.orderpicking.outbound.service;

import br.silveira.orderpicking.outbound.dto.OutboundCreateDto;

public interface OutboundService {
    Long createOutbound(OutboundCreateDto dto);
}

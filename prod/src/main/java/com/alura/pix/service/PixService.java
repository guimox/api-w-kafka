package com.alura.pix.service;

import com.alura.pix.dto.PixDTO;
import com.alura.pix.model.Pix;
import com.alura.pix.repository.PixRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PixService {

    private final PixRepository pixRepository;

    private final KafkaTemplate<String, PixDTO> kafkaTemplate;

    public PixService(PixRepository pixRepository, KafkaTemplate<String, PixDTO> kafkaTemplate) {
        this.pixRepository = pixRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public PixDTO salvarPix(PixDTO pixDTO) {
        pixRepository.save(Pix.toEntity(pixDTO));
        kafkaTemplate.send("pix-topic", pixDTO);
        return pixDTO;
    }

}

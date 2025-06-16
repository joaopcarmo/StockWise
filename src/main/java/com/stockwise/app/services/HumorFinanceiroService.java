package com.stockwise.app.services;

import com.stockwise.app.dto.HumorFinanceiroDto;
import com.stockwise.app.model.HumorFinanceiroModel;
import com.stockwise.app.model.HumorFinanceiroModel.EstadoHumor;
import com.stockwise.app.repositories.HumorFinanceiroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HumorFinanceiroService {

    @Autowired
    private HumorFinanceiroRepository humorFinanceiroRepository;

    public List<HumorFinanceiroDto> findAll() {
        return humorFinanceiroRepository.findAll()
                .stream()
                .map(HumorFinanceiroModel::toDto)
                .collect(Collectors.toList());
    }

    public HumorFinanceiroDto findById(UUID id) {
        HumorFinanceiroModel humor = humorFinanceiroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Humor financeiro não encontrado"));
        return humor.toDto();
    }

    public List<HumorFinanceiroDto> findByUsuarioId(UUID usuarioId) {
        return humorFinanceiroRepository.findByUsuarioIdOrderByDataDesc(usuarioId)
                .stream()
                .map(HumorFinanceiroModel::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public HumorFinanceiroDto save(HumorFinanceiroDto dto) {
        HumorFinanceiroModel humor = dto.toModel();
        if (humor.getData() == null) {
            humor.setData(LocalDateTime.now());
        }
        HumorFinanceiroModel salvo = humorFinanceiroRepository.save(humor);
        return salvo.toDto();
    }

    @Transactional
    public HumorFinanceiroDto update(UUID id, HumorFinanceiroDto dto) {
        HumorFinanceiroModel humor = humorFinanceiroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Humor financeiro não encontrado"));

        humor.setEstado(dto.getEstado());
        if (dto.getData() != null) {
            humor.setData(dto.getData());
        }

        HumorFinanceiroModel atualizado = humorFinanceiroRepository.save(humor);
        return atualizado.toDto();
    }

    @Transactional
    public HumorFinanceiroDto updateByUsuarioId(UUID usuarioId, HumorFinanceiroDto dto) {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);

        Optional<HumorFinanceiroModel> humorOpt = humorFinanceiroRepository
                .findByUsuarioIdAndDataHoje(usuarioId, startOfDay, endOfDay);

        if (humorOpt.isPresent()) {
            return update(humorOpt.get().getId(), dto);
        } else {
            dto.setUsuarioId(usuarioId);
            dto.setData(LocalDateTime.now());
            return save(dto);
        }
    }

    @Transactional
    public void delete(UUID id) {
        if (!humorFinanceiroRepository.existsById(id)) {
            throw new IllegalArgumentException("Humor financeiro não encontrado");
        }
        humorFinanceiroRepository.deleteById(id);
    }

    @Transactional
    public void deleteByUsuarioId(UUID usuarioId) {
        if (!humorFinanceiroRepository.existsByUsuarioId(usuarioId)) {
            throw new IllegalArgumentException("Nenhum humor financeiro encontrado para este usuário");
        }
        humorFinanceiroRepository.deleteByUsuarioId(usuarioId);
    }

    public Optional<HumorFinanceiroDto> findHumorHoje(UUID usuarioId) {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);

        return humorFinanceiroRepository
                .findByUsuarioIdAndDataHoje(usuarioId, startOfDay, endOfDay)
                .map(HumorFinanceiroModel::toDto);
    }

    public Optional<HumorFinanceiroDto> findUltimoHumor(UUID usuarioId) {
        return humorFinanceiroRepository.findTopByUsuarioIdOrderByDataDesc(usuarioId)
                .map(HumorFinanceiroModel::toDto);
    }

    public List<HumorFinanceiroDto> findByPeriodo(UUID usuarioId, LocalDateTime inicio, LocalDateTime fim) {
        return humorFinanceiroRepository.findByUsuarioIdAndDataBetweenOrderByDataDesc(usuarioId, inicio, fim)
                .stream()
                .map(HumorFinanceiroModel::toDto)
                .collect(Collectors.toList());
    }

    public List<HumorFinanceiroDto> findUltimos30Dias(UUID usuarioId) {
        LocalDateTime inicio = LocalDateTime.now().minusDays(30);
        return findByPeriodo(usuarioId, inicio, LocalDateTime.now());
    }

    public boolean jaRegistrouHoje(UUID usuarioId) {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1).minusNanos(1);

        return humorFinanceiroRepository
                .findByUsuarioIdAndDataHoje(usuarioId, startOfDay, endOfDay)
                .isPresent();
    }

    public List<Object[]> obterEstatisticasPorUsuario(UUID usuarioId) {
        return humorFinanceiroRepository.countEstadosByUsuarioId(usuarioId);
    }

    public List<HumorFinanceiroDto> findByEstado(EstadoHumor estado) {
        return humorFinanceiroRepository.findByEstado(estado)
                .stream()
                .map(HumorFinanceiroModel::toDto)
                .collect(Collectors.toList());
    }
}

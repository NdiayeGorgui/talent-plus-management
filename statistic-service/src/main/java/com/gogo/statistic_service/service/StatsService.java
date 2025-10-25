package com.gogo.statistic_service.service;

import com.gogo.statistic_service.dto.CompetenceFrequencyDTO;
import com.gogo.statistic_service.dto.MonthlyCountDTO;
import com.gogo.statistic_service.dto.OffreCountDTO;
import com.gogo.statistic_service.dto.StatutCountDTO;

import java.util.List;

public interface StatsService {
    List<StatutCountDTO> getCandidaturesParStatut();
    List<MonthlyCountDTO> getCandidaturesParMois();
    List<CompetenceFrequencyDTO> getCompetencesFrequentes();
    List<OffreCountDTO> getNombreOffresParRecruteur();
    List<OffreCountDTO> getNombreOffresParEmployeur();

}

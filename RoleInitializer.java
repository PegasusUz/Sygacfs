package com.example.pega.sygacfs.config;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.pega.sygacfs.models.Role;
import com.example.pega.sygacfs.repositories.RoleRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RoleInitializer {

    private final RoleRepository roleRepository;

    @Bean
    public CommandLineRunner initRoles() {
        return args -> {
            // Créer le rôle ADMIN s'il n'existe pas
            roleRepository.findByLibelleRole(Role.LibelleRole.ADMIN)
                .ifPresentOrElse(
                    role -> {}, // Le rôle existe déjà
                    () -> {
                        Role adminRole = Role.builder()
                                .libelleRole(Role.LibelleRole.ADMIN)
                                .permissions(Set.of(
                                        Role.Permission.LECTURE,
                                        Role.Permission.ECRITURE,
                                        Role.Permission.SUPPRESSION,
                                        Role.Permission.MODIFICATION
                                ))
                                .build();
                        roleRepository.save(adminRole);
                    }
                );

            // Créer le rôle FORMATEUR s'il n'existe pas
            roleRepository.findByLibelleRole(Role.LibelleRole.FORMATEUR)
                .ifPresentOrElse(
                    role -> {}, // Le rôle existe déjà
                    () -> {
                        Role formateurRole = Role.builder()
                                .libelleRole(Role.LibelleRole.FORMATEUR)
                                .permissions(Set.of(
                                        Role.Permission.LECTURE,
                                        Role.Permission.ECRITURE,
                                        Role.Permission.MODIFICATION
                                ))
                                .build();
                        roleRepository.save(formateurRole);
                    }
                );

            // Créer le rôle PARTICIPANT s'il n'existe pas
            roleRepository.findByLibelleRole(Role.LibelleRole.PARTICIPANT)
                .ifPresentOrElse(
                    role -> {}, // Le rôle existe déjà
                    () -> {
                        Role participantRole = Role.builder()
                                .libelleRole(Role.LibelleRole.PARTICIPANT)
                                .permissions(Set.of(
                                        Role.Permission.LECTURE
                                ))
                                .build();
                        roleRepository.save(participantRole);
                    }
                );
        };
    }
} 
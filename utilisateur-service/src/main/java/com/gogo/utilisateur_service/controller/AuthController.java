package com.gogo.utilisateur_service.controller;

import com.gogo.notification_service.dto.UserDto;
import com.gogo.utilisateur_service.config.JwtUtil;
import com.gogo.utilisateur_service.dto.*;
import com.gogo.utilisateur_service.enums.RoleName;
import com.gogo.utilisateur_service.model.Role;
import com.gogo.utilisateur_service.model.Utilisateur;
import com.gogo.utilisateur_service.service.ProfilService;
import com.gogo.utilisateur_service.service.RoleService;
import com.gogo.utilisateur_service.service.UtilisateurService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UtilisateurService utilisateurService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final ProfilService profilService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil,
                          UtilisateurService utilisateurService,
                          RoleService roleService,
                          PasswordEncoder passwordEncoder, ProfilService profilService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.utilisateurService = utilisateurService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.profilService = profilService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            String token = jwtUtil.generateToken(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

            JwtResponse response = new JwtResponse(token, userDetails.getUsername(), roles);
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            System.out.println("❌ Échec de connexion pour l'utilisateur : " + request.getUsername());
            throw e; // L'exception sera captée par le @ControllerAdvice
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        // 🔹 Vérifier si l'email est déjà utilisé
        if (utilisateurService.existsByEmail(request.getEmail())) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "⚠️ Un compte est déjà associé à cet email : " + request.getEmail());
            response.put("suggestion", "Veuillez vous connecter ou réinitialiser votre mot de passe.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }

        // 🔹 Vérifier si le username est déjà pris
        if (utilisateurService.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body(Map.of("message", "⚠️ Nom d'utilisateur déjà pris"));
        }

        // 🔹 Déterminer le rôle
        String roleName = (request.getRole() == null || request.getRole().isBlank())
                ? "CANDIDAT"
                : request.getRole().toUpperCase();

        if (roleName.equals("ADMIN")) {
            return ResponseEntity.status(403)
                    .body(Map.of("message", "❌ Vous ne pouvez pas créer un ADMIN via cette route"));
        }

        Role role = roleService.findByRole(RoleName.valueOf(roleName))
                .orElseThrow(() -> new RuntimeException("Rôle invalide : " + roleName));

        // 🔹 Créer le nouvel utilisateur
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setUsername(request.getUsername());
        utilisateur.setEmail(request.getEmail());
        utilisateur.setPassword(passwordEncoder.encode(request.getPassword()));
        utilisateur.setRoles(List.of(role));

        utilisateurService.saveUtilisateur(utilisateur);

        Map<String, String> response = new HashMap<>();
        response.put("message", "✅ Utilisateur enregistré avec le rôle " + roleName);
        response.put("email", utilisateur.getEmail());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<Object> getMyProfile(Authentication authentication) {
        String username = authentication.getName();
        String role = profilService.getUserRole(username);

        switch (role.toUpperCase()) {
            case "CANDIDAT" -> {
                CandidatProfileDTO dto = profilService.getCandidatProfile(username);
                dto.setRole("CANDIDAT"); // Très important !
                return ResponseEntity.ok(dto);
            }
            case "RECRUTEUR" -> {
                RecruteurProfileDTO dto = profilService.getRecruteurProfile(username);
                dto.setRole("RECRUTEUR");
                return ResponseEntity.ok(dto);
            }
            case "EMPLOYEUR" -> {
                EmployeurProfileDTO dto = profilService.getEmployeurProfile(username);
                dto.setRole("EMPLOYEUR");
                return ResponseEntity.ok(dto);
            }
            case "ADMIN" -> {
                AdminProfileDTO dto = profilService.getAdminProfile(username);
                dto.setRole("ADMIN");
                return ResponseEntity.ok(dto);
            }
            default -> {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Rôle non reconnu : " + role);
            }
        }
    }

    @GetMapping("/by-email/{email}")
    public Optional<UserDto> getByEmail(@PathVariable("email") String email) {
        Optional<UserDto> userOpt = utilisateurService.getByEmail(email);

        if (userOpt.isPresent()) {
            System.out.println("🔹 Utilisateur trouvé pour email=" + email + ", id=" + userOpt.get().getId());
        } else {
            System.out.println("⚠️ Aucun utilisateur trouvé pour email=" + email);
        }

        return userOpt;
    }



}

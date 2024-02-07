package org.techlab.labxpert.service.serviceImp;


import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.techlab.labxpert.config.JwtUtil;
import org.techlab.labxpert.dtos.UtilisateurDTO;
import org.techlab.labxpert.entity.Utilisateur;
import org.techlab.labxpert.repository.UtilisateurRepository;
import org.techlab.labxpert.service.I_Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@ComponentScan(basePackages = "com.labtech.labxpert")
public class UtilisateurServiceImp implements I_Utilisateur {

    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    ModelMapper modelMapper;
    @Override
    public UtilisateurDTO addUser(UtilisateurDTO userdto) {
        Utilisateur user=utilisateurRepository.save(modelMapper.map(userdto,Utilisateur.class));
        user.setPassword("********");
        return modelMapper.map(user,UtilisateurDTO.class);
    }
    @Override
    public List<UtilisateurDTO> showUsers() {
        List<Utilisateur> users=utilisateurRepository.findByDeletedFalse();
        return users.stream().map(user->{
            UtilisateurDTO utilisateurDTO=modelMapper.map(user,UtilisateurDTO.class);
            utilisateurDTO.setPassword("**********");
            return utilisateurDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public UtilisateurDTO showUserwithid(Long id) {
        Utilisateur utilisateur =utilisateurRepository.findById(id).get();
        utilisateur.setPassword("********");
        return modelMapper.map(utilisateur,UtilisateurDTO.class);
    }

    @Override
    public UserDetails authentification(String username) {
        Utilisateur user=utilisateurRepository.findUtilisateurByNomUtilisateur(username);
        System.out.println(user);
        UserDetails userDetails= new User(user.getNomUtilisateur(),user.getPassword(),new ArrayList<>());
        return userDetails;
    }

    @Override
    public UtilisateurDTO modUser(UtilisateurDTO userdto) {
        Utilisateur user=utilisateurRepository.save(modelMapper.map(userdto,Utilisateur.class));
        user.setPassword("********");
        return modelMapper.map(user,UtilisateurDTO.class);
    }

    @Override
    public boolean delUser(UtilisateurDTO userdto) {
        userdto.setDeleted(true);
        Utilisateur user=utilisateurRepository.save(modelMapper.map(userdto,Utilisateur.class));
        return user.isDeleted();
    }




}

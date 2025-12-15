package com.blacksystem.system.services.pets;

import com.blacksystem.system.models.Pet;
import com.blacksystem.system.models.User;
import com.blacksystem.system.models.dto.PetRequest;
import com.blacksystem.system.repositorys.pets.PetRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class PetService {

    private final PetRepository petRepository;
    private final Cloudinary cloudinary;

    public PetService(PetRepository petRepository, Cloudinary cloudinary) {
        this.petRepository = petRepository;
        this.cloudinary = cloudinary;
    }

    // 🐾 REGISTRAR MASCOTA
    public Pet registerPet(User user, PetRequest req, MultipartFile photo) {

        Pet pet = new Pet();
        pet.setName(req.getName());
        pet.setAge(req.getAge());
        pet.setWeight(req.getWeight());
        pet.setSpecies(req.getSpecies());           // Gato | Perro | Otro
        pet.setOtherSpecies(req.getOtherSpecies()); // solo si es "Otro"
        pet.setGender(req.getGender());             // Masculino | Femenino
        pet.setColor(req.getColor());
        pet.setAdoptionDate(LocalDate.parse(req.getAdoptionDate()));
        pet.setSterilized(req.isSterilized());
        pet.setOwner(user);

        // 📸 SUBIR IMAGEN A CLOUDINARY
        if (photo != null && !photo.isEmpty()) {
            try {
                Map uploadResult = cloudinary.uploader().upload(
                        photo.getBytes(),
                        ObjectUtils.asMap(
                                "folder", "pets",
                                "resource_type", "image"
                        )
                );
                pet.setImageUrl(uploadResult.get("secure_url").toString());
            } catch (Exception e) {
                throw new RuntimeException("Error uploading pet image", e);
            }
        }

        return petRepository.save(pet);
    }

    // 🐾 OBTENER MASCOTAS DEL USUARIO
    public List<Pet> getPetsByUser(User user) {
        return petRepository.findByOwner(user);
    }
}


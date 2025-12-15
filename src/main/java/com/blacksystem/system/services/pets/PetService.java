package com.blacksystem.system.services.pets;

import com.blacksystem.system.models.Pet;
import com.blacksystem.system.models.User;
import com.blacksystem.system.models.dto.PetRequest;
import com.blacksystem.system.repositorys.pets.PetRepository;
import com.blacksystem.system.services.CloudinaryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Service
public class PetService {

    private final PetRepository petRepository;
    private final CloudinaryService cloudinaryService;

    public PetService(PetRepository petRepository,
                      CloudinaryService cloudinaryService) {
        this.petRepository = petRepository;
        this.cloudinaryService = cloudinaryService;
    }

    public Pet registerPet(User user, PetRequest req, MultipartFile photo) {

        Pet pet = new Pet();
        pet.setName(req.getName());
        pet.setAge(req.getAge());
        pet.setWeight(req.getWeight());
        pet.setGender(req.getGender());
        pet.setColor(req.getColor());
        pet.setAdoptionDate(LocalDate.parse(req.getAdoptionDate()));
        pet.setSterilized(req.isSterilized());
        pet.setOwner(user);

        // 🔥 NORMALIZAR ESPECIE
        if (!"Gato".equalsIgnoreCase(req.getSpecies())
                && !"Perro".equalsIgnoreCase(req.getSpecies())) {

            pet.setSpecies("Otro");
            pet.setOtherSpecies(req.getOtherSpecies());
        } else {
            pet.setSpecies(req.getSpecies());
            pet.setOtherSpecies(null);
        }

        // 📸 SUBIR IMAGEN A CLOUDINARY
        if (photo != null && !photo.isEmpty()) {
            String imageUrl = cloudinaryService.uploadImage(photo, "pets");
            pet.setImageUrl(imageUrl);
        }

        return petRepository.save(pet);
    }

    public List<Pet> getPetsByUser(User user) {
        return petRepository.findByOwner(user);
    }
}

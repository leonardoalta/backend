package com.blacksystem.system.services.schudeler;

import com.blacksystem.system.models.AnnualVaccine;
import com.blacksystem.system.models.User;
import com.blacksystem.system.repositorys.vaccines.AnnualVaccineRepository;
import com.blacksystem.system.services.email.EmailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class VaccineReminderScheduler {

    private final AnnualVaccineRepository vaccineRepository;
    private final EmailService emailService;

    public VaccineReminderScheduler(
            AnnualVaccineRepository vaccineRepository,
            EmailService emailService
    ) {
        this.vaccineRepository = vaccineRepository;
        this.emailService = emailService;
    }

    /**
     * ⏰ Se ejecuta cada día (8 AM)
     */
    @Transactional
    @Scheduled(cron = "0 0 8 * * *")
    public void sendVaccineReminders() {

        LocalDate today = LocalDate.now();

        // 🔥 Solo vacunas con recordatorio ACTIVADO y fecha = HOY
        List<AnnualVaccine> vaccines =
                vaccineRepository.findByWantsRemindersTrueAndNextVaccineDate(today);

        for (AnnualVaccine v : vaccines) {

            User user = v.getPet().getOwner();

            String subject = "💉 Recordatorio de vacuna veterinaria";

            String body = """
                    Hola %s,

                    Te recordamos que tu mascota 🐾 tiene una vacuna programada.

                    🐶 Mascota: %s
                    💉 Vacuna: %s
                    📅 Fecha programada: %s
                    🧑‍⚕️ Veterinario: %s

                    — Pet Health Record
                    """.formatted(
                    user.getNameUser(),
                    v.getPet().getName(),
                    v.getName(),
                    v.getNextVaccineDate(),
                    v.getVetName()
            );

            emailService.sendEmail(
                    user.getEmailUser(),
                    subject,
                    body
            );
        }
    }
}

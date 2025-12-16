package com.blacksystem.system.services.schudeler;

import com.blacksystem.system.models.Treatment;
import com.blacksystem.system.models.User;
import com.blacksystem.system.services.email.EmailService;
import com.blacksystem.system.repositorys.TreatmentRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class TreatmentReminderScheduler {

    private final TreatmentRepository treatmentRepository;
    private final EmailService emailService;

    public TreatmentReminderScheduler(
            TreatmentRepository treatmentRepository,
            EmailService emailService
    ) {
        this.treatmentRepository = treatmentRepository;
        this.emailService = emailService;
    }

    /**
     * ⏰ Se ejecuta cada hora
     */
    @Scheduled(fixedRate = 120000) // cada 1 minuto
    public void sendTreatmentReminders() {

        List<Treatment> treatments =
                treatmentRepository.findByWantsRemindersTrue();

        LocalDate today = LocalDate.now();

        for (Treatment t : treatments) {

            long daysFromStart =
                    ChronoUnit.DAYS.between(t.getStartDate(), today);

            // ⛔ Aún no empieza o ya terminó
            if (daysFromStart < 0 || daysFromStart >= t.getDurationDays()) {
                continue;
            }

            User user = t.getPet().getOwner();

            String subject = "💊 Recordatorio de tratamiento veterinario";

            String body =   """
                    Hola %s,

                    Este es un recordatorio para el tratamiento de tu mascota 🐶

                    🐾 Mascota: %s
                    💊 Medicamento: %s
                    📅 Día %d de %d
                    ⏱ Intervalo: cada %d horas
                    🩺 Vía: %s

                    No olvides administrar la dosis indicada.

                    — Pet Health Record
                    """.formatted(
                    user.getNameUser(),
                    t.getPet().getName(),
                    t.getMedicineName(),
                    daysFromStart + 1,
                    t.getDurationDays(),
                    t.getIntervalHours(),
                    t.getAdministration()
            );

            emailService.sendEmail(
                    user.getEmailUser(),
                    subject,
                    body
            );
        }
    }
}

package com.blacksystem.system.services.schudeler;

import com.blacksystem.system.models.VetVisit;
import com.blacksystem.system.models.User;
import com.blacksystem.system.repositorys.VetVisitRepository;
import com.blacksystem.system.services.email.EmailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class VetVisitReminderScheduler {

    private final VetVisitRepository vetVisitRepository;
    private final EmailService emailService;

    public VetVisitReminderScheduler(
            VetVisitRepository vetVisitRepository,
            EmailService emailService
    ) {
        this.vetVisitRepository = vetVisitRepository;
        this.emailService = emailService;
    }

    /**
     * ⏰ PRODUCCIÓN → una vez al día (8 AM)
     */
    @Transactional
   // @Scheduled(cron = "0 0 8 * * *")
    @Scheduled(cron = "0 */2 * * * *")

    public void sendVetVisitReminders() {

        String today = LocalDate.now().toString(); // yyyy-MM-dd

        List<VetVisit> visits =
                vetVisitRepository.findByWantsRemindersTrueAndNextVisitDate(today);

        for (VetVisit v : visits) {

            User user = v.getPet().getOwner();

            String subject = "🩺 Recordatorio de cita veterinaria";

            String body = """
                    Hola %s,

                    Te recordamos que hoy tienes una cita veterinaria 🐾

                    🐶 Mascota: %s
                    📅 Fecha: %s
                    🏥 Clínica: %s
                    👨‍⚕️ Veterinario: %s
                    📝 Motivo: %s

                    — Pet Health Record
                    """.formatted(
                    user.getNameUser(),
                    v.getPet().getName(),
                    v.getNextVisitDate(),
                    v.getClinic(),
                    v.getVetName(),
                    v.getReason()
            );

            emailService.sendEmail(
                    user.getEmailUser(),
                    subject,
                    body
            );
        }
    }

    /**
     * 🧪 PRUEBAS → cada 2 minutos
     * (COMENTA el cron de arriba y usa este)
     */
    // @Scheduled(cron = "0 */2 * * * *")
}

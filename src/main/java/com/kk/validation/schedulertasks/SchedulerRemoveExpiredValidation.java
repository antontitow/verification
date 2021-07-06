package com.kk.validation.schedulertasks;

import com.kk.validation.domain.Verification;
import com.kk.validation.repository.VerificationRepo;
import com.kk.validation.service.SQLiteSrv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class SchedulerRemoveExpiredValidation {
    // Date format
    private static final String formatDate = "dd.MM.yyyy";
    // Token lifetime
    private static final int lifeTime = 7;
    private final VerificationRepo verificationEntity;
    private final SQLiteSrv sqLite;

    @Autowired
    public SchedulerRemoveExpiredValidation(VerificationRepo verificationEntity, SQLiteSrv sqLite) {
        this.verificationEntity = verificationEntity;
        this.sqLite = sqLite;
    }

    /**
     * checkExpire very day on 5:00
     */
    @ConditionalOnProperty(value = "scheduling.enabled", havingValue = "true", matchIfMissing = true)
    @Scheduled(cron = "0 0 5 * * *")
   // @Scheduled(fixedRate = 60000)
    public void checkExpire() throws ParseException {
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate);
        Date nowFormat = simpleDateFormat.parse(simpleDateFormat.format(now));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowFormat);
        calendar.roll(Calendar.DAY_OF_MONTH, lifeTime);
        Date PrevWeek = calendar.getTime();
        List<Verification> listverifications = verificationEntity.findByExpire();

        listverifications.forEach(
                (s) -> {
                    try {
                        Date verificationCreateDate = simpleDateFormat.parse(s.getDateCreate());
                        if (verificationCreateDate.before(PrevWeek)) {
                            s.setExpire();
                            verificationEntity.save(s);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                });
    }
}

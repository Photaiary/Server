package com.photaiary.Photaiary.report.service;

import com.photaiary.Photaiary.post.daily.entity.Daily;
import com.photaiary.Photaiary.post.daily.repository.DailyReposiotry;
import com.photaiary.Photaiary.post.photo.controller.exception.custom.VoException;
import com.photaiary.Photaiary.post.photo.entity.Photo;
import com.photaiary.Photaiary.post.photo.repository.PhotoRepository;
import com.photaiary.Photaiary.report.dto.ReportMostDto;
import com.photaiary.Photaiary.report.dto.ReportResponse;
import com.photaiary.Photaiary.user.entity.User;
import com.photaiary.Photaiary.user.entity.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final UserRepository userRepository;
    private final PhotoRepository photoRepository;
    private final DailyReposiotry dailyReposiotry;
    private final List<String> months = Arrays.asList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
    private final List<String> koreanMonth = Arrays.asList("해오름달", "시셈달", "물오름달", "잎새달", "푸름달", "누리달", "견우직녀달", "타오름달", "열매달", "하늘연달","미름달" ,"매듭달");
    @Transactional
    public ReportResponse getReport() throws Exception {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            Optional<User> user = userRepository.findByEmail(auth.getName());
            List<Daily> dailies = dailyReposiotry.findAll().stream().filter(h -> h.getUser().getEmail().equals(user.get().getEmail())).collect(Collectors.toList());
            List<Photo> photos = photoRepository.findAll().stream().filter(h -> dailies.contains(h.getDaily())).collect(Collectors.toList());

            ReportResponse reportResponse = new ReportResponse();
            System.out.println(reportResponse.getResult() == null);
            System.out.println(reportResponse.getResult().isEmpty());
            Set<String> years = dailies.stream().map(h -> h.getDailyValue().substring(0, 4)).collect(Collectors.toSet());
            for (String year : years) {
                List<Photo> tempPhoto = photos.stream().filter(p->p.getDaily()
                        .getDailyValue().substring(0,4).equals(year))
                        .collect(Collectors.toList());

                List<String> monthList = tempPhoto.stream().map(p -> p.getDaily().getDailyValue().substring(4,6)).collect(Collectors.toList());

                Integer mostMonth = 1;
                Integer mostCount = 0;
                for(String month: months){
                    if (mostCount < Collections.frequency(monthList, month)) {
                        mostCount = Collections.frequency(monthList, month);
                        mostMonth = Integer.parseInt(month);
                    }
                }

                ReportMostDto reportMostDto = new ReportMostDto();
                reportMostDto.setMost("부산");
                reportMostDto.setPhotoCount((long)tempPhoto.size());
                reportMostDto.setMonth(mostMonth);
                reportMostDto.setComment("`${userName}은 " + koreanMonth.get(mostMonth-1) +"의 여행가 입니다.");
                reportMostDto.setTop3("['자연','핫플','별곡']");
                reportResponse.getResult().add(Map.of(year, reportMostDto));
            }
            return reportResponse;
        } catch (Exception e) {
            throw new VoException(e.getMessage() + "yoyo");
        }
    }
}

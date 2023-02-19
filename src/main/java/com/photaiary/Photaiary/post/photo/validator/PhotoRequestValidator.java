package com.photaiary.Photaiary.post.photo.validator;

import com.photaiary.Photaiary.post.photo.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Component
@RequiredArgsConstructor
public class PhotoRequestValidator implements Validator {
    private final PhotoRepository photoRepository;
    private static final String BASE_PATTERN ="^(\\[)[a-zA-Z](\\])*";
    private static final String FIND_PATTERN = "\\('\\D\\D'\\)";
    private static final String NO_TAG = "EMPTY";

    @Override
    public Boolean validatePhotoId(@NotNull Long photoId) {
        return photoRepository.existsById(photoId);
    }

    @Override
    public Boolean validateTag(String tag) {
        return Pattern.matches(BASE_PATTERN, tag) && Pattern.matches(FIND_PATTERN, tag);
    }

    public List<String> getTagOrNull(String tag) {
        if (validateTag(tag)) {
            Pattern pattern = Pattern.compile(tag);
            Matcher matcher = pattern.matcher(FIND_PATTERN);
            List<String> result = new ArrayList<>();
            while (matcher.find()) {
                result.add(matcher.group());
            }
            return result;
        }
        return List.of(null);
    }

    public String getStringTag(String tag) {
        if(tag.equals(NO_TAG))
            return "000000000";
//        return 로직
        return "0101010";
    }
}
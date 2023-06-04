package jpabook.archiveB.base;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class FileStore {

    public String saveFile(MultipartFile file) throws IOException {
        if(file.isEmpty()) {
            return "no-file 이미지";
        }
        //오늘날짜 추출
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter =
                DateTimeFormatter.ofPattern("yyyyMMdd");
        String current_date = now.format(dateTimeFormatter);

        //파일 저장 절대 경로
        String absolutePath = new File("").getAbsolutePath()
                + File.separator + File.separator;

        //파일 세부 경로 지정
        String path = absolutePath + "files" + File.separator + current_date;

        //디렉토리가 존재하지 않으면 mkdirs로 생성
        if (!new File(path).exists()) {
            try {
                new File(path).mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        // 파일의 확장자 추출
        String originalFileExtension;
        String contentType = StringUtils.getFilenameExtension((file.getOriginalFilename()));
        originalFileExtension = "." + contentType;

        // 파일명 중복 피하고자 나노초까지 얻어와 지정
        String new_file_name = System.nanoTime() + originalFileExtension;


        //업로드 한 파일 데이터를 지정한 파일에 저장
        File newFile = new File(path + File.separator + new_file_name);
        file.transferTo(newFile);

        // 파일 권한 설정(쓰기, 읽기)
        newFile.setWritable(true);
        newFile.setReadable(true);

        return current_date + File.separator +new_file_name;
    }

    public boolean deleteFile(String filePath) throws IOException {
        File file = new File("C:/Users/tnals/study/archiveB/files/"+filePath);
        boolean result = file.delete();

        return result;
    }

}

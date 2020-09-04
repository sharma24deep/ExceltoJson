package com.deepanshu.ExcelToJson.Service;

import com.deepanshu.ExcelToJson.Utility.UploadUtility;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class UploadService {

    private final UploadUtility uploadUtil;

    public UploadService(UploadUtility uploadUtil) {
        this.uploadUtil = uploadUtil;
    }

    public void upload(MultipartFile file) throws Exception {

        Path tempDir = Files.createTempDirectory("");

        File tempFile = tempDir.resolve(file.getOriginalFilename()).toFile();

        file.transferTo(tempFile);

        Workbook workbook = WorkbookFactory.create(tempFile);

        Sheet sheet = workbook.getSheetAt(0);

        Stream<Row> rowStream = StreamSupport.stream(sheet.spliterator(), false);
rowStream.forEach(row->{
    Stream<Cell> cellStream=StreamSupport.stream(row.spliterator(),false);

    List<String> cellVals=cellStream.map(cell -> {
        String cellVal=cell.getStringCellValue();
        return cellVal;
            }).collect(Collectors.toList());

System.out.println(cellVals);
});

    }
}

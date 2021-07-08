package pl.vida.code.poc.domain.feed.handler.csv;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.springframework.stereotype.Component;
import pl.vida.code.poc.domain.exception.DomainException;
import pl.vida.code.poc.domain.feed.FeedContentParsed;
import pl.vida.code.poc.domain.feed.FeedContentColumn;
import pl.vida.code.poc.domain.feed.handler.ExtensionHandler;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvHandler implements ExtensionHandler {
    @Override
    public FeedContentParsed toFeed(String fileContent, String originalFilename, String feedId) {
        System.out.println("Starting csv, parsing " + originalFilename + ", length: " + fileContent.length());
        String[] columns;
        List<String[]> rowsWithContent;
        CSVParser csvParser = new CSVParserBuilder().withSeparator(',').build();
        try(CSVReader reader = new CSVReaderBuilder(
                new StringReader(fileContent))
                .withCSVParser(csvParser)
                .build()){
            columns = reader.readNext();
            rowsWithContent = reader.readAll();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
            throw new DomainException("Error occurred when started parsing file.");
        }
        // TODO: 17.06.2021 probably merida polska csv has bad format at last line
        rowsWithContent.remove(291);
        return parseContentToFeedContentParsed(columns, rowsWithContent, feedId);
    }

    private FeedContentParsed parseContentToFeedContentParsed(String[] columns, List<String[]> rowsWithContent, String feedId) {
        List<FeedContentColumn> feedContentColumns = new ArrayList<>();
        for(int columnIndex = 0; columnIndex < columns.length - 1; columnIndex++) {
            FeedContentColumn feedContentColumn = new FeedContentColumn(columns[columnIndex]);
            int rowIndex = 0;
            for(String[] rowWithContent: rowsWithContent) {
                feedContentColumn.addContent(rowIndex++, rowWithContent[columnIndex]);
            }
            feedContentColumns.add(feedContentColumn);
        }
        return new FeedContentParsed(feedId, feedContentColumns);
    }

}

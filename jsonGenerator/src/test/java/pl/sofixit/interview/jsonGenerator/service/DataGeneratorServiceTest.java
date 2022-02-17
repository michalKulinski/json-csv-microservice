package pl.sofixit.interview.jsonGenerator.service;

import org.junit.jupiter.api.Test;
import pl.sofixit.interview.jsonGenerator.model.FlattenData;
import pl.sofixit.interview.jsonGenerator.model.GeneratedData;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DataGeneratorServiceTest {

    DataGeneratorService dataGeneratorService;

    @Test
    void generateData(){

        //given
        String size = "3";
        dataGeneratorService = new DataGeneratorService();
        //when
        List<GeneratedData> generatedDataList = dataGeneratorService.generateData(size);

        //then
        assertEquals(3, generatedDataList.size());
    }

    @Test
    void generateFlattenData(){

        //given
        String size = "3";
        dataGeneratorService = new DataGeneratorService();
        //when
        List<FlattenData> generatedDataList = dataGeneratorService.generateFlattenData(size);

        //then
        assertEquals(3, generatedDataList.size());
    }

    @Test
    void generateFlattenDataThrowExceptionForDummySize(){

        //given
        String size = "dummy";
        dataGeneratorService = new DataGeneratorService();

        //when
        //then
        assertThatThrownBy(
                () -> dataGeneratorService.generateFlattenData(size))
                .isInstanceOf(NumberFormatException.class);


    }

}
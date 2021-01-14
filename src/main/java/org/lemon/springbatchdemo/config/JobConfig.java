package org.lemon.springbatchdemo.config;

import lombok.extern.slf4j.Slf4j;
import org.lemon.springbatchdemo.model.City;
import org.lemon.springbatchdemo.model.Location;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.util.List;

@Configuration
@Slf4j
public class JobConfig {

    @Bean
    public FlatFileItemReader<City> reader() {
        return new FlatFileItemReaderBuilder<City>()
                .name("personItemReader")
                .linesToSkip(1)
                .resource(new ClassPathResource("sample-data.csv"))
                .delimited()
                .names("name", "geoId")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<City>() {{
                    setTargetType(City.class);
                }})
                .build();
    }

    @Bean
    public CityItemProcessor processor() {
        return new CityItemProcessor();
    }

    @Bean
    public ItemWriter<Location> writer() {
        return list -> list.forEach(item -> log.info(item.toString()));
    }

    @Bean
    public Job importUserJob(JobBuilderFactory jobBuilders,
                             StepBuilderFactory stepBuilders) {
        return jobBuilders.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1(stepBuilders))
                .end()
                .build();
    }

    @Bean
    public Step step1(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("step1")
                .<City, Location> chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }
}

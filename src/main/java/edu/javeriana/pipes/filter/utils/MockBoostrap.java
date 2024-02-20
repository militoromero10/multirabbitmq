package edu.javeriana.pipes.filter.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.stream.Stream;

@Profile("tweet-generator")
@Component
public class MockBoostrap implements CommandLineRunner {

    @Value("${TWEET_SIZE:100}")
    private Integer size;

    @Override
    public void run(String... args) throws Exception {
        var tags = new String[]{"learning", "ia", "maths", "ml", "machinelearning", "data", "python", "java", "golang"};
        var random = new Random();

        try (var writer = new BufferedWriter(new FileWriter("file.in"))) {
            Stream.iterate(0, n -> n + 1)
                    .limit(size)
                    .forEach(i -> {
                        try {
                            writer.write(String.format("Tweet %d: text #%s", i, tags[random.nextInt(tags.length)]));
                            writer.newLine();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }


}

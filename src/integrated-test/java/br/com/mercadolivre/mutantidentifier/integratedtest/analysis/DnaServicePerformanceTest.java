package br.com.mercadolivre.mutantidentifier.integratedtest.analysis;

import br.com.mercadolivre.mutantidentifier.analysis.DnaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;
import java.util.stream.IntStream;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DnaServicePerformanceTest {

    private final Random random = new Random();
    private final String wrongGenes = "BDEFHIJKL";

    @Autowired
    private DnaService dnaService;

    @Test
    public void isMutant() {
        final int size = 40000;
        final String[] dna = new String[size];

        final long currentMillis = System.currentTimeMillis();
        IntStream.rangeClosed(1, size).forEach(l -> {

            final StringBuilder sb = new StringBuilder(size);
            IntStream.rangeClosed(1, size).forEach(c -> {
                final String gene = (l == c && c > size - 4) ? "A" : generateFromWrong();
                sb.append(gene);
            });

            String line = sb.toString();
            if (l == size) {
                line = "TTTT" + line.substring(4);
            }
            dna[l - 1] = line;

            if (l % 10000 == 0) {
                System.out.println(l + " linhas ciradas em " + (System.currentTimeMillis() - currentMillis) + "ms");
            }

        });

        System.out.println("Terminou em " + (System.currentTimeMillis() - currentMillis)/1000 + "s");

        final boolean isMutant = dnaService.isMutant(dna);

        assertTrue(isMutant);
    }

    private String generateFromWrong() {
        final int idx = random.nextInt(wrongGenes.length());
        return String.valueOf(wrongGenes.charAt(idx));
    }

}

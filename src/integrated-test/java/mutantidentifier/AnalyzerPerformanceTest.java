package mutantidentifier;

import br.com.mercadolivre.mutantidentifier.analyzers.DnaService;
import org.junit.Test;

import java.util.Random;
import java.util.stream.IntStream;

public class AnalyzerPerformanceTest {

    private final Random random = new Random();
    private final String wrongGenes = "BDEFHIJKL";

    @Test
    public void isMutantInOblique() {
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

        final boolean isMutant = new DnaService().isMutant(dna);
        System.out.println(isMutant);
    }

    private String generateFromWrong() {
        final int idx = random.nextInt(wrongGenes.length());
        return String.valueOf(wrongGenes.charAt(idx));
    }

}

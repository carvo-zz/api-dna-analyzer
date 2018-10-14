package br.com.mercadolivre.mutantidentifier.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Stats {

    @JsonProperty("count_mutant_dna")
    private long countMutant;

    @JsonProperty("count_human_dna")
    private long countHuman;

    private double ratio;

    public Stats(long countMutant, long countHuman) {
        this.countMutant = countMutant;
        this.countHuman = countHuman;
        this.ratio = calculateRatio(countMutant, countHuman);
    }

    private static double calculateRatio(long countMutant, long countHuman) {
        return countHuman == 0 ? 0
                : BigDecimal.valueOf(countMutant / (double) countHuman)
                .setScale(2, RoundingMode.HALF_EVEN)
                .doubleValue();
    }

//    public static void main(String[] args) {
//        long a = 1243;
//        long b = 604;
//        System.out.println(calculateRatio(0, 1));
//    }

    public long getCountMutant() {
        return countMutant;
    }

    public long getCountHuman() {
        return countHuman;
    }

    public double getRatio() {
        return ratio;
    }

}

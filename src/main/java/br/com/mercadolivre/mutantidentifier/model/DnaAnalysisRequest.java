package br.com.mercadolivre.mutantidentifier.model;

public class DnaAnalysisRequest {
    private String[] dna;

    public DnaAnalysisRequest() {

    }

    public DnaAnalysisRequest(String[] dna) {
        this.dna = dna;
    }

    public String[] getDna() {
        return dna;
    }

    public void setDna(String[] dna) {
        this.dna = dna;
    }
}

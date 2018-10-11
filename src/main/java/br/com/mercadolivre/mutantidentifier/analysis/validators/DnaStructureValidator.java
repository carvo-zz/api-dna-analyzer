package br.com.mercadolivre.mutantidentifier.analysis.validators;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DnaStructureValidator {

    public static final String MSG_NULL_OR_EMPTY = "DNA is required";
    public static final String MSG_NOT_SQUARE = "should be square matrix";

    public Optional<String> validate(String[] dna) {
        Optional<String> validation = isNotNull(dna);

        if (!validation.isPresent()) {
            validation = isSquareMatrix(dna);
        }

        return validation;
    }

    public Optional<String> isNotNull(String[] dna) {
        return Optional.ofNullable(dna)
                .filter(d -> d.length > 0)
                .map(d -> Optional.<String>empty())
                .orElse(Optional.of(MSG_NULL_OR_EMPTY));
    }

    public Optional<String> isSquareMatrix(String[] dna) {
        final int dim = dna.length;
        int i = 0;
        boolean isValid = Boolean.TRUE;

        while (isValid && i < dim) {
            isValid = dna[i].length() == dim;
            i++;
        }

        return isValid ? Optional.empty() : Optional.of(MSG_NOT_SQUARE);
    }
}

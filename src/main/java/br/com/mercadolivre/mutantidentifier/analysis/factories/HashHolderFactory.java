package br.com.mercadolivre.mutantidentifier.analysis.factories;

import br.com.mercadolivre.mutantidentifier.analysis.helpers.HashHolder;
import org.springframework.stereotype.Component;

@Component
public class HashHolderFactory {

    public HashHolder createHashHolder(final int matrixDim, final int bufferSize) {
        return new HashHolder(matrixDim, bufferSize);
    }
}

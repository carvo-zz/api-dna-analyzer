package br.com.mercadolivre.mutantidentifier.analysis.helpers;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HashHolder {
    private static final Logger LOG = LoggerFactory.getLogger(HashHolder.class);

    private static final int LINES_FOR_HASH = 1000;

    private final int matrixDim;
    private int computedLines = 0;
    private int totalComputedLines = 0;

    private String hash = "";
    private StringBuilder lines;

    public HashHolder(final int matrixDim) {
        this.matrixDim = matrixDim;
        this.lines = iniBuilder();
    }

    public void computeLine(final String line) {
        ++computedLines;
        ++totalComputedLines;

        lines.append(line);
        if (shouldGenerateHash()) {
            final String toHash = lines.toString() + hash;
            this.hash = DigestUtils.sha256Hex(toHash);
        }
    }

    private boolean shouldGenerateHash() {
        return computedLines == matrixDim || computedLines % LINES_FOR_HASH == 0;
    }

    private StringBuilder iniBuilder() {
        return new StringBuilder(matrixDim * LINES_FOR_HASH);
    }

    public String getHash() {
        if (computedLines == 0 || computedLines != matrixDim - 1) {
            // TODO warning, error, exception ?
        }
        LOG.info("Total computed lines: {}", totalComputedLines);
        return hash;
    }
}
package br.com.mercadolivre.mutantidentifier.analysis.helpers;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HashHolder {
    private static final Logger LOG = LoggerFactory.getLogger(HashHolder.class);

    private static final String ERROR_NOT_FINISHED = "HASH request before finish total buffer. computed %s, total %s";

    private final int bufferSize;

    private final int totalLines;
    private int computedLines = 0;

    private String hash = "";
    private StringBuilder buffer;

    /**
     *
     * @param totalLines
     * @param bufferSize Warning! <strong>Different buffer size generate different hash!</strong>
     */
    public HashHolder(final int totalLines, final int bufferSize) {
        this.totalLines = totalLines;
        this.buffer = createBuilder();
        this.bufferSize = bufferSize;
    }

    public void computeLine(final String line) {
        ++computedLines;

        buffer.append(line);
        if (shouldGenerateHash()) {
            updateHash();
            this.buffer = createBuilder();
        }
    }

    private boolean updateHash() {
        final String toHash = buffer.toString() + hash;
        this.hash = DigestUtils.sha256Hex(toHash);
        return Boolean.TRUE;
    }

    private boolean shouldGenerateHash() {
        return computedLines == totalLines || computedLines % bufferSize == 0;
    }

    private StringBuilder createBuilder() {
        return new StringBuilder(totalLines * bufferSize);
    }

    public String getHash() {
        if (computedLines == 0 || computedLines != totalLines) {
            final String msg = String.format(ERROR_NOT_FINISHED, computedLines, totalLines);
            LOG.error(msg);
            throw new RuntimeException(msg);
        }
        return hash;
    }
}